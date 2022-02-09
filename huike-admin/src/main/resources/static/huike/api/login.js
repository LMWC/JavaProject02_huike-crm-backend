// 登录方法
function login(data) {
  return $axios({
    url: '/login',
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
function getInfo() {
  return $axios({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
function logout() {
  return $axios({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
function getCodeImg() {
  return $axios({
    url: '/captchaImage',
    method: 'get'
  })
}