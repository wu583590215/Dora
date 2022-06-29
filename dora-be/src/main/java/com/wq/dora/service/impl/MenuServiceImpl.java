package com.wq.dora.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.util.StringUtil;
import com.wq.dora.constant.RoleFlag;
import com.wq.dora.dto.MenuRoleEditDto;
import com.wq.dora.dto.MenuRouterDto;
import com.wq.dora.dto.TreeViewDto;
import com.wq.dora.dto.UserInfoDto;
import com.wq.dora.entity.Menu;
import com.wq.dora.mapper.MenuMapper;
import com.wq.dora.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 菜单实现类
 * @author wuqian
 * @date 2022/06/03
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper doraMenuMapper;

    /**
     * 保存
     * @param doraMenu
     */
    @Override
    public void save(Menu doraMenu) {
        doraMenuMapper.insert(doraMenu);
    }

    /**
     * 删除关联表及菜单表
     *
     * @param id
     */
    @Override
    public void deleteById(String id) {
        doraMenuMapper.deleteMenu(id);
        doraMenuMapper.deleteAllMenuRel(id);
    }

    /**
     * 更新
     * @param menu
     */
    @Override
    public void update(Menu menu) {
        doraMenuMapper.updateByPrimaryKey(menu);
    }

    /**
     * 编辑菜单权限
     * @param menuRoleEditDto
     */
    @Override
    public void editMenuRoles(MenuRoleEditDto menuRoleEditDto) {
        String menuNo = menuRoleEditDto.getMenuNo();
        String relativeType = menuRoleEditDto.getRelativeType();
        // 新增的权限插入
        List<String> addList = menuRoleEditDto.getAddList();
        if (addList != null && addList.size() > 0) {
            doraMenuMapper.insertMenuRel(addList, menuNo, relativeType);
        }
        // 删除的进行删除
        List<String> deleteList = menuRoleEditDto.getDeleteList();
        if (deleteList != null && deleteList.size() > 0) {
            doraMenuMapper.deleteMenuRel(deleteList, menuNo, relativeType);
        }
    }

    /**
     * 用主键查询
     * @param id
     * @return
     */
    @Override
    public Menu findById(String id) {
        return doraMenuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询菜单的权限列表
     * @param menuNo
     * @param roleFlag
     * @return
     */
    @Override
    public List<String> findMenuRoles(String menuNo, String roleFlag) {
        return doraMenuMapper.selectMenuRel(menuNo, roleFlag);
    }

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> findAll() {
        return doraMenuMapper.selectAll();
    }

    /**
     * 查询菜单树图
     * @return
     */
    @Override
    public List<TreeViewDto> getMenuTree() {
        List<Menu> menus = doraMenuMapper.selectAll();
        List<Menu> collect = menus.stream().filter(item -> StringUtil.isEmpty(item.getParentMenuNo())).collect(Collectors.toList());
        return menuListToTree(collect, menus);
    }

    /**
     * 查询菜单树图
     * @return
     */
    @Override
    public List<MenuRouterDto> getMenuRouter() {
        List<Menu> menus = doraMenuMapper.selectAll();
        List<Menu> collect = menus.stream().filter(item -> StringUtil.isEmpty(item.getParentMenuNo())).collect(Collectors.toList());
        return menuRouterDtoList(collect, menus);
    }

    /**
     * 查询菜单路由的递归
     * @param topNode
     * @param allMenus
     * @return
     */
    private List<MenuRouterDto> menuRouterDtoList(List<Menu> topNode, List<Menu> allMenus) {
        UserInfoDto userInfoDto = (UserInfoDto) StpUtil.getTokenSession().get("userInfo");

        return topNode.stream().map(item -> {
            String menuNo = item.getMenuNo();
            String roleFlag = item.getRoleFlag();
            List<String> roleList = RoleFlag._01.getCode().equals(roleFlag) ? userInfoDto.getRoleList() : userInfoDto.getPosList();
            // 查询当前用户是否拥有菜单权限
            int i = roleList.size()> 0 ? doraMenuMapper.checkMenuRole(roleList, menuNo, roleFlag) : 0;
            if (i > 0) {
                 // 当有权限时添加展示路由
                MenuRouterDto menuRouterDto = new MenuRouterDto();
                menuRouterDto.setIcon(item.getIcon());
                menuRouterDto.setMenuName(item.getMenuName());
                menuRouterDto.setRouter(item.getRouter());
                // 判断是否拥有子节点
                List<Menu> childrenListr = allMenus.stream()
                        .filter(childItem -> item.getMenuNo().equals(childItem.getParentMenuNo())).collect(Collectors.toList());
                if (childrenListr.size() > 0) {
                    menuRouterDto.setChildren(this.menuRouterDtoList(childrenListr, allMenus));
                }
                return menuRouterDto;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 查询机构树图
     *
     * @param topNode 树图顶部节点
     * @param allDeps 所有树图列表
     * @return
     */
    private List<TreeViewDto> menuListToTree(List<Menu> topNode, List<Menu> allDeps) {
        return topNode.stream().map(item -> {
            TreeViewDto treeViewDto = new TreeViewDto();
            treeViewDto.setKey(item.getMenuNo());
            treeViewDto.setTitle(item.getMenuName());
            treeViewDto.setSlots(new TreeViewDto.SlotsEntity("menu"));

            List<Menu> childrenListr = allDeps.stream()
                    .filter(childItem -> item.getMenuNo().equals(childItem.getParentMenuNo())).collect(Collectors.toList());
            if (childrenListr.size() > 0) {
                treeViewDto.setSlots(new TreeViewDto.SlotsEntity("bars"));
                treeViewDto.setChildren(this.menuListToTree(childrenListr, allDeps));
            }
            return treeViewDto;
        }).collect(Collectors.toList());
    }






}
