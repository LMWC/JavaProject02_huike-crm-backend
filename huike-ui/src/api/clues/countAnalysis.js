// 统计页面接口
import request from '@/utils/request'


// 活动统计
export function reportActivityStatistics (params) {
  return request({
    url: `/report/activityStatistics/${params.beginCreateTime}/${params.endCreateTime}`,
    method: 'get'
  })
}

// 渠道统计
export function reportChanelStatistics (params) {
  return request({
    url: `/report/chanelStatistics/${params.beginCreateTime}/${params.endCreateTime}`,
    method: 'get'
  })
}

// 线索统计
export function reportCluesStatistics (params) {
  return request({
    url: `/report/cluesStatistics/${params.beginCreateTime}/${params.endCreateTime}`,
    method: 'get'
  })
}

// 客户统计
export function reportContractStatistics (params) {
  return request({
    url: `/report/contractStatistics/${params.beginCreateTime}/${params.endCreateTime}`,
    method: 'get'
  })
}

// 销售统计
export function reportSalesStatistics (params) {
return request({
  url: `/report/salesStatistics/${params.beginCreateTime}/${params.endCreateTime}`,
  method: 'get'
  })
}

// 学科分布统计
export function reportSubjectStatistics (params) {
  return request({
    url: `/report/subjectStatistics/${params.beginCreateTime}/${params.endCreateTime}`,
    method: 'get'
  })
}

// 客户统计报表
export function reportContractStatisticsList (params) {
  return request({
    url: '/report/contractStatisticsList',
    method: 'get',
    params: { ...params }
  })
}


// 销售统计报表
export function reportSalesStatisticsList (params) {
  return request({
    url: '/report/salesStatisticsList',
    method: 'get',
    params: { ...params }
  })
}

// 线索统计报表
export function reportCluesStatisticsList (params) {
  return request({
    url: '/report/cluesStatisticsList',
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


// 渠道统计列表-报表
export function reportActivityStatisticsList (params) {
  return request({
    url: '/report/activityStatisticsList',
    method: 'get',
    params: { ...params }
  })
}


// 新添加的接口---销售统计-统计维度的接口
// 归属部门
export function deptStatisticsList(params) {
  return request({
    url: `/report/deptStatisticsList/${params.beginCreateTime}/${params.endCreateTime}`,
    method: 'get',
    params: { ...params }
  })
}

// 归属渠道
export function channelStatisticsList(params) {
  return request({
    url: `/report/channelStatisticsList/${params.beginCreateTime}/${params.endCreateTime}`,
    method: 'get',
    params: { ...params }
  })
}


// 归属人
export function ownerShipStatisticsList(params) {
  return request({
    url: `/report/ownerShipStatisticsList/${params.beginCreateTime}/${params.endCreateTime}`,
    method: 'get',
    params: { ...params }
   })
}