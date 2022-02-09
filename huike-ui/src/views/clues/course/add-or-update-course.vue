<template>
  <!-- 添加或修改线索管理对话框 -->
  <el-dialog :title="!form.id ? '添加课程' : '修改课程'" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <el-form-item label="课程学科" prop="subject">
        <el-select v-model="form.subject" placeholder="请选择课程学科">
          <el-option
            v-for="dict in subjectOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入课程名称" maxlength="10" />
      </el-form-item>
      <el-form-item label="适应人群" prop="applicablePerson">
        <el-select v-model="form.applicablePerson" placeholder="请选择适应人群">
          <el-option
            v-for="dict in applicablePersonOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程价格" prop="price">
        <el-input-number v-model="form.price" :precision="0" :step="1" placeholder="请输入课程价格" controls-position="right" :min="0" :max="99999" />
        <!-- <el-input v-model="form.price" placeholder="请输入课程价格" /> -->
      </el-form-item>
      <el-form-item label="课程介绍" prop="info" class="wp-100">
        <el-input v-model="form.info" type="textarea" placeholder="请输入课程介绍" :rows="2" maxlength="20" resize="none" show-word-limit />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button class="cus-search-btn" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { isVerification } from '@/utils/validate'
import { getCourse, addCourse, updateCourse } from '@/api/clues/course'
export default {
  components: {},
  filters: {},
  data () {
    return {
      visible: false,
      // 表单参数
      form: {
        id: null,
        name: null,
        subject: null,
        price: undefined,
        applicablePerson: null,
        info: null
      },
      // 课程学科字典
      subjectOptions: [],
      // 适用人群字典
      applicablePersonOptions: []
    }
  },
  computed: {
    rules () {
      return {
        name: [
          { required: true, message: '课程名称不能为空', trigger: 'blur' },
          { validator: isVerification, message: '禁止输入特殊字符' }
        ],
        subject: [{ required: true, message: '课程学科不能为空', trigger: 'change' }],
        price: [{ required: true, message: '课程价格不能为空', trigger: 'blur' }],
        applicablePerson: [{ required: true, message: '适应人群不能为空', trigger: 'change' }]
      }
    }
  },
  watch: {},
  created () {},
  mounted () {},
  methods: {
    init () {
      this.visible = true
      this.getDicList()
      this.$nextTick(() => {
        this.$refs.form.resetFields()
        if (this.form.id) {
          this.getInfo()
        }
      })
    },
    // 获取信息
    getInfo () {
      getCourse(this.form.id).then(res => {
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
      this.getDicts('course_subject').then(response => {
        this.subjectOptions = response.data
      })
      this.getDicts('applicable_person').then(response => {
        this.applicablePersonOptions = response.data
      })
    },
    /** 提交按钮 */
    async submitForm () {
      this.$refs.form.validate(async valid => {
        if (valid) {
          const res = await (this.form.id ? updateCourse : addCourse)(this.form)
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
