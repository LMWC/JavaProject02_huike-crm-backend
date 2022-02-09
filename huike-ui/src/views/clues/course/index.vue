<template>
  <div class="app-container customer-page-box">
    <el-form
      v-show="showSearch"
      ref="queryForm"
      class="cus-search-box"
      :model="queryParams"
    >
      <el-form-item label="课程编号" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入课程编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入课程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="适用人群" prop="applicablePerson">
        <el-select
          v-model="queryParams.applicablePerson"
          placeholder="请选择适用人群"
          clearable
        >
          <el-option
            v-for="dict in applicablePersonOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
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
        <!-- <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker> -->
      </el-form-item>
      <el-form-item label="课程学科" prop="subject">
        <el-select
          v-model="queryParams.subject"
          placeholder="请选择课程学科"
          clearable
        >
          <el-option
            v-for="dict in subjectOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-form-item class="search-btn wp-75">
        <el-button
          class="cus-search-btn"
          type="primary"
          @click="handleQuery"
        >搜索</el-button>
        <el-button class="cus-reset-btn" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="cus-btn-box">
      <div class="label">
        已选<span class="num">{{ ids.length }}</span>项
      </div>
      <div class="cus-btn-con">
        <el-button
          v-hasPermi="['clues:course:add']"
          class="primary-btn"
          @click="addOrUpdateHandle()"
        >添加课程</el-button>
        <el-button
          v-hasPermi="['clues:clue:remove']"
          class="default-btn"
          :disabled="multiple"
          @click="handleDelete()"
        >批量删除</el-button>
      </div>
    </div>

    <div
      v-loading="dataListLoading"
      :class="{ 'loading-box': dataListLoading }"
    >
      <div v-show="dataList && dataList.length > 0">
        <el-table
          stripe
          class="cus-table-box"
          :data="dataList"
          @selection-change="handleSelectionChange"
          @sort-change="dataListSortChangeHandle"
        >
          <el-table-column type="selection" width="62" align="center" />
          <el-table-column
            label="课程编号"
            min-width="100"
            align="center"
            prop="code"
          />
          <el-table-column
            label="课程学科"
            min-width="100"
            align="center"
            prop="subject"
            :formatter="subjectFormat"
          />
          <el-table-column
            label="课程名称"
            min-width="100"
            align="center"
            prop="name"
          />
          <el-table-column
            label="价格(元)"
            min-width="100"
            align="center"
            prop="price"
          />
          <el-table-column
            label="适用人群"
            min-width="100"
            align="center"
            prop="applicablePerson"
            :formatter="applicablePersonFormat"
          />
          <el-table-column
            label="课程介绍"
            min-width="120"
            align="center"
            prop="info"
            show-overflow-tooltip
          />
          <!-- <el-table-column
            label="创建时间"
            min-width="140"
            align="center"
            prop="createTime"
          /> -->
          <el-table-column
            label="创建时间"
            min-width="120"
            align="center"
            prop="createTime"
            sortable
          />
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
            min-width="120"
          >
            <template slot-scope="scope">
              <el-button
                v-hasPermi="['clues:course:edit']"
                size="mini"
                type="text"
                @click="addOrUpdateHandle(scope.row.id)"
              >修改</el-button>
              <el-button
                v-hasPermi="['clues:course:remove']"
                size="mini"
                type="text"
                class="del-text"
                @click="handleDelete(scope.row.id)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="pageNum"
          :limit.sync="pageSize"
          @pagination="getList"
        />
      </div>
      <empty
        v-show="(!dataList || dataList.length <= 0) && !dataListLoading"
        style="height: calc(100vh - 66px - 215px - 83px )"
        class="table-empty-box"
      />
    </div>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update
      v-if="addOrUpdateVisible"
      ref="addOrUpdate"
      @refreshDataList="handleQuery"
    />
  </div>
</template>

<script>
import mixinViewModule from '@/mixins/view-module'
import { listCourse, delCourse } from '@/api/clues/course'
import AddOrUpdate from './add-or-update-course'
export default {
  name: 'Course',
  components: {
    AddOrUpdate
  },
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: listCourse,
        deleteFun: delCourse,
        deleteIsBatch: true
      },
      createTime: '',
      queryParams: {
        code: '',
        name: '',
        applicablePerson: '',
        beginCreateTime: '',
        endCreateTime: '',
        subject: ''
      },
      // 课程学科字典
      subjectOptions: [],
      // 适用人群字典
      applicablePersonOptions: []
    }
  },
  watch: {
    createTime (val) {
      console.log(val)
      if (val && val.length >= 2) {
        this.queryParams.beginCreateTime = val[0]
        this.queryParams.endCreateTime = val[1]
      } else {
        this.queryParams.beginCreateTime = ''
        this.queryParams.endCreateTime = ''
      }
    }
  },
  created () {
    this.getDicts('course_subject').then(response => {
      this.subjectOptions = response.data
    })
    this.getDicts('applicable_person').then(response => {
      this.applicablePersonOptions = response.data
    })
  },
  methods: {
    // 课程学科字典翻译
    subjectFormat (row) {
      return this.selectDictLabel(this.subjectOptions, row.subject)
    },
    // 适用人群字典翻译
    applicablePersonFormat (row) {
      return this.selectDictLabel(
        this.applicablePersonOptions,
        row.applicablePerson
      )
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm('queryForm')
      this.createTime = null
      this.queryParams.beginCreateTime = ''
      this.queryParams.endCreateTime = ''
      this.handleQuery()
    }
  }
}
</script>
