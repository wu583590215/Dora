package com.wq.dora.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.wq.dora.common.Result;
import com.wq.dora.common.ResultGenerator;
import com.wq.dora.dto.LoginDto;
import com.wq.dora.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
* 授权Controller
* @date 2022/06/26
* @author Wuqian
*/
@RestController
@RequestMapping("/acc")
public class AccessController {
    @Autowired
    AccessService accessService;

    /**
    * 登录
    * @param loginDto 实体对象
    * @return 结果
    */
    @PostMapping("/login")
    public Result<SaTokenInfo> add(@RequestBody LoginDto loginDto) {
        return ResultGenerator.genSuccessResult(accessService.login(loginDto));
    }

    /**
     * 查看登录状态
     * @return 结果
     */
    @PostMapping("/isLogin")
    public Result isLogin() {
        return ResultGenerator.genSuccessResult(StpUtil.isLogin());
    }

    /**
     * 注销
     * @return 结果
     */
    @PostMapping("/logout")
    public Result logout() {
        accessService.logout();
        return ResultGenerator.genSuccessResult();
    }


}
