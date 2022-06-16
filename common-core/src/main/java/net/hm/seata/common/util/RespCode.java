package net.hm.seata.common.util;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
public enum RespCode {
    OK(200, "操作成功！");

    public final int code;
    public final String message;

    RespCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
