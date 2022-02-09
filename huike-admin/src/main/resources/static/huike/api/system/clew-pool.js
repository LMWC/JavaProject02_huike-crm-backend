
// 新增线索池规则
function addCluesRulePool(data) {
  // 线索
  return $axios({
    url: '/rule/pool',
    method: 'post',
    data: data
  })
}

// 修改线索池规则
function updateCluesRulePool(data) {
  // 线索
  return $axios({
    url: '/rule/pool',
    method: 'put',
    data: data
  })
}

// 线索池规则获取基础信息type=0 线索 type=1 商机
function getCluesRulePool(type) {
  // 线索
  return $axios({
    url: `/rule/pool/${type}`,
    method: 'get'
  })
}