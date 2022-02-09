
// 新增线索规则配置
function addCluesRule(data) {
  // 线索
  return $axios({
    url: '/rule',
    method: 'post',
    data: data
  })
}

// 查询线索配置列表
function listCluesRule(query) {
  // 线索
  return $axios({
    url: '/rule/list/'+query.ruleType,
    method: 'get'
  })
}

// 查询规则详细
function getCluesRule(id, ruleType) {
  // 线索
  return $axios({
    url: '/rule/' + id,
    method: 'get'
  })
}

// 修改线索规则
function updateCluesRule(data) {
  // 线索
  return $axios({
    url: '/rule',
    method: 'put',
    data: data
  })
}

// 删除线索规则
function delCluesRule(id) {
  // 线索
  return $axios({
    url: '/rule/' + id,
    method: 'delete'
  })
}

// 修改线索规则排序
function updateCluesRuleOrder(data) {
  // 线索
  return $axios({
    url: '/rule/order',
    method: 'put',
    data: data
  })
}