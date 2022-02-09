<template>
  <div class="app-container customer-page-box">
    <el-form ref="form" class="cus-search-box cus-form-row-box" :model="form" label-width="165px" :rules="rules">
      <el-form-item :label="ruleType==='1'?'商机回收时限':'线索回收时限'" prop="limitTime" class="wp-100">
        <!-- <el-select v-model="form.limitTime" placeholder="请选择线索回收时限">
          <el-option
            v-for="dict in limitTimeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select> -->
        <el-input-number v-model="form.limitTime" :precision="0" :min="1" :max="limitTimeMax" :step="1" :placeholder="ruleType==='1'?'请输入':'请输入'" controls-position="right" />
        <el-select v-model="form.limitTimeType" :placeholder="ruleType==='1'?'请选择商机回收时限类型':'请选择线索回收时限类型'" class="ml10" @change="limitTimeTypeChangeHandle">
          <el-option
            v-for="dict in limitTimeTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="ruleType==='1'?'商机回收到期提醒定时':'线索回收到期提醒定时'" prop="warnTime" class="wp-100">
        <!-- <el-select v-model="form.warnTime" placeholder="请选择线索回收到期提醒定时">
          <el-option
            v-for="dict in warnTimeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select> -->
        <el-input-number v-model="form.warnTime" :precision="0" :min="1" :max="warnTimeMax" :step="1" :placeholder="ruleType==='1'?'请输入':'请输入'" controls-position="right" />
        <el-select v-model="form.warnTimeType" :placeholder="ruleType==='1'?'请选择商机回收到期提醒定时类型':'请选择线索回收到期提醒定时类型'" class="ml10" @change="warnTimeTypeChangeHandle">
          <el-option
            v-for="dict in warnTimeTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="重复捞取时间限制" prop="repeatGetTime" class="wp-100">
        <!-- <el-select v-model="form.repeatGetTime" placeholder="请选择重复捞取时间限制">
          <el-option
            v-for="dict in repeatGetTimeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select> -->
        <el-input-number v-model="form.repeatGetTime" :precision="0" :min="1" :max="repeatGetTimeMax" :step="1" placeholder="请输入" controls-position="right" />
        <el-select v-model="form.repeatType" placeholder="请选择重复捞取时间限制类型" class="ml10" @change="repeatGetTimeTypeChangeHandle">
          <el-option
            v-for="dict in repeatGetTimeTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="个人保有量限制" prop="maxNunmber" class="wp-100">
        <!-- <el-select v-model="form.maxNunmber" placeholder="请选择个人保有量限制">
          <el-option
            v-for="dict in numberList99"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select> -->
        <el-input-number v-model="form.maxNunmber" :precision="0" :min="1" :max="99" :step="1" placeholder="请输入" controls-position="right" />
        <span class="unit">个</span>
      </el-form-item>
      <el-form-item class="search-btn wp-100">
        <el-button class="cus-search-btn" type="primary" @click="submitHandle">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { addCluesRulePool, updateCluesRulePool, getCluesRulePool } from '@/api/system/clew-pool'
export default {
  name: 'ClewPool',
  components: {
  },
  props: {
    ruleType: {
      type: String,
      default: '0'
    }
  },
  data () {
    return {
      form: {
        id: null,
        limitTime: undefined,
        limitTimeType: '0',
        warnTime: undefined,
        warnTimeType: '0',
        repeatGetTime: undefined,
        repeatType: '0',
        maxNunmber: undefined,
        type: 0
      },
      limitTimeTypeOptions: [],
      warnTimeTypeOptions: [],
      repeatGetTimeTypeOptions: [],
      limitTimeOptions: [],
      warnTimeOptions: [],
      repeatGetTimeOptions: [],
      // numberList24: [],
      // numberList31: [],
      // numberList4: [],
      // numberList12: [],
      // numberList99: [],
      limitTimeMax: 1,
      warnTimeMax: 1,
      repeatGetTimeMax: 1
    }
  },
  computed: {
    rules () {
      return {
        limitTime: [{ required: true, message: this.ruleType === '1' ? '商机回收时限不能为空' : '线索回收时限不能为空', trigger: 'blur' }],
        warnTime: [{ required: true, message: this.ruleType === '1' ? '商机回收到期提醒定时不能为空' : '线索回收到期提醒定时不能为空', trigger: 'blur' }],
        repeatGetTime: [{ required: true, message: '重复捞取时间限制不能为空', trigger: 'blur' }],
        maxNunmber: [{ required: true, message: '个人保有量限制不能为空', trigger: 'blur' }]
      }
    }
  },
  watch: {
  },
  created () {
    this.init()
  },
  methods: {
    init () {
      this.getDicList()
      this.$nextTick(() => {
        this.$refs.form.resetFields()
        this.getInfo()
      })
    },
    // 获取信息
    getInfo () {
      getCluesRulePool(this.ruleType).then(res => {
        if (res.code !== 200) {
          return this.$message.error(res.msg)
        }
        this.form = {
          ...this.form,
          ...res.data
        }
        this.limitTimeTypeChangeHandle(this.form.limitTimeType, '1')
        this.warnTimeTypeChangeHandle(this.form.warnTimeType, '1')
        this.repeatGetTimeTypeChangeHandle(this.form.repeatType, '1')
      }).catch(() => {})
    },
    limitTimeTypeChangeHandle (val, type) {
      if (type !== '1') {
        this.form.limitTime = ''
      }
      if (val === '0') {
        // 小时
        // this.limitTimeOptions = JSON.parse(JSON.stringify(this.numberList24))
        this.limitTimeMax = 24
      } else if (val === '1') {
        // this.limitTimeOptions = JSON.parse(JSON.stringify(this.numberList31))
        this.limitTimeMax = 31
        // 天
      } else if (val === '2') {
        // 周
        // this.limitTimeOptions = JSON.parse(JSON.stringify(this.numberList4))
        this.limitTimeMax = 4
      }
    },
    warnTimeTypeChangeHandle (val, type) {
      if (type !== '1') {
        this.form.warnTime = ''
      }
      if (val === '0') {
        // 小时
        // this.warnTimeOptions = JSON.parse(JSON.stringify(this.numberList24))
        this.warnTimeMax = 24
      } else if (val === '1') {
        // this.warnTimeOptions = JSON.parse(JSON.stringify(this.numberList31))
        this.warnTimeMax = 31
        // 天
      } else if (val === '2') {
        // 周
        // this.warnTimeOptions = JSON.parse(JSON.stringify(this.numberList4))
        this.warnTimeMax = 4
      }
    },
    repeatGetTimeTypeChangeHandle (val, type) {
      if (type !== '1') {
        this.form.repeatGetTime = ''
      }
      if (val === '0') {
        // 天
        // this.repeatGetTimeOptions = JSON.parse(JSON.stringify(this.numberList31))
        this.repeatGetTimeMax = 31
      } else if (val === '1') {
        // this.repeatGetTimeOptions = JSON.parse(JSON.stringify(this.numberList4))
        this.repeatGetTimeMax = 4
        // 周
      } else if (val === '2') {
        // 月
        // this.repeatGetTimeOptions = JSON.parse(JSON.stringify(this.numberList12))
        this.repeatGetTimeMax = 12
      }
    },
    getDicList () {
      this.getDicts('limit_time').then(response => {
        this.limitTimeTypeOptions = response.data
      })
      this.getDicts('warn_time').then(response => {
        this.warnTimeTypeOptions = response.data
      })
      this.getDicts('repeat_get_time').then(response => {
        this.repeatGetTimeTypeOptions = response.data
      })
      // const numberList24 = []
      // const numberList31 = []
      // const numberList4 = []
      // const numberList12 = []
      // const numberList99 = []
      // for (let i = 0; i < 99; i++) {
      //   if (i < 24) {
      //     numberList24.push({
      //       dictLabel: (i + 1),
      //       dictValue: (i + 1)
      //     })
      //   }
      //   if (i < 31) {
      //     numberList31.push({
      //       dictLabel: (i + 1),
      //       dictValue: (i + 1)
      //     })
      //   }
      //   if (i < 4) {
      //     numberList4.push({
      //       dictLabel: (i + 1),
      //       dictValue: (i + 1)
      //     })
      //   }
      //   if (i < 12) {
      //     numberList12.push({
      //       dictLabel: (i + 1),
      //       dictValue: (i + 1)
      //     })
      //   }
      //   numberList99.push({
      //     dictLabel: (i + 1),
      //     dictValue: (i + 1)
      //   })
      // }
      // this.numberList4 = numberList4
      // this.numberList31 = numberList31
      // this.numberList12 = numberList12
      // this.numberList24 = numberList24
      // this.numberList99 = numberList99
    },
    submitHandle () {
      this.form.type = this.ruleType
      this.$refs.form.validate(async valid => {
        if (valid) {
          const res = await (this.form.id ? updateCluesRulePool : addCluesRulePool)(this.form)
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
.customer-page-box{
  .cus-form-row-box{
    width: 497px;
  }
  .el-input-number{
    width: 122px !important;
  }
  .el-select{
    width: 200px !important;
  }
  .unit{
    margin-left: 25px;
    color: #333333;
    font-size: 14px;
  }
}

</style>
