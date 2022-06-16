package net.hm.seata.order.dto;

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
public class Account {
    private Integer id;
    private String userId;
    private int amount;
}
