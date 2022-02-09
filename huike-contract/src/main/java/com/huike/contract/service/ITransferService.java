package com.huike.contract.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huike.clues.dto.SysUserDto;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.contract.domain.vo.TransferVo;

public interface ITransferService {

    public List<TransferVo> selectTransferList(SysUser user);


    public Map<String,Object> assignment(String type, Long userId, Long transferUserId);
    
    
}