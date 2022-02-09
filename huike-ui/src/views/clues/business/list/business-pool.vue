<template>
  <div class="app-container customer-page-box">
    <el-form v-show="showSearch" ref="queryForm" class="cus-search-box" :model="queryParams">
      <el-form-item label="客户ID" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入客户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入客户姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="意向学科" prop="subject">
        <el-select v-model="queryParams.subject" placeholder="请选择意向学科" clearable>
          <el-option
            v-for="dict in subjectOptions"
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
      <el-form-item class="search-btn wp-75">
        <el-button class="cus-search-btn" type="primary" @click="handleQuery">搜索</el-button>
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
          @click="makeHandle()"
        >批量捞取</el-button>
        <el-button
          v-hasPermi="['business:business:assignment']"
          class="default-btn"
          :disabled="multiple"
          @click="allotHandle()"
        >批量分配</el-button>
      </div>
    </div>
    <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
      <div v-show="dataList && dataList.length>0">
        <el-table class="cus-table-box" :data="dataList" stripe @selection-change="handleSelectionChange" @sort-change="dataListSortChangeHandle">
          <el-table-column type="selection" width="62" align="center" />
          <el-table-column label="客户ID" align="center" prop="id" min-width="80" />
          <el-table-column label="姓名" align="center" prop="name" min-width="100" />
          <el-table-column label="手机号" align="center" prop="phone" :formatter="phoneFormat" min-width="100" />
          <el-table-column label="意向学科" align="center" prop="subject" :formatter="subjectFormat" min-width="100" />
          <el-table-column label="创建时间" align="center" min-width="140" prop="createTime" sortable>
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" align="center" width="200" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <!-- <el-button
                size="mini"
                type="text"
                @click="infoHandle(scope.row.id,'pool')"
              >查看</el-button> -->
              <el-button
                size="mini"
                type="text"
                @click="makeHandle(scope.row.id)"
              >捞取</el-button>
              <el-button
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
    <!-- 弹窗, 分配 -->
    <allot v-if="allotVisible" ref="allot" @refreshDataList="handleQuery" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import mixinViewModule from '@/mixins/view-module'
import { listBusinessPool, gainBusiness } from '@/api/business/business'
import Allot from './../operation/allot-business'
export default {
  name: 'BusinessPool',
  components: {
    Allot
  },
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: listBusinessPool,
        deleteIsBatch: true
      },
      uploadVisible: false,
      allotVisible: false,
      createTime: '',
      queryParams: {
        id: null,
        name: null,
        phone: null,
        subject: null,
        params: {
          beginCreateTime: '',
          endCreateTime: ''
        }
      },
      // 渠道来源字典
      channelOptions: [],
      subjectOptions: []
    }
  },
  computed: {
    ...mapGetters(['name', 'userId'])
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
    this.getDicts('clues_item').then(response => {
      this.channelOptions = response.data
    })
    this.getDicts('course_subject').then(response => {
      this.subjectOptions = response.data
    })
  },
  methods: {
    phoneFormat (row) {
      if (!row.phone) {
        return ''
      }
      return row.phone.substring(0, 3) + '****' + row.phone.substring(8, 11)
    },
    // 课程学科字典翻译
    subjectFormat (row) {
      return this.selectDictLabel(this.subjectOptions, row.subject)
    },
    // 渠道来源字典翻译
    channelOptionsFormat (row) {
      return this.selectDictLabel(this.channelOptions, row.channel)
    },
    // 捞取
    makeHandle (id) {
      console.log('-=-=-=makeHandle-=-=', id)
      const ids = id ? [id] : this.ids
      if (!ids || ids.length <= 0) {
        return this.$message({
          message: '请选择至少一条数据',
          type: 'warning',
          duration: 500
        })
      }
      this.$confirm(`您已选择${ids.length}条数据，确认捞取?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        beforeClose: async (action, instance, done) => {
          if (action === 'confirm') {
            instance.confirmButtonLoading = true
            instance.confirmButtonText = '操作中'
            try {
              const res = await gainBusiness({
                ids,
                userId: this.userId
              })
              done()
              if (res.code !== 200) {
                instance.confirmButtonLoading = false
                instance.confirmButtonText = '确定'
                return this.$message.error(res.msg)
              }
              this.msgSuccess('操作成功')
              this.handleQuery()
              instance.confirmButtonLoading = false
              instance.confirmButtonText = '确定'
            } catch {
              done()
              instance.confirmButtonLoading = false
              instance.confirmButtonText = '确定'
            }
          } else {
            done()
          }
        }
      }).then(() => {

      })
      // })
      //         .then(async () => {
      //           const res = await gainBusiness({
      //             ids,
      //             userId: this.userId
      //           })
      //           if (res.code !== 200) {
      //             return this.$message.error(res.msg)
      //           }
      //           this.msgSuccess('操作成功')
      //           this.handleQuery()
      //         }).catch(() => {
      //
      //         })
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm('queryForm')
      this.createTime = null
      this.queryParams.params.beginCreateTime = ''
      this.queryParams.params.endCreateTime = ''
      this.handleQuery()
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
    // 详情
    infoHandle (id, pageType) {
      this.$router.push({ path: '/business/details', query: { id, pageType } })
    }
  }
}
</script>
