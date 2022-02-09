<template>
  <div v-if="tabsIndex === 0">
    <el-form ref="allTransferForm" class="cus-search-box" :model="queryParams">
      <el-form-item label="创建时间">
        <el-date-picker v-model="createTime" placeholder="请选择" clearable type="daterange" align="right" unlink-panels value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="queryParams.phonenumber" placeholder="手机号码" />
      </el-form-item>
      <el-form-item label="用户名称">
        <el-input v-model="queryParams.userName" placeholder="用户名称" />
      </el-form-item>
      <el-form-item class="search-btn">
        <el-button class="cus-search-btn" type="primary" @click="searchHandle">搜索</el-button>
        <el-button class="cus-reset-btn" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- table -->
    <div v-loading="dataListLoading" :class="{ 'loading-box': dataListLoading }">
      <div v-show="dataList && dataList.length > 0">
        <el-table class="cus-table-box" :data="dataList" stripe>
          <el-table-column label="序号" type="index" width="75" />
          <!-- <el-table-column label="编号" align="center" prop="userId" /> -->
          <el-table-column label="用户名称" prop="userName" align="center" />
          <el-table-column label="线索数量" prop="clueNum" align="center" />
          <el-table-column label="商机数量" prop="businessNum" align="center" />
          <el-table-column label="创建时间" prop="createTime" sortable align="center" />
          <el-table-column label="手机号码" prop="phonenumber" align="center" />
          <el-table-column label="操作" align="center">
            <template slot-scope="{ row }">
              <el-button type="text" size="mini" @click="goDetail(row)">处 理</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="pageNum"
          :limit.sync="pageSize"
          @pagination="getList"
        />
      </div>
      <empty v-show="(!dataList || dataList.length <= 0) && !dataListLoading" :box-height="645" class="table-empty-box" />
    </div>

    <!-- 有关处理和详情的弹框 -->
    <el-dialog v-if="visible" :title="title" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
        <el-form-item class="wp-100" label="手机号码">
          <span class="des">{{ form.phonenumber }}</span>
        </el-form-item>
        <el-form-item class="wp-100" label="用户姓名">
          <span class="des">{{ form.userName }}</span>
        </el-form-item>
        <el-form-item class="wp-100" label="创建日期">
          <span class="des">{{ form.createTime }}</span>
        </el-form-item>
        <el-form-item class="wp-100">
          <el-radio v-model="form.radio" label="0" :disabled="form.clueNum === 0">线索{{ form.clueNum }}</el-radio>
          <el-radio v-model="form.radio" label="1" :disabled="form.businessNum === 0">商机{{ form.businessNum }}</el-radio>
        </el-form-item>
        <el-form-item v-if="(form.clueNum !== 0 || form.businessNum !== 0)" class="wp-100" label="请选择部门" prop="departId">
          <treeselect v-model="form.departId" :options="departmentOptions" :show-count="true" placeholder="请选择部门" />
        </el-form-item>
        <el-form-item v-if="(form.clueNum !== 0 || form.businessNum !== 0)" class="wp-100" label="请选择人员" prop="personId">
          <el-select v-model="form.personId" filterable placeholder="请选择人员">
            <el-option v-for="person in personnelOptions" :key="person.userId" :label="person.userName" :value="person.userId" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="(form.clueNum !== 0 || form.businessNum !== 0)" class="cus-reset-btn" @click="cancelFun">取 消</el-button>
        <el-button v-if="(form.clueNum !== 0 || form.businessNum !== 0)" class="cus-search-btn" @click="submitForm">转 派</el-button>
        <el-button v-if="form.clueNum === 0 && form.businessNum === 0" class="cus-search-btn" @click="completeFun">完 成</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import { listClue } from '@/api/clues/clue'
import mixinViewModule from '@/mixins/view-module'
import { getDevApiTransferList, queryTransferAssignmentByUserIdAndTransferUserId } from '@/api/transferManage/transfer.js'
import { treeselect } from '@/api/system/dept'
import { listUserSelect } from '@/api/system/user'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  components: {
    Treeselect
  },
  mixins: [mixinViewModule],
  props: {
    tabsIndex: {
      type: Number,
      required: true,
      default: 0
    }
  },
  data () {
    return {
      mixinViewModuleOptions: {
        getListFun: getDevApiTransferList
      },
      createTime: [],
      queryParams: {
        userId: '',
        userName: '',
        params: {
          beginTime: '',
          endTime: ''
        }
      },
      form: {
        usernumber: '',
        userName: '',
        departId: undefined,
        personId: undefined,
        radio: '0',
        clueNum: 0,
        businessNum: 0
      },
      title: '处理',
      visible: false,
      departmentOptions: [],
      personnelOptions: [],
      rules: {
        departId: [
          {
            required: true,
            message: '请选择部门',
            trigger: 'blur'
          }
        ],
        personId: [
          {
            required: true,
            message: '请选择人员',
            trigger: 'change'
          }
        ]
      },
      firstIn: true
    }
  },
  watch: {
    createTime (val) {
      if (val && val.length >= 2) {
        this.queryParams.params.beginTime = val[0]
        this.queryParams.params.endTime = val[1]
      } else {
        this.queryParams.params.beginTime = ''
        this.queryParams.params.endTime = ''
      }
    },
    'form.departId' (val) {
      if (!this.firstIn) {
        this.$nextTick(() => {
          // this.$refs.form.validateField('departId')
          this.firstIn = false
        })
      }
      if (val) {
        this.getUserList()
      } else {
        this.personnelOptions = []
      }
      this.form.personId = null
    }
  },
  methods: {
    searchHandle () {
      this.handleQuery()
    },
    resetQuery () {
      // TODO
      this.resetForm('queryForm')
      this.createTime = []
      this.queryParams.userName = ''
      this.queryParams.userId = ''
      this.queryParams.params.beginTime = ''
      this.queryParams.params.endTime = ''
      this.handleQuery()
    },
    goDetail (row) {
      // TODO
      this.visible = true
      this.form = {
        ...this.form,
        ...row,
        clueNum: Number(row.clueNum),
        businessNum: Number(row.businessNum)
      }
      if (Number(row.clueNum) > 0 && Number(row.businessNum) > 0) {
        this.form.radio = '0'
      } else if (Number(row.clueNum) > 0 && Number(row.businessNum) === 0) {
        this.form.radio = '0'
      } else if (Number(row.clueNum) === 0 && Number(row.businessNum) > 0) {
        this.form.radio = '1'
      } else {
        this.form.radio = ''
      }
      this.getTreeselect()
    },
    // 弹框的事件提交
    submitForm () {
      this.firstIn = false
      this.$refs.form.validate(async valid => {
        if (valid) {
          const params = {
            type: this.form.radio,
            userId: this.form.userId,
            transferUserId: this.form.personId
          }
          const res = await queryTransferAssignmentByUserIdAndTransferUserId(params)
          if (res.code !== 200) {
            return this.$message.error(res.msg || '操作失败')
          }
          this.msgSuccess('操作成功')
          if (JSON.stringify(res.data) === '{}') {
            this.visible = false
            this.form.departId = undefined
            this.form.personId = undefined
            this.handleQuery()
          } else {
            this.form.clueNum = res.data.clueNum
            this.form.businessNum = res.data.businessNum
            this.form.radio = ''
          }
        }
      })
    },
    // 取消
    cancelFun () {
      this.visible = false
      this.$refs.form.resetFields()
    },
    // 完成
    completeFun () {
      this.visible = false
      this.$refs.form.resetFields()
      this.handleQuery()
    },
    /** 查询部门下拉树结构 */
    getTreeselect () {
      treeselect().then(response => {
        this.departmentOptions = response.data
      })
    },
    // todo 缺少所有用户列表
    async getUserList () {
      const res = await listUserSelect({
        deptId: this.form.departId
      })
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.personnelOptions = res.data
    }
  }
}
</script>

<style lang="scss" scoped>

.des {
  height: 18px;
  font-size: 13px;
  font-weight: 400;
  text-align: left;
  color: #332929;
  line-height: 18px;
  margin-top: 8px;
}
</style>
