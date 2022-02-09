<template>
  <div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: sideTheme === 'theme-dark' ? variables.menuBg : variables.menuLightBg }">
    <transition name="sidebarLogoFade">
      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logoMini" :src="logoMini" class="sidebar-logo logo-mini">
        <h1 v-else class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.sidebarTitle : variables.sidebarLightTitle }">{{ title }} </h1>
      </router-link>
      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
        <h1 class="sidebar-title" :style="{ color: sideTheme === 'theme-dark' ? variables.sidebarTitle : variables.sidebarLightTitle }">{{ title }} </h1>
      </router-link>
    </transition>
  </div>
</template>

<script>
import logoImg from '@/assets/logo/logo.png'
import logoMiniImg from '@/assets/logo/logo-mini.png'
import variables from '@/assets/styles/variables.scss'

export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {
      title: '汇客CRM系统',
      logo: logoImg,
      logoMini: logoMiniImg
    }
  },
  computed: {
    variables () {
      return variables
    },
    sideTheme () {
      return this.$store.state.settings.sideTheme
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 140px;
  line-height: 140px;
  background: #2b2f3a;
  text-align: center;
  overflow: hidden;
  // padding-top: 10px;
  box-sizing: border-box;

  & .sidebar-logo-link {
    padding-top: 10px;
    height: 100%;
    width: 100%;
    display: flex;
    flex-flow: column;
    align-items: center;
    justify-content: center;
    box-sizing: border-box;

    & .sidebar-logo {
      width: 41px;
      height: 46px;
      vertical-align: middle;
      // margin-right: 12px;
    }

    & .logo-mini {
      width: 37px;
      height: 40px;
      vertical-align: middle;
      // margin-right: 12px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 16px 0 0;
      padding: 0;
      font-size: 18px;
      font-family: FZLTZHK;
      text-align: left;
      color: #ffffff;
      line-height: 25px;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
.hideSidebar{
  .sidebar-logo-container{
    height: 130px;
    line-height: 130px;
  }
  .sidebar-logo-link{
    justify-content: flex-start;
    padding-top: 36px;
  }
}
</style>
