
// 操作日志查看
function monitorOperlogByOperIds () {
  return $axios({
    url: `/monitor/operlog/{operIds}`,
    method: 'delete'
  })
}

// 操作日志清空
function monitorOperlogClean () {
  return $axios({
    url: '/monitor/operlog/clean',
    method: 'delete'
  })
}

// 操作日志导出
function monitorOperlogExport () {
  return $axios({
    url: '/monitor/operlog/export',
    method: 'get'
  })
}

// 操作日志列表
function monitorOperlogList(params) {
  return $axios({
    url: '/monitor/operlog/list',
    method: 'get',
    params: params
  })
}