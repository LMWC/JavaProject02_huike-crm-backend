import { praseStrEmpty } from "@/utils/common";

// 查询用户列表
function listUser(query) {
  return $axios({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}

// 获取用户下拉列表
function listUserSelect(query) {
  return $axios({
    url: '/system/user/listselect',
    method: 'get',
    params: query
  })
}

// 查询用户详细
function getUser(userId) {
  return $axios({
    url: '/system/user/' + praseStrEmpty(userId),
    method: 'get'
  })
}

// 新增用户
function addUser(data) {
  return $axios({
    url: '/system/user',
    method: 'post',
    data: data
  })
}

// 修改用户
function updateUser(data) {
  return $axios({
    url: '/system/user',
    method: 'put',
    data: data
  })
}

// 删除用户
function delUser(userId) {
  return $axios({
    url: '/system/user/' + userId,
    method: 'delete'
  })
}

// 导出用户
function exportUser(query) {
  return $axios({
    url: '/system/user/export',
    method: 'get',
    params: query
  })
}

// 用户密码重置
function resetUserPwd(userId, password) {
  const data = {
    userId,
    password
  }
  return $axios({
    url: '/system/user/resetPwd',
    method: 'put',
    data: data
  })
}

// 用户状态修改
function changeUserStatus(userId, status) {
  const data = {
    userId,
    status
  }
  return $axios({
    url: '/system/user/changeStatus',
    method: 'put',
    data: data
  })
}

// 查询用户个人信息
function getUserProfile() {
  return $axios({
    url: '/system/user/profile',
    method: 'get'
  })
}

// 修改用户个人信息
function updateUserProfile(data) {
  return $axios({
    url: '/system/user/profile',
    method: 'put',
    data: data
  })
}

// 用户密码重置
function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return $axios({
    url: '/system/user/profile/updatePwd',
    method: 'put',
    params: data
  })
}

// 用户头像上传
function uploadAvatar(data) {
  return $axios({
    url: '/system/user/profile/avatar',
    method: 'post',
    data: data
  })
}

// 下载用户导入模板
function importTemplate() {
  return $axios({
    url: '/system/user/importTemplate',
    method: 'get'
  })
}
