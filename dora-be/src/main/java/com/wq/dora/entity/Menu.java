package com.wq.dora.entity;

import lombok.Data;

@Data
public class Menu {
    private String menuNo;

    private String menuName;

    private String router;

    private String icon;

    private String parentMenuNo;

    private String roleFlag;
}