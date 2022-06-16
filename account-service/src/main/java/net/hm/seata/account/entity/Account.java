package net.hm.seata.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@TableName(value = "t_account", autoResultMap = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    private Integer id;
    private String userId;
    private int amount;
}
