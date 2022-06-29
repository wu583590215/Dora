package com.wq.dora.common;

/**
 * 响应码枚举，参考HTTP状态码的语义
 * @author wuqian
 * @date 2022/6/28
 */
public enum ResultCode {
    SUCCESS(200),//成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未认证（签名错误，未登录）
    FORBIDDEN(403),//拒绝访问，无权限
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
