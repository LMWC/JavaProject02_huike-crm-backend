<template>
  <div class="tabs-list-box">
    <el-tabs v-model="activeName">
      <el-tab-pane
        label="商机"
        name="tabs1"
      />
      <el-tab-pane
        label="公海池"
        name="tabs2"
      />
      <!-- <keep-alive> -->
      <component
        :is="activeName"
        @goTabs="goTabs"
      />
      <!-- </keep-alive> -->
    </el-tabs>
  </div>
</template>

<script>
import tabs1 from './list/business'
import tabs2 from './list/business-pool'
export default {
  components: {
    tabs1,
    tabs2
  },
  filters: {},
  data () {
    return {
      activeName: 'tabs1'
    }
  },
  computed: {},
  watch: {},
  created () {
    this.activeName = 'tabs1'
  },
  mounted () {},
  methods: {
    goTabs (id) {
      this.activeName = 'tabs' + id
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
