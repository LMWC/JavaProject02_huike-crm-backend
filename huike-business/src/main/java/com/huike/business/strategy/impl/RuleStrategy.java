package com.huike.business.strategy.impl;

import com.huike.business.domain.TbBusiness;
import com.huike.business.strategy.Rule;
import com.huike.clues.domain.TbAssignRecord;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.service.ITbCourseService;
import com.huike.common.core.domain.entity.SysDictData;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.DictUtils;
import com.huike.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *  rule 处理策略
 *  根据规则系统自动分配对应的商机专员来处理
 *  1.意向学科是java的分配给lisi商机专员
 *  2.意向学科是前端的分配给lisi1商机专员
 */
@ConditionalOnProperty(name = "rule.transfor", havingValue = "rule")
@Service("BussinessruleStrategy")
public class RuleStrategy implements Rule {

    //这里能够成功注入吗？
    @Autowired
    private static TbAssignRecordMapper assignRecordMapper;

    //这里能够成功注入吗？
    @Autowired
    private static SysUserMapper userMapper;

    @Autowired
    private static SysDictDataMapper dictDataMapper;

    private static SysUser lisi = new SysUser();

    private static SysUser lisi1 = new SysUser();

    //内存中JAVA学科的内容--提前预加载在内存中
    private static SysDictData subjectJAVA = new SysDictData();

    //内存中前端学科的内容--提前预加载在内存中
    private static SysDictData subjectHtml = new SysDictData();

    //这里是通过空间换时间的方式来将JAVA学科和前端学科，lisi,lisi1 存储在静态资源中
    //这里通过mapper去进行查询，能够查询成功吗
    /**
     * 参考一下adminStrategy是怎么做的
     */
    static{
        try{
            //空间换时间的方式将数据库中的学科读取到内存中
            //预加载学科数据到内存中
            List<SysDictData> course_subject = dictDataMapper.selectDictDataByType("course_subject");
            for (SysDictData index: course_subject) {
                //找到java和前端两个学科对应的数值
                if(index.getDictLabel().equals("Java")){
                    subjectJAVA = index;
                }
                if(index.getDictLabel().equals("前端")){
                    subjectHtml = index;
                }
            }
            //预加载lisi和lisi1的数据到内存中
            lisi = userMapper.selectUserByName("lisi");
            lisi1 = userMapper.selectUserByName("lisi1");
        }catch (Exception e){
        }
    }


    /**
     * 定义一些规则来自动分配
     *
     * 1.意向学科是java的分配给lisi商机专员
     * 2.意向学科是前端的分配给lisi1商机专员
     * 3.如果没有匹配到规则则不分配等待管理员和主管来进行分配
     * @param tbBusiness
     * @return
     */
    @Override
    public Integer transforBusiness(TbBusiness tbBusiness) {
        //注意处理空指针的问题
        if(tbBusiness.getSubject().equals(subjectJAVA.getDictLabel())){
            //如果意向学科是java--分配给lisi
            return distribute(tbBusiness,lisi);
        }else if(tbBusiness.getSubject().equals(subjectHtml.getDictLabel())){
            //如果意向学科是前端--分配给lisi1
            return distribute(tbBusiness,lisi1);
        }else{
            //不进行分配，不添加分配记录-----即待分配状态
            return 1;
        }
    }


    /**
     * 分配商机给具体用户的方法
     * 看我看我！！！！！！！！！！！！！！！
     * 看我能少走弯路！！！！！！！！！！！！
     * 这部分代码没有预设bug，不用研究
     * 该方法主要是将商机分配给具体的人的方法
     * @param business
     * @param user
     * @return
     */
    private int distribute(TbBusiness business,SysUser user){
        TbAssignRecord tbAssignRecord =new TbAssignRecord();
        tbAssignRecord.setAssignId(business.getId());
        tbAssignRecord.setUserId(user.getUserId());
        tbAssignRecord.setUserName(user.getUserName());
        tbAssignRecord.setDeptId(user.getDeptId());
        tbAssignRecord.setCreateBy(SecurityUtils.getUsername());
        tbAssignRecord.setCreateTime(DateUtils.getNowDate());
        tbAssignRecord.setType(TbAssignRecord.RecordType.BUSNIESS.getValue());
        business.setNextTime(null);
        return assignRecordMapper.insertAssignRecord(tbAssignRecord);
    }

}
