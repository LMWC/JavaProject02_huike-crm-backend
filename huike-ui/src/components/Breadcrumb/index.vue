<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">
        <span v-if="item.redirect==='noRedirect'||index==levelList.length-1" class="no-redirect" :class="{'current-bread':index===levelList.length-1}">{{ item.meta.title }}</span>
        <a v-else :class="{'current-bread':index===levelList.length-1}" @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
export default {
  data () {
    return {
      levelList: null
    }
  },
  watch: {
    $route (route) {
      // if you go to the redirect page, do not update the breadcrumbs
      if (route.path.startsWith('/redirect/')) {
        return
      }
      this.getBreadcrumb()
    }
  },
  created () {
    this.getBreadcrumb()
  },
  methods: {
    getBreadcrumb () {
      // only show routes with meta.title
      console.log(' this.$route.matched', this.$route.matched)
      let matched = this.$route.matched.filter(item => item.meta && item.meta.title && item.path)
      const first = matched[0]
      console.log('matched', matched)
      if (!this.isDashboard(first)) {
        matched = [{ path: '/index', meta: { title: '首页' } }].concat(matched)
      }

      this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
    },
    isDashboard (route) {
      const name = route && route.name
      if (!name) {
        return false
      }
      return name.trim() === '首页'
    },
    handleLink (item) {
      const { redirect, path } = item
      if (redirect) {
        this.$router.push(redirect)
        return
      }
      this.$router.push(path)
    }
  }
}
</script>

<style lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 66px;
  // margin-left: 8px;

  .no-redirect {
    // color: #97a8be;
    color: #AEB5C4;
    cursor: text;
  }
  ::v-deep .el-breadcrumb__inner{
    color: #AEB5C4;
  }
  .el-breadcrumb__inner.is-link, .el-breadcrumb__inner a{
    color: #AEB5C4;
  }
  .current-bread{
    color: var(--current-color);
  }
  ::v-deep .el-breadcrumb__separator{
    color: #aeb5c4;
  }
}
</style>
