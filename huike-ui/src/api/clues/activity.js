import request from '@/utils/request'

// 查询活动管理列表
export function listActivity(query) {
  return request({
    url: '/clues/activity/list',
    method: 'get',
    params: query
  })
}

//获取渠道下活动管理列表
export function getActivityByChannel(channel) {
  return request({
    url: `/clues/activity/listselect/${channel}`,
    method: 'get'
  })
}

// 查询活动管理详细
export function getActivity(id) {
  return request({
    url: '/clues/activity/' + id,
    method: 'get'
  })
}

// 新增活动管理
export function addActivity(data) {
  return request({
    url: '/clues/activity',
    method: 'post',
    data: data
  })
}

// 修改活动管理
export function updateActivity(data) {
  return request({
    url: '/clues/activity',
    method: 'put',
    data: data
  })
}

// 删除活动管理
export function delActivity(id) {
  return request({
    url: '/clues/activity/' + id,
    method: 'delete'
  })
}

// 导出活动管理
export function exportActivity(query) {
  return request({
    url: '/clues/activity/export',
    method: 'get',
    params: query
  })
}

// 活动通过
export function passActivity(id) {
  return request({
    url: '/clues/activity/pass/'+id,
    method: 'put'
  })
}
// 活动驳回
export function rejectActivity(id) {
  return request({
    url: '/clues/activity/reject/'+id,
    method: 'put'
  })
}
