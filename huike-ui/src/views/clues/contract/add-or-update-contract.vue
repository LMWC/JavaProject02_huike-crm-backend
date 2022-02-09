<template>
  <!-- 添加或修改线索管理对话框 -->
  <el-dialog :title="!form.id ? '添加合同' : '修改合同'" :visible.sync="visible" width="618px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" class="cus-search-box cus-dialog-form-box" :model="form" :rules="rules">
      <el-form-item label="客户姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入客户姓名" />
      </el-form-item>
      <el-form-item label="客户手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入客户手机号" />
      </el-form-item>
      <el-form-item label="合同编号" prop="contractNo">
        <el-input v-model="form.contractNo" placeholder="请输入合同编号" maxlength="20" />
      </el-form-item>
      <el-form-item label="购买学科" prop="subject">
        <el-select v-model="form.subject" placeholder="请选择购买学科" @change="subjectChangeHandle">
          <el-option
            v-for="dict in subjectOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="购买课程" prop="courseId">
        <el-select v-model="form.courseId" placeholder="请选择购买课程">
          <el-option
            v-for="dict in courseOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="渠道来源" prop="channel">
        <el-select v-model="form.channel" placeholder="请输入渠道来源" @change="channelChangeHandle">
          <el-option
            v-for="dict in channelOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动信息" prop="activityId">
        <el-select v-model="form.activityId" placeholder="请选择活动信息">
          <el-option
            v-for="item in activityList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
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
import { getContractInfo, addContract, updateContract } from '@/api/contract/contract'
import { getActivityByChannel } from '@/api/clues/activity'
import { listCourseSelect } from '@/api/clues/course'
import { getToken } from '@/utils/auth'
import { isValidatePhone, isValidateCnName } from '@/utils/validate'
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
        name: null,
        phone: null,
        contractNo: null,
        subject: null,
        courseId: null,
        channel: null,
        activityId: null,
        fileName: null
      },
      // 课程学科字典
      subjectOptions: [],
      courseOptions: [],
      activityList: [],
      channelOptions: []
    }
  },
  computed: {
    rules () {
      return {
        name: [
          { required: true, message: '客户姓名不能为空', trigger: 'blur' },
          { validator: isValidateCnName, message: '请输入真实的客户姓名' }
        ],
        phone: [
          { required: true, message: '客户手机号不能为空', trigger: 'blur' },
          { validator: isValidatePhone, message: '请输入正确的客户手机号' }
        ],
        subject: [{ required: true, message: '购买学科不能为空', trigger: 'change' }],
        courseId: [{ required: true, message: '购买课程不能为空', trigger: 'change' }],
        // activityId: [{ required: true, message: '活动信息不能为空', trigger: 'change' }],
        contractNo: [{ required: true, message: '合同编号不能为空', trigger: 'blur' }],
        fileList: [{ required: true, message: '请上传pdf格式的合同', trigger: 'change' }]
      }
    }
  },
  watch: {
    // 'form.channel' () {
    //   this.getActivityList()
    // }
  },
  created () {

  },
  mounted () {},
  methods: {
    init () {
      this.visible = true
      this.$nextTick(() => {
        this.$refs.form.resetFields()
        this.form.fileName = null
        this.uploadError = false
        this.fileList = []
        this.courseOptions = []
        this.activityList = []
        if (this.form.id) {
          this.getInfo()
        }
        this.getDicList()
        // this.getCourseList()
      })
    },
    // 获取信息
    getInfo () {
      getContractInfo(this.form.id).then(res => {
        if (res.code !== 200) {
          return this.$message.error(res.msg)
        }
        this.form = {
          ...this.form,
          ...res.data
        }
        if (this.form.fileName) {
          this.fileList.push({
            name: '合同.pdf'
          })
          this.pdfUrl = this.form.fileName
        }
        if (this.form.channel) {
          this.getActivityList()
        }
        if (this.form.courseId) {
          this.getCourseList()
        }
      }).catch(() => {})
    },
    getDicList () {
      this.getDicts('clues_item').then(response => {
        this.channelOptions = response.data
      })
      this.getDicts('course_subject').then(response => {
        this.subjectOptions = response.data
      })
    },
    channelChangeHandle () {
      this.form.activityId = ''
      this.getActivityList()
    },
    // 所有活动列表
    async getActivityList () {
      this.activityList = []
      const res = await getActivityByChannel(this.form.channel)
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.activityList = res.data
    },
    subjectChangeHandle () {
      this.$nextTick(() => {
        this.$refs.form.clearValidate('courseId')
      })
      this.form.courseId = null
      this.getCourseList()
    },
    // 所有课程列表
    async getCourseList () {
      this.courseOptions = []
      const res = await listCourseSelect({ subject: this.form.subject })
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.courseOptions = res.data
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
          const res = await (this.form.id ? updateContract : addContract)(this.form)
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
