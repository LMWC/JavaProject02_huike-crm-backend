<template>
  <div v-if="tabsIndex === 3" class="customer-all customer-page-box">
    <Search :show-btns="false" @searchFormData="getSearchData" />
    <div>
      <channelCharts :search="search" />
      <!-- form+table部分 -->
      <div>
        <!-- 标题和导出 -->
        <div class="title-export">
          <div class="title">
            <h3>渠道统计报表</h3>
          </div>
          <!-- <div class="export">
            <el-button class="primary-btn customer-btn" @click="exportFn">导出</el-button>
          </div> -->
        </div>
        <el-form ref="queryParams" class="cus-search-box" :model="queryParams">
          <!-- // TODo -->
          <el-form-item label="活动编号">
            <el-input v-model="queryParams.code" placeholder="请输入活动编号" />
          </el-form-item>
          <el-form-item label="活动名称">
            <el-input v-model="queryParams.name" placeholder="请输入活动名称" />
          </el-form-item>
          <el-form-item />
          <el-form-item class="search-btn">
            <el-button class="cus-search-btn" type="primary" @click="handleQuery">搜索</el-button>
            <el-button class="cus-reset-btn" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
          <div v-show="dataList && dataList.length > 0">
            <el-table class="cus-table-box" :data="dataList" stripe @selection-change="handleSelectionChange">
              <!-- <el-table-column type="selection" width="62" align="center" /> -->
              <el-table-column label="渠道来源" align="center" prop="channel" :formatter="channelOptionsFormat" />
              <el-table-column label="活动编号" align="center" prop="code" />
              <el-table-column label="活动名称" align="center" prop="name" show-overflow-tooltip />
              <el-table-column label="活动简介" align="center" prop="info" show-overflow-tooltip />
              <el-table-column label="线索总数" align="center" prop="cluesNum" />
              <el-table-column label="伪线索数" align="center" prop="falseCluesNum" />
              <el-table-column label="转化商机数" align="center" prop="businessNum" />
              <el-table-column label="转化客户数" align="center" prop="customersNum" />
              <el-table-column label="成交金额" align="center" prop="amount" />
              <el-table-column label="活动成本" align="center" prop="cost" />
              <el-table-column label="创建时间" sortable align="center" prop="createTime" min-width="140" />
            </el-table>
            <pagination
              v-show="queryParams.total > 0"
              :total="queryParams.total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getList"
            />
          </div>
          <empty v-show="(!dataList || dataList.length <= 0) && !dataListLoading" :box-height="645" class="table-empty-box" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Search from '@/components/common/Search.vue'
import channelCharts from './channelCharts.vue'
// 渠道统计
import { reportActivityStatisticsList } from '@/api/clues/countAnalysis.js'
import dayjs from 'dayjs'

export default {
  components: {
    Search,
    channelCharts
  },
  props: {
    tabsIndex: {
      type: Number,
      required: true,
      default: 3
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
        getListFun: reportActivityStatisticsList
      },
      queryParams: {
        beginCreateTime: dayjs().subtract(7, 'days').format('YYYY-MM-DD'),
        endCreateTime: dayjs().format('YYYY-MM-DD'),
        code: '',
        name: '',
        total: 0,
        pageNum: 1,
        pageSize: 10
      },
      search: {},
      ids: [],
      dataList: [],
      dataListLoading: false,
      channelOptions: []
    }
  },
  watch: {
    search () {
      this.queryParams.beginCreateTime = this.search.dateDayArr[0]
      this.queryParams.endCreateTime = this.search.dateDayArr[1]
      this.handleQuery()
    },
    tabsIndex () {
      this.tabsIndex === 3 && this.resetQuery()
    }
  },
  created () {
    this.tabsIndex === 3 && this.resetQuery()
    // 渠道来源
    this.getDicts('clues_item').then(response => {
      this.channelOptions = response.data
    })
  },
  methods: {
    // 渠道来源字典翻译
    channelOptionsFormat (row) {
      return this.selectDictLabel(this.channelOptions, row.channel)
    },
    // 重新定义列表请求接口
    getList () {
      this.dataListLoading = true
      this.dataList = []
      const params = {
        ...this.queryParams
      }
      this.mixinViewModuleOptions.getListFun(params).then(res => {
        this.dataListLoading = false
        if (res.code !== 200) {
          // this.dataList = []
          this.queryParams.total = 0
          return this.$message.error(res.msg)
        }
        this.dataList = res.rows
        this.queryParams.total = res.total
      }).catch(() => {
        this.dataListLoading = false
      })
    },
    exportFn () {
      // TODO
    },
    handleQuery () {
      // TODO
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery () {
      // TODO
      this.queryParams.beginCreateTime = dayjs().subtract(7, 'days').format('YYYY-MM-DD')
      this.queryParams.endCreateTime = dayjs().format('YYYY-MM-DD')
      this.queryParams.code = ''
      this.queryParams.name = ''
      this.handleQuery()
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
