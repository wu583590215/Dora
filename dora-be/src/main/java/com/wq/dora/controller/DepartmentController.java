package com.wq.dora.controller;
import com.wq.dora.common.Result;
import com.wq.dora.common.ResultGenerator;
import com.wq.dora.dto.TreeViewDto;
import com.wq.dora.entity.Department;
import com.wq.dora.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
* Department控制层代码
* @date 2022/06/26
* @author Wuqian
*/
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    /**
    * 新增记录
    * @param department 实体对象
    * @return 结果
    */
    @PostMapping("/add")
    public Result add(@RequestBody Department department) {
        departmentService.save(department);
        return ResultGenerator.genSuccessResult();
    }

    /**
    * 删除记录
    * @param id 主键
    * @return 结果
    */
    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        departmentService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
    * 更新记录
    * @param department 实体对象
    * @return 结果
    */
    @PostMapping("/update")
    public Result update(@RequestBody Department department) {
        departmentService.update(department);
        return ResultGenerator.genSuccessResult();
    }

    /**
    * 通过主键查询记录
    * @param id 主键
    * @return 结果
    */
    @PostMapping("/detail")
    public Result<Department> detail(@RequestParam String id) {
        Department department = departmentService.findById(id);
        return ResultGenerator.genSuccessResult(department);
    }

    /**
     * 获取机构树图
     * @return 结果
     */
    @PostMapping("/getOrgTree")
    public Result<List<TreeViewDto>> getOrgTree() {
        List<TreeViewDto>  department = departmentService.getOrgTree();
        return ResultGenerator.genSuccessResult(department);
    }

}
