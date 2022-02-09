<template>
  <div class="customer-detail-box">
    <el-row class="cus-row-box">
      <el-col :span="18">
        <div class="l-box">
          <div class="l-top-box pt20">
            <div class="title-box">
              <span class="title-label">{{ dataForm.id }}</span>
            </div>
            <div class="con-box">
              <div class="info-box">
                <div class="info-list mt0">
                  <span class="label">创建时间</span>
                  <span class="des">{{ dataForm.createTime }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">归属时间</span>
                  <span class="des">{{ dataForm.ownerTime }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">手机号</span>
                  <span class="des">{{ dataForm.pageType!=='pool'?dataForm.phone:(phoneFormat(dataForm.phone)) }}</span>
                </div>
                <div class="info-list">
                  <span class="label">渠道来源</span>
                  <span class="des">{{ channelOptionsFormat(dataForm.channel) }}</span>
                </div>
                <div class="info-list">
                  <span class="label">线索分配</span>
                  <span class="des">{{ dataForm.createBy }}</span>
                </div>
                <div class="info-list">
                  <span class="label">线索归属</span>
                  <span class="des">{{ dataForm.owner }}</span>
                </div>
                <div class="info-list">
                  <span class="label">活动信息</span>
                  <span class="des">{{ dataForm.activityInfo }}</span>
                </div>
              </div>
            </div>
          </div>
          <div class="l-top-box">
            <div class="title-box">
              <span class="title-label">添加跟进</span>
              <div class="btn-box">
                <el-button
                  v-if="dataForm.pageType==='follow'"
                  v-hasPermi="['clues:record:add']"
                  class="primary-btn"
                  @click="addOrUpdateHandle()"
                >伪线索</el-button>
                <el-button
                  v-if="dataForm.pageType==='follow'"
                  v-hasPermi="['clues:record:add']"
                  class="default-btn"
                  @click="turnHandle()"
                >转商机</el-button>
              </div>
            </div>
            <div class="con-box">
              <el-form ref="form" class="cus-search-box cus-form-box" :model="form" :rules="rules">
                <el-form-item label="客户姓名" prop="name">
                  <el-input v-model="form.name" placeholder="请输入客户姓名" :disabled="dataForm.pageType!=='follow'" />
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                  <el-radio-group v-model="form.sex" :disabled="dataForm.pageType!=='follow'">
                    <el-radio
                      v-for="dict in sexOptions"
                      :key="dict.dictValue"
                      :label="parseInt(dict.dictValue)"
                    >{{ dict.dictLabel }}</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item />
                <el-form-item label="年龄" prop="age">
                  <el-input-number v-model="form.age" :precision="0" :min="1" :max="99" :step="1" placeholder="请输入年龄" controls-position="right" :disabled="dataForm.pageType!=='follow'" />
                </el-form-item>
                <el-form-item label="微信号" prop="weixin">
                  <el-input v-model="form.weixin" placeholder="请输入微信号" maxlength="20" :disabled="dataForm.pageType!=='follow'" />
                </el-form-item>
                <el-form-item label="qq" prop="qq">
                  <el-input v-model="form.qq" placeholder="请输入qq" maxlength="20" :disabled="dataForm.pageType!=='follow'" />
                </el-form-item>
                <el-form-item class="wp-100" label="意向等级" prop="level">
                  <el-radio-group v-model="form.level" :disabled="dataForm.pageType!=='follow'">
                    <el-radio
                      v-for="dict in levelOptions"
                      :key="dict.dictValue"
                      :label="parseInt(dict.dictValue)"
                    >{{ dict.dictLabel }}</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item class="wp-100" label="意向学科" prop="subject">
                  <el-select v-model="form.subject" placeholder="请选择意向学科" clearable :disabled="dataForm.pageType!=='follow'">
                    <el-option
                      v-for="dict in subjectOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item class="wp-100" label="跟进记录" prop="record">
                  <el-input v-model="form.record" type="textarea" :rows="4" show-word-limit placeholder="请输入跟进记录" maxlength="255" :disabled="dataForm.pageType!=='follow'" />
                </el-form-item>
                <el-form-item class="wp-100" label="下次跟进时间" prop="nextTime">
                  <el-date-picker
                    v-model="form.nextTime"
                    clearable
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm"
                    format="yyyy-MM-dd HH:mm"
                    placeholder="请选择下次跟进时间"
                    :disabled="dataForm.pageType!=='follow'"
                    :picker-options="pickerOptions"
                    default-time="12:00:00"
                  />
                </el-form-item>
                <el-form-item class="search-btn wp-100">
                  <el-button v-if="dataForm.pageType==='follow'" v-hasPermi="['clues:record:add']" class="cus-search-btn" :loading="saveLoading" @click="submitForm()">确认</el-button>
                  <el-button class="cus-reset-btn" @click="goPage()">返回</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="r-box pt20">
          <div class="title-box">
            <span class="title-label">操作记录</span>
          </div>
          <div class="con-box">
            <div class="record-list">
              <div v-for="(item,index) in dataList" :key="item.id" class="item-box">
                <div v-if="showRecord(item.level,'level',index)" class="con-box">
                  <span class="label">意向等级：</span>
                  <span class="des num">{{ item.level?levelFormat(item.level):'' }}</span>
                </div>
                <div v-if="showRecord(item.subject,'subject',index)" class="con-box">
                  <span class="label">意向学科：</span>
                  <span class="des">{{ item.subject?subjectFormat(item.subject):'' }}</span>
                </div>
                <div v-if="item.falseReason" class="con-box">
                  <span class="label">伪线索原因：</span>
                  <span class="des">{{ reasonsOptionsFormat(item.falseReason) }}</span>
                </div>
                <div v-if="item.record" class="con-box con-column">
                  <span class="label">{{ item.falseReason?'伪线索备注':'跟进记录' }}：</span>
                  <span class="des">{{ item.record }}</span>
                </div>
                <div v-if="item.nextTime" class="con-box">
                  <span class="label">下次跟进时间：</span>
                  <span class="des">{{ parseTime(item.nextTime, '{y}-{m}-{d} {h}:{i}') }}</span>
                </div>
                <div class="operation-box">
                  <div class="label">操作人：{{ item.createBy }}</div>
                  <div class="label">{{ item.createTime }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="goPage" />
  </div>
</template>

<script>
import { getClue, addClueRecord, turnBusiness, listClueRecord } from '@/api/clues/clue'
import { isValidateCnName, isValidateQq, isValidateWx } from '@/utils/validate'
import AddOrUpdate from './../operation/report-clues'
export default {
  components: {
    AddOrUpdate
  },
  filters: {},
  data () {
    return {
      addOrUpdateVisible: false,
      saveLoading: false,
      // 详情数据
      dataForm: {
        id: '',
        pageType: 'info'
      },
      // 表单参数
      form: {
        id: null,
        name: null,
        sex: 0,
        age: undefined,
        weixin: null,
        qq: null,
        level: null,
        subject: null,
        record: null,
        nextTime: null
      },
      // 性能字典
      sexOptions: [],
      // 意向等级字典
      levelOptions: [],
      // 意向学科字典
      subjectOptions: [],
      // 状态字典
      statusOptions: [],
      channelOptions: [],
      falseReasonOptions: [],
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
        level: [{ required: true, message: '意向等级不能为空', trigger: 'change' }],
        subject: [{ required: true, message: '意向学科不能为空', trigger: 'change' }],
        record: [{ required: true, message: '跟进记录不能为空', trigger: 'blur' }],
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
    async getInfo () {
      const res = await getClue(this.dataForm.id)
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.dataForm = {
        ...this.dataForm,
        ...res.data
      }
      this.form.name = this.dataForm.name
      this.form.sex = this.dataForm.sex ? parseInt(this.dataForm.sex, 10) : 0
      this.form.age = this.dataForm.age ? this.dataForm.age : undefined
      this.form.weixin = this.dataForm.weixin
      this.form.qq = this.dataForm.qq
    },
    async getRecordList () {
      const res = await listClueRecord({ clueId: this.dataForm.id })
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.dataList = res.rows
    },
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
    // 渠道来源字典翻译
    channelOptionsFormat (val) {
      return this.selectDictLabel(this.channelOptions, val)
    },
    // 伪线索字典翻译
    reasonsOptionsFormat (val) {
      return this.selectDictLabel(this.falseReasonOptions, val)
    },
    getDicList () {
      this.getDicts('sys_user_sex').then(response => {
        this.sexOptions = response.data
      })
      this.getDicts('clues_level').then(response => {
        this.levelOptions = response.data
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
      this.getDicts('reasons_for_reporting').then(response => {
        this.falseReasonOptions = response.data
      })
    },
    addOrUpdateHandle () {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.form.id = this.dataForm.id
        this.$refs.addOrUpdate.init()
      })
    },
    // 转商机
    turnHandle () {
      this.$confirm('确认转为客户?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          const res = await turnBusiness(this.dataForm.id)
          if (res.code !== 200) {
            return this.$message.error(res.msg)
          }
          this.msgSuccess('操作成功')
          this.goPage()
        }).catch(() => {

        })
    },
    /** 提交按钮 */
    submitForm () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.form.clueId = this.dataForm.id
          this.saveLoading = true
          addClueRecord(this.form).then(res => {
            this.saveLoading = false
            if (res.code !== 200) {
              return this.$message.error(res.msg)
            }
            this.msgSuccess('操作成功')
            this.getRecordList()
            this.$refs.form.resetFields()
            this.getInfo()
          }).catch(() => {
            this.saveLoading = false
          })
        }
      })
    },
    clearInput () {
      this.form.level = null
      this.form.subject = null
      this.form.record = null
      this.form.nextTime = null
    },
    goPage () {
      this.$router.push({ path: '/clue' })
    },
    showRecord (val, name, index) {
      if (index > 0) {
        return val !== this.dataList[index - 1][name]
      } else {
        return val
      }
    }
  }
}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/modules/clue-details.scss";
.pt20{
  padding-top: 20px !important;
}
.title-box{
  height: 50px !important;
  line-height: 50px !important;
}
.btn-box{
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
}
</style>
