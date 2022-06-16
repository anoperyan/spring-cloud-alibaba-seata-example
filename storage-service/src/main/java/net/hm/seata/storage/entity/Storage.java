package net.hm.seata.storage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@TableName(value = "t_storage", autoResultMap = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Storage {
    private Integer id;
    private String commodityCode;
    private Integer count;
}
