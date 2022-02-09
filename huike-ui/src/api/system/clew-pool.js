import request from '@/utils/request'

// 新增线索池规则
export function addCluesRulePool(data) {
  // 线索
  return request({
    url: '/rule/pool',
    method: 'post',
    data: data
  })
}

// 修改线索池规则
export function updateCluesRulePool(data) {
  // 线索
  return request({
    url: '/rule/pool',
    method: 'put',
    data: data
  })
}

// 线索池规则获取基础信息type=0 线索 type=1 商机
export function getCluesRulePool(type) {
  // 线索
  return request({
    url: `/rule/pool/${type}`,
    method: 'get'
  })
}