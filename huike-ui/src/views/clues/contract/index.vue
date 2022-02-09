<template>
  <div class="app-container customer-page-box">
    <el-form v-show="showSearch" ref="queryForm" class="cus-search-box" :model="queryParams">
      <el-form-item label="合同编号" prop="contractNo">
        <el-input
          v-model="queryParams.contractNo"
          placeholder="请输入合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入客户姓名"
          clearable
          @keyup.enter.native="searchHandle"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="searchHandle"
        />
      </el-form-item>
      <el-form-item label="购买学科" prop="subject">
        <el-select v-model="queryParams.subject" placeholder="请选择购买学科" clearable>
          <el-option
            v-for="dict in subjectOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="购买课程" prop="courseId">
        <el-select v-model="queryParams.courseId" placeholder="请选择购买课程" clearable>
          <el-option
            v-for="dict in courseOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="createTime"
          clearable
          type="daterange"
          align="right"
          unlink-panels
          value-format="yyyy-MM-dd"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item class="search-btn wp-50">
        <el-button class="cus-search-btn" type="primary" @click="handleQuery">搜索</el-button>
        <el-button class="cus-reset-btn" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- <div class="tabs-list-box">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane
          :label="'全部('+totalCount.total+')'"
          name="tabs1"
        />
        <el-tab-pane
          :label="'待审核('+totalCount.toAudit+')'"
          name="1"
        />
        <el-tab-pane
          :label="'已通过('+totalCount.pass+')'"
          name="2"
        />
        <el-tab-pane
          :label="'已驳回('+totalCount.reject+')'"
          name="3"
        />
      </el-tabs>
    </div> -->

    <div class="cus-btn-box">
      <!-- <div class="label">
        已选<span class="num">{{ids.length}}</span>项
      </div> -->
      <div class="cus-btn-con">
        <el-button
          v-hasPermi="['contract:contract:add']"
          class="primary-btn"
          @click="addOrUpdateHandle()"
        >添加合同</el-button>
      </div>
    </div>

    <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
      <div v-show="dataList && dataList.length>0">
        <el-table stripe class="cus-table-box" :data="dataList" @sort-change="dataListSortChangeHandle">
          <!-- <el-table-column type="selection" width="62" align="center" /> -->
          <el-table-column label="序号" align="center" type="index" width="75" />
          <el-table-column label="合同编号" align="center" prop="contractNo" />
          <el-table-column label="客户姓名" align="center" prop="name" min-width="100" />
          <el-table-column label="手机号" align="center" prop="phone" min-width="100" />
          <el-table-column label="购买学科" align="center" prop="subject" :formatter="subjectFormat" />
          <el-table-column label="购买课程" align="center" prop="courseId" :formatter="courseFormat" />
          <!-- <el-table-column label="审核状态" min-width="100" align="center" prop="status" :formatter="statusFormat" /> -->
          <el-table-column
            label="创建时间"
            min-width="120"
            align="center"
            prop="createTime"
            sortable
          />
          <el-table-column fixed="right" label="操作" align="center" class-name="small-padding fixed-width" min-width="120">
            <template slot-scope="scope">
              <el-button
                v-hasPermi="['contract:contract:info']"
                size="mini"
                type="text"
                @click="previewHandle(scope.row.fileName)"
              >预览</el-button>
              <!-- <el-button
                v-if="scope.row.status==3"
                v-hasPermi="['contract:contract:edit']"
                size="mini"
                type="text"
                @click="addOrUpdateHandle(scope.row.id)"
              >修改</el-button> -->
              <!-- <el-button
                v-if="scope.row.status==1"
                v-hasPermi="['contract:contract:audit']"
                size="mini"
                type="text"
                @click="infoHandle(scope.row.id,'audit')"
              >审核</el-button> -->
              <el-button
                v-hasPermi="['contract:contract:info']"
                size="mini"
                type="text"
                @click="infoHandle(scope.row.id,'info')"
              >详情</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
      <empty v-show="(!dataList || dataList.length<=0) && !dataListLoading" style="height: calc(100vh - 66px - 66px - 215px - 83px )" class="table-empty-box" />
    </div>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="handleQuery" />
    <preview-pdf v-if="pdfVisible" ref="pdf" title="预览合同" :pdf-url="pdfUrl" />
  </div>
</template>

<script>
import mixinViewModule from '@/mixins/view-module'
import { listContract, delContract } from '@/api/contract/contract'
import { listCourseSelect } from '@/api/clues/course'
import AddOrUpdate from './add-or-update-contract'
import PreviewPdf from '@/components/PreviewPdf'
export default {
  name: 'Activity',
  components: {
    AddOrUpdate,
    PreviewPdf
  },
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: listContract,
        deleteFun: delContract,
        deleteIsBatch: true
      },
      pdfVisible: false,
      activeName: 'tabs1',
      // 渠道来源字典
      subjectOptions: [],
      // 合同类型字典
      courseOptions: [],
      // 状态字典
      statusOptions: [],
      createTime: '',
      activityTime: '',
      // 查询参数
      queryParams: {
        contractNo: null,
        name: null,
        phone: null,
        subject: null,
        courseId: null,
        status: null,
        params: {
          beginCreateTime: '',
          endCreateTime: ''
        }
      },
      totalCount: {
        pass: 0,
        reject: 0,
        toAudit: 0,
        total: 0
      },
      pdfUrl: ''
    }
  },
  watch: {
    createTime (val) {
      console.log(val)
      if (val && val.length >= 2) {
        this.queryParams.params.beginCreateTime = val[0]
        this.queryParams.params.endCreateTime = val[1]
      } else {
        this.queryParams.params.beginCreateTime = ''
        this.queryParams.params.endCreateTime = ''
      }
    }
  },
  created () {
    this.getDicts('course_subject').then(response => {
      this.channelOptions = response.data
    })
    this.getDicts('activity_status').then(response => {
      this.statusOptions = response.data
    })
    this.getDicts('course_subject').then(response => {
      this.subjectOptions = response.data
    })
    this.getCourseList()
  },
  methods: {
    // 意向学科字典翻译
    subjectFormat (row) {
      return this.selectDictLabel(this.subjectOptions, row.subject)
    },
    // 状态字典翻译
    statusFormat (row) {
      return this.selectDictLabel(this.statusOptions, row.status)
    },
    // 课程翻译
    courseFormat (row) {
      const course = this.courseOptions.find(item => item.id === row.courseId)
      if (course) {
        return course.name
      }
      return ''
    },
    // 获取数据列表
    getList () {
      this.dataListLoading = true
      this.dataList = []
      this.mixinViewModuleOptions.getListFun({
        order: this.order,
        orderField: this.orderField,
        pageNum: this.mixinViewModuleOptions.getDataListIsPage ? this.pageNum : null,
        pageSize: this.mixinViewModuleOptions.getDataListIsPage ? this.pageSize : null,
        ...this.queryParams
      }).then((res) => {
        console.log('-=-=-res=-=-=-=', res)
        this.dataListLoading = false
        if (res.code !== 200) {
          // this.dataList = []
          this.total = 0
          return this.$message.error(res.msg)
        }
        this.dataList = this.mixinViewModuleOptions.getDataListIsPage ? res.rows : res.data
        this.total = this.mixinViewModuleOptions.getDataListIsPage ? res.total : 0
        // this.totalCount = {
        //   ...this.totalCount,
        //   ...res.params
        // }
        console.log('-=-=-res=-=-=-=', this.dataList)
      }).catch(() => {
        this.dataListLoading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.pageNum = 1
      this.getList()
      this.getCourseList()
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm('queryForm')
      this.createTime = null
      this.queryParams.params.beginCreateTime = ''
      this.queryParams.params.endCreateTime = ''
      this.handleQuery()
    },
    handleClick () {
      if (this.activeName === 'tabs1') {
        this.queryParams.status = null
      } else {
        this.queryParams.status = this.activeName
      }
      this.handleQuery()
      console.log('-=-=-handleClick-=-=-= ', this.activeName)
    },
    // 所有课程列表
    async getCourseList () {
      const res = await listCourseSelect()
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.courseOptions = res.data
    },
    // 预览
    previewHandle (url) {
      if (!url) {
        return this.$message.error('暂无合同附件')
      }
      console.log('previewHandle', url)
      this.pdfUrl = url
      this.pdfVisible = true
      this.$nextTick(() => {
        this.$refs.pdf.visible = true
        this.$refs.pdf.init()
      //   this.$refs.allot.init()
      })
    },
    // 审核/详情
    infoHandle (id, pageType) {
      this.$router.push({ path: '/contract/details', query: { id, pageType } })
    }
  }
}
</script>
