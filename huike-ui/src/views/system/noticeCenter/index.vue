<template>
  <div class="noticeCenter">
    <Tab :tabs-params="['未读', '已读']" @toggleTabs="getToggleTabs" />
    <div>
      <!-- 未读 -->
      <unRead :tabs-index="tabsIndex" />
      <!-- 已读 -->
      <read :tabs-index="tabsIndex" />
    </div>
  </div>
</template>

<script>
import Tab from '@/components/common/Tab.vue'
import unRead from './components/unRead.vue'
import read from './components/read.vue'

export default {
  components: {
    Tab,
    unRead,
    read
  },
  data () {
    return {
      tabsIndex: 0
    }
  },
  methods: {
    getToggleTabs (index) {
      this.tabsIndex = index
    }
  }
}
</script>

<style lang="scss" scoped>
  .noticeCenter {

  }
</style>
