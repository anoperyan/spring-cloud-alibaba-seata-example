package net.hm.seata.common.util;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {
    private static final long serialVersionID = 1L;

    @Setter
    @Getter
    private int code;

    @Setter
    @Getter
    private String message;

    @Setter
    @Getter
    private T data;

    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.code = RespCode.OK.code;
        r.message = RespCode.OK.message;
        return r;
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.code = RespCode.OK.code;
        r.message = RespCode.OK.message;
        return r;
    }
}
