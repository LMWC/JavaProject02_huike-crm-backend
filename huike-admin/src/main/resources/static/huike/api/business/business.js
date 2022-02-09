
// 查询商机列表
function listBusiness(query) {
  return $axios({
    url: '/business/list',
    method: 'get',
    params: query
  })
}

// 查询公海池列表
function listBusinessPool(query) {
  return $axios({
    url: '/business/pool',
    method: 'get',
    params: query
  })
}

// 查询商机详细
function getBusiness(id) {
  return $axios({
    url: '/business/' + id,
    method: 'get'
  })
}

// 新增商机
function addBusiness(data) {
  return $axios({
    url: '/business',
    method: 'post',
    data: data
  })
}

// 修改商机
function updateBusiness(data) {
  return $axios({
    url: '/business',
    method: 'put',
    data: data
  })
}

// 删除商机
function delBusiness(id) {
  return $axios({
    url: '/business/' + id,
    method: 'delete'
  })
}

// 导出商机
function exportBusiness(query) {
  return $axios({
    url: '/business/export',
    method: 'get',
    params: query
  })
}

// 批量分配商机
function assignmentBusiness(data) {
  return $axios({
    url: `/business/assignment`,
    method: 'put',
    data: data
  })
}

// 批量捞取商机
function gainBusiness(data) {
  return $axios({
    url: `/business/gain`,
    method: 'put',
    data: data
  })
}

// 商机沟通记录列表
function listBusinessRecord(query) {
  return $axios({
    url: '/business/record/list',
    method: 'get',
    params: query
  })
}

// 新增跟进
function addBusinessRecord(data) {
  return $axios({
    url: '/business/record',
    method: 'post',
    data: data
  })
}

// 踢回公海
function backBusiness(data) {
  return $axios({
    url: `/business/back/${data.id}/${data.backReason}`,
    method: 'put'
  })
}