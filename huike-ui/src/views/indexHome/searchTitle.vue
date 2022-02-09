<template>
  <div class="search-title">
    <el-form ref="queryParams" :model="queryParams" class="search-title-box" label-position="left">
      <!-- <el-form-item label="选择部门" style="width: 25%;">
        <treeselect v-model="queryParams.deptId" :options="departmentOptions" :show-count="true" placeholder="请选择归属部门" />
      </el-form-item> -->
      <!-- <el-form-item label="选择日期">
        <el-select v-model="searchTitleForm.dateType" style="width: 100px;margin-right:10px;">
          <el-option v-for="(type) in dateTypeOptions" :key="type.dictValue" :label="type.dictLabel" :value="type.dictValue" />
        </el-select>

      </el-form-item> -->
      <!-- style="padding-left: 0px;" -->
      <el-form-item label="选择日期">
        <!-- <el-date-picker
          v-if="searchTitleForm.dateType === '0'"
          v-model="searchTitleForm.dateYear"
          type="year"
          placeholder="请选择"
        />
        <el-date-picker
          v-if="searchTitleForm.dateType === '1'"
          v-model="searchTitleForm.dateMonth"
          type="month"
          placeholder="请选择"
        /> -->
        <!-- v-if="searchTitleForm.dateType === '2'" -->
        <el-date-picker v-model="queryParams.dateDayArr" style="margin-bottom: 2px;" clearable unlink-panels type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" value-format="yyyy-MM-dd" />
        <!-- <el-input></el-input> -->
      </el-form-item>
      <el-form-item>
        <el-button class="cus-search-btn" type="primary" @click="handleQuery('搜索')">搜索</el-button>
      </el-form-item>
    </el-form>
    <div class="btns">
      <el-button class="cus-reset-btn" icon="el-icon-refresh-right" @click="handleQuery('刷新')">刷新</el-button>
    </div>
  </div>
</template>

<script>
// import Treeselect from '@riophae/vue-treeselect'
// import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import dayjs from 'dayjs'
// import { treeselect } from '@/api/system/dept'

export default {
  components: {
    // Treeselect
  },
  props: {
    departmentOptions: {
      type: Array,
      require: true,
      default: () => []
    }
  },
  data () {
    return {
      queryParams: {
        deptId: null,
        dateDayArr: [
          dayjs().subtract(30, 'days').format('YYYY-MM-DD'),
          dayjs().format('YYYY-MM-DD')
        ]
      },
      // departmentOptions: [],
      courseOptions: [],
      dateTypeOptions: [
        {
          dictLabel: '年',
          dictValue: '0'
        },
        {
          dictLabel: '月',
          dictValue: '1'
        }
      ]
    }
  },
  // created () {
  //   /** 查询部门下拉树结构 */
  //   treeselect().then(response => {
  //     this.departmentOptions = response.data
  //     this.queryParams.deptId = response.data[0].id
  //   })
  // },
  watch: {
    departmentOptions (val) {
      // this.queryParams.deptId = this.[0].id
    }
  },
  methods: {
    // 归属部门&&归属人 统一调用分散给子组件
    getDepartAndBelonger () {
      // TODO
      // 所属渠道接口前置 岗位放最后
      Promise.all([]).then(res => {
        this.departAndBelongerList = [...res]
      })
    },
    handleQuery (type) {
      // TODO
      type === '搜索' && this.$emit('sendQueryParams', this.queryParams)
      if (type === '刷新') {
        this.queryParams = {
          ...this.queryParams,
          dateDayArr: [
            dayjs().subtract(30, 'days').format('YYYY-MM-DD'),
            dayjs().format('YYYY-MM-DD')
          ]
        }
        this.$emit('sendQueryParams', this.queryParams)
      }
    },
    resetQuery () {
      // TODO
    }
  }
}
</script>

<style lang="scss" scoped>
  .search-title {
    width: 100%;
    background-color: #fff;
    display: flex;
    .form {
      flex: 1;
      display: flex;
    }
    .btns {
      margin: 22px 0px;
      .cus-search-btn {
        text-align: left;
      }
      .cus-reset-btn {
        text-align: right;
        margin-right: 20px;
      }
    }
  }
</style>
