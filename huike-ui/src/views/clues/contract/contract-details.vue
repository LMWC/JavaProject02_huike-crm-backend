<template>
  <div class="customer-detail-box">
    <el-row>
      <el-col :span="24">
        <div class="l-box">
          <div class="l-top-box">
            <div class="title-box">
              <span class="title-label">合同详情</span>
              <div class="btn-box">
                <el-button
                  icon="el-icon-view"
                  class="cus-reset-btn"
                  @click="previewHandle(dataForm.fileName)"
                >预览</el-button>
              </div>
            </div>
            <div class="con-box">
              <div class="info-box">
                <div class="info-list mt0">
                  <span class="label">客户姓名</span>
                  <span class="des">{{ dataForm.name }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">购买学科</span>
                  <span class="des">{{ subjectFormat(dataForm.subject) }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">购买课程</span>
                  <span class="des">{{ courseName }}</span>
                </div>
                <div class="info-list mt0">
                  <span class="label">购买金额</span>
                  <span class="des">{{ dataForm.order }}</span>
                </div>
                <div class="info-list">
                  <span class="label">是否折扣</span>
                  <span class="des">{{ (dataForm.discountType&&dataForm.discountType!=='无')?'是':'否' }}</span>
                </div>
                <div class="info-list">
                  <span class="label">创建时间</span>
                  <span class="des">{{ dataForm.createTime }}</span>
                </div>
                <div class="info-list">
                  <span class="label">折扣类型</span>
                  <span class="des">{{ dataForm.discountType }}</span>
                </div>
              </div>
            </div>
            <div class="search-btn">
              <el-button class="cus-reset-btn" @click="goPage()">返回</el-button>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 弹窗, 新增 / 修改 -->
    <preview-pdf v-if="pdfVisible" ref="pdf" title="预览合同" :pdf-url="pdfUrl" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getContract } from '@/api/contract/contract'
import { listCourseSelect } from '@/api/clues/course'
import PreviewPdf from '@/components/PreviewPdf'
export default {
  components: {
    PreviewPdf
  },
  filters: {},
  data () {
    return {
      addOrUpdateVisible: false,
      saveLoading: false,
      showBtnFlag: false,
      // 详情数据
      dataForm: {
        id: '',
        pageType: 'info'
      },
      pdfUrl: '',
      pdfVisible: false,
      courseName: '',
      // order: '',
      // 意向学科字典
      subjectOptions: [],
      dataList: []
    }
  },
  computed: {
    ...mapGetters(['name']),
    rules () {
      return {

      }
    }
  },
  watch: {},
  created () {
    const queryParams = this.$route.query
    this.dataForm = {
      ...this.dataForm,
      ...queryParams
    }
    this.getDicList()
    this.getInfo()
  },
  mounted () {},
  methods: {
    async getInfo () {
      const res = await getContract(this.dataForm.id)
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.dataForm = {
        ...this.dataForm,
        ...res.data
      }
      if (this.dataForm.courseId !== null && this.dataForm.courseId !== '') {
        this.getCourseList()
      }
    },
    // 所有课程列表
    async getCourseList () {
      const res = await listCourseSelect()
      if (res.code !== 200) {
        return this.$message.error(res.msg)
      }
      this.courseOptions = res.data
      const course = this.courseOptions.find(item => item.id === this.dataForm.courseId)
      if (course) {
        this.courseName = course.name
        // this.order = course.price
      }
    },
    // 意向学科字典翻译
    subjectFormat (val) {
      return this.selectDictLabel(this.subjectOptions, val)
    },
    getDicList () {
      this.getDicts('course_subject').then(response => {
        this.subjectOptions = response.data
      })
    },
    // 预览
    previewHandle (url) {
      console.log('previewHandle')
      this.pdfUrl = url
      this.pdfVisible = true
      this.$nextTick(() => {
        this.$refs.pdf.visible = true
        this.$refs.pdf.init()
      //   this.$refs.allot.init()
      })
    },
    goPage () {
      this.$router.push({ path: '/contract' })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "~@/assets/styles/modules/clue-details.scss";
.customer-detail-box{
  .flow-box{
    padding: 58px 120px 0 97px !important;
  }
  .info-list{
    width: 25% !important;
  }
  .steps-box{
    display: flex;
    .steps-list{
      flex-basis: 50%;
      .step-item{
        display: flex;
        height: 45px;
        align-items: center;
        .step{
          width: 45px;
          height: 45px;
          line-height: 39px;
          border: 3px solid #e9e9eb;
          border-radius: 50%;
          font-size: 24px;
          font-weight: 500;
          color: #666666;
          text-align: center;
          position: relative;
        }
      }
      &:nth-child(5){
        max-width: 50px;
        text-align: center;
      }
    }
    .step-con{
      margin-top: 8px;
      .des{
        height: 17px;
        line-height: 17px;
        font-size: 12px;
        font-weight: 500;
        color: #666666;
      }
      .name{
        height: 20px;
        line-height: 20px;
        margin-top: 6px;
        font-size: 14px;
        font-weight: 500;
        color: #333333;
      }
      .date-box{
        padding-top: 10px;
        .date-label{
          height: 18px;
          line-height: 18px;
          font-size: 13px;
          font-weight: 500;
          color: #666666;
          margin-top: 8px;
        }
        .date{
          margin-top: 8px;
          height: 18px;
          line-height: 18px;
          font-size: 13px;
          font-weight: 400;
          color: #333333;
        }
      }
    }
  }

  .line-box{
    position: relative;
    height: 30px;
    flex: 1;
    margin: 0 20px 0 20px;
    .line{
      width: 100%;
      height: 1px;
      border-bottom: 1px dashed #e9e9eb;
      display: inline-block;
      font-size: 14px;
      color: #e9e9eb;
      position: relative;
      i{
        position: absolute;
        right: -7px;
        top: -6px;
      }
    }
  }
  .actived{
    .step{
      border: 3px solid #05cc95 !important;
      color: #05cc95 !important;
    }
    .line{
      border-bottom: 1px dashed #05CC95;
      color: #05CC95;
    }
  }
  .reject-current{
    .line{
      border-bottom: 1px dashed #e9e9eb;
      color: #e9e9eb;
    }
  }
  .pass-box{
    width: 24px;
    height: 24px;
    line-height: 24px;
    text-align: center;
    background: #05cc95;
    border-radius: 50%;
    box-shadow: 0px 2px 4px 0px rgba(39,155,123,0.21);
    font-size: 16px;
    color: #ffffff;
    position: absolute;
    right: -12px;
    bottom: 0;
  }
  .reject-box{
    width: 24px;
    height: 24px;
    line-height: 24px;
    text-align: center;
    background: #fd7065;
    border-radius: 50%;
    box-shadow: 0px 2px 4px 0px rgba(52,56,55,0.21);
    font-size: 16px;
    color: #ffffff;
    position: absolute;
    right: -12px;
    bottom: 0;
  }
  .search-btn{
    padding: 20px;
    text-align: right;
  }
  .finish-step{
    max-width: 130px !important;
    text-align: left !important;
  }
}

</style>
