<template>
  <div class="app-container customer-page-box">
    <el-form v-show="showSearch" ref="queryForm" class="cus-search-box" :model="queryParams">
      <el-form-item label="客户ID" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入客户ID"
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
      <el-form-item label="客户姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入客户姓名"
          clearable
          @keyup.enter.native="searchHandle"
        />
      </el-form-item>
      <el-form-item label="商机归属" prop="owner">
        <el-input
          v-model="queryParams.owner"
          placeholder="请输入商机归属"
          clearable
          @keyup.enter.native="searchHandle"
        />
      </el-form-item>
      <el-form-item label="商机状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择商机状态" clearable>
          <el-option
            v-for="dict in statusOptions"
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
      </el-form-item>
      <el-form-item class="search-btn wp-50">
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
          v-hasPermi="['business:business:add']"
          class="primary-btn"
          @click="addOrUpdateHandle()"
        >新建商机</el-button>
        <!-- <el-button
          v-hasPermi="['business:business:add']"
          class="default-btn"
          @click="uploadHandle()"
        >批量添加</el-button> -->
        <el-button
          v-hasPermi="['business:business:assignment']"
          class="default-btn"
          :disabled="multiple"
          @click="allotHandle()"
        >批量分配</el-button>
        <!-- <el-button
          class="default-btn"
          @click="allotHandle(1)"
        >分配测试</el-button> -->
      </div>
    </div>
    <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
      <div v-show="dataList && dataList.length>0">
        <el-table ref="table" class="cus-table-box" :data="dataList" stripe @selection-change="handleSelectionChange" @filter-change="childrenFilterChange" @sort-change="dataListSortChangeHandle">
          <el-table-column type="selection" width="62" align="center" />
          <el-table-column label="客户ID" align="center" prop="id" min-width="80" />
          <el-table-column label="姓名" align="center" prop="name" min-width="100" />
          <el-table-column label="手机号" align="center" prop="phone" min-width="100" />
          <el-table-column label="归属人" align="center" prop="owner" min-width="100" />
          <el-table-column label="创建时间" align="center" min-width="140" prop="createTime" sortable>
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="商机状态"
            align="center"
            prop="status"
            min-width="100"
            :formatter="statusFormat"
            :filter-multiple="false"
            :filters="statusFiltersOptions"
            :filter-method="filterStatus"
            column-key="status"
            filter-placement="bottom-end"
          />
          <el-table-column label="下次跟进时间" align="center" min-width="140" prop="nextTime" sortable>
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.nextTime, '{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" align="center" min-width="120" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                v-hasPermi="['business:business:info']"
                size="mini"
                type="text"
                @click="infoHandle(scope.row.id,'info')"
              >查看</el-button>
              <el-button
                v-if="(scope.row.status==1 || scope.row.status==2) && scope.row.owner==name"
                v-hasPermi="['business:record:add']"
                size="mini"
                type="text"
                @click="followHandle(scope.row.id)"
              >跟进</el-button>
              <el-button
                v-if="scope.row.status==1"
                v-hasPermi="['business:business:assignment']"
                size="mini"
                type="text"
                @click="allotHandle(scope.row.id)"
              >分配</el-button>
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
    <!-- 弹窗, 分配 -->
    <allot v-if="allotVisible" ref="allot" @refreshDataList="handleQuery" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import mixinViewModule from '@/mixins/view-module'
import { listBusiness, delBusiness } from '@/api/business/business'
import AddOrUpdate from './../operation/add-or-update-business'
import Allot from './../operation/allot-business'
export default {
  name: 'Business',
  components: {
    AddOrUpdate,
    Allot
  },
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: listBusiness,
        deleteFun: delBusiness,
        deleteIsBatch: true
      },
      uploadVisible: false,
      allotVisible: false,
      createTime: '',
      queryParams: {
        id: null,
        name: null,
        phone: null,
        owner: null,
        status: null,
        params: {
          beginCreateTime: '',
          endCreateTime: ''
        }
      },
      // 渠道来源字典
      channelOptions: [],
      // 性能字典
      sexOptions: [],
      // 意向等级字典
      levelOptions: [],
      // 意向学科字典
      subjectOptions: [],
      // 状态字典
      statusOptions: [],
      statusFiltersOptions: []
    }
  },
  computed: {
    ...mapGetters(['name'])
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
    this.getDicts('sys_user_sex').then(response => {
      this.sexOptions = response.data
    })
    this.getDicts('clues_level').then(response => {
      this.levelOptions = response.data
    })
    this.getDicts('course_subject').then(response => {
      this.subjectOptions = response.data
    })
    this.getDicts('clue_status').then(response => {
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
    this.getDicts('clues_item').then(response => {
      this.channelOptions = response.data
    })
  },

  methods: {
    // 性能字典翻译
    sexFormat (row) {
      return this.selectDictLabel(this.sexOptions, row.sex)
    },
    // 意向等级字典翻译
    levelFormat (row) {
      return this.selectDictLabel(this.levelOptions, row.level)
    },
    // 意向学科字典翻译
    subjectFormat (row) {
      return this.selectDictLabel(this.subjectOptions, row.subject)
    },
    // 状态字典翻译
    statusFormat (row) {
      return this.selectDictLabel(this.statusOptions, row.status)
    },
    // 渠道来源字典翻译
    channelOptionsFormat (row) {
      return this.selectDictLabel(this.channelOptions, row.channel)
    },
    // 跟进
    followHandle (id) {
      this.$router.push({ path: '/business/follow', query: { id } })
    },
    // 详情
    infoHandle (id, pageType) {
      this.$router.push({ path: '/business/details', query: { id, pageType } })
    },
    // 分配
    allotHandle (id) {
      const ids = id ? [id] : this.ids
      console.log('ids', ids)
      if (!ids || ids.length <= 0) {
        return this.$message({
          message: '请选择至少一条数据',
          type: 'warning',
          duration: 500
        })
      }
      console.log('-=-=allotHandle-=-=-', ids)
      this.allotVisible = true
      this.$nextTick(() => {
        this.$refs.allot.form.id = ids
        this.$refs.allot.init()
      })
    },
    uploadHandle () {
      this.uploadVisible = true
      this.$nextTick(() => {
        this.$refs.upload.init()
      })
    },
    searchHandle () {
      this.setDefaultFilter(6, this.queryParams.status)
      this.handleQuery()
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm('queryForm')
      this.createTime = null
      this.queryParams.params.beginCreateTime = ''
      this.queryParams.params.endCreateTime = ''
      this.$refs.table.clearFilter('status')
      this.handleQuery()
    },
    filterStatus () {
      return true
    }
  }
}
</script>
