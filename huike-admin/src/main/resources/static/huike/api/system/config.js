
// 查询参数列表
function listConfig(query) {
  return $axios({
    url: '/system/config/list',
    method: 'get',
    params: query
  })
}

// 查询参数详细
function getConfig(configId) {
  return $axios({
    url: '/system/config/' + configId,
    method: 'get'
  })
}

// 根据参数键名查询参数值
function getConfigKey(configKey) {
  return $axios({
    url: '/system/config/configKey/' + configKey,
    method: 'get'
  })
}

// 新增参数配置
function addConfig(data) {
  return $axios({
    url: '/system/config',
    method: 'post',
    data: data
  })
}

// 修改参数配置
function updateConfig(data) {
  return $axios({
    url: '/system/config',
    method: 'put',
    data: data
  })
}

// 删除参数配置
function delConfig(configId) {
  return $axios({
    url: '/system/config/' + configId,
    method: 'delete'
  })
}

// 清理参数缓存
function clearCache() {
  return $axios({
    url: '/system/config/clearCache',
    method: 'delete'
  })
}

// 导出参数
function exportConfig(query) {
  return $axios({
    url: '/system/config/export',
    method: 'get',
    params: query
  })
}