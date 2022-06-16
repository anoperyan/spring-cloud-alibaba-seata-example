package net.hm.seata.biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private Integer id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private int money;
}
