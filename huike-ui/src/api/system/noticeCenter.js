// 通知中心相关接口
import request from '@/utils/request'

// 获取未读接口
export function systemNoticePagelistStatus (params) {
  return request({
    url: `/system/notice/pagelist/${params.status}`,
    method: 'get'
  })
}

// 未读变已读
export function systemNoticeBynoticeId (params) {
  return request({
    url: `/system/notice/${params.noticeId}`,
    method: 'put'
  })
}

// 总的未读数量接口
export function allSystemNoticeListByStatus(params) {
  return request({
    url: `/system/notice/list/${params.status}`,
    method: 'get'
  })
}