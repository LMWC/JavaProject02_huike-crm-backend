<template>
  <el-dialog :title="title" :visible.sync="visible" width="80%" append-to-body :close-on-click-modal="false">
    <div v-loading="!loading" style="min-height: 400px">
      <div v-if="visible">
        <pdf v-for="i in numPages" :key="i" :src="url" :page="i" />
      </div>
    </div>

    <!-- <div slot="footer" class="dialog-footer">
      <el-button class="cus-reset-btn" @click="visible = false">取 消</el-button>
      <el-button class="cus-search-btn" @click="submitForm">确 定</el-button>
    </div> -->
  </el-dialog>
</template>

<script>
import pdf from 'vue-pdf'
export default {
  components: {
    pdf
  },
  props: {
    title: {
      type: String,
      default: ''
    },
    pdfUrl: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      visible: false,
      url: '',
      numPages: undefined,
      loading: false
    }
  },
  computed: {

  },
  watch: {

  },
  created () {
  },
  mounted () {
  },
  methods: {
    init () {
      this.loading = false
      this.numPages = undefined
      this.url = pdf.createLoadingTask(this.pdfUrl)
      this.getNumPages()
    },
    getNumPages () {
      console.log('-==-getNumPages-=-=-', this.url)
      this.url.promise.then(pdf => {
        console.log('-==-pdf-=-=-', pdf)
        this.numPages = pdf.numPages
        console.log('this.numPages', this.numPages)
        this.loading = true
      }).catch(() => {
        // console.error('pdf加载失败')
        this.loading = true
        this.$message.error('合同附件加载失败')
      })
    }
  }
}
</script>
<style lang="scss" scoped>

</style>
