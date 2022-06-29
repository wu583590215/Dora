package com.wq.dora.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户信息dto
 * @author wuqian
 */
@Data
public class UserInfoDto {
    private String userNo;
    private String userName;
    private String depNo;
    private String depName;
    private List<String> roleList;
    private List<String> posList;
}
