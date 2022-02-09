
// 查询字典类型列表
function listType(query) {
  return $axios({
    url: '/system/dict/type/list',
    method: 'get',
    params: query
  })
}

// 查询字典类型详细
function getType(dictId) {
  return $axios({
    url: '/system/dict/type/' + dictId,
    method: 'get'
  })
}

// 新增字典类型
function addType(data) {
  return $axios({
    url: '/system/dict/type',
    method: 'post',
    data: data
  })
}

// 修改字典类型
function updateType(data) {
  return $axios({
    url: '/system/dict/type',
    method: 'put',
    data: data
  })
}

// 删除字典类型
function delType(dictId) {
  return $axios({
    url: '/system/dict/type/' + dictId,
    method: 'delete'
  })
}

// 清理参数缓存
function clearCache() {
  return $axios({
    url: '/system/dict/type/clearCache',
    method: 'delete'
  })
}

// 导出字典类型
function exportType(query) {
  return $axios({
    url: '/system/dict/type/export',
    method: 'get',
    params: query
  })
}

// 获取字典选择框列表
function optionselect() {
  return $axios({
    url: '/system/dict/type/optionselect',
    method: 'get'
  })
}