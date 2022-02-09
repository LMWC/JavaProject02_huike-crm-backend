// 通知中心相关接口

// 获取未读接口
function systemNoticePagelistStatus (params) {
  return $axios({
    url: `/system/notice/pagelist/${params.status}`,
    method: 'get'
  })
}

// 未读变已读
function systemNoticeBynoticeId (params) {
  return $axios({
    url: `/system/notice/${params.noticeId}`,
    method: 'put'
  })
}

// 总的未读数量接口
function allSystemNoticeListByStatus(params) {
  return $axios({
    url: `/system/notice/list/${params.status}`,
    method: 'get'
  })
}