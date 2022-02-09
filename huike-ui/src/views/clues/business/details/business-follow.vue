<template>
  <div class="customer-detail-box">
    <el-row>
      <el-col :span="24">
        <!-- 客户资料 -->
        <div class="l-box">
          <el-form ref="form" class="cus-search-box cus-form-box" :model="form" :rules="rules">
            <div class="l-top-box">
              <div class="title-box">
                <span class="title-label">客户资料<span class="des">ID&nbsp;&nbsp;{{ form.id }}</span></span>
              </div>
              <div class="con-box">
                <el-form-item label="客户姓名" prop="name">
                  <el-input v-model="form.name" placeholder="请输入客户姓名" />
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                  <el-input v-model="form.phone" placeholder="请输入手机号" />
                </el-form-item>
                <el-form-item label="职业" prop="occupation">
                  <el-select v-model="form.occupation" placeholder="请选择职业" clearable>
                    <el-option
                      v-for="dict in occupationOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="学历" prop="education">
                  <el-select v-model="form.education" placeholder="请选择学历" clearable>
                    <el-option
                      v-for="dict in educationOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="所在地区">
                  <el-cascader
                    v-model="area"
                    size="large"
                    :options="provinceAndCityDataOptions"
                    @change="handleChange"
                  />
                </el-form-item>
                <el-form-item label="微信号" prop="weixin">
                  <el-input v-model="form.weixin" placeholder="请输入微信号" />
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                  <el-input-number v-model="form.age" :precision="0" :min="1" :max="99" :step="1" placeholder="请输入年龄" controls-position="right" />
                </el-form-item>
                <el-form-item label="专业" prop="major">
                  <el-select v-model="form.major" placeholder="请选择专业" clearable>
                    <el-option
                      v-for="dict in majorOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="在职情况" prop="job">
                  <el-select v-model="form.job" placeholder="请选择在职情况" clearable>
                    <el-option
                      v-for="dict in jobOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="当前薪资" prop="salary">
                  <el-select v-model="form.salary" placeholder="请选择当前薪资" clearable>
                    <el-option
                      v-for="dict in salaryOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="qq" prop="qq">
                  <el-input v-model="form.qq" placeholder="请输入qq" />
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                  <el-radio-group v-model="form.sex">
                    <el-radio
                      v-for="dict in sexOptions"
                      :key="dict.dictValue"
                      :label="dict.dictValue"
                    >{{ dict.dictLabel }}</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="目标薪资" prop="expectedSalary">
                  <el-select v-model="form.expectedSalary" placeholder="请选择目标薪资" clearable>
                    <el-option
                      v-for="dict in salaryOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item class="wp-50" label="备注" prop="remark">
                  <el-input v-model="form.remark" type="text" :rows="1" show-word-limit placeholder="请输入备注" maxlength="20" />
                </el-form-item>
              <!-- </el-form> -->
              </div>
            </div>

            <!-- 客户意向 -->
            <div class="l-top-box">
              <div class="title-box">
                <span class="title-label">客户意向</span>
              </div>
              <div class="con-box">
                <!-- <el-form ref="form" class="cus-search-box cus-form-box" :model="form" :rules="rules"> -->
                <el-form-item label="意向学科" prop="subject">
                  <el-select v-model="form.subject" placeholder="请选择意向学科" clearable @change="subjectChangeHandle">
                    <el-option
                      v-for="dict in subjectOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="学习原因" prop="reasons">
                  <el-input v-model="form.reasons" placeholder="请输入学习原因" maxlength="20" />
                </el-form-item>
                <el-form-item label="职业计划" prop="plan">
                  <el-input v-model="form.plan" placeholder="请输入职业计划" maxlength="20" />
                </el-form-item>
                <el-form-item label="时间计划" prop="planTime">
                  <el-date-picker
                    v-model="form.planTime"
                    clearable

                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="请选择时间计划"
                  />
                </el-form-item>
                <el-form-item label="意向课程" prop="courseId">
                  <el-select v-model="form.courseId" placeholder="请选择意向课程" clearable @change="courseHandle">
                    <el-option
                      v-for="dict in courseOptions"
                      :key="dict.id"
                      :label="dict.name"
                      :value="dict.id"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="意向金额">
                  <el-input v-model="interestAmount" disabled placeholder="请输入意向金额" />
                </el-form-item>
                <el-form-item class="wp-50" label="其他意向" prop="otherIntention">
                  <el-input v-model="form.otherIntention" type="text" :rows="1" show-word-limit placeholder="请输入其他意向" maxlength="20" />
                </el-form-item>
              <!-- </el-form> -->
              </div>
            </div>

            <!-- 添加沟通记录 -->
            <div class="l-top-box">
              <div class="title-box">
                添加沟通记录
              </div>
              <div class="con-box">
                <!-- <el-form ref="form" class="cus-search-box cus-form-box" :model="form" :rules="rules"> -->
                <el-form-item label="跟进状态" prop="trackStatus">
                  <el-select v-model="form.trackStatus" placeholder="请选择跟进状态" clearable>
                    <el-option
                      v-for="dict in trackStatusOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="下次跟进时间" prop="nextTime">
                  <el-date-picker
                    v-model="form.nextTime"
                    clearable
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm"
                    format="yyyy-MM-dd HH:mm"
                    placeholder="请选择下次跟进时间"
                    :picker-options="pickerOptions"
                    default-time="12:00:00"
                  />
                </el-form-item>
                <el-form-item class="wp-50" />
                <el-form-item class="wp-50" label="沟通重点" prop="keyItemsArray">
                  <el-select v-model="form.keyItemsArray" multiple placeholder="请选择沟通重点" clearable>
                    <el-option
                      v-for="dict in keyItemsOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item class="wp-50" label="沟通纪要" prop="record">
                  <el-input v-model="form.record" type="text" show-word-limit placeholder="请输入沟通纪要" maxlength="20" />
                </el-form-item>
                <el-form-item class="search-btn wp-100">
                  <el-button v-hasPermi="['business:record:add']" :loading="saveLoading" class="cus-search-btn" @click="submitForm()">保存</el-button>
                  <el-button
                    v-hasPermi="['business:business:back']"
                    class="default-btn"
                    @click="addOrUpdateHandle()"
                  >踢回公海</el-button>
                  <el-button
                    v-hasPermi="['contract:contract:change']"
                    class="default-btn"
                    @click="addOrUpdateContractHandle()"
                  >转交成客户</el-button>
                </el-form-item>

              </div>
            </div>
          </el-form>

          <!-- 历史沟通记录 -->
          <div class="l-table-box">
            <div class="table-title-box">
              历史沟通记录
            </div>
            <div class="table-con">
              <div v-loading="dataListLoading" :class="{'loading-box':dataListLoading}">
                <div v-show="dataList && dataList.length>0">
                  <el-table class="cus-table-box" :data="dataList" stripe>
                    <el-table-column label="本次沟通时间" align="center" min-width="140">
                      <template slot-scope="scope">
                        <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="沟通重点" align="center" prop="id" min-width="150">
                      <template slot-scope="scope">
                        <div v-if="scope.row.keyItems">
                          <span v-for="item in (scope.row.keyItems.split(','))" :key="item" class="cus-tag-box">
                            {{ keyItemsFormat(item) }}
                          </span>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column label="沟通状态" align="center" min-width="150">
                      <template slot-scope="scope">
                        <div class="cus-status">{{ trackStatusFormat(scope.row.trackStatus) }}</div>
                        <div v-show="scope.row.nextTime" class="cus-date">下次跟进{{ parseTime(scope.row.nextTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</div>
                      </template>
                    </el-table-column>
                    <el-table-column label="沟通纪要" align="center" prop="record" min-width="150" />
                    <el-table-column label="沟通人" align="center" prop="createBy" min-width="100" />
                  </el-table>
                </div>
                <empty v-show="(!dataList || dataList.length<=0) && !dataListLoading" :box-height="645" class="table-empty-box" />
              </div>
            </div>
          </div>

        </div>
      </el-col>
    </el-row>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="goPage" />
    <add-or-update-contract v-if="addOrUpdateContractVisible" ref="addOrUpdateContract" @refreshDataList="goPage" />
  </div>
</template>

<script>
import { getBusiness, addBusinessRecord, listBusinessRecord } from '@/api/business/business'
import { listCourseSelect } from '@/api/clues/course'
import { isValidateCnName, isValidatePhone, isValidateQq, isValidateWx } from '@/utils/validate'
import { provinceAndCityData, CodeToText, TextToCode } from 'element-china-area-data'
import AddOrUpdate from './../operation/report-business'
import AddOrUpdateContract from './../operation/add-or-update-contract'
export default {
  components: {
    AddOrUpdate,
    AddOrUpdateContract
  },
  filters: {},
  data () {
    return {
      addOrUpdateVisible: false,
      addOrUpdateContractVisible: false,
      saveLoading: false,
      dataListLoading: false,
      interestAmount: 0,
      // 详情数据
      dataForm: {
        id: ''
      },
      area: null,
      // 表单参数
      form: {
        id: null,
        name: null,
        phone: null,
        occupation: null,
        education: null,
        provinces: null,
        city: null,
        weixin: null,
        age: undefined,
        major: null,
        job: null,
        salary: null,
        qq: null,
        sex: 0,
        expectedSalary: null,
        remark: null,
        subject: null,
        reasons: null,
        plan: null,
        planTime: null,
        courseId: null,
        otherIntention: null,
        trackStatus: null,
        nextTime: null,
        keyItems: null,
        keyItemsArray: [],
        record: null
      },
      occupationOptions: [],
      educationOptions: [],
      majorOptions: [],
      jobOptions: [],
      salaryOptions: [],
      // 性能字典
      sexOptions: [],
      // 意向学科字典
      subjectOptions: [],
      // 状态字典
      statusOptions: [],
      channelOptions: [],
      provinceAndCityDataOptions: provinceAndCityData,
      courseOptions: [],
      trackStatusOptions: [],
      keyItemsOptions: [],
      dataList: [],
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7 // 禁用以前的日期，今天不禁用
        }
      }
    }
  },
  computed: {
    rules () {
      const validDateTime = (rule, value, callback) => {
        if ((new Date(value)).getTime() < (new Date()).getTime()) {
          return callback(new Error('下次跟进时间不能小于当前时间'))
        } else {
          callback()
        }
      }
      return {
        name: [
          { required: true, message: '客户姓名不能为空', trigger: 'blur' },
          { validator: isValidateCnName, message: '请输入真实的姓名' }
        ],
        phone: [
          { required: true, message: '手机号不能为空', trigger: 'blur' },
          { validator: isValidatePhone, message: '请输入正确的手机号' }
        ],
        subject: [{ required: true, message: '意向学科不能为空', trigger: 'change' }],
        courseId: [{ required: true, message: '意向课程不能为空', trigger: 'change' }],
        otherIntention: [{ required: true, message: '其他意向不能为空', trigger: 'blur' }],
        trackStatus: [{ required: true, message: '跟进状态不能为空', trigger: 'change' }],
        keyItemsArray: [{ required: true, message: '沟通重点不能为空', trigger: 'change' }],
        record: [{ required: true, message: '沟通纪要不能为空', trigger: 'blur' }],
        nextTime: [
          { required: true, message: '下次跟进时间不能为空', trigger: 'blur' },
          { validator: validDateTime, message: '下次跟进时间不能小于当前时间' }
        ],
        weixin: [{ validator: isValidateWx, message: '请输入正确的微信号' }],
        qq: [{ validator: isValidateQq, message: '请输入正确的QQ号' }]
      }
    }
  },
  watch: {},
  created () {
    const queryParams = this.$route.query
    this.dataForm = {
      ...this.dataForm,
      ...queryParams
    }
    this.getDicList()
    this.getInfo()

    this.getRecordList()
  },
  mounted () {},
  methods: {
    // 性能字典翻译
    sexFormat (val) {
      return this.selectDictLabel(this.sexOptions, val)
    },
    // 意向等级字典翻译
    levelFormat (val) {
      return this.selectDictLabel(this.levelOptions, val)
    },
    // 意向学科字典翻译
    subjectFormat (val) {
      return this.selectDictLabel(this.subjectOptions, val)
    },
    // 状态字典翻译
    statusFormat (val) {
      return this.selectDictLabel(this.statusOptions, val)
    },
    // 渠道来源字典翻译
    channelOptionsFormat (val) {
      return this.selectDictLabel(this.channelOptions, val)
    },
    keyItemsFormat (val) {
      return this.selectDictLabel(this.keyItemsOptions, val)
    },
    trackStatusFormat (val) {
      return this.selectDictLabel(this.trackStatusOptions, val)
    },
    // 获取信息
    getInfo () {
      getBusiness(this.dataForm.id).then(res => {
        if (res.code !== 200) {
          return this.$message.error(res.msg)
        }
        this.form = {
          ...this.form,
          ...res.data
        }
        this.form.age = this.form.age ? this.form.age : undefined
        if (this.form.provinces && this.form.city) {
          this.area = [TextToCode[this.form.provinces].code, TextToCode[this.form.provinces][this.form.city].code]
        }
        if (this.form.focus) {
          this.form.focusArray = this.form.focus.split(',')
        }
        this.clearText()
        if (this.form.subject) {
          this.getCourseList()
        }
      }).catch(() => {})
    },
    clearText () {
      this.$nextTick(() => {
        this.$refs.form.clearValidate('trackStatus')
        this.$refs.form.clearValidate('nextTime')
        this.$refs.form.clearValidate('keyItems')
        this.$refs.form.clearValidate('keyItemsArray')
        this.$refs.form.clearValidate('record')
      })
      this.form.trackStatus = null
      this.form.nextTime = null
      this.form.keyItems = null
      this.form.keyItemsArray = []
      this.form.record = null
    },
    getDicList () {
      this.getDicts('sys_user_sex').then(response => {
        this.sexOptions = response.data
      })
      this.getDicts('course_subject').then(response => {
        this.subjectOptions = response.data
      })
      this.getDicts('clue_status').then(response => {
        this.statusOptions = response.data
      })
      this.getDicts('clues_item').then(response => {
        this.channelOptions = response.data
      })
      this.getDicts('communication_point').then(response => {
        this.keyItemsOptions = response.data
      })
      this.getDicts('occupation').then(response => {
        this.occupationOptions = response.data
      })
      this.getDicts('education').then(response => {
        this.educationOptions = response.data
      })
      this.getDicts('major').then(response => {
        this.majorOptions = response.data
      })
      this.getDicts('job').then(response => {
        this.jobOptions = response.data
      })
      this.getDicts('salary').then(response => {
        this.salaryOptions = response.data
      })
      this.getDicts('track_status').then(response => {
        this.trackStatusOptions = response.data
      })
    },
    subjectChangeHandle () {
      this.$nextTick(() => {
        this.$refs.form.clearValidate('courseId')
      })
      this.form.courseId = null
      this.getCourseList()
    },
    // 所有课程列表
    async getCourseList () {
      this.courseOptions = []
      this.interestAmount = 0
      const res = await listCourseSelect({ subject: this.form.subject })
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.courseOptions = res.data
      if (this.form.courseId !== null && this.form.courseId !== '') {
        this.courseHandle(this.form.courseId)
      }
    },
    async getRecordList () {
      const res = await listBusinessRecord({ businessId: this.dataForm.id })
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.dataList = res.data
    },
    addOrUpdateHandle () {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.form.id = this.dataForm.id
        this.$refs.addOrUpdate.init()
      })
    },
    addOrUpdateContractHandle () {
      this.addOrUpdateContractVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdateContract.form.id = this.dataForm.id
        this.$refs.addOrUpdateContract.init()
      })
    },
    handleChange (value) {
      console.log(value)
      if (value && value.length > 0) {
        this.form.provinces = CodeToText[value[0]]
        this.form.city = CodeToText[value[1]]
      } else {
        this.form.provinces = ''
        this.form.city = ''
      }
    },
    /** 提交按钮 */
    submitForm () {
      console.log(this.form)
      this.form.keyItems = String(this.form.keyItemsArray)
      const form = JSON.parse(JSON.stringify(this.form))
      form.id = ''
      form.businessId = this.dataForm.id
      this.$refs.form.validate(valid => {
        if (valid) {
          console.log('valid')
          this.saveLoading = true
          addBusinessRecord(form).then(res => {
            this.saveLoading = false
            if (res.code !== 200) {
              return this.$message.error(res.msg)
            }
            this.msgSuccess('操作成功')
            this.getRecordList()
            // 清空添加沟通记录表单
            this.clearText()
          }).catch(() => {
            this.saveLoading = false
          })
        }
      })
    },
    courseHandle (id) {
      const course = this.courseOptions.find(item => item.id === id)
      if (course) {
        this.interestAmount = course.price
      } else {
        this.interestAmount = 0
      }
    },
    goPage () {
      this.$router.push({ path: '/business' })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/modules/clue-details.scss";
.customer-detail-box{
  .cus-form-box{
    display: block;
    margin-left: 0;
  }
  .l-box{
    box-shadow: none;
    .l-top-box{
      padding: 0 20px 0 0;
      .title-box{
        height: 66px;
        line-height: 66px;
        border-bottom: 2px solid #e9e9e8;
        color: #333333;
        font-size: 14px;
        padding-left: 30px;
        .des{
          color: #aeb5c4;
          font-size: 14px;
          font-weight: 400;
          padding-left: 10px;
        }
      }
      .con-box{
        padding: 25px 36px 0 44px;
        margin-left: -28px;
        display: flex;
        flex-wrap: wrap;
        .el-form-item{
          width: 25%;
        }
      }
    }
    .l-table-box{
      .table-title-box{
        height: 43px;
        line-height: 43px;
        background: #f9f9f9;
        font-size: 14px;
        color: #333333;
        font-weight: 500;
        padding-left: 30px;
      }
      .table-con{
        padding: 24px 56px 67px 32px;
      }
    }
  }
  .cus-tag-box{
    height: 28px;
    padding: 0 17px;
    line-height: 26px;
    border: 1px solid #e9e9eb;
    display: inline-block;
    color: #666666;
    font-size: 14px;
    background: #ffffff;
    border-radius: 5px;
    margin: 4px 0 4px 8px;
    // &+.cus-tag-box{
    //   margin-left: 8px;
    // }
  }
  .cus-date{
    color: #666666;
  }
}
</style>
