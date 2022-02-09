<template>
  <div v-if="tabsIndex === 1" class="customer-all sale-analysis">
    <!-- :btns-params="[ '销售金额', '合同数量' ]" -->
    <Search :show-btns="true" :index-btns="showChartsIndex.index" @sendBtnIndexToCustomer="getBtnIndexBySearch" @searchFormData="getSearchData" />
    <!-- <saleAnalysisChart v-if="showChartsIndex.item === '销售金额'" :show-charts-index="showChartsIndex" :search="search" /> -->
    <!-- <saleLineContractChart v-if="showChartsIndex.item === '合同数量'" :show-charts-index="showChartsIndex" :search="search" /> -->
    <saleAnalysisChart :show-charts-index="showChartsIndex" :search="search" />
    <!-- form+table部分 -->
    <div>
      <!-- 标题和导出 -->
      <div class="title-export">
        <div class="title">
          <h3>销售统计报表</h3>
        </div>
        <!-- <div class="export">
          <el-button class="primary-btn customer-btn" @click="exportFn">导出</el-button>
        </div> -->
      </div>
      <!-- <el-form ref="queryParams" class="cus-search-box" :model="queryParams">
        <el-form-item label="所属渠道">
          <el-select v-model="queryParams.channel">
            <el-option v-for="(channel) in departAndBelongerList[0]" :key="channel.dictValue" :label="channel.dictLabel" :value="channel.dictValue" />
          </el-select>
        </el-form-item>
        <el-form-item label="归属部门">
          <treeselect v-model="queryParams.deptId" :options="departAndBelongerList[1]" :show-count="true" placeholder="请选择归属部门" />
        </el-form-item>
        <el-form-item label="归属人">
          <el-input v-model="queryParams.createBy" placeholder="请输入归属人" />
        </el-form-item>
        <el-form-item class="search-btn wp-25">
          <el-button class="cus-search-btn" type="primary" @click="searchHandle">搜索</el-button>
          <el-button class="cus-reset-btn" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form> -->
      <!-- 新添加统计维度 -->
      <div class="title-export">
        <div class="title">
          <h3>
            统计维度
            <el-select v-model="value" placeholder="请选择" @change="dimenChange">
              <el-option v-for="item in dimenOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </h3>
        </div>
      </div>
      <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
        <div v-show="dataList && dataList.length > 0">
          <!-- @filter-change="filterChange" -->
          <!-- :filters="filterStatus" :column-key="'type'" :filter-multiple="false"  -->
          <!-- @selection-change="handleSelectionChange" -->
          <el-table class="cus-table-box" :data="dataList" stripe>
            <!-- <el-table-column type="selection" width="62" align="center" /> -->
            <el-table-column type="index" label="排行" align="center" width="100" />
            <el-table-column v-if="value === '归属部门'" label="归属部门" prop="deptName" />
            <el-table-column v-if="value === '归属渠道'" label="归属渠道" prop="channel" />
            <el-table-column v-if="value === '归属人'" label="归属人" prop="create_by" />
            <el-table-column label="合同数量" align="center" prop="num" />
            <el-table-column label="成交金额" align="center" prop="total_amount" />
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
import Search from '@/components/common/Search.vue'
import saleAnalysisChart from './saleAnalysisChart.vue'
import mixinViewModule from '@/mixins/view-module'
// import saleLineContractChart from './saleLineContractChart.vue'
// import Treeselect from '@riophae/vue-treeselect'
// import '@riophae/vue-treeselect/dist/vue-treeselect.css'
// 销售统计 ---reportSalesStatisticsList废弃 归属部门  归属渠道 归属人
import { deptStatisticsList, channelStatisticsList, ownerShipStatisticsList } from '@/api/clues/countAnalysis.js'
import dayjs from 'dayjs'

export default {
  components: {
    Search,
    saleAnalysisChart
    // saleLineContractChart,
    // Treeselect
  },
  mixins: [mixinViewModule],
  props: {
    tabsIndex: {
      type: Number,
      required: true,
      default: 1
    },
    departAndBelongerList: {
      type: Array,
      required: true,
      default: () => []
    }
  },
  data () {
    return {
      filterStatus: [{ text: '归属渠道', value: '归属渠道' }, { text: '归属部门', value: '归属部门' }, { text: '归属人', value: '归属人' }],
      mixinViewModuleOptions: {
        getListFun: deptStatisticsList
      },
      // showChartsIndex: '销售金额',
      showChartsIndex: {
        item: '销售金额',
        indexBtns: 0
      },
      queryParams: {
        beginCreateTime: dayjs().subtract(7, 'days').format('YYYY-MM-DD'),
        endCreateTime: dayjs().format('YYYY-MM-DD')
        // channel: '',
        // deptId: null,
        // owner: 0,
        // total: 0,
        // pageNum: 1,
        // pageSize: 10
        // createBy: ''
      },
      dataList: [],
      search: {},
      dataListLoading: false,
      ids: [],
      // 统计维度
      dimenOptions: [
        {
          label: '归属渠道',
          value: '归属渠道'
        },
        {
          label: '归属部门',
          value: '归属部门'
        },
        {
          label: '归属人',
          value: '归属人'
        }
      ],
      value: '归属部门'
    }
  },
  watch: {
    search () {
      this.queryParams.beginCreateTime = this.search.dateDayArr[0]
      this.queryParams.endCreateTime = this.search.dateDayArr[1]
      this.searchHandle()
    },
    tabsIndex () {
      this.tabsIndex === 1 && this.resetQuery()
    }
  },
  created () {
    this.tabsIndex === 1 && this.resetQuery()
  },
  methods: {
    // 统计维度change事件
    dimenChange (e) {
      this.value = e
      if (e === '归属部门') {
        this.mixinViewModuleOptions.getListFun = deptStatisticsList
        this.getList()
      } else if (e === '归属渠道') {
        this.mixinViewModuleOptions.getListFun = channelStatisticsList
        this.getList()
      } else {
        this.mixinViewModuleOptions.getListFun = ownerShipStatisticsList
        this.getList()
      }
    },
    // 重新定义列表请求接口
    getList () {
      this.dataListLoading = true
      this.dataList = []
      const params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
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
      // this.queryParams.channel = ''
      // this.queryParams.createBy = ''
      // this.queryParams.deptId = null
      // this.queryParams.total = 0
      // this.queryParams.pageNum = 1
      // this.queryParams.pageSize = 10
      this.searchHandle()
    },
    getSearchData (data) {
      this.search = { ...data }
    },
    filterChange (filters) {
      if (filters.type) {
        this.changeLabel = filters.type[0]
      }
      // 重新拉取接口
      this.getList()
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.ids)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/assets/styles/useAnalysisAndIndex.scss';
</style>
