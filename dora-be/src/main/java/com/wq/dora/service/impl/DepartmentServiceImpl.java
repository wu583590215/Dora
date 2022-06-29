package com.wq.dora.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.wq.dora.common.ServiceException;
import com.wq.dora.dto.TreeViewDto;
import com.wq.dora.entity.Department;
import com.wq.dora.mapper.DepartmentMapper;
import com.wq.dora.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门实现类
 * @author wuqian
 * @date 2022/06/03
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentMapper doraDepartmentMapper;

    /**
     * 保存
     * @param doraDepartment
     */
    @Override
    public void save(Department doraDepartment) {
        doraDepartmentMapper.insert(doraDepartment);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void deleteById(String id) {
        int i = doraDepartmentMapper.selectChildCount(id);
        if (i > 0) {
            throw new ServiceException("该机构存在下级机构，无法删除！");
        }
        doraDepartmentMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新
     * @param doraDepartment
     */
    @Override
    public void update(Department doraDepartment) {
        doraDepartmentMapper.updateByPrimaryKey(doraDepartment);
    }

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @Override
    public Department findById(String id) {
        return doraDepartmentMapper.selectByPrimaryKey(id);
    }


    /**
     * 获取机构树图
     * @return
     */
    @Override
    public List<TreeViewDto> getOrgTree() {
        List<Department> departments = doraDepartmentMapper.selectAll();
        List<Department> collect = departments.stream().filter(item -> StringUtil.isEmpty(item.getParentDepNo())).collect(Collectors.toList());
        return orgListToTree(collect, departments);
    }

    /**
     * @param topNode 树图顶部节点
     * @param allDeps 所有树图列表
     * @return
     */
    private List<TreeViewDto> orgListToTree(List<Department> topNode, List<Department> allDeps) {
        return topNode.stream().map(item -> {
            TreeViewDto treeViewDto = new TreeViewDto();
            treeViewDto.setKey(item.getDepNo());
            treeViewDto.setTitle(item.getDepName());
            treeViewDto.setSlots(new TreeViewDto.SlotsEntity("apartment"));

            List<Department> childrenListr = allDeps.stream()
                    .filter(childItem -> item.getDepNo().equals(childItem.getParentDepNo())).collect(Collectors.toList());
            if (childrenListr != null && childrenListr.size() > 0) {
                treeViewDto.setSlots(new TreeViewDto.SlotsEntity("bank"));
                treeViewDto.setChildren(this.orgListToTree(childrenListr,allDeps));
            }
            return treeViewDto;
        }).collect(Collectors.toList());
    }


}
