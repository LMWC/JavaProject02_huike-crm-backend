<template>
  <!-- 添加或修改线索管理对话框 -->
  <el-dialog :title="!form.id ? '添加活动' : '修改活动'" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <el-form-item label="渠道来源" prop="channel">
        <el-select v-model="form.channel" placeholder="请选择渠道来源">
          <el-option
            v-for="dict in channelOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入活动名称" maxlength="10" />
      </el-form-item>
      <el-form-item label="活动日期" prop="activityTime" class="wp-100">
        <el-date-picker
          v-model="form.activityTime"
          clearable
          type="datetimerange"
          align="right"
          unlink-panels
          value-format="yyyy-MM-dd HH:mm"
          format="yyyy-MM-dd HH:mm"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="['00:00:00', '23:59:59']"
          :picker-options="pickerOptions"
        />
        <!-- <el-date-picker
          v-model="form.beginTime"
          clearable
          type="datetime"
          value-format="yyyy-MM-dd HH:mm"
          placeholder="选择开始时间"
        />
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker
          v-model="form.endTime"
          clearable
          type="datetime"
          value-format="yyyy-MM-dd HH:mm"
          placeholder="选择结束时间"
        /> -->
      </el-form-item>
      <el-form-item label="活动简介" prop="info">
        <el-input v-model="form.info" placeholder="请输入活动简介" maxlength="10" />
      </el-form-item>
      <el-form-item label="活动类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择活动类型" @change="typeChangeHandle">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item v-if="form.type==1" label="课程折扣" prop="discount">
        <el-input-number v-model="form.discount" :min="0.1" :precision="1" :step="0.1" :max="9.9" placeholder="请输入课程课程折扣" controls-position="right" />
      </el-form-item>
      <el-form-item v-if="form.type==2" label="代金券" prop="vouchers">
        <el-input-number v-model="form.vouchers" :step="1" :precision="0" :min="1" :max="9999" placeholder="请输入代金券" controls-position="right" />
        <!-- <el-input v-model="form.vouchers" placeholder="请输入代金券" /> -->
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button class="cus-search-btn" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
// import { isVerification } from '@/utils/validate'
import { getActivity, addActivity, updateActivity } from '@/api/clues/activity'
export default {
  components: {},
  filters: {},
  data () {
    return {
      visible: false,
      // 表单参数
      form: {
        id: null,
        channel: null,
        name: null,
        info: null,
        type: null,
        discount: undefined,
        vouchers: undefined,
        beginTime: '',
        status: null,
        endTime: '',
        activityTime: []
      },
      // 渠道来源字典
      channelOptions: [],
      // 活动类型字典
      typeOptions: [],
      // 状态字典
      statusOptions: [],
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7 // 禁用以前的日期，今天不禁用
          // return date.getTime() <= Date.now();    //禁用今天以及以前的日期
        }
      }
    }
  },
  computed: {
    rules () {
      return {
        channel: [{ required: true, message: '渠道来源不能为空', trigger: 'change' }],
        name: [{ required: true, message: '活动名称不能为空', trigger: 'blur' }],
        info: [{ required: true, message: '活动简介不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '活动类型不能为空', trigger: 'change' }],
        discount: [{ required: true, message: '课程折扣不能为空', trigger: 'blur' }],
        vouchers: [{ required: true, message: '代金券不能为空', trigger: 'blur' }],
        beginTime: [{ required: true, message: '开始时间不能为空', trigger: 'change' }],
        endTime: [{ required: true, message: '结束时间不能为空', trigger: 'change' }],
        activityTime: [{ required: true, message: '活动时间不能为空', trigger: 'blur' }]
      }
    }
  },
  watch: {
    // 'form.beginTime' (val) {
    //   console.log('form.beginTime', val)
    //   if (val) {
    //     this.pickerOptions = {
    //       disabledDate (time) {
    //         // console.log(time)
    //         // console.log(time.getTime())
    //         // console.log(new Date(val).getTime)
    //         return time.getTime() < new Date(val) - 8.64e7
    //       }
    //     }
    //   } else {
    //     this.pickerOptions = {
    //     }
    //   }
    // }
  },
  created () {},
  mounted () {},
  methods: {
    init () {
      this.visible = true
      this.getDicList()
      this.$nextTick(() => {
        this.$refs.form.resetFields()
        console.log('this.form.activityTime', this.form.activityTime)
        if (this.form.activityTime && this.form.activityTime.length > 0) {
          this.form.activityTime.splice(0, this.form.activityTime.length)
        }
        if (this.form.id) {
          this.getInfo()
        }
      })
    },
    // 获取信息
    getInfo () {
      getActivity(this.form.id).then(res => {
        if (res.code !== 200) {
          return this.$message.error(res.msg)
        }
        this.form = {
          ...this.form,
          ...res.data
        }
        if (this.form.beginTime && this.form.endTime) {
          this.form.activityTime.push(...[this.form.beginTime, this.form.endTime])
        }
      }).catch(() => {})
    },
    getDicList () {
      this.getDicts('clues_item').then(response => {
        this.channelOptions = response.data
      })
      this.getDicts('channel_type').then(response => {
        this.typeOptions = response.data
      })
      this.getDicts('activity_status').then(response => {
        this.statusOptions = response.data
      })
    },
    typeChangeHandle () {
      this.form.vouchers = undefined
      this.form.discount = undefined
    },
    /** 提交按钮 */
    async submitForm () {
      this.$refs.form.validate(async valid => {
        if (valid) {
          this.form.beginTime = this.form.activityTime[0]
          this.form.endTime = this.form.activityTime[1]
          const res = await (this.form.id ? updateActivity : addActivity)(this.form)
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
</style>
