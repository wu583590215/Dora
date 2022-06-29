package com.wq.dora.service;

import com.wq.dora.dto.MenuRoleEditDto;
import com.wq.dora.dto.MenuRouterDto;
import com.wq.dora.dto.TreeViewDto;
import com.wq.dora.entity.Menu;

import java.util.List;


/**
 * 菜单服务类
 * @author wuqian
 * @date 2022/06/03
 */
public interface MenuService {
    /**
     * 保存
     * @param model 实体对象
     */
    void save(Menu model);

    /**
     * 用主键删除
     * @param id 主键
     */
    void deleteById(String id);

    /**
     * 更新
     * @param model 实体对象
     */
    void update(Menu model);

    /**
     * 更新菜单权限
     * @param menuRoleEditDto
     */
    void editMenuRoles(MenuRoleEditDto menuRoleEditDto);

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    Menu findById(String id);

    /**
     * 查询所有
     * @return
     */
    List<Menu> findAll();

    /**
     * 获取菜单树图
     * @return
     */
    List<TreeViewDto> getMenuTree();

    /**
     * 获取菜单权限
     * @param menuNo
     * @param roleFlag
     * @return
     */
    List<String> findMenuRoles(String menuNo, String roleFlag);


    /**
     * 查询菜单路由
     * @return
     */
    List<MenuRouterDto> getMenuRouter();
}
