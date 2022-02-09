
// 查询活动管理列表
function listActivity(query) {
  return $axios({
    url: '/clues/activity/list',
    method: 'get',
    params: query
  })
}

//获取渠道下活动管理列表
function getActivityByChannel(channel) {
  return $axios({
    url: `/clues/activity/listselect/${channel}`,
    method: 'get'
  })
}

// 查询活动管理详细
function getActivity(id) {
  return $axios({
    url: '/clues/activity/' + id,
    method: 'get'
  })
}

// 新增活动管理
function addActivity(data) {
  return $axios({
    url: '/clues/activity',
    method: 'post',
    data: data
  })
}

// 修改活动管理
function updateActivity(data) {
  return $axios({
    url: '/clues/activity',
    method: 'put',
    data: data
  })
}

// 删除活动管理
function delActivity(id) {
  return $axios({
    url: '/clues/activity/' + id,
    method: 'delete'
  })
}

// 导出活动管理
function exportActivity(query) {
  return $axios({
    url: '/clues/activity/export',
    method: 'get',
    params: query
  })
}

// 活动通过
function passActivity(id) {
  return $axios({
    url: '/clues/activity/pass/'+id,
    method: 'put'
  })
}
// 活动驳回
function rejectActivity(id) {
  return $axios({
    url: '/clues/activity/reject/'+id,
    method: 'put'
  })
}
