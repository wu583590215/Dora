package com.wq.dora.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 * @author wuqian
 */
@Data
public class MenuRouterDto {
    private String menuName;

    private String router;

    private String icon;

    private List<MenuRouterDto> children = new ArrayList<>();

}
