<template>
  <div v-if="tabsIndex === 2" class="customer-all">
    <Search :btns-params="[ '新增线索数量', '线索转化率' ]" :show-btns="true" :index-btns="showChartsIndex.index" @sendBtnIndexToCustomer="getBtnIndexBySearch" @searchFormData="getSearchData" />
    <clueLineChart v-if="showChartsIndex.item === '新增线索数量'" :search="search" />
    <clueFunnelChart v-if="showChartsIndex.item === '线索转化率'" :search="search" />
    <!-- form+table部分 -->
    <div>
      <!-- 标题和导出 -->
      <div class="title-export">
        <div class="title">
          <h3>线索统计报表</h3>
        </div>
        <!-- <div class="export">
          <el-button class="primary-btn customer-btn" @click="exportFn">导出</el-button>
        </div> -->
      </div>
      <el-form ref="queryParams" class="cus-search-box" :model="queryParams">
        <el-form-item label="线索编号">
          <el-input v-model="queryParams.id" placeholder="请输入线索编号" />
        </el-form-item>
        <!-- <el-form-item label="客户姓名">
          <el-input v-model="queryParams.name" placeholder="请输入客户姓名" />
        </el-form-item> -->
        <el-form-item label="归属人">
          <el-input v-model="queryParams.owner" placeholder="请输入归属人" />
        </el-form-item>
        <el-form-item label="归属部门">
          <treeselect v-model="queryParams.deptId" :options="departAndBelongerList[1]" :show-count="true" placeholder="请选择归属部门" />
        </el-form-item>
        <!-- <el-form-item label="岗位">
          <el-select v-model="queryParams.post">
            <el-option v-for="(post) in departAndBelongerList[2]" :key="post.dictValue" :label="post.dictLabel" :value="post.dictValue" />
          </el-select>
        </el-form-item> -->
        <el-form-item class="search-btn">
          <el-button class="cus-search-btn" type="primary" @click="searchHandle">搜索</el-button>
          <el-button class="cus-reset-btn" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
        <div v-show="dataList && dataList.length > 0">
          <el-table class="cus-table-box" :data="dataList" stripe @selection-change="handleSelectionChange">
            <!-- <el-table-column type="selection" width="62" align="center" /> -->
            <el-table-column label="线索编号" align="center" prop="id" />
            <el-table-column label="手机号" align="center" prop="phone" min-width="100" />
            <!-- <el-table-column label="归属部门" align="center" prop="deptId" /> -->
            <el-table-column label="归属部门" align="center" prop="deptName" />
            <el-table-column label="归属人" align="center" prop="owner" />
            <el-table-column label="渠道来源" align="center" prop="channel" :formatter="channelOptionsFormat" />
            <el-table-column label="活动详情" align="center" prop="" show-overflow-tooltip>
              <template slot-scope="{ row }">
                <span>{{ row.code }}{{ row.info }}</span>
              </template>
            </el-table-column>
            <el-table-column label="线索状态" align="center" prop="clueStatus" :formatter="clueStatusOptionsFormat" />
            <el-table-column label="创建时间" sortable align="center" prop="clueCreateTime" min-width="160" />
            <el-table-column label="商机状态" align="center" prop="businessStatus" :formatter="sjFormatOptions" />
            <el-table-column label="转化商机时间" align="center" prop="businessCreateTime" min-width="100" show-overflow-tooltip />
            <el-table-column label="成交时间" align="center" prop="contractCreateTime" min-width="100" show-overflow-tooltip />
            <el-table-column label="成交金额" align="center" prop="order" />
          </el-table>
          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="pageNum"
            :limit.sync="pageSize"
            @pagination="getList"
          />
        </div>
        <empty v-show="(!dataList || dataList.length <= 0) && !dataListLoading" :box-height="645" class="table-empty-box" />
      </div>
    </div>
  </div>
</template>

<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Search from '@/components/common/Search.vue'
import clueLineChart from './clueLineChart.vue'
import clueFunnelChart from './clueFunnelChart.vue'
import mixinViewModule from '@/mixins/view-module'
// import { listClue } from '@/api/clues/clue'
// 线索统计
// import { reportCluesStatistics } from '@/api/clues/countAnalysis.js'
// 线索统计列表
import { reportCluesStatisticsList } from '@/api/clues/countAnalysis.js'
import dayjs from 'dayjs'

export default {
  components: {
    Search,
    clueLineChart,
    clueFunnelChart,
    Treeselect
  },
  mixins: [mixinViewModule],
  props: {
    tabsIndex: {
      type: Number,
      required: true,
      defalut: 2
    },
    departAndBelongerList: {
      type: Array,
      required: true,
      default: () => []
    }
  },
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: reportCluesStatisticsList
      },
      queryParams: {
        beginCreateTime: dayjs().subtract(7, 'days').format('YYYY-MM-DD'),
        endCreateTime: dayjs().format('YYYY-MM-DD'),
        id: '',
        owner: '',
        deptId: null,
        post: ''
      },
      showChartsIndex: {
        item: '新增线索数量',
        indexBtns: 0
      },
      search: {},
      dataList: [],
      dataListLoading: false,
      ids: [],
      channelOptions: [],
      // 商机状态
      statusOptions: []
    }
  },
  watch: {
    search () {
      this.queryParams.beginCreateTime = this.search.dateDayArr[0]
      this.queryParams.endCreateTime = this.search.dateDayArr[1]
      this.searchHandle()
    },
    tabsIndex () {
      this.tabsIndex === 2 && this.resetQuery()
    }
  },
  created () {
    this.tabsIndex === 2 && this.resetQuery()
    // 渠道来源
    this.getDicts('clues_item').then(response => {
      this.channelOptions = response.data
    })
    // 商机状态
    this.getDicts('clue_status').then(response => {
      this.statusOptions = response.data
    })
  },
  methods: {
    // 线索状态字典翻译
    clueStatusOptionsFormat (row) {
      switch (row.clueStatus) {
        case '1':
          return '待跟进'
        case '2':
          return '跟进中'
        case '3':
          return '回收'
        case '4':
          return '伪线索/踢回公海'
        case '5':
          return '删除'
        case '6':
          return '转换商机'
        case '7':
          return '转换客户'
        default:
          return ''
      }
    },
    // 渠道来源字典翻译
    channelOptionsFormat (row) {
      return this.selectDictLabel(this.channelOptions, row.channel)
    },
    // 商机表格转译
    sjFormatOptions (row) {
      // return this.selectDictLabel(this.statusOptions, row.channel)
      switch (row.businessStatus) {
        case '1':
          return '待跟进'
        case '2':
          return '跟进中'
        case '3':
          return '回收'
        case '4':
          return '伪线索/踢回公海'
        case '5':
          return '删除'
        case '6':
          return '转换商机'
        case '7':
          return '转换客户'
        default:
          return ''
      }
    },
    // 重新定义列表请求接口
    getList () {
      this.dataListLoading = true
      this.dataList = []
      const params = {
        pageNum: this.mixinViewModuleOptions.getDataListIsPage ? this.pageNum : null,
        pageSize: this.mixinViewModuleOptions.getDataListIsPage ? this.pageSize : null,
        ...this.queryParams
      }
      this.mixinViewModuleOptions.getListFun(params).then(res => {
        this.dataListLoading = false
        if (res.code !== 200) {
          // this.dataList = []
          this.total = 0
          return this.$message.error(res.msg)
        }
        this.dataList = res.rows
        this.total = res.total
      }).catch(() => {
        this.dataListLoading = false
      })
    },
    searchHandle () {
      this.pageNum = 1
      this.getList()
    },
    getBtnIndexBySearch (data) {
      this.showChartsIndex = data
    },
    exportFn () {
      // TODO
    },
    resetQuery () {
      // TODO
      this.queryParams.beginCreateTime = dayjs().subtract(7, 'days').format('YYYY-MM-DD')
      this.queryParams.endCreateTime = dayjs().format('YYYY-MM-DD')
      this.queryParams.id = ''
      this.queryParams.owner = ''
      this.queryParams.deptId = null
      this.queryParams.post = ''
      this.searchHandle()
    },
    getSearchData (data) {
      this.search = { ...data }
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.ids)
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
