<template>
  <!-- 添加或修改线索管理对话框 -->
  <el-dialog :title="!form.id ? ('添加'+(ruleType==='1'?'商机':'线索')+'规则') : ('修改'+(ruleType==='1'?'商机':'线索')+'规则')" :visible.sync="visible" width="80%" append-to-body :modal-append-to-body="false" :close-on-click-modal="false">
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <div class="rule-box">
        <div class="title-box">{{ ruleType==='1'?'商机':'线索' }}条件</div>
        <div v-for="(item, rulesIndex) in form.rulesList" :key="item.number" class="rule-item-box">
          <div class="rule-index">{{ item.number }}</div>
          <div class="rule-item-con">
            <!-- <el-form-item label="规则类型" prop="ruleKey"> -->
            <el-form-item label="" :prop="'rulesList.'+rulesIndex+'.ruleKey'" :rules="rules.ruleKey">
              <!-- <el-input v-model="form.channel" placeholder="请输入渠道来源" /> -->
              <el-select v-model="item.ruleKey" filterable placeholder="请选择规则类型" @change="((val)=>ruleKeyChangeHandle(val,rulesIndex))">
                <el-option
                  v-for="dict in ruleKeyOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="" :prop="'rulesList.'+rulesIndex+'.expression'" :rules="rules.expression">
              <!-- <el-input v-model="form.channel" placeholder="请输入渠道来源" /> -->
              <el-select v-model="item.expression" filterable placeholder="请选择规则条件" @change="((val)=>expressionChangeHandle(val,item.expression,rulesIndex))">
                <el-option
                  v-for="dict in item.expressionOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="" :prop="'rulesList.'+rulesIndex+'.ruleValue'" :rules="rules.ruleValue">
              <!-- <el-input v-model="form.channel" placeholder="请输入渠道来源" /> -->
              <el-select v-if="item.ruleKey!=='createTime'" v-model="item.ruleValue" filterable placeholder="请选择规则值">
                <el-option
                  v-for="dict in item.ruleValueOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
              <el-date-picker
                v-if="item.ruleKey==='createTime' && item.expression!=='between'"
                key="picker2"
                v-model="item.ruleValue"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择时间"
              />
              <el-date-picker
                v-if="item.ruleKey==='createTime' && item.expression==='between'"
                key="picker1"
                v-model="item.ruleValue"
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
          </div>
          <div class="rule-item-btn">
            <div class="cus-rule-btn" @click="delHandle(rulesIndex,item.number)">-</div>
            <div class="cus-rule-btn" @click="addHandle">+</div>
          </div>
        </div>
      </div>
      <div class="rule-box">
        <div class="title-box mb25">条件匹配：{{ expressionName }}</div>
        <div class="expression-box">
          <div v-for="(item,expressionIndex) in form.expressionList" :key="expressionIndex" class="expression-item-box">
            <span v-for="sub in form.rulesList" :key="sub.number" class="expression-item" :class="{'actived': item.includes(sub.number)}" @click="expressionItemHandle(expressionIndex,sub.number)">{{ sub.number }}</span>
          </div>
        </div>
        <div class="expression-btn">
          <el-button class="cus-search-btn" @click="addExpressionHandle">添加条件关系</el-button>
        </div>
      </div>
      <div class="rule-box">
        <div class="title-box">分配给</div>
        <div class="rule-item-con">
          <div class="rule-item-box assign-box">
            <el-form-item label="" prop="assignType">
              <el-select v-model="form.assignType" filterable placeholder="请选择" @change="assignTypeChangeHandle">
                <el-option
                  v-for="dict in assignTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item v-show="form.assignType==0&&form.assignType" label="" prop="userType">
              <el-select v-model="form.userType" filterable placeholder="请选择" @change="userTypeChangeHandle">
                <el-option
                  v-for="dict in userTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <!-- {{ form.userType }}-{{ form.assignType }} -->
            <el-form-item v-if="(form.assignType==0 && form.userType==2) || form.assignType!=0" label="" prop="userNameData">
              <!-- 部门用户 -->
              <el-select v-if="form.assignType==0" v-model="form.userNameData" placeholder="请选择" multiple popper-class="cus-select-tree" @remove-tag="userRemoveHandle">
                <el-option :value="form.userData" style="height: auto">
                  <el-tree
                    ref="tree"
                    :data="deptAndUserTreeData"
                    :render-after-expand="false"
                    :expand-on-click-node="false"
                    accordion
                    show-checkbox
                    node-key="id"
                    highlight-current
                    :props="defaultProps"
                    :default-checked-keys="form.userIdData"
                    @check-change="handleCheckChange"
                  />
                </el-option>
              </el-select>
              <!-- 部门 -->
              <el-select v-if="form.assignType==2" v-model="form.userNameData" placeholder="请选择" multiple popper-class="cus-select-tree" @remove-tag="userRemoveHandle">
                <el-option :value="form.userData" style="height: auto">
                  <el-tree
                    ref="tree"
                    :data="deptTreeList"
                    :render-after-expand="false"
                    :expand-on-click-node="false"
                    accordion
                    show-checkbox
                    node-key="id"
                    highlight-current
                    :props="defaultProps"
                    :default-checked-keys="form.userIdData"
                    @check-change="handleCheckDeptChange"
                  />
                </el-option>
              </el-select>
              <!-- 岗位 -->
              <el-select v-if="form.assignType==1" v-model="form.userNameData" multiple filterable placeholder="请选择">
                <el-option
                  v-for="dict in postList"
                  :key="dict.postId"
                  :label="dict.postName"
                  :value="dict.postId"
                />
              </el-select>
              <!-- 角色 -->
              <el-select v-if="form.assignType==3" v-model="form.userNameData" multiple filterable placeholder="请选择">
                <el-option
                  v-for="dict in roleList"
                  :key="dict.roleId"
                  :label="dict.roleName"
                  :value="dict.roleId"
                />
              </el-select>
            </el-form-item>
          </div>
        </div>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button class="cus-search-btn" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
import { deptAndUserTreeList, treeselect } from '@/api/system/dept'
import { getAllPostList } from '@/api/system/post'
import { getRoleSelect } from '@/api/system/role'
import { addCluesRule, getCluesRule, updateCluesRule } from '@/api/system/clew'
export default {
  components: {},
  filters: {},
  data () {
    return {
      visible: false,
      insert: true,
      ruleType: '0',
      maxIndex: 1,
      expressionName: '(1)',
      expressionCondition: '1',
      expressionConditionStr: 'rule1',
      // 表单参数
      form: {
        id: '',
        rulesList: [
          {
            number: 1,
            ruleKey: '',
            expression: '',
            ruleValue: '',
            expressionOptions: [],
            ruleValueOptions: []
          }
        ],
        expressionList: [
          [1]
          // [{
          //   number: 1,
          //   actived: false
          // }]
        ],
        assignType: '',
        userType: '',
        userIdData: [],
        userData: [],
        userNameData: []
      },
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
      userTypeOptions: [
        {
          dictValue: '1',
          dictLabel: '本人'
        },
        {
          dictValue: '2',
          dictLabel: '选择用户'
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      treeList: [],
      treeData: [],
      deptAndUserTreeData: [],
      postList: [],
      deptTreeList: [],
      roleList: []
    }
  },
  computed: {
    ...mapGetters(['userId', 'name']),
    rules () {
      const validUserType = (rule, value, callback) => {
        if (this.form.assignType === '0' && this.form.assignType && !this.form.userType) {
          return callback(new Error('用户类型不能为空'))
        } else {
          callback()
        }
      }
      return {
        // phone: [
        //   { required: true, message: "手机号不能为空", trigger: "blur" },
        //   { validator: isValidatePhone, message: '请输入正确的手机号' }
        // ],
        ruleKey: [{ required: true, message: '规则类型不能为空', trigger: 'change' }],
        expression: [{ required: true, message: '规则条件不能为空', trigger: 'change' }],
        ruleValue: [{ required: true, message: '规则值不能为空', trigger: 'change' }],
        assignType: [{ required: true, message: '分配类型不能为空', trigger: 'change' }],
        userType: [{ validator: validUserType, trigger: 'change' }],
        userNameData: [{ required: true, message: '归属人不能为空', trigger: 'change' }]
        // activityId: [
        //   { required: true, message: "活动信息不能为空", trigger: "blur" }
        // ],
        // weixin: [
        //   { validator: isValidateWx, message: '请输入正确的微信号' }
        // ],
        // qq: [
        //   { validator: isValidateQq, message: '请输入正确的QQ号' }
        // ]
      }
    }
  },
  watch: {
    'form.expressionList' (val) {
      // 匹配规则 [[1,2][3,4][5]] (1||2)&(3||4)&(5)
      console.log('form.expressionList', val)
      let expression = ''
      let expressionCondition = ''
      let expressionConditionStr = ''
      if (val && val.length > 0) {
        for (let i = 0; i < val.length; i++) {
          if (i > 0 && expression && val[i].length > 0) {
            expression += '或'
            expressionCondition += ';'
            expressionConditionStr += ';'
          }
          for (let j = 0; j < val[i].length; j++) {
            if (j === 0) {
              expression += '('
            }
            if (j > 0) {
              expression += '和'
              expressionCondition += ','
              expressionConditionStr += ','
            }
            expression += val[i][j]
            expressionCondition += val[i][j]
            expressionConditionStr += 'rule' + val[i][j]
            if (j === val[i].length - 1) {
              expression += ')'
            }
          }
        }
      }
      this.expressionName = expression
      this.expressionCondition = expressionCondition
      this.expressionConditionStr = expressionConditionStr
    }
  },
  created () {

  },
  mounted () {
    // console.log('this.$refs.tree', this.$refs.tree)
  },
  methods: {
    init () {
      this.insert = true
      this.visible = true
      this.$nextTick(() => {
        this.$refs.form.resetFields()
        this.maxIndex = 1
        this.form = {
          id: this.form.id,
          rulesList: [
            {
              number: 1,
              ruleKey: '',
              expression: '',
              ruleValue: '',
              expressionOptions: [],
              ruleValueOptions: []
            }
          ],
          expressionList: [
            [1]
          // [{
          //   number: 1,
          //   actived: false
          // }]
          ],
          assignType: '',
          userType: '',
          userIdData: [],
          userData: [],
          userNameData: []
        }
        this.getDicList()
        this.getPostList()
        this.getDeptTreeList()
        this.getDeptAndUserTreeList()
        this.getRoleSelect()
        // this.form.assignType = '0'
        // this.form.userType = '2'
        // this.form.userIdData = ['111', '110']
        // this.form.userData = []
        // console.log('this.$refs.tree', this.$refs.tree)
        // this.$refs.tree.setCheckedKeys(this.form.userIdData)
      })
    },
    // 获取信息
    getInfo () {
      getCluesRule(this.form.id, this.ruleType).then(res => {
        console.log('getCluesRule', res)
        if (res.code !== 200) {
          return this.$message.error(res.msg)
        }
        const dataForm = JSON.parse(JSON.stringify(res.data))
        // 线索条件
        let maxNumber = 1
        if (dataForm.rulesList && dataForm.rulesList.length > 0) {
          dataForm.rulesList.forEach(item => {
            if (item.number > maxNumber) {
              maxNumber = item.number
            }
            // 时间条件
            if (item.ruleKey === 'createTime') {
              item.expressionOptions = JSON.parse(JSON.stringify(this.dateExpressionOptions))
              if (item.expression === 'between') {
                item.ruleValue = item.ruleValue.split(',')
              }
            } else if (item.ruleKey === 'channel') {
              item.expressionOptions = JSON.parse(JSON.stringify(this.generalExpressionOptions))
              item.ruleValueOptions = JSON.parse(JSON.stringify(this.channelOptions))
            } else if (item.ruleKey === 'subject') {
              item.expressionOptions = JSON.parse(JSON.stringify(this.generalExpressionOptions))
              item.ruleValueOptions = JSON.parse(JSON.stringify(this.subjectOptions))
            } else if (item.ruleKey === 'level') {
              item.expressionOptions = JSON.parse(JSON.stringify(this.generalExpressionOptions))
              item.ruleValueOptions = JSON.parse(JSON.stringify(this.levelOptions))
            }
          })
        }
        this.maxIndex = maxNumber
        // 条件匹配：
        const expressionList = []
        if (dataForm.ruleCondition) {
          const ruleCondition = dataForm.ruleCondition.split(';')
          ruleCondition.forEach(item => {
            // 转为Number类型
            expressionList.push(item.split(',').map(sub => {
              if (/^rule/.test(sub)) {
                return Number(sub.substring(4))
              } else {
                return Number(sub)
              }
            }))
          })
          dataForm.expressionList = expressionList
        }
        // 分配
        if (dataForm.assignType !== '1' && dataForm.assignType !== '3') {
          dataForm.userIdData = dataForm.assignObject.split(',')
          dataForm.userNameData = dataForm.assignObjectNames
          const userData = []
          if (dataForm.userIdData && dataForm.userIdData.length > 0) {
            for (let i = 0; i < dataForm.userIdData.length > 0; i++) {
              userData.push({
                id: dataForm.userIdData[i],
                label: dataForm.userNameData[i]
              })
            }
            dataForm.userData = userData
          }
        } else {
          // dataForm.userNameData = dataForm.assignObject.split(',')
          dataForm.userNameData = dataForm.assignObject.split(',').map(item => Number(item))
        }
        // 用户分配类型
        dataForm.userType = ''
        if (dataForm.assignType === '0') {
          if (dataForm.userIdData.length === 1 && dataForm.userIdData[0] === String(this.userId)) {
            dataForm.userType = '1'
          } else {
            dataForm.userType = '2'
          }
        }
        console.log('-=--=dataForm333=-', dataForm)
        this.form = {
          ...this.form,
          ...dataForm
        }
        console.log('-=--=form=-', this.form)
      }).catch(() => {})
    },
    getDicList () {
      Promise.all([
        this.getDicts('clues_item').then(response => {
          this.channelOptions = response.data
        }),
        this.getDicts('clues_level').then(response => {
          this.levelOptions = response.data
        }),
        this.getDicts('course_subject').then(response => {
          this.subjectOptions = response.data
        }),
        // 规则类型
        this.getDicts('clue_rule').then(response => {
          this.ruleKeyOptions = response.data
        }),
        // 线索创建时间表达式
        this.getDicts('clue_rule_createtime').then(response => {
          this.dateExpressionOptions = response.data
        }),
        // 线索通用表达式
        this.getDicts('clue_rule_common').then(response => {
          this.generalExpressionOptions = response.data
        }),
        // 分配类型
        this.getDicts('assign_type').then(response => {
          this.assignTypeOptions = response.data
        })
      ]).then(() => {
        if (this.form.id) {
          this.getInfo()
        }
      })
    },
    // 获取部门和人员树
    async getDeptAndUserTreeList () {
      const res = await deptAndUserTreeList()
      if (res.code !== 200) {
        return false
      }
      this.deptAndUserTreeData = res.data
    },
    // 获取岗位列表
    async getPostList () {
      const res = await getAllPostList()
      if (res.code !== 200) {
        return false
      }
      this.postList = res.data
    },
    async getRoleSelect () {
      const res = await getRoleSelect()
      if (res.code !== 200) {
        return false
      }
      this.roleList = res.data
    },
    // 获取部门树列表
    async getDeptTreeList () {
      const res = await treeselect()
      if (res.code !== 200) {
        return false
      }
      this.deptTreeList = res.data
    },
    addHandle () {
      if (this.form.rulesList.length >= 10) {
        return this.$message.error('最多添加10条规则')
      }
      this.maxIndex++
      this.form.rulesList.push({
        number: this.maxIndex,
        ruleKey: '',
        expression: '',
        ruleValue: ''
      })
      this.expressionItemAllHandle(this.maxIndex)
    },
    delHandle (index, number) {
      console.log('delHandle', number)
      if (this.form.rulesList.length <= 1) {
        return this.$message.error('至少保留一条规则')
      }
      this.form.rulesList.splice(index, 1)
      this.expressionItemAllHandle(number)
    },
    ruleKeyChangeHandle (val, index) {
      console.log('val', val)
      this.form.rulesList[index].ruleValueOptions = []
      this.form.rulesList[index].ruleValue = null
      this.form.rulesList[index].expression = ''
      // 时间条件
      if (val === 'createTime') {
        console.log('this.dateExpressionOptions', this.dateExpressionOptions)
        this.form.rulesList[index].expressionOptions = JSON.parse(JSON.stringify(this.dateExpressionOptions))
      } else if (val === 'channel') {
        this.form.rulesList[index].expressionOptions = JSON.parse(JSON.stringify(this.generalExpressionOptions))
        this.form.rulesList[index].ruleValueOptions = JSON.parse(JSON.stringify(this.channelOptions))
      } else if (val === 'subject') {
        this.form.rulesList[index].expressionOptions = JSON.parse(JSON.stringify(this.generalExpressionOptions))
        this.form.rulesList[index].ruleValueOptions = JSON.parse(JSON.stringify(this.subjectOptions))
      } else if (val === 'level') {
        this.form.rulesList[index].expressionOptions = JSON.parse(JSON.stringify(this.generalExpressionOptions))
        this.form.rulesList[index].ruleValueOptions = JSON.parse(JSON.stringify(this.levelOptions))
      }
    },
    expressionChangeHandle (val, expression, index) {
      console.log('val', val)
      console.log('expression', expression)
      console.log('index', index)
      // 时间条件
      if (this.form.rulesList[index].ruleKey === 'createTime') {
        this.form.rulesList[index].ruleValueOptions = []
        if ((Array.isArray(this.form.rulesList[index].ruleValue) && val !== 'between') || (!Array.isArray(this.form.rulesList[index].ruleValue) && val === 'between')) {
          this.form.rulesList[index].ruleValue = null
        }
      }
    },
    // 删除当前行点击项
    expressionItemHandle (index, val, flag) {
      console.log(index)
      console.log(val)
      console.log(this.form.expressionList)
      console.log(this.form.expressionList[index])
      const expressionIndex = this.form.expressionList[index].findIndex(item => String(item) === String(val))
      if (expressionIndex >= 0) {
        this.form.expressionList[index].splice(expressionIndex, 1)
      } else {
        if (!flag) {
          this.form.expressionList[index].push(val)
        }
      }
    },
    // 删除所有条件匹配的项
    expressionItemAllHandle (val) {
      console.log('expressionItemAllHandle', val)
      if (this.form.expressionList && this.form.expressionList.length > 0) {
        for (let i = 0; i < this.form.expressionList.length; i++) {
          this.expressionItemHandle(i, val, true)
        }
      }
    },
    // 添加条件关系
    addExpressionHandle () {
      const list = []
      this.form.rulesList.forEach(item => {
        list.push(item.number)
      })
      this.form.expressionList.push(list)
    },
    // tree数据处理方法
    handleCheckChange (data, checked) {
      console.log('data', data)
      console.log('checked', checked)
      // console.log(this.$refs.tree.getCheckedNodes(false, true))
      // console.log(this.$refs.tree.getCheckedKeys())
      if (checked) { // tree实现单选
        if (data.type === 1) {
          const index = this.form.userData.findIndex(item => String(item.id) === String(data.id))
          if (index < 0) {
            this.form.userData.push(data)
            this.form.userNameData.push(data.label)
            this.form.userIdData.push(data.id)
          }
        }
      } else {
        if (data.type === 1) {
          const index = this.form.userData.findIndex(item => String(item.id) === String(data.id))
          if (index >= 0) {
            this.form.userData.splice(index, 1)
            this.form.userNameData.splice(index, 1)
            this.form.userIdData.splice(index, 1)
          }
        }
      }
      console.log('userData', this.form.userData)
      console.log('userNameData', this.form.userNameData)
      console.log('userIdData', this.form.userIdData)
    },
    handleCheckDeptChange (data, checked) {
      console.log('handleCheckDeptChange', data, checked)
      if (!data.children || data.children.length <= 0) {
        if (checked) { // tree实现单选
          const index = this.form.userData.findIndex(item => String(item.id) === String(data.id))
          if (index < 0) {
            this.form.userData.push(data)
            this.form.userNameData.push(data.label)
            this.form.userIdData.push(data.id)
          }
        } else {
          console.log('-==-this.form.userData=-', this.form.userData)
          console.log('-==-this.form.userData=-', data.id)
          const index = this.form.userData.findIndex(item => String(item.id) === String(data.id))
          console.log('-==-this.form.userData=-', index)
          if (index >= 0) {
            this.form.userData.splice(index, 1)
            this.form.userNameData.splice(index, 1)
            this.form.userIdData.splice(index, 1)
          }
        }
      }
    },
    userChangeHandle (val) {
      console.log('-==-=userChangeHandle-=-=-', val)
    },
    userRemoveHandle (val) {
      console.log('-==-=userRemoveHandle-=-=-', val)
      // console.log('this.form.userNameData', this.form.userNameData)
      // console.log('this.form.userData', this.form.userData)
      const index = this.form.userData.findIndex(item => item.label === val)
      // console.log('index', index)
      if (index >= 0) {
        // this.form.userNameData.splice(index,1)
        this.form.userData.splice(index, 1)
        this.form.userIdData.splice(index, 1)
        // this.form.userNameData.splice(index, 1)
      }
      // console.log('this.form.userData', this.form.userData)
      this.$refs.tree.setCheckedNodes(this.form.userData)
      // console.log(this.$refs.tree.getCheckedKeys())
    },
    assignTypeChangeHandle () {
      this.form.userType = ''
      this.form.userData = []
      this.form.userIdData = []
      this.form.userNameData = []
    },
    userTypeChangeHandle () {
      if (this.form.userType === '1') {
        this.form.userData = []
        this.form.userIdData = [this.userId]
        this.form.userNameData = [this.name]
      } else {
        this.form.userData = []
        this.form.userIdData = []
        this.form.userNameData = []
      }
    },
    /** 提交按钮 */
    submitForm () {
      console.log('this.form.assignType', this.form.assignType)
      console.log('this.form.userNameData', this.form.userNameData)
      console.log('this.form.userIdData', this.form.userIdData)
      console.log(this.form)
      this.$refs.form.validate(async valid => {
        if (valid) {
          if (!this.expressionName) {
            return this.$message.error('请选择条件匹配')
          }
          const rulesList = []
          this.form.rulesList.forEach(item => {
            rulesList.push({
              number: item.number,
              ruleKey: item.ruleKey,
              expression: item.expression,
              ruleValue: String(item.ruleValue)
            })
          })
          const form = {
            id: this.form.id,
            rulesList,
            ruleCondition: this.expressionConditionStr,
            assignType: this.form.assignType,
            assignObject: (this.form.assignType === '1' || this.form.assignType === '3') ? String(this.form.userNameData) : String(this.form.userIdData),
            type: this.ruleType
          }
          console.log('-=-=-=form-=-==', form)
          // return
          const res = await (this.form.id ? updateCluesRule : addCluesRule)(form)
          if (res.code !== 200) {
            return this.$message.error(res.msg)
          }
          this.msgSuccess('操作成功')
          this.visible = false
          this.$emit('refreshDataList')
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.cus-dialog-form-box{
  margin-left: 0;
}
.rule-box{
  width: 100%;
  margin-bottom: 10px;
  .title-box{
    height: 17px;
    line-height: 17px;
    font-size: 12px;
    font-weight: 500;
    color: #666666;
    margin-bottom: 16px;
  }
  .el-form-item{
    width: calc(100%/3);
  }
  .assign-box{
    margin-left: -28px;
  }
  .rule-item-box{
    // width: 100%;
    display: flex;
    align-items: center;
    .rule-index{
      width: 30px;
      height: 30px;
      line-height: 30px;
      background: #ffffff;
      border: 1px solid #e9e9e8;
      border-radius: 50%;
      text-align: center;
      box-sizing: border-box;
      cursor: pointer;
      font-size: 16px;
      color: #524f63;
      margin-bottom: 17px;
    }
    .rule-item-con{
      flex: 1;
      display: flex;
    }
    .rule-item-btn{
      width: 76px;
      margin-left: 26px;
      display: flex;
      margin-bottom: 15px;
      .cus-rule-btn{
        width: 32px;
        height: 32px;
        line-height: 28px;
        background: #f9f9f9;
        border: 1px solid #e9e9eb;
        border-radius: 50%;
        font-size: 20px;
        color: #666666;
        text-align: center;
        box-sizing: border-box;
        cursor: pointer;
        margin-left: 12px;
        &:first-child{
          margin-left: 0;
        }
      }
      .rule-add{
        background: #05cc95;
        border: 1px solid #05cc95;
        color: #ffffff;
        margin-left: 12px;
      }
    }
  }
  .expression-box{
    .expression-item-box{
      margin-bottom: 10px;
      display: flex;
      .expression-item{
        width: 36px;
        height: 36px;
        line-height: 36px;
        background: #f9f9f9;
        font-size: 14px;
        color: #666666;
        border-radius: 6px;
        cursor: pointer;
        margin-right: 12px;
        text-align: center;
        &.actived{
          background: #05cc95;
          color: #ffffff;
        }
      }
    }
  }
  .expression-btn{
    padding: 18px 0 22px;
  }
  .mb25{
    margin-bottom: 25px;
  }
}
</style>
