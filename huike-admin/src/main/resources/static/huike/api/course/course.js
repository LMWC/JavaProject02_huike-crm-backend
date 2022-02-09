
// 查询课程管理列表
function listCourse(query) {
  return $axios({
    url: '/clues/course/list',
    method: 'get',
    params: query
  })
}

// 课程下拉列表
function listCourseSelect(query) {
  return $axios({
    url: '/clues/course/listselect',
    method: 'get',
    params: query
  })
}

// 查询课程管理详细
function getCourse(id) {
  return $axios({
    url: '/clues/course/' + id,
    method: 'get'
  })
}

// 新增课程管理
function addCourse(data) {
  return $axios({
    url: '/clues/course',
    method: 'post',
    data: data
  })
}

// 修改课程管理
function updateCourse(data) {
  return $axios({
    url: '/clues/course',
    method: 'put',
    data: data
  })
}

// 删除课程管理
function delCourse(id) {
  return $axios({
    url: '/clues/course/' + id,
    method: 'delete'
  })
}

// 导出课程管理
function exportCourse(query) {
  return $axios({
    url: '/clues/course/export',
    method: 'get',
    params: query
  })
}