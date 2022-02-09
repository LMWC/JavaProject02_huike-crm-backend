
// 查询岗位列表
function listPost(query) {
  return $axios({
    url: '/system/post/list',
    method: 'get',
    params: query
  })
}

// 查询岗位详细
function getPost(postId) {
  return $axios({
    url: '/system/post/' + postId,
    method: 'get'
  })
}

// 新增岗位
function addPost(data) {
  return $axios({
    url: '/system/post',
    method: 'post',
    data: data
  })
}

// 修改岗位
function updatePost(data) {
  return $axios({
    url: '/system/post',
    method: 'put',
    data: data
  })
}

// 删除岗位
function delPost(postId) {
  return $axios({
    url: '/system/post/' + postId,
    method: 'delete'
  })
}

// 导出岗位
function exportPost(query) {
  return $axios({
    url: '/system/post/export',
    method: 'get',
    params: query
  })
}

// 查询所有岗位列表
function getAllPostList() {
  return $axios({
    url: 'system/post/optionselect',
    method: 'get',
    params: {}
  })
}