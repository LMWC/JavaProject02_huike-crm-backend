<template>
  <!-- 分配线索对话框 -->
  <el-dialog title="伪线索上报" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <el-form-item class="wp-100" label="原因" prop="reason">
        <el-select v-model="form.reason" placeholder="请选择原因">
          <el-option
            v-for="dict in falseReasonOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item class="wp-100" label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" maxlength="255" :rows="4" show-word-limit placeholder="请输入备注" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button class="cus-search-btn" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { reportClue } from '@/api/clues/clue'
export default {
  components: {
  },
  filters: {},
  data () {
    return {
      visible: false,
      insert: true,
      falseReasonOptions: [],
      // 表单参数
      form: {
        id: '',
        reason: null,
        remark: null
      },
      firstIn: true
    }
  },
  computed: {
    rules () {
      return {
        reason: [{ required: true, message: '原因不能为空', trigger: 'blur' }]
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
          const res = await reportClue(this.form)
          if (res.code !== 200) {
            return this.$message.error(res.msg || '操作失败')
            // this.$confirm(`${res.msg}`,'分配失败', {
            //   confirmButtonText: '确定',
            //   cancelButtonText: '取消',
            //   type: 'warning'
            // })
            // .then(() => {
            //
            // }).catch(() => {
            //
            // })
          }
          this.msgSuccess('操作成功')
          this.visible = false
          this.$emit('refreshDataList')
        }
      })
    },
    getDicList () {
      this.getDicts('reasons_for_reporting').then(response => {
        this.falseReasonOptions = response.data
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
