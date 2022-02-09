
// 查询合同列表
function listContract(query) {
  return $axios({
    url: '/contract/list',
    method: 'get',
    params: query
  })
}

// 获取合同及流程信息
function getContract(id) {
  return $axios({
    url: '/contract/' + id,
    method: 'get'
  })
}

// 查询合同基本信息详细-修改
function getContractInfo(id) {
  return $axios({
    url: '/contract/detail/' + id,
    method: 'get'
  })
}

// 新增合同
function addContract(data) {
  return $axios({
    url: '/contract',
    method: 'post',
    data: data
  })
}

// 修改合同
function updateContract(data) {
  return $axios({
    url: '/contract',
    method: 'put',
    data: data
  })
}

// 删除合同
function delContract(id) {
  return $axios({
    url: '/contract/' + id,
    method: 'delete'
  })
}

// 导出合同
function exportContract(query) {
  return $axios({
    url: '/contract/export',
    method: 'get',
    params: query
  })
}

// 合同审核通过
function passContract(id) {
  return $axios({
    url: `/contract/pass/${id}`,
    method: 'put'
  })
}

// 合同审核驳回
function rejectContract(data) {
  return $axios({
    url: `contract/reject`,
    method: 'put',
    data
  })
}

// 商机转合同
function changeContract(id, data) {
  return $axios({
    url: `contract/changeContract/${id}`,
    method: 'put',
    data
  })
}