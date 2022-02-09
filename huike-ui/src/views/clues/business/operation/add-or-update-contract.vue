<template>
  <!-- 添加或修改线索管理对话框 -->
  <el-dialog title="添加合同" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <el-form-item label="客户手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item label="合同编号" prop="contractNo">
        <el-input v-model="form.contractNo" placeholder="请输入合同编号" maxlength="20" />
      </el-form-item>
      <div v-show="fileList.length<=0" class="down-box">
        <span class="label required">上传合同</span>
        <el-upload
          ref="upload"
          :action="uploadFileUrl"
          :before-upload="handleBeforeUpload"
          :file-list="fileList"
          :limit="1"
          :on-error="handleUploadError"
          :on-exceed="handleExceed"
          :on-success="handleUploadSuccess"
          :headers="headers"
          class="upload-file-uploader"
          :show-file-list="false"
        >
          <!-- 上传按钮 -->
          <el-button slot="trigger" icon="iconfont iconcus-upload" class="cus-search-btn" type="primary">上传</el-button>
        </el-upload>
      </div>
      <div v-show="uploadError && (!fileList || fileList.length<=0)" class="upload-error-box">请上传合同</div>
      <div v-show="fileList.length>0" class="down-box">
        <span class="label">已上传</span>
        <span class="text-btn" :class="{'text-bg':selectActived}" @mouseover="selectActived=true" @mouseout="selectActived=false">
          <span class="text" @click="previewHandle">{{ fileList.length>0?fileList[0].name:'' }}</span>
          <i v-show="selectActived" class="icon el-icon-close del-btn" @click="delHandle" />
        </span>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button class="cus-search-btn" @click="submitForm">确 定</el-button>
    </div>
    <preview-pdf v-if="pdfVisible" ref="pdf" title="预览合同" :pdf-url="pdfUrl" />
  </el-dialog>
</template>

<script>
import { changeContract } from '@/api/contract/contract'
import { getToken } from '@/utils/auth'
import { isValidatePhone } from '@/utils/validate'
import PreviewPdf from '@/components/PreviewPdf'
export default {
  components: {
    PreviewPdf
  },
  filters: {},
  data () {
    return {
      visible: false,
      pdfVisible: false,
      selectActived: false,
      uploadError: false,
      pdfUrl: '',
      uploadFileUrl: process.env.VUE_APP_BASE_API + '/common/upload', // 上传的图片服务器地址
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      fileList: [],
      fileType: ['pdf'],
      // 表单参数
      form: {
        id: null,
        phone: null,
        contractNo: null,
        fileName: null
      }
    }
  },
  computed: {
    rules () {
      return {
        phone: [
          { required: true, message: '手机号不能为空', trigger: 'blur' },
          { validator: isValidatePhone, message: '请输入正确的手机号' }
        ],
        contractNo: [{ required: true, message: '合同编号不能为空', trigger: 'blur' }],
        fileList: [{ required: true, message: '请上传pdf格式的合同', trigger: 'change' }]
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
      this.$nextTick(() => {
        this.$refs.form.resetFields()
        this.form.fileName = null
        this.uploadError = false
        this.fileList = []
      })
    },
    /** 提交按钮 */
    async submitForm () {
      this.$refs.form.validate(async valid => {
        if (valid) {
          if (!this.fileList || this.fileList.length <= 0) {
            this.uploadError = true
            return false
          } else {
            this.uploadError = false
          }
          const id = this.form.id
          const form = JSON.parse(JSON.stringify(this.form))
          delete form.id
          const res = await changeContract(id, form)
          if (res.code !== 200) {
            return this.$message.error(res.msg)
          }
          this.msgSuccess('操作成功')
          this.visible = false
          this.$emit('refreshDataList')
        }
      })
    },
    // 上传前校检格式和大小
    handleBeforeUpload (file) {
      console.log('handleBeforeUpload', file)
      // 校检文件类型
      if (this.fileType) {
        let fileExtension = ''
        console.log('file.name', file.name)
        if (file.name.lastIndexOf('.') > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf('.') + 1)
        }
        console.log('fileExtension', fileExtension)
        const isTypeOk = this.fileType.some((type) => {
          if (file.type && file.type.indexOf(type) > -1) return true
          if (fileExtension && fileExtension.indexOf(type) > -1) return true
          return false
        })
        if (!isTypeOk) {
          this.$message.error(`文件格式不正确, 请上传${this.fileType.join('/')}格式文件!`)
          return false
        }
      }
      // 校检文件大小
      // if (this.fileSize) {
      //   const isLt = file.size / 1024 / 1024 < this.fileSize
      //   if (!isLt) {
      //     this.$message.error(`上传文件大小不能超过 ${this.fileSize} MB!`)
      //     return false
      //   }
      // }
      return true
    },
    // 文件个数超出
    handleExceed () {
      this.$message.error('只允许上传单个文件')
    },
    // 上传失败
    handleUploadError () {
      this.$message.error('上传失败, 请重试')
      this.fileList.splice(0, 1)
    },
    // 上传成功回调
    handleUploadSuccess (res, file, fileList) {
      console.log('-=-=-handleUploadSuccess=-=-=-=-', res)
      console.log('this.fileList', this.fileList)
      console.log('fileList', fileList)
      if (res.code !== 200) {
        this.fileList.splice(0, 1)
        return this.$message.error(res.msg)
      }
      // this.fileList.splice(0, 1)
      // this.$message.success('上传成功')
      // this.resultFlag = true
      this.fileList = fileList
      // this.pdfUrl = process.env.VUE_APP_BASE_API_URL + '/' + res.url
      // this.form.fileName = '/' + res.url
      this.pdfUrl = res.url
      this.form.fileName = res.url
    },
    // 删除
    delHandle () {
      this.$confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.fileList.splice(0, 1)
        }).catch(() => {

        })
    },
    // 预览
    previewHandle () {
      console.log('previewHandle')
      this.pdfVisible = true
      this.$nextTick(() => {
        this.$refs.pdf.visible = true
        this.$refs.pdf.init()
      //   this.$refs.allot.init()
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.down-box{
  display: flex;
  align-items: center;
  padding: 8px 0 0 28px;
  box-sizing: border-box;
  width: 100%;
  .label{
    height: 20px;
    font-size: 14px;
    font-weight: 400;
    color: #666666;
    line-height: 20px;
    text-align: left;
    margin-right: 25px;
  }
  .required::before{
    content: "*";
    color: #FD7065;
    margin-right: 4px;
  }
  .text-btn{
    font-size: 14px;
    font-weight: 400;
    color: #333333;
    height: 33px;
    line-height: 33px;
    border-radius: 3px;
    display: flex;
    cursor: pointer;
    align-items: center;
    padding: 0 16px 0 10px;
    flex:1;
    .text{
      flex:1;
    }
  }
  .text-bg{
    flex: 1;
    background: #f9f9f9;
    .text{
      color: #05CC95;
    }
  }
  .btn{
    flex: 1;
    text-align: right;
  }
}
.upload-error-box{
  color: #FD7065;
  font-size: 12px;
  line-height: 1;
  padding: 10px 0 0px 133px;
}
</style>
