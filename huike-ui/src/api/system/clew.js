import request from '@/utils/request'

// 新增线索规则配置
export function addCluesRule(data) {
  // 线索
  return request({
    url: '/rule',
    method: 'post',
    data: data
  })
}

// 查询线索配置列表
export function listCluesRule(query) {
  // 线索
  return request({
    url: '/rule/list/'+query.ruleType,
    method: 'get'
  })
}

// 查询规则详细
export function getCluesRule(id, ruleType) {
  // 线索
  return request({
    url: '/rule/' + id,
    method: 'get'
  })
}

// 修改线索规则
export function updateCluesRule(data) {
  // 线索
  return request({
    url: '/rule',
    method: 'put',
    data: data
  })
}

// 删除线索规则
export function delCluesRule(id) {
  // 线索
  return request({
    url: '/rule/' + id,
    method: 'delete'
  })
}

// 修改线索规则排序
export function updateCluesRuleOrder(data) {
  // 线索
  return request({
    url: '/rule/order',
    method: 'put',
    data: data
  })
}