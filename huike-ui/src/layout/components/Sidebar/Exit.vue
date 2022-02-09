<template>
  <div class="exit-box" :class="{'collapse':collapse}">
    <transition name="exitFade">
      <div class="exit-con">
        <div class="user-avatar-box" @click="goNotice">
          <el-badge :value="$store.state.user.value" :max="100" class="item">
            <img :src="avatar" class="user-avatar">
          </el-badge>
        </div>
        <!-- <el-tooltip class="item" effect="dark" :content="name" placement="top-start"> -->
        <div class="user-name ellipsis">{{ name }}</div>
        <!-- </el-tooltip> -->
        <img src="@/assets/images/exit.png" alt="" class="user-exit" @click="logout">
      </div>
    </transition>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { allSystemNoticeListByStatus } from '@/api/system/noticeCenter'
import { checkPermi } from '@/utils/permission'
export default {
  components: {},
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar'
    ])
  },
  created () {
    this.$store.dispatch('updateValueNum')
  },
  mounted () {},
  methods: {
    async logout () {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/index'
        })
      })
    },
    goNotice () {
      if (!checkPermi(['system:noticeCenter:list'])) {
        return false
      }
      this.$router.push('/system/noticeCenter')
    }
  }
}
</script>
<style lang="scss" scoped>
.exitFade-enter-active {
  transition: opacity 1.5s;
}

.exitFade-enter,
.exitFade-leave-to {
  opacity: 0;
}
.exit-box{
  .exit-con{
    cursor: pointer;
    height: 70px;
    border-top: 1px solid #393c43;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    padding: 0 20px;
    .user-avatar-box{
      width: 34px;
      height: 34px;
      .user-avatar{
        width: 34px;
        height: 34px;
        border-radius: 50%;
      }
    }
    .user-name{
      // flex: 1;
      min-width: 60px;
      padding: 0 12px;
      font-size: 14px;
      color: #aeb5c4;
      border-right: 1px solid #aeb5c4;;
    }
    .user-exit{
      width: 18px;
      height: 14px;
      margin-left: 15px;
      cursor: pointer;
    }
  }
}
.collapse{
   .exit-con{
     height: 100px;
     flex-flow: column;
     justify-content: center;
     .user-name{
       max-width: 70px;
       margin-top: 7px;
       border-right: 0;
       font-size: 12px;
     }
     .user-exit{
       margin-top: 6px;
       width: 14px;
       height: 10px;
       margin-left: 0;
       cursor: pointer;
     }
     ::v-deep .el-badge__content.is-fixed{
       top: 9px;
     }
   }
}
</style>
