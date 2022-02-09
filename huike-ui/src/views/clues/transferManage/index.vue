<template>
  <div class="transfer-manage">
    <!-- <Tab :tabs-params="['全部', '代办', '已延期', '已完成']" @toggleTabs="getToggleTabs" /> -->
    <!-- form+table -->
    <div>
      <allTransfer :tabs-index="tabsIndex" />
      <!-- <agencyTransfer :tabs-index="tabsIndex" /> -->
      <!-- <delayTransfer :tabs-index="tabsIndex" /> -->
      <!-- <completeTransfer :tabs-index="tabsIndex" /> -->
    </div>
  </div>
</template>

<script>
// import Tab from '@/components/common/Tab.vue'
// 全部
import allTransfer from './components/allTransfer.vue'
// 代办
// import agencyTransfer from './components/agencyTransfer.vue'
// 延期
// import delayTransfer from './components/delayTransfer.vue'
// 完成
// import completeTransfer from './components/completeTransfer.vue'

export default {
  components: {
    // Tab,
    allTransfer
    // agencyTransfer,
    // delayTransfer,
    // completeTransfer
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
  .transfer-manage {

  }
</style>
