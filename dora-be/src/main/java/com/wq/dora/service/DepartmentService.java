package com.wq.dora.service;
import com.wq.dora.dto.TreeViewDto;
import com.wq.dora.entity.Department;

import java.util.List;


/**
 * 部门服务类
 * @author wuqian
 * @date 2022/06/03
 */
public interface DepartmentService  {
    /**
     * 保存
     * @param model 实体对象
     */
    void save(Department model);

    /**
     * 用主键删除
     * @param id 主键
     */
    void deleteById(String id);

    /**
     * 更新
     * @param model 实体对象
     */
    void update(Department model);

    /**
     * 通过主键查询
     * @param id
     * @return 实体对象
     */
    Department findById(String id);

    /**
     * 获取机构树图
     * @return
     */
    List<TreeViewDto> getOrgTree();
}
