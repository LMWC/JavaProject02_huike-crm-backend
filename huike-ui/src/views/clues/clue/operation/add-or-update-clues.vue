<template>
  <!-- 添加或修改线索管理对话框 -->
  <el-dialog :title="!form.id ? '添加线索' : '修改线索'" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item label="渠道来源" prop="channel">
        <!-- <el-input v-model="form.channel" placeholder="请输入渠道来源" /> -->
        <el-select v-model="form.channel" filterable placeholder="请输入渠道来源">
          <el-option
            v-for="dict in channelOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动信息" prop="activityId">
        <el-select v-model="form.activityId" filterable placeholder="请选择活动信息">
          <el-option
            v-for="item in activityList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入客户姓名" maxlength="20" />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.sex">
          <el-radio
            v-for="dict in sexOptions"
            :key="dict.dictValue"
            :label="parseInt(dict.dictValue)"
          >{{ dict.dictLabel }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input-number v-model="form.age" :precision="0" :min="1" :max="99" :step="1" placeholder="请输入年龄" controls-position="right" />
        <!-- <el-input v-model="form.age" placeholder="请输入年龄" /> -->
      </el-form-item>
      <el-form-item label="微信号" prop="weixin">
        <el-input v-model="form.weixin" placeholder="请输入微信号" maxlength="20" />
      </el-form-item>
      <el-form-item label="QQ" prop="qq">
        <el-input v-model="form.qq" placeholder="请输入qq" maxlength="20" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button class="cus-search-btn" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { isValidatePhone, isValidateQq, isValidateWx, isValidateCnName } from '@/utils/validate'
import { getClue, addClue, updateClue } from '@/api/clues/clue'
import { getActivityByChannel } from '@/api/clues/activity'
export default {
  components: {},
  filters: {},
  data () {
    return {
      visible: false,
      insert: true,
      // 表单参数
      form: {
        id: null,
        name: null,
        phone: null,
        channel: null,
        activityId: null,
        sex: 0,
        age: undefined,
        weixin: null,
        qq: null
      },
      // 性能字典
      sexOptions: [],
      // 渠道来源字典
      channelOptions: [],
      activityList: []
    }
  },
  computed: {
    rules () {
      return {
        phone: [
          { required: true, message: '手机号不能为空', trigger: 'blur' },
          { validator: isValidatePhone, message: '请输入正确的手机号' }
        ],
        channel: [{ required: true, message: '渠道不能为空', trigger: 'change' }],
        // activityId: [
        //   { required: true, message: '活动信息不能为空', trigger: 'blur' }
        // ],
        weixin: [{ validator: isValidateWx, message: '请输入正确的微信号' }],
        qq: [{ validator: isValidateQq, message: '请输入正确的QQ号' }],
        name: [{ validator: isValidateCnName, message: '请输入真实的姓名' }]
      }
    }
  },
  watch: {
    'form.channel' () {
      this.getActivityList()
    }
  },
  created () {},
  mounted () {},
  methods: {
    init () {
      this.insert = true
      this.visible = true
      this.$nextTick(() => {
        this.form.sex = 0
        this.$refs.form.resetFields()
        if (this.form.id) {
          this.insert = false
          this.getInfo()
        }
        this.getDicList()
        // 获取活动列表
        // this.getActivityList()
      })
    },
    // 获取信息
    getInfo () {
      getClue(this.form.id).then(res => {
        if (res.code !== 200) {
          return this.$message.error(res.msg)
        }
        this.form = {
          ...this.form,
          ...res.data
        }
      }).catch(() => {})
    },
    getDicList () {
      this.getDicts('clues_item').then(response => {
        this.channelOptions = response.data
      })
      this.getDicts('sys_user_sex').then(response => {
        this.sexOptions = response.data
      })
    },
    // todo 缺少所有活动列表
    async getActivityList () {
      this.form.activityId = ''
      this.activityList = []
      const res = await getActivityByChannel(this.form.channel)
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.activityList = res.data
    },
    /** 提交按钮 */
    submitForm () {
      this.$refs.form.validate(async valid => {
        if (valid) {
          const res = await (this.form.id ? updateClue : addClue)(this.form)
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
