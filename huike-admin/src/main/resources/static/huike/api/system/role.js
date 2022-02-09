
// 查询角色列表
function listRole(query) {
  return $axios({
    url: '/system/role/list',
    method: 'get',
    params: query
  })
}

// 查询角色详细
function getRole(roleId) {
  return $axios({
    url: '/system/role/' + roleId,
    method: 'get'
  })
}

// 新增角色
function addRole(data) {
  return $axios({
    url: '/system/role',
    method: 'post',
    data: data
  })
}

// 修改角色
function updateRole(data) {
  return $axios({
    url: '/system/role',
    method: 'put',
    data: data
  })
}

// 角色数据权限
function dataScope(data) {
  return $axios({
    url: '/system/role/dataScope',
    method: 'put',
    data: data
  })
}

// 角色状态修改
function changeRoleStatus(roleId, status) {
  const data = {
    roleId,
    status
  }
  return $axios({
    url: '/system/role/changeStatus',
    method: 'put',
    data: data
  })
}

// 删除角色
function delRole(roleId) {
  return $axios({
    url: '/system/role/' + roleId,
    method: 'delete'
  })
}

// 导出角色
function exportRole(query) {
  return $axios({
    url: '/system/role/export',
    method: 'get',
    params: query
  })
}

// 查询角色下拉
function getRoleSelect() {
  return $axios({
    url: '/system/role/optionselect',
    method: 'get'
  })
}