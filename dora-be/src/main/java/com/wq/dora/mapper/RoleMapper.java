package com.wq.dora.mapper;

import com.wq.dora.entity.Role;

import java.util.List;

/**
 * 角色管理mapper
 * @author wuqian
 * @date 2022/06/03
 */
public interface RoleMapper {
    int deleteByPrimaryKey(String roleNo);

    int insert(Role record);

    Role selectByPrimaryKey(String roleNo);

    List<Role> selectAll(Role role);

    int updateByPrimaryKey(Role record);
}