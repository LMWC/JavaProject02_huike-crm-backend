package com.huike.framework.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.huike.common.core.domain.entity.SysUser;
import com.huike.common.core.domain.model.LoginUser;
import com.huike.common.enums.UserStatus;
import com.huike.common.exception.BaseException;
import com.huike.common.utils.StringUtils;
import com.huike.clues.service.ISysUserService;

/**
 * 用户验证处理
 *
 * 
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
<<<<<<< HEAD
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
=======
	
	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

	@Autowired
	private ISysUserService userService;

	@Autowired
	private SysPermissionService permissionService;

<<<<<<< HEAD
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }
=======
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser user = userService.selectUserByUserName(username);
		if (StringUtils.isNull(user)) {
			log.info("登录用户：{} 不存在.", username);
			throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
		} else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
			log.info("登录用户：{} 已被删除.", username);
			throw new BaseException("对不起，您的账号：" + username + " 已被删除");
		} else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			log.info("登录用户：{} 已被停用.", username);
			throw new BaseException("对不起，您的账号：" + username + " 已停用");
		}
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485

		return createLoginUser(user);
	}

<<<<<<< HEAD
    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
=======
	public UserDetails createLoginUser(SysUser user) {
		return new LoginUser(user, permissionService.getMenuPermission(user));
	}
>>>>>>> a89c1ce12cfb3643528f0edecc6e2034a1285485
}
