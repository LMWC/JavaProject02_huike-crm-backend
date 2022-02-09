<template>
  <div v-if="tabsIndex === 1">
    <el-form ref="agencyTransferForm" :model="agencyTransferForm" class="cus-search-box">
      <el-form-item label="编号">
        <el-input v-model="agencyTransferForm.number" />
      </el-form-item>
      <el-form-item label="用户编号">
        <el-input v-model="agencyTransferForm.userNumber" />
      </el-form-item>
      <el-form-item label="减员姓名">
        <el-input v-model="agencyTransferForm.downName" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="agencyTransferForm.state">
          <el-option v-for="state in stateOptions" :key="state.dictValue" :label="state.dictLabel" :value="state.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="agencyTransferForm.type">
          <el-option v-for="type in typeOptions" :key="type.dictValue" :label="type.dictLabel" :value="type.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="agencyTransferForm.time" placeholder="请选择" clearable type="daterange" align="right" unlink-panels value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item class="search-btn wp-50">
        <el-button class="cus-search-btn" type="primary" @click="handleQuery">搜索</el-button>
        <el-button class="cus-reset-btn" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <div v-loading="dataListLoading" :class="{ 'loading-box': dataListLoading }">
      <div v-show="dataList && dataList.length > 0">
        <el-table class="cus-table-box" :data="dataList" stripe @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="62" align="center" />
          <el-table-column label="编号" align="center" prop="id" />
          <el-table-column label="类型" align="center" />
          <el-table-column label="用户编号" align="center" />
          <el-table-column label="减员姓名" align="center" />
          <el-table-column label="创建时间" sortable align="center" />
          <el-table-column label="状态" align="center" />
          <el-table-column label="操作" align="center">
            <template slot-scope="{ row }">
              <el-button type="text" size="mini" @click="goDetail(row)">处理</el-button>
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
import { listClue } from '@/api/clues/clue'
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
        getListFun: listClue
      },
      agencyTransferForm: {

      },
      stateOptions: [],
      typeOptions: []
    }
  },
  methods: {
    handleQuery () {
      // TODO
    },
    resetQuery () {
      // TODO
    },
    handleSelectionChange () {
      // TODO
    },
    goDetail () {
      // TODO
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
