package com.wq.dora.constant;


/**
 * 用户状态
 * @author wuqian
 */
public enum UserStatus {
    _01("01","启用"),
    _02("02","停用"),
    _03("03","锁定");

    private String code;
    private String name;

    UserStatus(String code, String name) {
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
        for (UserStatus value : values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
             }
        }
        return null;
    }
}
