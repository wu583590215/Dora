package com.wq.dora.dto;

import lombok.Data;

import java.util.List;

/**
 * 菜单权限编辑dto
 * @author wuqian
 */
@Data
public class MenuRoleEditDto {
    private String menuNo;
    private String relativeType;
    private List<String> addList;
    private List<String> deleteList;
}
