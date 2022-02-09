<template>
  <div class="count-analys">
    <tab :tabs-params="['客户统计', '销售统计', '线索统计', '渠道统计']" @toggleTabs="getToggleTabs" />
    <!-- 模块展示部分 -->
    <div>
      <!-- 客户统计 -->
      <customerAnalysis :tabs-index="tabsIndex" :depart-and-belonger-list="departAndBelongerList.slice(0, 2)" />
      <!-- 销售统计 -->
      <saleAnalysis :tabs-index="tabsIndex" :depart-and-belonger-list="departAndBelongerList.slice(0, 2)" />
      <!-- 线索统计 -->
      <clueAnalysis :tabs-index="tabsIndex" :depart-and-belonger-list="departAndBelongerList.slice(0, 2)" />
      <!-- 渠道统计 -->
      <channelAnalysis :tabs-index="tabsIndex" :depart-and-belonger-list="departAndBelongerList.slice(0, 2)" />
    </div>

  </div>
</template>

<script>
import Tab from '@/components/common/Tab.vue'
import customerAnalysis from './components/customerAnalysis/customerAnalysis.vue'
import saleAnalysis from './components/saleAnalysis/saleAnalysis.vue'
import clueAnalysis from './components/clueAnalysis/clueAnalysis.vue'
import channelAnalysis from './components/channelAnalysis/channelAnalysis.vue'
import { treeselect } from '@/api/system/dept'

export default {
  components: {
    Tab,
    customerAnalysis,
    saleAnalysis,
    clueAnalysis,
    channelAnalysis
  },
  data () {
    return {
      tabsIndex: 0,
      // 分散给子组件的下拉框数据
      departAndBelongerList: []
    }
  },
  created () {
    this.getDicts('clues_item').then(response => {
      this.departAndBelongerList.push(response.data)
    })
    /** 查询部门下拉树结构 */
    treeselect().then(response => {
      // this.departmentOptions = response.data
      this.departAndBelongerList.push(response.data)
    })
  },
  methods: {
    getToggleTabs (index) {
      this.tabsIndex = index
    },
    // 归属部门&&归属人 统一调用分散给子组件
    getDepartAndBelonger () {
      // TODO
      // 所属渠道接口前置 岗位放最后
      Promise.all([]).then(res => {
        this.departAndBelongerList = [...res]
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .count-analys {
    // border: 1px solid red;
  }
</style>
