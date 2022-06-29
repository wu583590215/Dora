package com.wq.dora.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wq.dora.common.Result;
import com.wq.dora.common.ResultGenerator;
import com.wq.dora.dto.EditPasswordDto;
import com.wq.dora.dto.UserRoleEditDto;
import com.wq.dora.entity.User;
import com.wq.dora.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
* User控制层代码
* @date 2022/06/26
* @author Wuqian
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
    * 新增记录
    * @param user 实体对象
    * @return 结果
    */
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    /**
    * 删除记录
    * @param id 主键
    * @return 结果
    */
    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 重置密码
     * @param id 主键
     * @return 结果
     */
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestParam String id) {
        userService.resetPassword(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
    * 更新记录
    * @param user 实体对象
    * @return 结果
    */
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    /**
    * 通过主键查询记录
    * @param id 主键
    * @return 结果
    */
    @PostMapping("/detail")
    public Result<User> detail(@RequestParam String id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }


    /**
    * 分页查询记录，默认查询所有
    * @param page 页码
    * @param size 每页记录数
    * @return
    */
    @PostMapping("/list")
    public Result<PageInfo<User>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo<User> pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 通过主键查询岗位列表
     * @param userNo 主键
     * @return 结果
     */
    @PostMapping("/posList")
    public Result<List<String>> posList(@RequestParam String userNo) {
        List<String> posList = userService.posList(userNo);
        return ResultGenerator.genSuccessResult(posList);
    }

    /**
     * 修改岗位列表
     * @param userRoleEditDto
     * @return 结果
     */
    @PostMapping("/editUserPosition")
    public Result editUserPosition(@RequestBody UserRoleEditDto userRoleEditDto) {
        userService.editUserPosition(userRoleEditDto);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 通过主键查询角色列表
     * @param userNo 主键
     * @return 结果
     */
    @PostMapping("/roleList")
    public Result<List<String>> roleList(@RequestParam String userNo) {
        List<String> posList = userService.roleList(userNo);
        return ResultGenerator.genSuccessResult(posList);
    }

    /**
     * 修改角色列表
     * @param userRoleEditDto
     * @return 结果
     */
    @PostMapping("/editUserRole")
    public Result editUserRole(@RequestBody UserRoleEditDto userRoleEditDto) {
        userService.editUserRole(userRoleEditDto);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 获取缓存的userInfo
     * @return 结果
     */
    @PostMapping("/userInfo")
    public Result userInfo() {
        return ResultGenerator.genSuccessResult(StpUtil.getTokenSession().get("userInfo"));
    }

    /**
     * 获取缓存的userInfo
     * @param
     * @return 结果
     */
    @PostMapping("/editPassword")
    public Result editPassword(@RequestBody EditPasswordDto editPasswordDto) {
        userService.editPassword(editPasswordDto);
        return ResultGenerator.genSuccessResult();
    }
}
