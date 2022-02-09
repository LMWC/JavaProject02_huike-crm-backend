import request from '@/utils/request'

// 查询线索管理列表
export function listClue(query) {
  return request({
    url: '/clues/clue/list',
    method: 'get',
    params: query
  })
}

// 查询线索管理线索池列表
export function listCluePool(query) {
  return request({
    url: '/clues/clue/pool',
    method: 'get',
    params: query
  })
}

// 查询线索管理详细
export function getClue(id) {
  return request({
    url: '/clues/clue/' + id,
    method: 'get'
  })
}

// 新增线索管理
export function addClue(data) {
  return request({
    url: '/clues/clue',
    method: 'post',
    data: data
  })
}

// 修改线索管理
export function updateClue(data) {
  return request({
    url: '/clues/clue',
    method: 'put',
    data: data
  })
}

// 删除线索管理
export function delClue(id) {
  return request({
    url: '/clues/clue/' + id,
    method: 'delete'
  })
}

// 导出线索管理
export function exportClue(query) {
  return request({
    url: '/clues/clue/export',
    method: 'get',
    params: query
  })
}

// 批量分配线索
export function assignmentClue(data) {
  return request({
    url: `/clues/clue/assignment`,
    method: 'put',
    data: data
  })
}

// 批量捞取线索
export function gainClue(data) {
  return request({
    url: `/clues/clue/gain`,
    method: 'put',
    data: data
  })
}

// 线索转商机
export function turnBusiness(id) {
  return request({
    url: `clues/clue/changeBusiness/${id}`,
    method: 'put',
    data: {}
  })
}

// 线索上报
export function reportClue(data) {
  const putData = JSON.parse(JSON.stringify(data))
  delete putData.id
  return request({
    url: `/clues/clue/false/${data.id}`,
    method: 'put',
    data: putData
  })
}

// 线索任务列表
export function listClueRecord(query) {
  return request({
    url: '/clues/record/list',
    method: 'get',
    params: query
  })
}

// 新增跟进记录管理
export function addClueRecord(data) {
  return request({
    url: '/clues/record',
    method: 'post',
    data: data
  })
}

// 下载
export function downloadByMinio(query) {
  return request({
    url: 'common/downloadByMinio',
    method: 'get',
    params: query
  })
}