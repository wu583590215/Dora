package com.wq.dora.mapper;

import com.wq.dora.entity.Department;

import java.util.List;

/**
 * 岗位管理mapper
 * @author wuqian
 * @date 2022/06/03
 */
public interface DepartmentMapper {

    int deleteByPrimaryKey(String depNo);

    int insert(Department record);

    Department selectByPrimaryKey(String depNo);

    int selectChildCount(String depNo);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);
}