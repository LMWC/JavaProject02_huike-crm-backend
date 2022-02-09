package com.huike.clues.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.mapper.SysDeptMapper;
import com.huike.clues.mapper.SysRoleMapper;
import com.huike.clues.mapper.mybatis_plus.SysDeptPlusMapper;
import com.huike.clues.service.ISysDeptService;
import com.huike.common.annotation.DataScope;
import com.huike.common.constant.UserConstants;
import com.huike.common.core.domain.TreeDeptAndUser;
import com.huike.common.core.domain.TreeSelect;
import com.huike.common.core.domain.entity.SysDept;
import com.huike.common.core.domain.entity.SysRole;
import com.huike.common.exception.CustomException;
import com.huike.common.utils.StringUtils;

/**
 * 部门管理 服务实现
 * 
 * 
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptPlusMapper, SysDept> implements ISysDeptService {
	@Autowired
	private SysDeptMapper deptMapper;
	
	@Autowired
	private SysDeptPlusMapper deptPlusMapper;

	@Autowired
	private SysRoleMapper roleMapper;

	/**
	 * 查询部门管理数据
	 * 
	 * @param dept 部门信息
	 * @return 部门信息集合
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<SysDept> selectDeptList(SysDept dept) {
		return deptPlusMapper.selectList(getQueryWrapperBySysDept(dept));
//		return deptMapper.selectDeptList(dept);
	}

	@Override
	public List<SysDept> selectDeptAndUserList() {
		return deptMapper.selectDeptAndUserList();
	}

	/**
	 * 构建前端所需要树结构
	 * 
	 * @param depts 部门列表
	 * @return 树结构列表
	 */
	@Override
	public List<SysDept> buildDeptTree(List<SysDept> depts) {
		List<SysDept> returnList = new ArrayList<SysDept>();
		List<Long> tempList = new ArrayList<Long>();
		for (SysDept dept : depts) {
			tempList.add(dept.getDeptId());
		}
		for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();) {
			SysDept dept = (SysDept) iterator.next();
			// 如果是顶级节点, 遍历该父节点的所有子节点
			if (!tempList.contains(dept.getParentId())) {
				recursionFn(depts, dept);
				returnList.add(dept);
			}
		}
		if (returnList.isEmpty()) {
			returnList = depts;
		}
		return returnList;
	}

	/**
	 * 构建前端所需要下拉树结构
	 * 
	 * @param depts 部门列表
	 * @return 下拉树结构列表
	 */
	@Override
	public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
		List<SysDept> deptTrees = buildDeptTree(depts);
		return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	@Override
	public List<TreeSelect> buildDeptAndUserTree(List<SysDept> depts) {
		List<SysDept> deptTrees = buildDeptTree(depts);
		return deptTrees.stream().map(TreeDeptAndUser::new).collect(Collectors.toList());
	}

	/**
	 * 根据角色ID查询部门树信息
	 * 
	 * @param roleId 角色ID
	 * @return 选中部门列表
	 */
	@Override
	public List<Integer> selectDeptListByRoleId(Long roleId) {
		SysRole role = roleMapper.selectRoleById(roleId);
		return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
	}

	/**
	 * 根据部门ID查询信息
	 * 
	 * @param deptId 部门ID
	 * @return 部门信息
	 */
	@Override
	public SysDept selectDeptById(Long deptId) {
		//select d.dept_id, d.parent_id, d.ancestors, d.dept_name, d.order_num, d.leader, d.phone, d.email, d.status, d.del_flag, d.create_by, d.create_time 
        //from sys_dept d
		//where dept_id = #{deptId}
		return this.getOne(new LambdaQueryWrapper<SysDept>().eq(SysDept::getDeptId, deptId));
//		return deptMapper.selectDeptById(deptId);
	}

	/**
	 * 根据ID查询所有子部门（正常状态）
	 * 
	 * @param deptId 部门ID
	 * @return 子部门数
	 */
	@Override
	public int selectNormalChildrenDeptById(Long deptId) {
		//where status = 0 and del_flag = '0' and find_in_set(#{deptId}, ancestors)
		return this.count(new LambdaQueryWrapper<SysDept>().
				eq(SysDept::getStatus, 0).
				eq(SysDept::getDelFlag,0).apply(1 ==1,"FIND_IN_SET ('"+deptId+"',ancestors)"));
//		return deptMapper.selectNormalChildrenDeptById(deptId);
	}

	/**
	 * 是否存在子节点
	 * 
	 * @param deptId 部门ID
	 * @return 结果
	 */
	@Override
	public boolean hasChildByDeptId(Long deptId) {
		//select count(1) from sys_dept where del_flag = '0' and parent_id = #{deptId} limit 1
		return this.count(new LambdaQueryWrapper<SysDept>().
				eq(SysDept::getDelFlag, 0).
				eq(SysDept::getParentId, deptId).
				last("limit 1")
				)>0?true:false;
//		int result = deptMapper.hasChildByDeptId(deptId);
//		return result > 0 ? true : false;
	}

	/**
	 * 查询部门是否存在用户
	 * 
	 * @param deptId 部门ID
	 * @return 结果 true 存在 false 不存在
	 */
	@Override
	public boolean checkDeptExistUser(Long deptId) {
		//select count(1) from sys_user where dept_id = #{deptId} and del_flag = '0'
		int result = deptMapper.checkDeptExistUser(deptId);
		return result > 0 ? true : false;
	}

	/**
	 * 校验部门名称是否唯一
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public String checkDeptNameUnique(SysDept dept) {
		Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
		SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
		if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 新增保存部门信息
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public int insertDept(SysDept dept) {
		SysDept info = deptMapper.selectDeptById(dept.getParentId());
		// 如果父节点不为正常状态,则不允许新增子节点
		if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
			throw new CustomException("部门停用，不允许新增");
		}
		dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
		return deptMapper.insertDept(dept);
	}

	/**
	 * 修改保存部门信息
	 * 
	 * @param dept 部门信息
	 * @return 结果
	 */
	@Override
	public int updateDept(SysDept dept) {
		SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
		SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
		if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
			String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
			String oldAncestors = oldDept.getAncestors();
			dept.setAncestors(newAncestors);
			updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
		}
		int result = deptMapper.updateDept(dept);
		if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
			// 如果该部门是启用状态，则启用该部门的所有上级部门
			updateParentDeptStatus(dept);
		}
		return result;
	}

	/**
	 * 修改该部门的父级部门状态
	 * 
	 * @param dept 当前部门
	 */
	private void updateParentDeptStatus(SysDept dept) {
		String updateBy = dept.getUpdateBy();
		dept = deptMapper.selectDeptById(dept.getDeptId());
		dept.setUpdateBy(updateBy);
		deptMapper.updateDeptStatus(dept);
	}

	/**
	 * 修改子元素关系
	 * 
	 * @param deptId       被修改的部门ID
	 * @param newAncestors 新的父ID集合
	 * @param oldAncestors 旧的父ID集合
	 */
	public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
		List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
		for (SysDept child : children) {
			child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
		}
		if (children.size() > 0) {
			deptMapper.updateDeptChildren(children);
		}
	}

	/**
	 * 删除部门管理信息
	 * 
	 * @param deptId 部门ID
	 * @return 结果
	 */
	@Override
	public int deleteDeptById(Long deptId) {
		return deptMapper.deleteDeptById(deptId);
	}

	/**
	 * 递归列表
	 */
	private void recursionFn(List<SysDept> list, SysDept t) {
		// 得到子节点列表
		List<SysDept> childList = getChildList(list, t);
		t.setChildren(childList);
		for (SysDept tChild : childList) {
			if (hasChild(list, tChild)) {
				recursionFn(list, tChild);
			}
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
		List<SysDept> tlist = new ArrayList<SysDept>();
		Iterator<SysDept> it = list.iterator();
		while (it.hasNext()) {
			SysDept n = (SysDept) it.next();
			if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<SysDept> list, SysDept t) {
		return getChildList(list, t).size() > 0 ? true : false;
	}
		
	/**
	 *   <include refid="selectDeptVo"/>
        where d.del_flag = '0'
        <if test="parentId != null and parentId != 0">
			AND parent_id = #{parentId}
		</if>
		<if test="deptName != null and deptName != ''">
			AND dept_name like concat('%', #{deptName}, '%')
		</if>
		<if test="status != null and status != ''">
			AND status = #{status}
		</if>
		<!-- 数据范围过滤 -->
		${params.dataScope}
		order by d.parent_id, d.order_num
	 * @param dept
	 * @return
	 */
	private LambdaQueryWrapper<SysDept> getQueryWrapperBySysDept(SysDept dept) {
		LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<SysDept>();
		//del_flag
		queryWrapper.eq(SysDept::getDelFlag, 0);
		//parentId
		queryWrapper.eq((dept.getParentId()!=null)&&StringUtils.isNotEmpty(dept.getParentId()+""),
				SysDept::getParentId,
				dept.getParentId());
		//deptName
		queryWrapper.like((dept.getDeptName()!=null)&&StringUtils.isNotEmpty(dept.getDeptName()+""),
				SysDept::getDeptName,
				dept.getDeptName());
		//status
		queryWrapper.eq((dept.getStatus()!=null)&&StringUtils.isNotEmpty(dept.getStatus()+""),
				SysDept::getStatus,
				dept.getStatus());
		//order by d.parent_id, d.order_num
		queryWrapper.orderByAsc(SysDept::getParentId);
		queryWrapper.orderByAsc(SysDept::getOrderNum);
		return queryWrapper;
	}
}
