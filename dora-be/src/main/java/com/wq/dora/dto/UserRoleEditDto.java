package com.wq.dora.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户权限编辑dto
 * @author wuqian
 */
@Data
public class UserRoleEditDto {
    private List<String> addList;
    private List<String> deleteList;
    private String userNo;
}
