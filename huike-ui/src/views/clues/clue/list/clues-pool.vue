<template>
  <div class="app-container customer-page-box">
    <el-form v-show="showSearch" ref="queryForm" class="cus-search-box" :model="queryParams">
      <el-form-item label="线索ID" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入线索ID"
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
      <!-- <el-form-item label="线索归属" prop="channel">
        <el-input
          v-model="queryParams.channel"
          placeholder="请输入线索归属"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item class="search-btn wp-100">
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
          @click="allotHandle()"
        >批量捞取</el-button>
      </div>
    </div>
    <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
      <div v-show="dataList && dataList.length>0">
        <el-table class="cus-table-box" :data="dataList" stripe @selection-change="handleSelectionChange" @sort-change="dataListSortChangeHandle">
          <el-table-column type="selection" width="62" align="center" />
          <el-table-column label="线索ID" align="center" prop="id" />
          <el-table-column label="手机号" align="center" prop="phone" :formatter="phoneFormat" />
          <el-table-column label="渠道来源" align="center" prop="channel" :formatter="channelOptionsFormat" />
          <el-table-column label="活动名称" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.activityName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" min-width="140" prop="createTime" sortable>
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" align="center" class-name="small-padding fixed-width" width="180">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="followHandle(scope.row.id,'pool')"
              >查看</el-button>
              <el-button
                size="mini"
                type="text"
                @click="allotHandle(scope.row.id)"
              >捞取</el-button>
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
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import mixinViewModule from '@/mixins/view-module'
import { listCluePool, gainClue } from '@/api/clues/clue'
export default {
  name: 'Clue',
  components: {
  },
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: listCluePool,
        deleteIsBatch: true
      },
      uploadVisible: false,
      allotVisible: false,
      createTime: '',
      queryParams: {
        id: null,
        phone: null,
        channel: null,
        params: {
          beginCreateTime: '',
          endCreateTime: ''
        }
      },
      // 渠道来源字典
      channelOptions: []
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
  },
  methods: {
    phoneFormat (row) {
      if (!row.phone) {
        return ''
      }
      return row.phone.substring(0, 3) + '****' + row.phone.substring(8, 11)
    },
    // 渠道来源字典翻译
    channelOptionsFormat (row) {
      return this.selectDictLabel(this.channelOptions, row.channel)
    },
    // 捞取
    allotHandle (id) {
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
        // })
        beforeClose: async (action, instance, done) => {
          if (action === 'confirm') {
            instance.confirmButtonLoading = true
            instance.confirmButtonText = '操作中'
            try {
              const res = await gainClue({
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

      //         .then(async () => {
      //           const res = await gainClue({
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
    // 跟进
    followHandle (id, pageType) {
      this.$router.push({ path: '/clues/details', query: { id, pageType } })
    }
  }
}
</script>
