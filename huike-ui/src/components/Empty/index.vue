<template>
  <div class="empty-box" :style="{height:boxHeight+'px'}">
    <img class="icon" src="@/assets/images/icon-empty.png">
    <span class="label">暂无内容</span>
  </div>
</template>

<script>
export default {
  components: {},
  filters: {},
  props: {
    boxHeight: {
      type: Number,
      default: 320
    }
  },
  data () {
    return {
    }
  },
  computed: {},
  watch: {},
  created () {},
  mounted () {},
  methods: {}
}
</script>
<style lang="scss" scoped>
.empty-box{
  width: 100%;
  min-height: 320px;
  display: flex;
  flex-flow: column;
  align-items: center;
  justify-content: center;
  .icon{
    width: 370px;
    height: 199px;
  }
  .label{
    height: 20px;
    line-height: 20px;
    font-size: 14px;
    color: #666666;
    margin-top: 41px;
    font-weight: 500;
  }
}
</style>
