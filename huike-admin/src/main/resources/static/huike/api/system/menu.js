
// 查询菜单列表
function listMenu(query) {
  return $axios({
    url: '/system/menu/list',
    method: 'get',
    params: query
  })
}

// 查询菜单详细
function getMenu(menuId) {
  return $axios({
    url: '/system/menu/' + menuId,
    method: 'get'
  })
}

// 查询菜单下拉树结构
function treeselect() {
  return $axios({
    url: '/system/menu/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询菜单下拉树结构
function roleMenuTreeselect(roleId) {
  return $axios({
    url: '/system/menu/roleMenuTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增菜单
function addMenu(data) {
  return $axios({
    url: '/system/menu',
    method: 'post',
    data: data
  })
}

// 修改菜单
function updateMenu(data) {
  return $axios({
    url: '/system/menu',
    method: 'put',
    data: data
  })
}

// 删除菜单
function delMenu(menuId) {
  return $axios({
    url: '/system/menu/' + menuId,
    method: 'delete'
  })
}