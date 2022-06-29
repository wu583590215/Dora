package com.wq.dora.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.wq.dora.common.ServiceException;
import com.wq.dora.constant.UserStatus;
import com.wq.dora.dto.EditPasswordDto;
import com.wq.dora.dto.UserInfoDto;
import com.wq.dora.dto.UserRoleEditDto;
import com.wq.dora.entity.User;
import com.wq.dora.mapper.UserMapper;
import com.wq.dora.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * 用户实现类
 * @author wuqian
 * @date 2022/06/03
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper doraUserMapper;

    private static final String DEFAULT_PASSWORD = "123456";

    /**
     * 修改密码
     *
     * @param editPasswordDto
     */
    @Override
    public void editPassword(EditPasswordDto editPasswordDto) {
        UserInfoDto userInfo = (UserInfoDto) StpUtil.getTokenSession().get("userInfo");
        String localPassword = this.findById(userInfo.getUserNo()).getPassword();
        if (!localPassword.equals(editPasswordDto.getOldPassword())) {
            throw new ServiceException("原密码输入不正确");
        } else {
            doraUserMapper.updatePassword(userInfo.getUserNo(), editPasswordDto.getNewPassword());
        }
    }

    /**
     * 新增用户
     *
     * @param doraUser
     */
    @Override
    public void save(User doraUser) {
        doraUser.setStatus(UserStatus._01.getCode());
        doraUser.setPassword(DigestUtils.md5DigestAsHex(DEFAULT_PASSWORD.getBytes()));
        doraUserMapper.insert(doraUser);
    }

    /**
     * 删除用户，同时删除关联的岗位和角色
     *
     * @param id
     */
    @Override
    public void deleteById(String id) {
        doraUserMapper.deleteByPrimaryKey(id);
        doraUserMapper.deleteAllPositions(id);
        doraUserMapper.deleteAllRoles(id);
    }

    /**
     * 重置密码
     *
     * @param id
     */
    @Override
    public void resetPassword(String id) {
        doraUserMapper.resetPassword(id, DigestUtils.md5DigestAsHex(DEFAULT_PASSWORD.getBytes()));
    }

    /**
     * 更新用户信息
     *
     * @param doraUser
     */
    @Override
    public void update(User doraUser) {
        doraUserMapper.updateByPrimaryKey(doraUser);
    }

    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {
        return doraUserMapper.selectByPrimaryKey(id);
    }


    /**
     * 获取用户角色
     *
     * @param userNo
     * @return
     */
    @Override
    public List<String> roleList(String userNo) {
        return doraUserMapper.selectRoles(userNo);
    }

    /**
     * 获取用户岗位列表
     *
     * @param userNo
     * @return
     */
    @Override
    public List<String> posList(String userNo) {
        return doraUserMapper.selectUserPositions(userNo);
    }

    /**
     * 编辑用户的岗位
     *
     * @param editUserPosition
     */
    @Override
    public void editUserPosition(UserRoleEditDto editUserPosition) {
        List<String> addList = editUserPosition.getAddList();
        if (addList != null && addList.size() > 0) {
            doraUserMapper.insertPositions(addList, editUserPosition.getUserNo());
        }

        List<String> deleteList = editUserPosition.getDeleteList();
        if (deleteList != null && deleteList.size() > 0) {
            doraUserMapper.deletePositions(deleteList, editUserPosition.getUserNo());
        }

    }

    /**
     * 编辑用户的岗位
     *
     * @param editUserPosition
     */
    @Override
    public void editUserRole(UserRoleEditDto editUserPosition) {
        List<String> addList = editUserPosition.getAddList();
        if (addList != null && addList.size() > 0) {
            doraUserMapper.insertRoles(addList, editUserPosition.getUserNo());
        }

        List<String> deleteList = editUserPosition.getDeleteList();
        if (deleteList != null && deleteList.size() > 0) {
            doraUserMapper.deleteRoles(deleteList, editUserPosition.getUserNo());
        }

    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        return doraUserMapper.selectAll();
    }


}
