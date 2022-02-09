<template>
  <!-- 分配商机对话框 -->
  <el-dialog title="分配商机" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
    <div class="tip-box">
      您选择了 <span class="num">{{ form.id.length }}</span> 个商机，请选择人员进行分配
    </div>
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <el-form-item label="部门" prop="deptId">
        <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择部门" />
      </el-form-item>
      <el-form-item label="用户" prop="userId">
        <el-select v-model="form.userId" filterable placeholder="请选择用户">
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button :loading="loading" class="cus-search-btn" @click="submitForm">{{ loading?'操作中':'确 定' }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { assignmentBusiness } from '@/api/business/business'
import { treeselect } from '@/api/system/dept'
import { listUserSelect } from '@/api/system/user'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  components: {
    Treeselect
  },
  filters: {},
  data () {
    return {
      visible: false,
      insert: true,
      loading: false,
      // 表单参数
      form: {
        id: [],
        deptId: null,
        userId: null
      },
      // 部门树选项
      deptOptions: [],
      userList: [],
      firstIn: true
    }
  },
  computed: {
    rules () {
      return {
        deptId: [{ required: true, message: '部门不能为空', trigger: 'blur' }],
        userId: [{ required: true, message: '用户不能为空', trigger: 'change' }]
      }
    }
  },
  watch: {
    'form.deptId' (val) {
      console.log('=-=-=-deptId=-=-=-', val)
      if (!this.firstIn) {
        this.$refs.form.validateField('deptId')
        this.firstIn = false
      }
      this.form.userId = ''
      if (val) {
        this.getUserList()
      } else {
        this.userList = []
      }
    }
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
        this.getTreeselect()
      })
    },
    /** 查询部门下拉树结构 */
    getTreeselect () {
      treeselect().then(response => {
        this.deptOptions = response.data
      })
    },
    // todo 缺少所有用户列表
    async getUserList () {
      const res = await listUserSelect({
        deptId: this.form.deptId
      })
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.userList = res.data
    },
    /** 提交按钮 */
    submitForm () {
      this.firstIn = false
      this.$refs.form.validate(async valid => {
        if (valid) {
          const form = {
            ids: this.form.id,
            userId: this.form.userId
          }
          this.loading = true
          try {
            const res = await assignmentBusiness(form)
            if (res.code !== 200) {
              this.loading = false
              return this.$message.error(res.msg || '操作失败')
            }
            this.msgSuccess('操作成功')
            this.visible = false
            this.loading = false
            this.$emit('refreshDataList')
          } catch {
            this.loading = false
          }
        }
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
