import request from '@/utils/request'

// 查询课程管理列表
export function listCourse(query) {
  return request({
    url: '/clues/course/list',
    method: 'get',
    params: query
  })
}

// 课程下拉列表
export function listCourseSelect(query) {
  return request({
    url: '/clues/course/listselect',
    method: 'get',
    params: query
  })
}

// 查询课程管理详细
export function getCourse(id) {
  return request({
    url: '/clues/course/' + id,
    method: 'get'
  })
}

// 新增课程管理
export function addCourse(data) {
  return request({
    url: '/clues/course',
    method: 'post',
    data: data
  })
}

// 修改课程管理
export function updateCourse(data) {
  return request({
    url: '/clues/course',
    method: 'put',
    data: data
  })
}

// 删除课程管理
export function delCourse(id) {
  return request({
    url: '/clues/course/' + id,
    method: 'delete'
  })
}

// 导出课程管理
export function exportCourse(query) {
  return request({
    url: '/clues/course/export',
    method: 'get',
    params: query
  })
}