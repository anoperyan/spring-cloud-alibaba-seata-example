package net.hm.seata.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@TableName(value = "t_order", autoResultMap = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private int money;
}
