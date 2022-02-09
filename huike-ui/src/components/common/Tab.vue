<template>
  <div class="Tabs">
    <ul>
      <li v-for="(item, index) in tabsParams" v-cloak :key="index" class="li-tab" :class="{ active: index === nowIndex }" @click.stop="toggleTabs(index)">
        {{ item }}
        <span />
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    tabsParams: {
      required: true,
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      componentName: 'Tabs',
      nowIndex: 0
    }
  },
  methods: {
    toggleTabs (index) {
      this.nowIndex = index
      this.$emit('toggleTabs', index)
    }
  }
}
</script>

<style lang="scss" scoped>
  [v-cloak] {
    display: none!important;
  }
  .Tabs {
    width: 100%;
    border-bottom: 1px solid #e9e9e8;
    margin-top: 24px;
    ul {
      display: flex;
      text-align: center;
      margin: 0;
      list-style: none;
      margin-bottom: 22px;
      padding: 0;
      padding-left: 20px;
      box-sizing: border-box;
      // transition: all 0.3s 0.2s ease-out;
      li {
        position: relative;
        color: #aeb5c4;
        font-size: 14px;
        cursor: pointer;
        display:inline-block;
        text-align: center;
        font-family: PingFangSC, PingFangSC-Regular;
        font-weight: 400;
        margin-left: 60px;
      }
      li:first-child {
        margin-left: 0;
      }
    }

    .active {
      font-size: 14px;
      font-family: PingFangSC, PingFangSC-Medium;
      color: #333333;
      border-radius: 1px;
      font-weight: 550;

      span {
        position: absolute;
        left: 48%;
        margin-top: 18px;
        transform: translateX(-50%);
        display: block;
        width: 40px;
        height: 4px;
        opacity: 1;
        background: #05cc95;
        border-radius: 2px;
        text-align: left;
      }
    }
  }
</style>
