<template>
  <div class="system-log customer-page-box">
    <el-form ref="queryParams" :model="queryParams" class="cus-search-box">
      <el-form-item label="关键词">
        <el-input v-model="queryParams.keyWord" placeholder="请输入关键词" />
      </el-form-item>
      <el-form-item label="选择时间">
        <!-- <el-input v-model="systemLogForm.choseDate"></el-input> -->
        <el-date-picker v-model="time" placeholder="请选择" clearable type="daterange" align="right" unlink-panels value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item />
      <el-form-item class="search-btn wp-0">
        <el-button class="cus-search-btn" type="primary" @click="searchHandle">搜索</el-button>
        <el-button class="cus-reset-btn" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="cus-btn-box">
      <div class="label">
        已选<span class="num">{{ ids.length }}</span>项
      </div>
      <div class="cus-btn-con">
        <el-button
          class="primary-btn"
          :disabled="multiple"
          @click="handleDelete()"
        >删除</el-button>
        <el-button
          class="default-btn"
          @click="handleClean()"
        >清空</el-button>
        <el-button
          class="default-btn"
          :disabled="multiple"
          @click="handleExport()"
        >导出</el-button>
      </div>
    </div>

    <div v-loading="dataListLoading" :class="{'loading-box': dataListLoading}">
      <div v-show="dataList && dataList.length > 0">
        <el-table class="cus-table-box" :data="dataList" stripe @selection-change="handleSelectionChange" @filter-change="childrenFilterChange" @sort-change="dataListSortChangeHandle">
          <!-- <el-table-column label="序号" align="center" type="index" width="75" /> -->
          <el-table-column type="selection" width="62" align="center" />
          <!-- <el-table-column label="角色编号" align="center" /> -->
          <el-table-column label="用户名" prop="operName" align="center" />
          <el-table-column label="请求方式" prop="requestMethod" align="center" />
          <el-table-column label="访问地址" prop="operUrl" align="center" />
          <!-- <el-table-column label="姓名" align="center" /> -->
          <!-- <el-table-column label="组织结构" align="center" /> -->
          <el-table-column label="模块名称" prop="title" align="center" show-overflow-tooltip />
          <el-table-column label="登录IP" prop="operIp" align="center" />
          <!-- <el-table-column label="登录时间" prop="operTime" align="center" width="160" /> -->
          <!-- <el-table-column label="功能" align="center" /> -->
          <el-table-column label="操作时间" align="center" prop="operTime" />
          <!-- <el-table-column label="操作状态" prop="status" align="center" /> -->
          <!-- <el-table-column label="退出时间" align="center" /> -->
          <el-table-column label="操作" align="center" prop="btn">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="handleView(scope.row,scope.index)"
              >查看</el-button>
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
      <empty v-show="(!dataList || dataList.length <= 0) && !dataListLoading" :box-height="645" class="table-empty-box" />
    </div>

    <!-- 操作日志详细 -->
    <el-dialog title="操作日志详细" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" label-width="100px" size="mini">
        <el-form-item class="wp-100" label="操作模块：">{{ form.title }}</el-form-item>
        <el-form-item
          label="登录信息："
          class="wp-50"
        >{{ form.operName }} / {{ form.operIp }} / {{ form.operLocation }}</el-form-item>
        <el-form-item class="wp-75" label="请求地址：">{{ form.operUrl }}</el-form-item>
        <el-form-item class="wp-75" label="请求方式：">{{ form.requestMethod }}</el-form-item>
        <el-form-item class="wp-75" label="操作方法：">{{ form.method }}</el-form-item>
        <el-form-item class="wp-75" label="请求参数：">{{ form.operParam }}</el-form-item>
        <el-form-item class="wp-75" label="返回参数：">{{ form.jsonResult }}</el-form-item>
        <el-form-item class="wp-75" label="操作状态：">
          <div v-if="form.status === 0">正常</div>
          <div v-else-if="form.status === 1">失败</div>
        </el-form-item>
        <el-form-item class="wp-100" label="操作时间：">{{ parseTime(form.operTime) }}</el-form-item>
        <el-form-item v-if="form.status === 1" class="wp-100" label="异常信息：">{{ form.errorMsg }}</el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="cus-search-btn" @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 操作日志查看  操作日志清空  操作日志导出  操作日志列表
import { monitorOperlogByOperIds, monitorOperlogClean, monitorOperlogExport, monitorOperlogList } from '@/api/system/systemLog'
import { delOperlog } from '@/api/monitor/operlog'
import mixinViewModule from '@/mixins/view-module'

export default {
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: monitorOperlogList,
        deleteIsBatchKey: 'operId'
      },
      time: [],
      queryParams: {
        keyWord: '',
        params: {
          beginTime: '',
          endTime: ''
        }
      },
      open: false,
      form: {}
    }
  },
  watch: {
    time (val) {
      if (val && val.length >= 2) {
        this.queryParams.params.beginTime = val[0]
        this.queryParams.params.endTime = val[1]
      } else {
        this.queryParams.params.beginTime = ''
        this.queryParams.params.endTime = ''
      }
    }
  },
  methods: {
    searchHandle () {
      this.handleQuery()
    },
    resetQuery () {
      // TODO
      this.queryParams.keyWord = ''
      this.queryParams.params.beginTime = ''
      this.queryParams.params.endTime = ''
      this.time = ''
    },
    /** 删除按钮操作 */
    handleDelete () {
      const operIds = this.ids
      this.$confirm('是否确认删除日志编号为"' + operIds + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return delOperlog(operIds)
      }).then(() => {
        this.handleQuery()
        this.msgSuccess('删除成功')
      })
    },
    /** 清空按钮操作 */
    handleClean () {
      this.$confirm('是否确认清空所有操作日志数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return monitorOperlogClean()
      }).then(() => {
        this.handleQuery()
        this.msgSuccess('清空成功')
      })
    },
    /** 导出按钮操作 */
    handleExport () {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有操作日志数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return monitorOperlogExport(queryParams)
      }).then(response => {
        this.download(response.msg)
      })
    },
    /** 详细按钮操作 */
    handleView (row) {
      this.open = true
      this.form = row
    }
  }
}
</script>

<style lang="scss" scoped>
  .system-log {
  }
</style>
