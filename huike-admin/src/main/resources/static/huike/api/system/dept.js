
// 查询部门列表
function listDept(query) {
  return $axios({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}

// 查询部门列表（排除节点）
function listDeptExcludeChild(deptId) {
  return $axios({
    url: '/system/dept/list/exclude/' + deptId,
    method: 'get'
  })
}

// 查询部门详细
function getDept(deptId) {
  return $axios({
    url: '/system/dept/' + deptId,
    method: 'get'
  })
}

// 查询部门下拉树结构
function treeselect() {
  return $axios({
    url: '/system/dept/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询部门树结构
function roleDeptTreeselect(roleId) {
  return $axios({
    url: '/system/dept/roleDeptTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增部门
function addDept(data) {
  return $axios({
    url: '/system/dept',
    method: 'post',
    data: data
  })
}

// 修改部门
function updateDept(data) {
  return $axios({
    url: '/system/dept',
    method: 'put',
    data: data
  })
}

// 删除部门
function delDept(deptId) {
  return $axios({
    url: '/system/dept/' + deptId,
    method: 'delete'
  })
}

// 查询部门和用户树结构
function deptAndUserTreeList() {
  return $axios({
    url: '/system/dept/treeAnduser',
    method: 'get'
  })
}