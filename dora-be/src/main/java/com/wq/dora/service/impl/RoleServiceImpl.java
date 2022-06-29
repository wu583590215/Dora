package com.wq.dora.service.impl;

import com.wq.dora.common.ServiceException;
import com.wq.dora.entity.Role;
import com.wq.dora.mapper.RoleMapper;
import com.wq.dora.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 角色实现类
 * @author wuqian
 * @date 2022/06/03
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper doraRoleMapper;

    /**
     * 保存
     * @param doraRole
     */
    @Override
    public void save(Role doraRole) {
        Role role = doraRoleMapper.selectByPrimaryKey(doraRole.getRoleNo());
        if (role == null) {
            doraRoleMapper.insert(doraRole);
        }else {
            throw new ServiceException("存在重复的角色编号!");
        }
    }

    /**
     * 通过主键删除
     * @param id 主键
     */
    @Override
    public void deleteById(String id) {
        doraRoleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新
     * @param doraRole
     */
    @Override
    public void update(Role doraRole) {
        doraRoleMapper.updateByPrimaryKey(doraRole);
    }

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    @Override
    public Role findById(String id) {
        return doraRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     * @param role
     * @return
     */
    @Override
    public List<Role> findAll(Role role) {
        return doraRoleMapper.selectAll(role);
    }


}
