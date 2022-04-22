package com.huike.clues.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.huike.common.utils.uuid.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huike.clues.domain.TbActivity;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.clues.service.ITbActivityService;
import com.huike.common.constant.Constants;
import com.huike.common.core.redis.RedisCache;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.StringUtils;

/**
 * 活动管理Service业务层处理
 * @date 2021-04-01
 */
@Service
public class TbActivityServiceImpl implements ITbActivityService {
    @Autowired
    private TbActivityMapper tbActivityMapper;

    @Autowired
    private RedisCache redisCache;


    /**
     * 查询活动管理
     * 
     * @param id 活动管理ID
     * @return 活动管理
     */
    @Override
    public TbActivity selectTbActivityById(Long id)
    {
        return tbActivityMapper.selectTbActivityById(id);
    }




    @Override
    public TbActivity selectTbActivityByCode(String code) {
        return tbActivityMapper.selectTbActivityByCode(code);
    }

    /**
     * 查询活动管理列表
     * 
     * @param tbActivity 活动管理
     * @return 活动管理
     */
    @Override
    public List<TbActivity> selectTbActivityList(TbActivity tbActivity) {
        return tbActivityMapper.selectTbActivityList(tbActivity);
    }

    /**
     * 新增活动管理
     * 
     * @param tbActivity 活动管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertTbActivity(TbActivity tbActivity){
        tbActivity.setCreateTime(DateUtils.getNowDate());
        tbActivity.setCode(getCode());
        tbActivity.setStatus("2");
        int rows= tbActivityMapper.insertTbActivity(tbActivity);
        loadAllActivityCode();
        return rows;
    }


    /**
     * 修改活动管理
     * 
     * @param tbActivity 活动管理
     * @return 结果
     */
    @Override
    public int updateTbActivity(TbActivity tbActivity){
        TbActivity dbActivity= tbActivityMapper.selectTbActivityById(tbActivity.getId());
        int rows= tbActivityMapper.updateTbActivity(tbActivity);
        //结束时间修改任务
        if(tbActivity.getEndTime()!=null&&!tbActivity.getEndTime().equals(dbActivity.getEndTime())){
            String target="activityTask.finish('"+tbActivity.getId()+"')";
            String jobName="活动结束任务id_"+tbActivity.getId();
        }
        return rows;
    }

    /**
     * 批量删除活动管理
     * 
     * @param ids 需要删除的活动管理ID
     * @return 结果
     */
    @Override
    public int deleteTbActivityByIds(Long[] ids)
    {
        return tbActivityMapper.deleteTbActivityByIds(ids);
    }

    /**
     * 删除活动管理信息
     * 
     * @param id 活动管理ID
     * @return 结果
     */
    @Override
    public int deleteTbActivityById(Long id)
    {
        TbActivity tbActivity = tbActivityMapper.selectTbActivityById(id);
        int rows=tbActivityMapper.deleteTbActivityById(id);
        return rows;
    }

    @Override
    public Map getCountByStatus() {
        return tbActivityMapper.getCountByStatus();
    }

    /**
     * 加载活动编号到缓存中
     */
    public void loadAllActivityCode() {
        List<String> codeList= tbActivityMapper.selectAllCode();
        Set<String> set= new HashSet<>(codeList);
        redisCache.setCacheSet(Constants.ACT_CODE_KEY, set);
    }

    /**
     * 生成活动编号
     * @return
     */
    private String getCode(){
        //随机8位编码
        String code= StringUtils.getRandom(8);
        //店铺校验
        Set<String> codeSets =  redisCache.getCacheSet(Constants.ACT_CODE_KEY);
        if(codeSets.contains(code)){
            return getCode();
        }
        return code;
    }
}
