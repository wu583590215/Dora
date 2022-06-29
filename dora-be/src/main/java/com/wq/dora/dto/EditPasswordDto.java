package com.wq.dora.dto;

import lombok.Data;

/**
 * 修改密码dto
 * @author wuqian
 */
@Data
public class EditPasswordDto {
    private String oldPassword;
    private String newPassword;
    private String userNo;
}
