<template>
  <div class="app-container customer-page-box">
    <div class="cus-btn-box">
      <div class="cus-btn-con">
        <el-button
          class="primary-btn"
          @click="addOrUpdateHandle()"
        >添加规则</el-button>
        <!-- <el-button
          v-hasPermi="['clues:rule:add']"
          class="default-btn"
          @click="addOrUpdateHandle()"
        >重新排序</el-button> -->
      </div>
    </div>
    <div v-loading="dataListLoading" class="cus-no-page" :class="{'loading-box':dataListLoading}">
      <div v-show="dataList && dataList.length>0">
        <el-table class="cus-table-box" :data="dataList" row-key="id" stripe @selection-change="handleSelectionChange">
          <el-table-column label="序号" align="center" type="index" width="75" />
          <el-table-column label="编号" align="center" prop="id" />
          <el-table-column label="条件" align="center" width="400">
            <template slot-scope="scope">
              <div v-html="rulesFormat(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="分配" show-overflow-tooltip align="center" prop="assignObject">
            <template slot-scope="scope">
              {{ assignTypeOptionsFormat(scope.row.assignType) }}-{{ String(scope.row.assignObjectNames) }}
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" align="center" class-name="small-padding fixed-width" width="200">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="addOrUpdateHandle(scope.row.id)"
              >修改</el-button>
              <el-button
                class="del-text"
                size="mini"
                type="text"
                @click="handleDelete(scope.row.id)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- <pagination
          v-show="total>0"
          :total="total"
          :page.sync="pageNum"
          :limit.sync="pageSize"
          @pagination="getList"
        /> -->
      </div>
      <empty v-show="(!dataList || dataList.length<=0) && !dataListLoading" style="height: calc(100vh - 66px - 66px - 83px - 45px )" class="table-empty-box" />
    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="handleQuery" />
    <add-or-update-business v-if="addOrUpdateBusinessVisible" ref="addOrUpdateBusiness" @refreshDataList="handleQuery" />
  </div>
</template>

<script>
import Sortable from 'sortablejs'
import mixinViewModule from '@/mixins/view-module'
import { listCluesRule, delCluesRule, updateCluesRuleOrder } from '@/api/system/clew'
import AddOrUpdate from './../operation/add-or-update-clew'
import AddOrUpdateBusiness from './../operation/add-or-update-business'
export default {
  name: 'Clew',
  components: {
    AddOrUpdate,
    AddOrUpdateBusiness
  },
  mixins: [mixinViewModule],
  props: {
    ruleType: {
      type: String,
      default: '0'
    }
  },
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: listCluesRule,
        deleteFun: delCluesRule,
        deleteIsBatch: false
      },
      queryParams: {
        ruleType: this.ruleType
      },
      addOrUpdateBusinessVisible: false,
      // 规则key
      ruleKeyOptions: [],
      // 条件
      expressionOptions: [],
      // 时间条件
      dateExpressionOptions: [],
      // 通用条件
      generalExpressionOptions: [],
      // 规则值
      ruleValueOptions: [],
      // 渠道来源
      channelOptions: [],
      // 线索意向级别
      levelOptions: [],
      // 意向学科字典
      subjectOptions: [],
      // 分配类型
      assignTypeOptions: [],
      // 商机性质
      businessStatusOptions: [],
      // 商机规则类型
      businessRuleKeyOptions: []

    }
  },
  watch: {
  },
  created () {
    this.getDicList()
  },
  mounted () {
    this.rowDrop()
  },
  methods: {
    channelOptionsFormat (val) {
      return this.selectDictLabel(this.channelOptions, val)
    },
    levelOptionsFormat (val) {
      return this.selectDictLabel(this.levelOptions, val)
    },
    subjectOptionsFormat (val) {
      return this.selectDictLabel(this.subjectOptions, val)
    },
    ruleKeyOptionsFormat (val) {
      return this.selectDictLabel(this.ruleKeyOptions, val)
    },
    dateExpressionOptionsFormat (val) {
      return this.selectDictLabel(this.dateExpressionOptions, val)
    },
    generalExpressionOptionsFormat (val) {
      return this.selectDictLabel(this.generalExpressionOptions, val)
    },
    assignTypeOptionsFormat (val) {
      return this.selectDictLabel(this.assignTypeOptions, val)
    },
    businessStatusFormat (val) {
      return this.selectDictLabel(this.businessStatusOptions, val)
    },
    businessRuleKeyFormat (val) {
      return this.selectDictLabel(this.businessRuleKeyOptions, val)
    },
    getDicList () {
      this.getDicts('clues_item').then(response => {
        this.channelOptions = response.data
      })
      this.getDicts('clues_level').then(response => {
        this.levelOptions = response.data
      })
      this.getDicts('course_subject').then(response => {
        this.subjectOptions = response.data
      })
      // 规则类型
      this.getDicts('clue_rule').then(response => {
        this.ruleKeyOptions = response.data
      })
      // 线索创建时间表达式
      this.getDicts('clue_rule_createtime').then(response => {
        this.dateExpressionOptions = response.data
      })
      // 线索通用表达式
      this.getDicts('clue_rule_common').then(response => {
        this.generalExpressionOptions = response.data
      })
      // 分配类型
      this.getDicts('assign_type').then(response => {
        this.assignTypeOptions = response.data
      })
      // 商机性质
      this.getDicts('business_status_rule').then(response => {
        this.businessStatusOptions = response.data
      })
      // 商机规则类型
      this.getDicts('business_rule').then(response => {
        this.businessRuleKeyOptions = response.data
      })
    },
    // 规则格式化
    rulesFormat (row) {
      const ruleCondition = row.ruleCondition
      const rulesList = row.rulesList
      let rules = ''
      if (ruleCondition) {
        const ruleConditionList = ruleCondition.split(';')
        if (ruleConditionList && ruleConditionList.length > 0) {
          for (let i = 0; i < ruleConditionList.length; i++) {
            const expression = ruleConditionList[i]
            let expressionList = []
            if (expression) {
              expressionList = expression.split(',')
            }
            if (i > 0 && rules && expressionList.length > 0) {
              rules += '<br/><span class="rule-list-cus-con">或</span><br/>'
            }
            for (let j = 0; j < expressionList.length; j++) {
              if (j === 0) {
                rules += '<span class="rule-list-cus-start">(</span>'
              }
              if (j > 0) {
                rules += '<br/><span class="rule-list-cus-con">和</span><br/>'
              }
              // rules += expressionList[j]
              // 获取当前条件
              const expression = expressionList[j]
              let expressionRule = ''
              if (rulesList && rulesList.length > 0) {
                const obj = rulesList.find(item => {
                  return String(item.number) === String(expression) || 'rule' + item.number === String(expression)
                })
                if (obj) {
                  expressionRule += '<span class="rule-list-cus-index">' + obj.number + '</span>'
                  // 商机
                  if (this.ruleType === '1') {
                    expressionRule += '<span class="rule-list-cus-key">' + this.businessRuleKeyFormat(obj.ruleKey) + '</span>'
                  } else {
                    expressionRule += '<span class="rule-list-cus-key">' + this.ruleKeyOptionsFormat(obj.ruleKey) + '</span>'
                  }
                  // 判断规则类型是否为时间
                  if (obj.ruleKey === 'createTime') {
                    expressionRule += '<span class="rule-list-cus-exp">' + this.dateExpressionOptionsFormat(obj.expression) + '</span>'
                    expressionRule += '<span class="rule-list-cus-key">' + obj.ruleValue + '</span>'
                  } else if (obj.ruleKey === 'channel') {
                    expressionRule += '<span class="rule-list-cus-exp">' + this.generalExpressionOptionsFormat(obj.expression) + '</span>'
                    expressionRule += '<span class="rule-list-cus-key">' + this.channelOptionsFormat(obj.ruleValue) + '</span>'
                  } else if (obj.ruleKey === 'subject') {
                    expressionRule += '<span class="rule-list-cus-exp">' + this.generalExpressionOptionsFormat(obj.expression) + '</span>'
                    expressionRule += '<span class="rule-list-cus-key">' + this.subjectOptionsFormat(obj.ruleValue) + '</span>'
                  } else if (obj.ruleKey === 'level') {
                    expressionRule += '<span class="rule-list-cus-exp">' + this.generalExpressionOptionsFormat(obj.expression) + '</span>'
                    expressionRule += '<span class="rule-list-cus-key">' + this.levelOptionsFormat(obj.ruleValue) + '</span>'
                  } else if (obj.ruleKey === 'status') {
                    expressionRule += '<span class="rule-list-cus-exp">' + this.generalExpressionOptionsFormat(obj.expression) + '</span>'
                    expressionRule += '<span class="rule-list-cus-key">' + this.businessStatusFormat(obj.ruleValue) + '</span>'
                  } else if (obj.ruleKey === 'region') {
                    expressionRule += '<span class="rule-list-cus-exp">' + this.generalExpressionOptionsFormat(obj.expression) + '</span>'
                    expressionRule += '<span class="rule-list-cus-key">' + obj.ruleValue + '</span>'
                  }
                }
              }
              rules += expressionRule
              if (j === expressionList.length - 1) {
                rules += '<span class="rule-list-cus-end">)</span>'
              }
            }
          }
        }
      }
      return '<div class="rule-list-cus-box">' + rules + '</div>'
    },
    // 行拖拽
    rowDrop () {
      const tbody = document.querySelector('.el-table__body-wrapper tbody')
      // eslint-disable-next-line consistent-this
      const _this = this
      Sortable.create(tbody, {
        onEnd ({ newIndex, oldIndex }) {
          const currRow = _this.dataList.splice(oldIndex, 1)[0]
          _this.dataList.splice(newIndex, 0, currRow)
          console.log('_this.dataList', _this.dataList)
          const dataList = []
          for (let i = 0; i < _this.dataList.length; i++) {
            dataList.push({
              id: _this.dataList[i].id,
              ruleOrder: (i + 1)
            })
          }
          _this.sortHandle(dataList)
        }
      })
    },
    // 排序
    async sortHandle (dataList) {
      this.dataListLoading = true
      try {
        const res = await updateCluesRuleOrder(dataList)
        if (res.code !== 200) {
          this.dataListLoading = false
          return this.$message.error(res.msg)
        }
        this.handleQuery()
      } catch (error) {
        this.dataListLoading = false
      }
    },
    // 新增 / 修改
    addOrUpdateHandle (id) {
      console.log('addOrUpdateHandle1111111111  id', id)
      // 商机
      if (this.ruleType === '1') {
        this.addOrUpdateBusinessVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdateBusiness.form.id = id
          this.$refs.addOrUpdateBusiness.ruleType = this.ruleType
          this.$refs.addOrUpdateBusiness.init()
        })
      } else {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.form.id = id
          this.$refs.addOrUpdate.ruleType = this.ruleType
          this.$refs.addOrUpdate.init()
        })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.cus-btn-box{
  background: #ffffff;
  padding-right: 30px;
}
::v-deep .rule-list-cus-box{
  font-size: 13px;
  font-weight: 400;
  text-align: left;
  color: #524f63;
}
::v-deep .rule-list-cus-index{
  width: 22px;
  height: 22px;
  line-height: 20px;
  text-align: center;
  background: #ffffff;
  border: 1px solid #e9e9e8;
  border-radius: 50%;
  display: inline-block;
  box-sizing: border-box;
  margin-left: 10px;
  color: #524f63;
}
::v-deep .rule-list-cus-key{
  margin-left: 10px;
  // color: #05CC95;
  color: #333333;
}
::v-deep .rule-list-cus-exp{
  margin-left: 10px;
  color: #333333;
}
::v-deep .rule-list-cus-con{
  width: 22px;
  text-align: center;
  color: #524f63;
  display: inline-block;
  // margin-left: 10px;
  margin: 3px 0 3px 10px;
}
::v-deep .rule-list-cus-start{
  // margin-right: 10px;
  margin-right: -4px;
  color: #524f63;
}
::v-deep .rule-list-cus-end{
  margin-left: 10px;
  color: #524f63;
}

</style>
