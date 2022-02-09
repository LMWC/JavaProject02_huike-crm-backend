<template>
  <!-- 添加或修改商机管理对话框 -->
  <el-dialog :title="!form.id ? '添加商机' : '修改商机'" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <el-form-item label="客户姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入客户姓名" maxlength="20" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号" />
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
      <el-form-item label="地区">
        <el-cascader
          v-model="area"
          size="large"
          :options="provinceAndCityDataOptions"
          @change="handleChange"
        />
      </el-form-item>
      <el-form-item label="意向学科" prop="subject">
        <el-select v-model="form.subject" filterable placeholder="请选择意向学科">
          <el-option
            v-for="dict in subjectOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="渠道来源" prop="channel">
        <el-select v-model="form.channel" filterable placeholder="请选择渠道来源">
          <el-option
            v-for="dict in channelOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item class="wp-100" label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" :rows="2" maxlength="20" show-word-limit placeholder="请输入备注" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button class="cus-search-btn" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { isValidateCnName, isValidatePhone, isValidateQq, isValidateWx } from '@/utils/validate'
import { getBusiness, addBusiness, updateBusiness } from '@/api/business/business'
import { provinceAndCityData, CodeToText, TextToCode } from 'element-china-area-data'
export default {
  components: {},
  filters: {},
  data () {
    return {
      visible: false,
      insert: true,
      area: [],
      // 表单参数
      form: {
        id: null,
        name: null,
        phone: null,
        channel: null,
        sex: 0,
        age: undefined,
        weixin: null,
        qq: null,
        subject: null,
        provinces: null,
        city: null,
        remark: null
      },
      // 性能字典
      sexOptions: [],
      // 渠道来源字典
      channelOptions: [],
      subjectOptions: [],
      provinceAndCityDataOptions: provinceAndCityData
    }
  },
  computed: {
    rules () {
      return {
        name: [
          { required: true, message: '姓名不能为空', trigger: 'blur' },
          { validator: isValidateCnName, message: '请输入真实的姓名' }
        ],
        phone: [
          { required: true, message: '手机号不能为空', trigger: 'blur' },
          { validator: isValidatePhone, message: '请输入正确的手机号' }
        ],
        subject: [{ required: true, message: '意向学科不能为空', trigger: 'change' }],
        // activityId: [
        //   { required: true, message: '活动信息不能为空', trigger: 'blur' }
        // ],
        weixin: [{ validator: isValidateWx, message: '请输入正确的微信号' }],
        qq: [{ validator: isValidateQq, message: '请输入正确的QQ号' }]
      }
    }
  },
  watch: {},
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
      })
    },
    // 获取信息
    getInfo () {
      getBusiness(this.form.id).then(res => {
        if (res.code !== 200) {
          return this.$message.error(res.msg)
        }
        this.form = {
          ...this.form,
          ...res.data
        }
        if (this.form.provinces && this.form.city) {
          this.area = [TextToCode[this.form.provinces].code, TextToCode[this.form.provinces][this.form.city].code]
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
      this.getDicts('course_subject').then(response => {
        this.subjectOptions = response.data
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
      this.$refs.form.validate(async valid => {
        if (valid) {
          const res = await (this.form.id ? updateBusiness : addBusiness)(this.form)
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
