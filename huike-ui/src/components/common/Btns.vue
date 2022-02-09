<template>
  <div class="btns">
    <ul v-if="btnsParams.length > 0">
      <li v-for="( item, index ) in btnsParams" :key="index" :class="{ 'active': index === nowBtnIndex }" @click.stop="toggleBtns(item, index)">{{ item }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    btnsParams: {
      type: Array,
      required: true,
      default: () => []
    },
    indexBtns: {
      type: Number,
      requierd: true,
      default: 0
    }
  },
  data () {
    return {
      // nowBtnIndex: 0
      nowBtnIndex: this.indexBtns
    }
  },
  watch: {
    index (val) {
      this.nowBtnIndex = val
    }
  },
  methods: {
    toggleBtns (item, index) {
      this.nowBtnIndex = index
      this.$emit('sendBtnIndex', { item, index })
    }
  }
}
</script>

<style lang="scss" scoped>
  .btns {
    width: 100%;
    display: flex;
    justify-content: flex-end;
    ul {
      width: 260px;
      display: flex;
      margin: 0;
      padding: 0;
      list-style: none;
      height: 40px;
      border-radius: 7px;
      background: #f9f9f9;
      border: 1px solid #e9e9eb;
      padding: 3px 4px;
      box-sizing: border-box;
      li {
        flex: 1;
        padding: 6px 20px;
        border-radius: 6px;
        height: 32px;
        font-size: 14px;
        font-family: PingFangSC, PingFangSC-Semibold;
        font-weight: 600;
        text-align: center;
        cursor: pointer;
        color: #aeb5c4;
      }

      .active {
        color: #333333;
        transition: all 0.2s ease-in;
        background: #ffffff;
        box-shadow: 0px 1px 4px 0px rgba(0,0,0,0.06);
      }
    }
  }
</style>
