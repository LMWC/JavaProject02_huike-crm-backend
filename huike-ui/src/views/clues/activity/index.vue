<template>
  <div class="app-container customer-page-box">
    <el-form v-show="showSearch" ref="queryForm" class="cus-search-box" :model="queryParams">
      <el-form-item label="活动编号" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入活动编号"
          clearable
          @keyup.enter.native="searchHandle"
        />
      </el-form-item>
      <el-form-item label="渠道来源" prop="channel">
        <el-select v-model="queryParams.channel" placeholder="请选择渠道来源" clearable>
          <el-option
            v-for="dict in channelOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="活动状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择活动状态" clearable :disabled="activeName!=='tabs1'">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item> -->
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
      <el-form-item label="活动日期">
        <el-date-picker
          v-model="activityTime"
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
      <!-- <el-form-item label="开始时间" prop="beginTime">
        <el-date-picker
          v-model="queryParams.beginTime"
          clearable

          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择开始时间"
        />
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker
          v-model="queryParams.endTime"
          clearable

          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择结束时间"
        />
      </el-form-item> -->
      <el-form-item class="search-btn wp-100">
        <el-button class="cus-search-btn" type="primary" @click="searchHandle">搜索</el-button>
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
        <el-tab-pane
          :label="'已结束('+totalCount.finish+')'"
          name="4"
        />
      </el-tabs>
    </div> -->

    <div class="cus-btn-box">
      <!-- <div class="label">
        已选<span class="num">{{ids.length}}</span>项
      </div> -->
      <div class="cus-btn-con">
        <el-button
          v-hasPermi="['clues:activity:add']"
          class="primary-btn"
          @click="addOrUpdateHandle()"
        >添加活动</el-button>
      </div>
    </div>

    <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
      <div v-show="dataList && dataList.length>0">
        <el-table ref="table" stripe class="cus-table-box" :data="dataList" @sort-change="dataListSortChangeHandle" @filter-change="childrenFilterChange">
          <!-- <el-table-column type="selection" width="62" align="center" /> -->
          <el-table-column label="序号" align="center" type="index" width="75" />
          <el-table-column label="活动编号" align="center" prop="code" />
          <el-table-column label="渠道来源" align="center" prop="channel" :formatter="channelFormat" min-width="100" />
          <el-table-column label="活动名称" align="center" prop="name" min-width="100" />
          <el-table-column label="活动简介" align="center" prop="info" min-width="100" show-overflow-tooltip />
          <el-table-column label="活动明细" align="center" min-width="150">
            <template slot-scope="scope">
              <span>{{ typeFormat(scope.row) }}/{{ scope.row.type==1?scope.row.discount+'折':scope.row.vouchers+'元' }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="活动类型" align="center" prop="type" :formatter="typeFormat" />
            <el-table-column label="课程折扣" align="center" prop="discount" />
            <el-table-column label="代金券" align="center" prop="vouchers" /> -->
          <!-- <el-table-column label="开始时间" align="center" prop="beginTime" min-width="140" sortable> -->
          <el-table-column label="开始时间" align="center" prop="beginTime" min-width="140" sortable>
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.beginTime, '{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="开始时间" align="center" prop="beginTime" min-width="140" sortable> -->
          <el-table-column label="结束时间" align="center" prop="endTime" min-width="140" sortable>
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column
            v-if="activeName=='tabs1'"
            key="status"
            label="状态"
            min-width="100"
            align="center"
            prop="status"
            :formatter="statusFormat"
            :filter-multiple="false"
            :filters="statusFiltersOptions"
            :filter-method="filterStatus"
            column-key="status"
            filter-placement="bottom-end"
          /> -->
          <!-- <el-table-column
            v-if="activeName!='tabs1'"
            key="status1"
            label="状态"
            min-width="100"
            align="center"
            prop="status"
            :formatter="statusFormat"
          /> -->
          <el-table-column v-if="activeName!=2 && activeName!=4" fixed="right" label="操作" align="center" class-name="small-padding fixed-width" min-width="120">
            <template slot-scope="scope">
              <el-button
                v-hasPermi="['clues:activity:edit']"
                size="mini"
                type="text"
                @click="addOrUpdateHandle(scope.row.id)"
              >修改</el-button>
              <el-button
                v-hasPermi="['clues:activity:remove']"
                class="del-text"
                size="mini"
                type="text"
                @click="handleDelete(scope.row.id)"
              >删除</el-button>
              <!-- <el-button
                v-if="scope.row.status==1"
                v-hasPermi="['clues:activity:pass']"
                size="mini"
                type="text"
                @click="passHandle(scope.row.id)"
              >通过</el-button>
              <el-button
                v-if="scope.row.status==1"
                v-hasPermi="['clues:activity:reject']"
                class="del-text"
                size="mini"
                type="text"
                @click="rejectHandle(scope.row.id)"
              >驳回</el-button> -->
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="pageNum"
          :limit.sync="pageSize"
          @pagination="getList"
        />
      </div>
      <empty v-show="(!dataList || dataList.length<=0) && !dataListLoading" style="height: calc(100vh - 66px - 66px - 215px - 83px )" class="table-empty-box" />
    </div>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="handleQuery" />
  </div>
</template>

<script>
import mixinViewModule from '@/mixins/view-module'
import { listActivity, delActivity, passActivity, rejectActivity } from '@/api/clues/activity'
import AddOrUpdate from './add-or-update-activity'
export default {
  name: 'Activity',
  components: {
    AddOrUpdate
  },
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: listActivity,
        deleteFun: delActivity,
        deleteIsBatch: true
      },
      activeName: 'tabs1',
      // 渠道来源字典
      channelOptions: [],
      // 活动类型字典
      typeOptions: [],
      // 状态字典
      statusOptions: [],
      statusFiltersOptions: [],
      createTime: '',
      activityTime: '',
      // 查询参数
      queryParams: {
        code: null,
        channel: null,
        status: null,
        beginCreateTime: '',
        endCreateTime: '',
        beginTime: '',
        endTime: ''
      },
      totalCount: {
        finish: 0,
        pass: 0,
        reject: 0,
        toAudit: 0,
        total: 0
      }
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
    },
    activityTime (val) {
      console.log(val)
      if (val && val.length >= 2) {
        this.queryParams.beginTime = val[0]
        this.queryParams.endTime = val[1]
      } else {
        this.queryParams.beginTime = ''
        this.queryParams.endTime = ''
      }
    }
  },
  created () {
    this.getDicts('clues_item').then(response => {
      this.channelOptions = response.data
    })
    this.getDicts('channel_type').then(response => {
      this.typeOptions = response.data
    })
    this.getDicts('activity_status').then(response => {
      this.statusOptions = response.data
      const statusFiltersOptions = [
        {
          dictLabel: '全部',
          dictValue: ''
        }
      ]
      statusFiltersOptions.push(...response.data)
      statusFiltersOptions.forEach(item => {
        item.text = item.dictLabel
        item.value = item.dictValue
      })
      this.statusFiltersOptions = statusFiltersOptions
    })
  },
  methods: {
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
        this.totalCount = {
          ...this.totalCount,
          ...res.params
        }
        console.log('-=-=-res=-=-=-=', this.dataList)
      }).catch(() => {
        this.dataListLoading = false
      })
    },
    // 渠道来源字典翻译
    channelFormat (row) {
      return this.selectDictLabel(this.channelOptions, row.channel)
    },
    // 活动类型字典翻译
    typeFormat (row) {
      return this.selectDictLabel(this.typeOptions, row.type)
    },
    // 状态字典翻译
    statusFormat (row) {
      return this.selectDictLabel(this.statusOptions, row.status)
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm('queryForm')
      this.createTime = null
      this.queryParams.beginCreateTime = ''
      this.queryParams.endCreateTime = ''
      this.activityTime = null
      this.queryParams.beginTime = ''
      this.queryParams.endTime = ''
      this.$refs.table.clearFilter('status')
      this.handleQuery()
    },
    // 通过
    async passHandle (id) {
      this.$confirm('确认通过？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          const res = await passActivity(id)
          if (res.code !== 200) {
            return this.$message.error(res.msg)
          }
          this.msgSuccess('操作成功')
          this.handleQuery()
        }).catch(() => {

        })
    },
    // 驳回
    async rejectHandle (id) {
      this.$confirm('确认通过驳回？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          const res = await rejectActivity(id)
          if (res.code !== 200) {
            return this.$message.error(res.msg)
          }
          this.msgSuccess('操作成功')
          this.handleQuery()
        }).catch(() => {

        })
    },
    handleClick () {
      if (this.activeName === 'tabs1') {
        this.queryParams.status = null
      } else {
        this.queryParams.status = this.activeName
      }
      this.handleQuery()
    },
    searchHandle () {
      if (this.activeName === 'tabs1') {
        this.setDefaultFilter(8, this.queryParams.status)
      }
      this.handleQuery()
    },
    filterStatus () {
      return true
    }
  }
}
</script>
