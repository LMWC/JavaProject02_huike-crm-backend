<template>
  <div v-if="tabsIndex === 0" class="customer-all">
    <Search :btns-params="[ '新增客户数', '学科客户分布' ]" :show-btns="true" :index-btns="showChartsIndex.index" @sendBtnIndexToCustomer="getBtnIndexBySearch" @searchFormData="getSearchData" />
    <div>
      <customerLineCharts v-if="showChartsIndex.item === '新增客户数'" :show-charts-index="showChartsIndex" :search="search" />
      <customerPieCharts v-if="showChartsIndex.item === '学科客户分布'" :show-charts-index="showChartsIndex" :search="search" />
      <!-- form+table部分 -->
      <div>
        <!-- 标题和导出 -->
        <div class="title-export">
          <div class="title">
            <h3>客户统计报表</h3>
          </div>
          <!-- <div class="export">
            <el-button class="primary-btn customer-btn" @click="exportFn">导出</el-button>
          </div> -->
        </div>
        <el-form ref="queryParams" class="cus-search-box" :model="queryParams">
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
        </el-form>

        <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
          <div v-show="dataList && dataList.length > 0">
            <el-table class="cus-table-box" :data="dataList" stripe @selection-change="handleSelectionChange">
              <!-- <el-table-column type="selection" width="62" align="center" /> -->
              <el-table-column label="客户ID" align="center" prop="id" />
              <el-table-column label="姓名" prop="name" align="center" />
              <el-table-column label="课程学科" prop="subject" align="center" :formatter="subjectFormat" />
              <el-table-column label="课程名称" prop="courseId" align="center" :formatter="courseFormat" />
              <el-table-column label="渠道来源" prop="channel" align="center" :formatter="channelFormat" />
              <el-table-column label="归属人" align="center" prop="createBy" />
              <el-table-column label="成交时间" prop="createTime" sortable align="center" />
              <!-- <el-table-column label="操作" align="center">
                <template slot-scope="{ row }">
                  <el-button type="text" size="mini" @click="goDetail(row)">详情</el-button>
                </template>
              </el-table-column> -->
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
  </div>
</template>

<script>
// 客户统计列表
import { reportContractStatisticsList } from '@/api/clues/countAnalysis.js'
import Search from '@/components/common/Search.vue'
import customerLineCharts from './customerLineCharts.vue'
import customerPieCharts from './customerPieCharts.vue'
import mixinViewModule from '@/mixins/view-module'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import dayjs from 'dayjs'
import { listCourseSelect } from '@/api/clues/course'
export default {
  components: {
    Search,
    customerLineCharts,
    customerPieCharts,
    Treeselect
  },
  mixins: [mixinViewModule],
  props: {
    tabsIndex: {
      type: Number,
      required: true,
      default: 0
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
        getListFun: reportContractStatisticsList
      },
      queryParams: {
        beginCreateTime: dayjs().subtract(7, 'days').format('YYYY-MM-DD'),
        endCreateTime: dayjs().format('YYYY-MM-DD'),
        channel: '',
        deptId: null,
        createBy: ''
      },
      channelOptions: [],
      departOptions: [],
      ownerOptions: [],
      dataList: [],
      // showChartsIndex: '新增客户数'
      showChartsIndex: {
        item: '新增客户数',
        indexBtns: 0
      },
      search: {},
      // 渠道来源字典
      // channelOptions: [],
      // 课程字典状态
      courseOptions: [],
      // 课程学科
      subjectOptions: []
    }
  },
  watch: {
    search () {
      this.queryParams.beginCreateTime = this.search.dateDayArr[0]
      this.queryParams.endCreateTime = this.search.dateDayArr[1]
      this.searchHandle()
    },
    tabsIndex () {
      this.tabsIndex === 0 && this.resetQuery()
    }
  },
  created () {
    this.getDicts('course_subject').then(response => {
      this.subjectOptions = response.data
    })
    this.getCourseList()
  },
  methods: {
    // 重新定义列表请求接口
    getList () {
      this.dataListLoading = true
      this.dataList = []
      const params = {
        order: this.order,
        orderField: this.orderField,
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
        this.totalCount = {
          ...this.totalCount,
          ...res.params
        }
      }).catch(() => {
        this.dataListLoading = false
      })
    },
    searchHandle () {
      this.pageNum = 1
      this.handleQuery()
    },
    // 渠道来源
    channelFormat (row) {
      return this.selectDictLabel(this.departAndBelongerList[0], row.channel)
    },
    // 课程学科字典翻译
    subjectFormat (row) {
      return this.selectDictLabel(this.subjectOptions, row.subject)
    },
    // 课程翻译
    courseFormat (row) {
      const course = this.courseOptions.find(item => item.id === row.courseId)
      if (course) {
        return course.name
      }
      return ''
    },
    // 所有课程列表
    async getCourseList () {
      const res = await listCourseSelect()
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.courseOptions = res.data
    },
    exportFn () {
      // TODO
    },
    resetQuery () {
      // TODO
      this.queryParams.beginCreateTime = dayjs().subtract(7, 'days').format('YYYY-MM-DD')
      this.queryParams.endCreateTime = dayjs().format('YYYY-MM-DD')
      this.queryParams.channel = ''
      this.queryParams.deptId = null
      this.queryParams.createBy = ''
      this.searchHandle()
    },
    handleSelectionChange () {
      // TODO
    },
    getBtnIndexBySearch (data) {
      this.showChartsIndex = data
    },
    goDetail () {
      // TODO
    },
    getSearchData (data) {
      this.search = { ...data }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/assets/styles/useAnalysisAndIndex.scss';

>>> .el-table__fixed::before, .el-table__fixed-right::before {
  background-color: none;
}
</style>
