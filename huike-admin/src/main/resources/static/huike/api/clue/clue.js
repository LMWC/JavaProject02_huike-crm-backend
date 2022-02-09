
// 查询线索管理列表
function listClue(query) {
  return $axios({
    url: '/clues/clue/list',
    method: 'get',
    params: query
  })
}

// 查询线索管理线索池列表
function listCluePool(query) {
  return $axios({
    url: '/clues/clue/pool',
    method: 'get',
    params: query
  })
}

// 查询线索管理详细
function getClue(id) {
  return $axios({
    url: '/clues/clue/' + id,
    method: 'get'
  })
}

// 新增线索管理
function addClue(data) {
  return $axios({
    url: '/clues/clue',
    method: 'post',
    data: data
  })
}

// 修改线索管理
function updateClue(data) {
  return $axios({
    url: '/clues/clue',
    method: 'put',
    data: data
  })
}

// 删除线索管理
function delClue(id) {
  return $axios({
    url: '/clues/clue/' + id,
    method: 'delete'
  })
}

// 导出线索管理
function exportClue(query) {
  return $axios({
    url: '/clues/clue/export',
    method: 'get',
    params: query
  })
}

// 批量分配线索
function assignmentClue(data) {
  return $axios({
    url: `/clues/clue/assignment`,
    method: 'put',
    data: data
  })
}

// 批量捞取线索
function gainClue(data) {
  return $axios({
    url: `/clues/clue/gain`,
    method: 'put',
    data: data
  })
}

// 线索转商机
function turnBusiness(id) {
  return $axios({
    url: `clues/clue/changeBusiness/${id}`,
    method: 'put',
    data: {}
  })
}

// 线索上报
function reportClue(data) {
  const putData = JSON.parse(JSON.stringify(data))
  delete putData.id
  return $axios({
    url: `/clues/clue/false/${data.id}`,
    method: 'put',
    data: putData
  })
}

// 线索任务列表
function listClueRecord(query) {
  return $axios({
    url: '/clues/record/list',
    method: 'get',
    params: query
  })
}

// 新增跟进记录管理
function addClueRecord(data) {
  return $axios({
    url: '/clues/record',
    method: 'post',
    data: data
  })
}