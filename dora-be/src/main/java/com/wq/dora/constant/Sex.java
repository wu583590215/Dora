package com.wq.dora.constant;


/**
 * 性别
 * @author wuqian
 * @date 2022/06/28
 */
public enum Sex {
    _01("01","男"),
    _02("02","女");

    private String code;
    private String name;

    Sex(String code, String name) {
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
        for (Sex value : values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
             }
        }
        return null;
    }
}
