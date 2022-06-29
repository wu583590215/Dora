package com.wq.dora.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.wq.dora.dto.LoginDto;


/**
 * 权限服务类
 * @author wuqian
 * @date 2022/06/03
 */
public interface AccessService {

    /**
     * 登录
     * @param loginDto
     */
    SaTokenInfo login(LoginDto loginDto);

    /**
     * 注销
     */
    void logout();
}
