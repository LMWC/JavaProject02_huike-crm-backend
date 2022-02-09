<template>
  <div class="customer-detail-box">
    <el-row>
      <el-col :span="24">
        <!-- 客户资料 -->
        <div class="l-box">
          <div class="l-top-box">
            <div class="title-box">
              <span class="title-label">客户资料<span class="des">ID&nbsp;&nbsp;{{ form.id }}</span></span>
            </div>
            <div class="con-box">
              <div class="info-box">
                <div class="info-list mt0">
                  <span class="label">客户姓名</span>
                  <span class="des">{{ form.name }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">手机号</span>
                  <span class="des">{{ dataForm.pageType!=='pool'?form.phone:(phoneFormat(form.phone)) }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">职业</span>
                  <span class="des">{{ occupationFormat(form.occupation) }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">学历</span>
                  <span class="des">{{ educationFormat(form.education) }}</span>
                </div>
                <div class="info-list">
                  <span class="label">所在地区</span>
                  <span class="des">{{ form.provinces }}{{ form.city }}</span>
                </div>
                <div class="info-list">
                  <span class="label">微信号</span>
                  <span class="des">{{ form.weixin }}</span>
                </div>
                <div class="info-list">
                  <span class="label">年龄</span>
                  <span class="des">{{ form.age }}</span>
                </div>
                <div class="info-list">
                  <span class="label">专业</span>
                  <span class="des">{{ majorFormat(form.major) }}</span>
                </div>
                <div class="info-list">
                  <span class="label">在职情况</span>
                  <span class="des">{{ jobFormat(form.job) }}</span>
                </div>
                <div class="info-list">
                  <span class="label">当前薪资</span>
                  <span class="des">{{ salaryFormat(form.salary) }}</span>
                </div>
                <div class="info-list">
                  <span class="label">qq</span>
                  <span class="des">{{ form.qq }}</span>
                </div>
                <div class="info-list">
                  <span class="label">性别</span>
                  <span class="des">{{ sexFormat(form.sex) }}</span>
                </div>
                <div class="info-list">
                  <span class="label">目标薪资</span>
                  <span class="des">{{ salaryFormat(form.expectedSalary) }}</span>
                </div>
                <div class="info-list wp-50">
                  <span class="label">备注</span>
                  <span class="des">{{ form.remark }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- 客户意向 -->
          <div class="l-top-box">
            <div class="title-box">
              <span class="title-label">客户意向</span>
            </div>
            <div class="con-box">
              <div class="info-box">
                <div class="info-list mt0">
                  <span class="label">意向学科</span>
                  <span class="des">{{ subjectFormat(form.subject) }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">学习原因</span>
                  <span class="des">{{ form.reasons }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">职业计划</span>
                  <span class="des">{{ form.plan }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">时间计划</span>
                  <span class="des">{{ form.planTime }}</span>
                </div>
                <div class="info-list">
                  <span class="label">意向课程</span>
                  <span class="des">{{ courseName }}</span>
                </div>
                <div class="info-list">
                  <span class="label">意向金额</span>
                  <span class="des">{{ coursePrice }}</span>
                </div>
                <div class="info-list wp-50">
                  <span class="label">其他意向</span>
                  <span class="des">{{ form.otherIntention }}</span>
                </div>
              </div>

            </div>
          </div>

          <!-- 历史沟通记录 -->
          <div class="l-top-box">
            <div class="title-box">
              <span class="title-label">历史沟通记录</span>
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
  </div>
</template>

<script>
import { getBusiness, listBusinessRecord } from '@/api/business/business'
import { listCourseSelect } from '@/api/clues/course'
export default {
  components: {
  },
  filters: {},
  data () {
    return {
      dataListLoading: false,
      // 详情数据
      dataForm: {
        id: '',
        pageType: ''
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
        age: null,
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
      courseOptions: [],
      trackStatusOptions: [],
      keyItemsOptions: [],
      dataList: [],
      courseName: '',
      coursePrice: ''
    }
  },
  computed: {
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
    // this.getCourseList()
    this.getRecordList()
  },
  mounted () {},
  methods: {
    phoneFormat (phone) {
      if (!phone) {
        return ''
      }
      return phone.substring(0, 3) + '****' + phone.substring(8, 11)
    },
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
    keyItemsFormat (val) {
      return this.selectDictLabel(this.keyItemsOptions, val)
    },
    trackStatusFormat (val) {
      return this.selectDictLabel(this.trackStatusOptions, val)
    },
    occupationFormat (val) {
      return this.selectDictLabel(this.occupationOptions, val)
    },
    educationFormat (val) {
      return this.selectDictLabel(this.educationOptions, val)
    },
    majorFormat (val) {
      return this.selectDictLabel(this.majorOptions, val)
    },
    jobFormat (val) {
      return this.selectDictLabel(this.jobOptions, val)
    },
    salaryFormat (val) {
      return this.selectDictLabel(this.salaryOptions, val)
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
        if (this.form.focus) {
          this.form.focusArray = this.form.focus.split(',')
        }
        if (this.form.courseId !== null && this.form.courseId !== '') {
          this.getCourseList()
        }
      }).catch(() => {})
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
    // 所有课程列表
    async getCourseList () {
      const res = await listCourseSelect()
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.courseOptions = res.data
      const course = this.courseOptions.find(item => item.id === this.form.courseId)
      if (course) {
        this.courseName = course.name
        this.coursePrice = course.price
      }
    },
    async getRecordList () {
      const res = await listBusinessRecord({ businessId: this.dataForm.id })
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.dataList = res.data
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
      margin-top: 20px;
      &:first-child{
        margin-top: 0;
      }
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
        // margin-left: -28px;
        display: flex;
        flex-wrap: wrap;
        .info-box{
          width: 100%;
        }
        .info-list{
          width: 25% !important;
        }
      }
      .table-con{
        padding: 24px 56px 67px 32px;
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
      // margin-left: 8px;
    // }
  }
  .cus-date{
    color: #666666;
  }
}
</style>
