package com.huike.contract.service;

import com.huike.common.core.domain.entity.SysUser;
import com.huike.contract.domain.vo.TransferVo;

import java.util.List;
import java.util.Map;

public interface ITransferService {

    public List<TransferVo> selectTransferList(SysUser user);


    public Map<String,Object> assignment(String type, Long userId, Long transferUserId);
}
