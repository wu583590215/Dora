package com.wq.dora.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wq.dora.common.Result;
import com.wq.dora.common.ResultGenerator;
import com.wq.dora.entity.Role;
import com.wq.dora.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
* Role控制层代码
* @date 2022/06/26
* @author Wuqian
*/
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    /**
    * 新增记录
    * @param role 实体对象
    * @return 结果
    */
    @PostMapping("/add")
    public Result add(@RequestBody Role role) {
        roleService.save(role);
        return ResultGenerator.genSuccessResult();
    }

    /**
    * 删除记录
    * @param id 主键
    * @return 结果
    */
    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        roleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
    * 更新记录
    * @param role 实体对象
    * @return 结果
    */
    @PostMapping("/update")
    public Result update(@RequestBody Role role) {
        roleService.update(role);
        return ResultGenerator.genSuccessResult();
    }

    /**
    * 通过主键查询记录
    * @param id 主键
    * @return 结果
    */
    @PostMapping("/detail")
    public Result<Role> detail(@RequestParam String id) {
        Role role = roleService.findById(id);
        return ResultGenerator.genSuccessResult(role);
    }


    /**
    * 分页查询记录，默认查询所有
    * @param page 页码
    * @param size 每页记录数
    * @return
    */
    @PostMapping("/list")
    public Result<PageInfo<Role>> list(@RequestBody(required = false) Role role, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Role> list = roleService.findAll(role);
        PageInfo<Role> pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
