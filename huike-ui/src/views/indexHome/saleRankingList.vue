// 销售排行榜
<template>
  <div class="sale-rank-list">
    <div class="sale-title">
      <h2>线索转化龙虎榜</h2>
      <div class="date">
        <!-- <el-form ref="listForm" :model="listForm" class="search-form">
          <el-form-item style="padding-left: 0px;">
            <el-select v-model="listForm.dateType" style="width: 100px;margin-right:10px;">
              <el-option v-for="(type) in dateTypeOptions" :key="type.dictValue" :label="type.dictLabel" :value="type.dictValue" />
            </el-select>
            <el-date-picker
              v-if="listForm.dateType === '0'"
              v-model="listForm.dateYear"
              type="year"
              placeholder="请选择"
            />
            <el-date-picker
              v-if="listForm.dateType === '1'"
              v-model="listForm.dateMonth"
              type="month"
              placeholder="请选择"
            />
            <el-date-picker v-if="listForm.dateType === '2'" v-model="listForm.dateDay" clearable unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" />
          </el-form-item>
        </el-form> -->
      </div>
    </div>
    <!-- 列表 -->
    <!-- height: 484px; -->
    <div class="list" style="height: 480px;">
      <el-scrollbar style="height:100%;width: 98%;">
        <!-- :default-sort="{ prop: 'saleNum', order: 'descending' }" -->
        <el-table :data="saleData" style="width: 96%;margin: 0 auto;">
          <el-table-column prop="id" label="排名" align="center" width="80">
            <template slot-scope="{ row }">
              <div class="nomal">
                <span class="base-span" :class="{ 'active-one': row.saleId === 1, 'active-two': row.saleId === 2, 'active-three': row.saleId === 3 }">{{ row.saleId }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="create_by" label="用户姓名" align="center" />
          <el-table-column prop="deptName" label="部门" align="center" />
          <el-table-column prop="num" sortable label="线索转化数(个)" align="center" min-width="100" />
          <el-table-column prop="radio" sortable label="线索转化率(%)" align="center" min-width="100" />
        </el-table>
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    saleData: {
      type: Array,
      require: true,
      default: () => []
    }
  },
  data () {
    return {
      listForm: {
        dateType: '2',
        dateYear: '',
        dateMonth: '',
        dateDay: ''
      },
      dateTypeOptions: [
        {
          dictLabel: '年',
          dictValue: '0'
        },
        {
          dictLabel: '月',
          dictValue: '1'
        },
        {
          dictLabel: '日',
          dictValue: '2'
        }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
  .sale-rank-list {
    width: 100%;
    height: 100%;
    padding-left: 20px;
    box-sizing: border-box;
  }
</style>
