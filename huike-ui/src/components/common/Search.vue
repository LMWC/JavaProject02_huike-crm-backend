<template>
  <div class="search">
    <div class="search-time">
      <!-- 原有逻辑已变动 -->
      <!-- <el-date-picker v-model="dateArr" clearable unlink-panels type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" /> -->
      <!-- 新逻辑 -->
      <el-form ref="searchForm" :model="searchForm" class="search-form">
        <!-- <el-form-item label="选择日期" class="form-item">
          <el-select v-model="searchForm.dateType" style="width: 100px;margin-right:10px;">
            <el-option v-for="(type) in dateTypeOptions" :key="type.dictValue" :label="type.dictLabel" :value="type.dictValue" />
          </el-select>
        </el-form-item> -->
        <el-form-item style="padding-left: 0px;">
          <!-- <el-date-picker
            v-if="searchForm.dateType === '0'"
            v-model="searchForm.dateYear"
            type="year"
            value-format="yyyy-MM-dd"
            placeholder="请选择"
            @change="getSearchFormData"
          />
          <el-date-picker
            v-if="searchForm.dateType === '1'"
            v-model="searchForm.dateMonth"
            type="month"
            value-format="yyyy-MM-dd"
            placeholder="请选择"
            @change="getSearchFormData"
          /> -->
          <!-- v-if="searchForm.dateType === '2'" -->
          <el-date-picker v-model="searchForm.dateDayArr" clearable unlink-panels type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" value-format="yyyy-MM-dd" @change="getSearchFormData" />
        </el-form-item>
      </el-form>

    </div>
    <div v-if="showBtns" class="search-btn">
      <Btns :btns-params="btnsParams" :index-btns="indexBtns" @sendBtnIndex="getBtnIndex" />
    </div>
  </div>
</template>

<script>
// 引入可拓展性的动画按钮
import Btns from './Btns.vue'
import dayjs from 'dayjs'

export default {
  components: {
    Btns
  },
  props: {
    btnsParams: {
      type: Array,
      // required: true,
      default: () => []
    },
    showBtns: {
      type: Boolean,
      required: true,
      default: false
    },
    indexBtns: {
      type: Number,
      required: false,
      default: 0
    }
  },
  data () {
    return {
      searchForm: {
        dateType: '2',
        dateYear: '',
        dateMonth: '',
        dateDayArr: [
          dayjs().subtract(7, 'days').format('YYYY-MM-DD'),
          dayjs().format('YYYY-MM-DD')
        ]
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
  },
  methods: {
    resetForm () {
      this.searchForm = {
        dateType: '2',
        dateYear: '',
        dateMonth: '',
        dateDayArr: [
          dayjs().subtract(7, 'days').format('YYYY-MM-DD'),
          dayjs().format('YYYY-MM-DD')
        ]
      }
    },
    getBtnIndex (data) {
      this.resetForm()
      this.$emit('sendBtnIndexToCustomer', data)
    },
    getSearchFormData () {
      this.$emit('searchFormData', this.searchForm)
      switch (this.searchForm.dateType) {
        case '0':
          this.searchForm.dateMonth = ''
          this.searchForm.dateDayArr = [
            dayjs().subtract(7, 'days').format('YYYY-MM-DD'),
            dayjs().format('YYYY-MM-DD')
          ]
          break
        case '1':
          this.searchForm.dateYear = ''
          this.searchForm.dateDayArr = [
            dayjs().subtract(7, 'days').format('YYYY-MM-DD'),
            dayjs().format('YYYY-MM-DD')
          ]
          break
        case '2':
          this.searchForm.dateYear = ''
          this.searchForm.dateMonth = ''
          break
        default:
          this.searchForm.dateYear = ''
          this.searchForm.dateMonth = ''
          this.searchForm.dateDayArr = []
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .search {
    width: 100%;
    display: flex;
    margin-top: 28px;
    padding-left: 20px;
    box-sizing: border-box;

    .search-time, .search-btn {
      flex: 1;
    }
    .search-time {
      .search-form {
        display: flex;
        flex-wrap: wrap;
        padding: 0;
        .form-item {
          margin-left: 6px;
          display: flex;
        }
      }
    }
    .search-btn {
      padding-right: 20px;
      box-sizing: border-box;
    }
  }

</style>
