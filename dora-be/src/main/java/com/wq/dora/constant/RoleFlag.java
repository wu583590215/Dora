package com.wq.dora.constant;


/**
 * 菜单关联类型
 * @author wuqian
 */
public enum RoleFlag {
    _01("01","菜单角色"),
    _02("02","菜单岗位");

    private String code;
    private String name;

    RoleFlag(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {
        for (RoleFlag value : values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
             }
        }
        return null;
    }
}
