package com.huike.clues.strategy.impl;

import com.huike.clues.domain.TbClue;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.SysUserMapper;
import com.huike.clues.mapper.TbAssignRecordMapper;
import com.huike.clues.strategy.Rule;
import com.huike.common.core.domain.entity.SysDictData;
import com.huike.common.core.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * 使用规则的方式进行线索的自动分配
 * 规则：
 * 1.将想要学java的分配给zhangsan
 * 2.将想要学前端的分配给zhangsan1
 * 3.不满足规则的不进行分配，由管理员或主管来进行分配
 */
@ConditionalOnProperty(name = "rule.clue.import", havingValue = "rule")
@Service("ClueRuleStrategy")
public class RuleStrategy implements Rule {

    @Autowired
    private TbAssignRecordMapper assignRecordMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    private static SysUser zhangsan = new SysUser();

    private static SysUser zhangsan1 = new SysUser();

    //内存中JAVA学科的内容--提前预加载在内存中
    private static SysDictData subjectJAVA = new SysDictData();

    //内存中前端学科的内容--提前预加载在内存中
    private static SysDictData subjectHtml = new SysDictData();

    /**
     * 预加载数据到内存中
     */
    @PostConstruct
    public void init() {
        //空间换时间的方式将数据库中的学科读取到内存中
        //预加载学科数据到内存中
        List<SysDictData> course_subject = dictDataMapper.selectDictDataByType("course_subject");
        for (SysDictData index : course_subject) {
            //找到java和前端两个学科对应的数值
            if (index.getDictLabel().equals("Java")) {
                subjectJAVA = index;
            }
            if (index.getDictLabel().equals("前端")) {
                subjectHtml = index;
            }
        }
        //预加载lisi和lisi1的数据到内存中
        zhangsan = userMapper.selectUserByName("zhangsan");
        zhangsan1 = userMapper.selectUserByName("zhangsan1");
    }

    /**=========================利用空间换时间将部分的数据提前存放到内存中================================**/
    @Override
    public Boolean loadRule(TbClue clue) {
        //TODO 完成基于规则分配线索部分的代码
        /**
         * 规则：
         *  * 1.将想要学java的分配给zhangsan
         *  * 2.将想要学前端的分配给zhangsan1
         *  * 3.不满足规则的不进行分配，由管理员或主管来进行分配
         *  将该线索数据添加到线索表中，但不往分配记录表里添加数据
         */
        return null;
    }

}