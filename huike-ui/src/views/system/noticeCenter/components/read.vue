<template>
  <div v-if="tabsIndex === 1" class="read">
    <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}" class="read-table">
      <div v-show="dataList && dataList.length > 0">
        <el-table class="cus-table-box" :data="dataList" stripe>
          <el-table-column label="序号" align="center" type="index" width="75" />
          <el-table-column label="发信人" prop="createBy" align="center" />
          <el-table-column label="内容" prop="noticeContent" align="center">
            <template slot-scope="{ row }">
              <span v-html="row.noticeContent"></span>
            </template>
          </el-table-column>
          <el-table-column label="通知时间" prop="createTime" sortable align="center" />
          <el-table-column label="操作" align="center">
            <template slot-scope="{ row }">
              <el-button type="text" size="mini" @click="seeRead(row)">查看</el-button>
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
  </div>
</template>

<script>
import { systemNoticePagelistStatus } from '@/api/system/noticeCenter'
import mixinViewModule from '@/mixins/view-module'

export default {
  mixins: [mixinViewModule],
  props: {
    tabsIndex: {
      type: Number,
      required: true,
      default: 1
    }
  },
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: systemNoticePagelistStatus
      },
      queryParams: {
        status: 1
      }
    }
  },
  watch: {
    tabsIndex (val) {
      val === 1 && this.handleQuery()
    }
  },
  methods: {
    seeRead (row) {
      if (row.noticeType === '1') {
        // 合同
        this.$router.push({ path: '/contract', query: { type: 1 } })
      } else {
        // 活动
        this.$router.push({ path: '/activity', query: { type: 1 } })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .read {
    .read-table {
      margin-top: 38px;
    }
  }
</style>
