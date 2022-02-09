import request from '@/utils/request'

// 查询商机列表
export function listBusiness(query) {
  return request({
    url: '/business/list',
    method: 'get',
    params: query
  })
}

// 查询公海池列表
export function listBusinessPool(query) {
  return request({
    url: '/business/pool',
    method: 'get',
    params: query
  })
}

// 查询商机详细
export function getBusiness(id) {
  return request({
    url: '/business/' + id,
    method: 'get'
  })
}

// 新增商机
export function addBusiness(data) {
  return request({
    url: '/business',
    method: 'post',
    data: data
  })
}

// 修改商机
export function updateBusiness(data) {
  return request({
    url: '/business',
    method: 'put',
    data: data
  })
}

// 删除商机
export function delBusiness(id) {
  return request({
    url: '/business/' + id,
    method: 'delete'
  })
}

// 导出商机
export function exportBusiness(query) {
  return request({
    url: '/business/export',
    method: 'get',
    params: query
  })
}

// 批量分配商机
export function assignmentBusiness(data) {
  return request({
    url: `/business/assignment`,
    method: 'put',
    data: data
  })
}

// 批量捞取商机
export function gainBusiness(data) {
  return request({
    url: `/business/gain`,
    method: 'put',
    data: data
  })
}

// 商机沟通记录列表
export function listBusinessRecord(query) {
  return request({
    url: '/business/record/list',
    method: 'get',
    params: query
  })
}

// 新增跟进
export function addBusinessRecord(data) {
  return request({
    url: '/business/record',
    method: 'post',
    data: data
  })
}

// 踢回公海
export function backBusiness(data) {
  return request({
    url: `/business/back/${data.id}/${data.backReason}`,
    method: 'put'
  })
}