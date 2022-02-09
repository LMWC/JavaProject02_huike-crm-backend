<template>
  <el-dialog :visible.sync="visible" title="批量添加线索" :close-on-click-modal="false" :close-on-press-escape="false">
    <div class="upload-box">
      <div class="title-box">上传文件</div>
      <div class="upload-con-box">
        <div class="down-box">
          <span class="label ">第一次上传请下载</span>
          <span class="text-btn" @click="downLoadHandle">
            <i class="img iconfont iconcus-download" />
            <!-- <img class="img" src="@/assets/images/btn_upload.png" mode=""> -->
            下载模板
          </span>
        </div>
        <div v-show="fileList.length<=0" class="upload-file down-box">
          <span class="label required">上传文件</span>
          <el-upload
            ref="upload"
            :action="uploadFileUrl"
            :before-upload="handleBeforeUpload"
            :file-list="fileList"
            :limit="1"
            :on-preview="handlePreview"
            :on-error="handleUploadError"
            :on-exceed="handleExceed"
            :on-success="handleUploadSuccess"
            :headers="headers"
            class="upload-file-uploader"
            :on-change="fileStatusChange"
            :auto-upload="false"
            :show-file-list="false"
          >
            <!-- 上传按钮 -->
            <el-button slot="trigger" icon="iconfont iconcus-upload" class="cus-search-btn" type="primary">上传</el-button>
          </el-upload>
        </div>

        <div v-show="fileList.length<=0" class="tip-box">
          <span>请上传xlsx或xls格式文件</span>
        </div>

        <div v-show="fileList.length>0" class="down-box mt30">
          <span class="label">已上传</span>
          <span class="text-btn" :class="{'text-bg':selectActived}" @mouseover="selectActived=true" @mouseout="selectActived=false">
            <span class="text">{{ fileList.length>0?fileList[0].name:'' }}</span>
            <i v-show="selectActived" class="icon el-icon-close del-btn" @click="delHandle" />
          </span>
          <!-- <div class="btn">
            <el-button class="primary-btn" icon="iconfont iconcus-upload" type="primary" @click="delHandle">删除</el-button>
          </div> -->
        </div>
        <div v-show="fileList.length>0" class="btn-box">
          <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
          <el-button class="cus-search-btn" :loading="loading" @click="submitUpload">{{ loading?'上传中':'确 定' }}</el-button>
        </div>
      </div>
    </div>
    <!-- <div v-show="showFlag" class="upload-box">
      <div class="title-box">结果
      </div>
      <div class="upload-result-box">
        <div class="down-box">
          <span class="text-btn text">
            系统导入数据 <span class="num">{{ resultData.total || 0 }}</span>条
          </span>
        </div>
        <div class="down-box">
          <span class="text-btn text">
            导入失败数据 <span class="error-num">{{ resultData.total || 0 }}</span>条，
            成功导入数据 <span class="succes-num">{{ resultData.total || 0 }}</span>条
          </span>
        </div>
        <div class="btn-box">
          <el-button class="cus-search-btn" type="primary" @click="visible=false">完成</el-button>
        </div>
      </div>
    </div> -->
  </el-dialog>
</template>

<script>
import { getToken } from '@/utils/auth'
import { downloadByMinio } from '@/api/clues/clue'

export default {
  data () {
    return {
      visible: false,
      uploadFileUrl: process.env.VUE_APP_BASE_API + '/clues/clue/importData', // 上传的图片服务器地址
      selectActived: false,
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      fileList: [],
      fileType: ['xls', 'xlsx'],
      isShowTip: true,
      fileSize: 5,
      resultFlag: false,
      showFlag: false,
      resultData: {},
      loading: false
    }
  },
  computed: {
    // 是否显示提示
    // showTip () {
    //   return this.isShowTip && (this.fileType || this.fileSize)
    // }
  },
  created () {

  },
  methods: {
    init () {
      this.visible = true
      this.fileList.splice(0, 1)
      this.resultFlag = false
      this.showFlag = false
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
      //   const isLt = file.size / 1024 / 1024 <span this.fileSize
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
      console.log('-=-=-handleUploadSuccess=-=-=-=-')
      console.log('this.fileList', this.fileList)
      console.log('fileList', fileList)
      this.loading = false
      // if (res.code !== 200) {
      //   this.resultFlag = false
      //   this.fileList.splice(0, 1)
      //   return this.$message.error(res.msg)
      // }
      // this.fileList.splice(0, 1)
      // this.$message.success('上传成功')
      // this.resultFlag = true
      // this.fileList = fileList
      if (res.code !== 200) {
        return this.$message.error(res.msg || '操作失败！')
      }
      const html = `<div style="font-size: 14px;color: #333333;"><span>导入成功<span style="color: #05CC95;font-weight:700;padding: 0 5px;">${res.data.successNum}</span>条</span><span style="margin-left:20px">导入失败<span style="color: #FD7065;font-weight:700;padding: 0 5px;">${res.data.failureNum}</span>条</span></div>`
      this.$alert(html, '导入结果', {
        confirmButtonText: '确定',
        dangerouslyUseHTMLString: true
      })
      this.visible = false
      this.$emit('refreshDataList')
      console.log('this.fileList11', this.fileList)
      // this.resultData = res.data
    },
    handlePreview (file) {
      console.log('-=-=-handlePreview=-=-=-=', file)
    },
    fileStatusChange (file, fileList) {
      console.log('-=-=-fileStatusChange=-=-=-=', file)
      console.log('-=-=-fileStatusChange=-=-fileList=-=', fileList)
      if (file.status === 'ready') {
        if (this.handleBeforeUpload(file)) {
          this.fileList = fileList
        } else {
          this.fileList.splice(0, 1)
        }
      }
    },
    // 删除文件
    handleDelete () {
      this.fileList.splice(0, 1)
    },
    submitUpload () {
      console.log('-=-=-submitUpload-=-=-')
      console.log('-=-=-submitUpload-=-=-', this.$refs.upload)
      this.$refs.upload.submit()
      this.loading = true
    },
    // 获取文件名称
    getFileName (name) {
      if (name.lastIndexOf('/') > -1) {
        return name.slice(name.lastIndexOf('/') + 1).toLowerCase()
      } else {
        return ''
      }
    },
    // 删除
    delHandle () {
      this.$confirm('确认删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
        // const res = await updateActivity(form)
        // if (res.code !== 200) {
        //   return this.$message.error(res.msg)
        // }
        // this.msgSuccess("操作成功");
        // this.handleQuery();
          this.fileList.splice(0, 1)
          this.resultFlag = false
          this.showFlag = false
        }).catch(() => {

        })
    },
    async downLoadHandle () {
      const res = await downloadByMinio({ fileName: encodeURI('clues.xlsx') })
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      window.location.href = res.url
    }
  }
}
</script>

<style scoped lang="scss">
.upload-file-uploader {
  // margin-bottom: 5px;
}
.upload-file-list .el-upload-list__item {
  border: 1px solid #e4e7ed;
  line-height: 2;
  margin-bottom: 10px;
  position: relative;
}
.upload-file-list .ele-upload-list__item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: inherit;
}
.ele-upload-list__item-content-action .el-link {
  margin-right: 10px;
}

.upload-box{
  margin: -7px -21px 0 -26px ;
  .title-box{
    width: 100%;
    height: 33px;
    // line-height: 33px;
    background: #f9f9f9;
    padding: 0 13px 0 15px;
    font-size: 14px;
    font-weight: 500;
    color: #332929;
    display: flex;
    align-items: center;
    .btn{
      flex: 1;
      text-align: right;
      .img{
        width: 20px;
        height: 20px;
        cursor: pointer;
      }
    }
  }
  .upload-file{
    display: flex;
    margin-top: 30px;
  }
  .upload-con-box{
    padding: 37px 3px 0 15px;
  }
  .down-box{
    display: flex;
    align-items: center;
    .label{
      width: 112px;
      height: 20px;
      font-size: 14px;
      font-weight: 400;
      color: #666666;
      line-height: 20px;
      text-align: right;
      margin-right: 35px;
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
      &:hover,&:active{
        color: #05CC95;
      }
      .text{
        flex:1;
      }
      .del-btn{
        width: 10px;
        height: 10px;
      }
      .img{
        // width: 20px;
        // height: 20px;
        font-size: 20px;
        margin-right: 4px;
      }
      .num{
        color: #333333;
      }
      .error-num{
        color: #FD7065;
      }
      .succes-num{
        color: #05CC95;
      }
    }
    .text-bg{
       flex: 1;
      background: #f9f9f9;
    }
    .btn{
      flex: 1;
      text-align: right;
    }
  }
  .btn-box{
    margin: 32px 10px 0 0;
    text-align: right;
  }
  .upload-result-box{
    padding: 17px 3px 0 15px;
    .text{
      height: 30px;
      line-height: 30px;
      margin-top: 10px;
      &:first-child{
        margin-top: 0;
      }
    }
    .btn-box{
      text-align: right;
      margin-top: 39px;
    }
  }
  ::v-deep .el-button{
    .iconfont{
      margin-right: 6px;
    }
  }
  .tip-box{
    height: 17px;
    font-size: 12px;
    font-weight: 400;
    color: #fd7065;
    line-height: 17px;
    margin-top: 10px;
    padding-left: 147px;
  }
}

</style>
