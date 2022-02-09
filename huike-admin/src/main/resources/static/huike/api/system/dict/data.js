
// 查询字典数据列表
function listData(query) {
  return $axios({
    url: '/system/dict/data/list',
    method: 'get',
    params: query
  })
}

// 查询字典数据详细
function getData(dictCode) {
  return $axios({
    url: '/system/dict/data/' + dictCode,
    method: 'get'
  })
}

// 根据字典类型查询字典数据信息
function getDicts(dictType) {
  return $axios({
    url: '/system/dict/data/type/' + dictType,
    method: 'get'
  })
}

// 新增字典数据
function addData(data) {
  return $axios({
    url: '/system/dict/data',
    method: 'post',
    data: data
  })
}

// 修改字典数据
function updateData(data) {
  return $axios({
    url: '/system/dict/data',
    method: 'put',
    data: data
  })
}

// 删除字典数据
function delData(dictCode) {
  return $axios({
    url: '/system/dict/data/' + dictCode,
    method: 'delete'
  })
}

// 导出字典数据
function exportData(query) {
  return $axios({
    url: '/system/dict/data/export',
    method: 'get',
    params: query
  })
}