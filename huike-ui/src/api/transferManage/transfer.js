import request from '@/utils/request'

// 获取转派管理列表
export function getDevApiTransferList (params) {
  return request({
    url: '/transfer/list',
    method: 'get',
    params: params
  })
}

// 转派处理
export function queryTransferAssignmentByUserIdAndTransferUserId (params) {
  return request({
    url: `/transfer/assignment/${params.type}/${params.userId}/${params.transferUserId}`,
    method: 'put'
  })
}