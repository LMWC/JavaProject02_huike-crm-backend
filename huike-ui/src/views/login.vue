<template>
  <div class="login">
    <div class="login-box">
      <img class="login-l-img" src="../assets/images/login-l.png" alt="">
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <div class="login-form-title">
          <img class="logo" src="../assets/images/login-logo.png" alt="">
          <span class="title-label">汇客CRM系统</span>
        </div>
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号" prefix-icon="iconfont iconcus-user" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            prefix-icon="iconfont iconcus-pwd"
            @keyup.enter.native="handleLogin"
          />
        </el-form-item>
        <el-form-item prop="code">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="验证码"
            style="width: 58%"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" class="login-code-img" @click="getCode">
          </div>
        </el-form-item>
        <!-- <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox> -->
        <el-form-item style="width:100%;">
          <el-button
            :loading="loading"
            class="login-btn"
            size="medium"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
          >
            <span v-if="!loading">登录</span>
            <span v-else>登录中...</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--  底部  -->
    <!-- <div class="el-login-footer">
      <span>Copyright © 2018-2021 huike.vip All Rights Reserved.</span>
    </div> -->
  </div>
</template>

<script>
import { getCodeImg } from '@/api/login'
import Cookies from 'js-cookie'
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: 'Login',
  data () {
    return {
      codeUrl: '',
      cookiePassword: '',
      loginForm: {
        username: 'admin',
        password: 'admin123',
        rememberMe: false,
        code: '',
        uuid: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', message: '用户名不能为空' }],
        password: [{ required: true, trigger: 'blur', message: '密码不能为空' }],
        code: [{ required: true, trigger: 'change', message: '验证码不能为空' }]
      },
      loading: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created () {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode () {
      getCodeImg().then(res => {
        this.codeUrl = 'data:image/gif;base64,' + res.img
        this.loginForm.uuid = res.uuid
      })
    },
    getCookie () {
      const username = Cookies.get('username')
      const password = Cookies.get('password')
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin () {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set('username', this.loginForm.username, { expires: 30 })
            Cookies.set('password', encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove('username')
            Cookies.remove('password')
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch('Login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' }).catch(() => {})
          }).catch(() => {
            this.loading = false
            this.getCode()
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  // background-image: url("../assets/images/login-background.jpg");
  // background-size: cover;
  background: #2b303b;
}


.login-box{
  width: 886px;
  height: 426px;
  border-radius: 8px;
  display: flex;
  .login-l-img{
    width: 533px;
    height: 426px;
  }
}


.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  // border-radius: 6px;
  background: #ffffff;
  width: 353px;
  padding: 62px 72px 0 66px;
  box-sizing: border-box;
  border-radius: 0px 8px 8px 0px;
  .el-form-item{
    margin-bottom: 30px;
  }
  .el-form-item.is-error .el-input__inner{
    border: 0 !important;
    border-bottom: 1px solid #fd7065 !important;
    background: #fff !important;
  }
  // .el-input {
  //   height: 30px;
  //   input {
  //     height: 30px;
  //   }
  // }
  .input-icon {
    height: 32px;
    width: 18px;
    margin-left: -2px;
  }
  .el-input__inner{
    border: 0;
    border-bottom: 1px solid #e9e9e8;
    border-radius: 0;
    font-size: 12px;
    font-weight: 400;
    color: #333333;
    height: 32px;
    line-height: 32px;
  }
  .el-input__prefix{
    left: 0;
    // top: -1px;
  }
  .el-input--prefix .el-input__inner{
    padding-left: 26px;
  }
  .el-input__inner::placeholder{
    color: #aeb5c4;
  }
  .el-form-item--medium .el-form-item__content{
    line-height: 32px;
  }
  .el-input--medium .el-input__icon{
    line-height: 32px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 40%;
  height: 32px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 32px;
}
.login-btn{
  border-radius: 17px;
  padding: 11px 20px !important;
  margin-top: 10px;
  font-weight: 500;
  font-size: 12px;
  border: 0;
  &:hover,&:focus{
    background: #09a57a;
    color: #ffffff;
  }
}
.login-form-title{
  height: 36px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 40px;
  .logo{
    width: 33px;
    height: 36px;
  }
  .title-label{
    // height: 28px;
    font-weight: 500;
    // line-height: 28px;
    // font-family: FZLTZHK;
    font-size: 20px;
    color: #333333;
    margin-left: 10px;
  }
}
</style>
