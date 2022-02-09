// 首页相关接口
import request from '@/utils/request'

export function indexBase (params) {
  return request({
    url: '/index',
    method: 'get',
    params: { ...params }
  })
}


// 商机转换龙虎榜
export function getBusinessChangeStatistics (params) {
  return request({
    url: '/index/businessChangeStatistics',
    method: 'get',
    params: { ...params }
  })
}

// 销售龙虎榜
export function getSalesStatistic (params) {
  return request({
    url: '/index/salesStatistic',
    method: 'get',
    params: { ...params }
  })
}

// 漏斗图数据
export function reportGetVulnerabilityMapChart (params) {
  return request({
    url: `/report/getVulnerabilityMap/${params.beginCreateTime}/${params.endCreateTime}`,
    method: 'get'
  })
}