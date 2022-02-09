import request from '@/utils/request'

// 操作日志查看
export function monitorOperlogByOperIds () {
  return request({
    url: `/monitor/operlog/{operIds}`,
    method: 'delete'
  })
}

// 操作日志清空
export function monitorOperlogClean () {
  return request({
    url: '/monitor/operlog/clean',
    method: 'delete'
  })
}

// 操作日志导出
export function monitorOperlogExport () {
  return request({
    url: '/monitor/operlog/export',
    method: 'get'
  })
}

// 操作日志列表
export function monitorOperlogList(params) {
  return request({
    url: '/monitor/operlog/list',
    method: 'get',
    params: params
  })
}