
// 查询公告列表
function listNotice(query) {
  return $axios({
    url: '/system/notice/list',
    method: 'get',
    params: query
  })
}

// 查询公告详细
function getNotice(noticeId) {
  return $axios({
    url: '/system/notice/' + noticeId,
    method: 'get'
  })
}

// 新增公告
function addNotice(data) {
  return $axios({
    url: '/system/notice',
    method: 'post',
    data: data
  })
}

// 修改公告
function updateNotice(data) {
  return $axios({
    url: '/system/notice',
    method: 'put',
    data: data
  })
}

// 删除公告
function delNotice(noticeId) {
  return $axios({
    url: '/system/notice/' + noticeId,
    method: 'delete'
  })
}