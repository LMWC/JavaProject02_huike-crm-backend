<template>
  <div class="tabs-list-box">
    <el-tabs v-model="activeName">
      <el-tab-pane
        label="商机分配"
        name="tabs1"
      />
      <el-tab-pane
        label="公海管理"
        name="tabs2"
      />
      <!-- <keep-alive> -->
      <component
        :is="activeName"
        rule-type="1"
        @goTabs="goTabs"
      />
      <!-- </keep-alive> -->
    </el-tabs>
  </div>
</template>

<script>
import tabs1 from '../clew/list/clew'
import tabs2 from '../clew/list/clew-pool'
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
