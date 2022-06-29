package com.wq.dora.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.wq.dora.common.ServiceException;
import com.wq.dora.constant.UserStatus;
import com.wq.dora.dto.LoginDto;
import com.wq.dora.dto.UserInfoDto;
import com.wq.dora.entity.User;
import com.wq.dora.service.AccessService;
import com.wq.dora.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限服务实现类
 * @author wuqian
 * @date 2022/06/03
 */
@Service
@Transactional
public class AccessServiceImpl implements AccessService {
    @Autowired
    UserService userService;

    /**
     * 登录
     *
     * @param loginDto
     */
    @Override
    public SaTokenInfo login(LoginDto loginDto) {
        String password = loginDto.getPassword();
        String userNo = loginDto.getUserNo();
        User user = userService.findById(userNo);
        if (user == null) {
            throw new ServiceException("登录失败！用户名错误");
        } else if (!UserStatus._01.getCode().equals(user.getStatus())) {
            throw new ServiceException("登录失败！用户状态异常，请联系系统管理员");
        } else if (!user.getPassword().equals(password)) {
            throw new ServiceException("登录失败！密码错误");
        } else {
            // 登录成功
            StpUtil.login(userNo);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

            // 存入缓存
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setDepName(user.getDepName());
            userInfoDto.setDepNo(user.getDepNo());
            userInfoDto.setUserNo(user.getUserNo());
            userInfoDto.setUserName(user.getUserName());
            userInfoDto.setPosList(userService.posList(userNo));
            userInfoDto.setRoleList(userService.roleList(userNo));
            StpUtil.getTokenSession().set("userInfo", userInfoDto);
            return tokenInfo;
        }
    }

    /**
     * 注销
     */
    @Override
    public void logout() {
        // 注销并清楚缓存
        StpUtil.logout();
    }

}
