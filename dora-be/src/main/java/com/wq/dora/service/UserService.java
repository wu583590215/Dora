package com.wq.dora.service;

import com.wq.dora.dto.EditPasswordDto;
import com.wq.dora.dto.UserRoleEditDto;
import com.wq.dora.entity.User;

import java.util.List;


/**
 * 用户服务类
 * @author wuqian
 * @date 2022/06/03
 */
public interface UserService {

    /**
     * 编辑密码
     *
     * @param editPasswordDto
     */
    void editPassword(EditPasswordDto editPasswordDto);

    /**
     * 保存
     *
     * @param model 实体对象
     */
    void save(User model);


    /**
     * 通过主键删除
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 重置密码
     *
     * @param id
     */
    void resetPassword(String id);


    /**
     * 更新
     *
     * @param model
     */
    void update(User model);

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    User findById(String id);

    /**
     * 查询所有用
     *
     * @return
     */
    List<User> findAll();

    /**
     * 获取岗位列表
     *
     * @param userNo
     * @return
     */
    List<String> posList(String userNo);

    /**
     * 获取角色列表
     *
     * @param userNo
     * @return
     */
    List<String> roleList(String userNo);

    /**
     * 编辑用户岗位
     *
     * @param editUserPosition
     */
    void editUserPosition(UserRoleEditDto editUserPosition);

    /**
     * 编辑用户角色
     *
     * @param editUserPosition
     */
    void editUserRole(UserRoleEditDto editUserPosition);
}
