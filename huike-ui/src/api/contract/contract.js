import request from '@/utils/request'

// 查询合同列表
export function listContract(query) {
  return request({
    url: '/contract/list',
    method: 'get',
    params: query
  })
}

// 获取合同及流程信息
export function getContract(id) {
  return request({
    url: '/contract/' + id,
    method: 'get'
  })
}

// 查询合同基本信息详细-修改
export function getContractInfo(id) {
  return request({
    url: '/contract/detail/' + id,
    method: 'get'
  })
}

// 新增合同
export function addContract(data) {
  return request({
    url: '/contract',
    method: 'post',
    data: data
  })
}

// 修改合同
export function updateContract(data) {
  return request({
    url: '/contract',
    method: 'put',
    data: data
  })
}

// 删除合同
export function delContract(id) {
  return request({
    url: '/contract/' + id,
    method: 'delete'
  })
}

// 导出合同
export function exportContract(query) {
  return request({
    url: '/contract/export',
    method: 'get',
    params: query
  })
}

// 合同审核通过
export function passContract(id) {
  return request({
    url: `/contract/pass/${id}`,
    method: 'put'
  })
}

// 合同审核驳回
export function rejectContract(data) {
  return request({
    url: `contract/reject`,
    method: 'put',
    data
  })
}

// 商机转合同
export function changeContract(id, data) {
  return request({
    url: `contract/changeContract/${id}`,
    method: 'put',
    data
  })
}