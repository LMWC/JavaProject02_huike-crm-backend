<template>
  <!-- 分配线索对话框 -->
  <el-dialog title="踢回公海" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <el-form-item class="wp-100" label="原因" prop="backReason">
        <el-select v-model="form.backReason" placeholder="请选择原因">
          <el-option
            v-for="dict in backReasonOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button class="cus-search-btn" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { backBusiness } from '@/api/business/business'
export default {
  components: {
  },
  filters: {},
  data () {
    return {
      visible: false,
      insert: true,
      backReasonOptions: [],
      // 表单参数
      form: {
        id: '',
        backReason: null,
        record: null
      },
      firstIn: true
    }
  },
  computed: {
    rules () {
      return {
        backReason: [{ required: true, message: '原因不能为空', trigger: 'blur' }]
      }
    }
  },
  watch: {
  },
  created () {},
  mounted () {},
  methods: {
    init () {
      this.insert = true
      this.visible = true
      this.$nextTick(() => {
        this.$refs.form.resetFields()
        this.firstIn = true
        this.getDicList()
      })
    },
    /** 提交按钮 */
    submitForm () {
      this.firstIn = false
      this.$refs.form.validate(async valid => {
        if (valid) {
          const res = await backBusiness(this.form)
          if (res.code !== 200) {
            return this.$message.error(res.msg || '操作失败')
          }
          this.msgSuccess('操作成功')
          this.visible = false
          this.$emit('refreshDataList')
        }
      })
    },
    getDicList () {
      this.getDicts('reasons_for_business_reporting').then(response => {
        this.backReasonOptions = response.data
      })
    }
  }
}
</script>
<style lang="scss" scoped>
  .tip-box{
    font-size: 14px;
    height: 17px;
    line-height: 17px;
    font-weight: 500;
    color: #666666;
    margin-bottom: 29px;
    .num{
      color: #05CC95;
    }
  }
</style>
