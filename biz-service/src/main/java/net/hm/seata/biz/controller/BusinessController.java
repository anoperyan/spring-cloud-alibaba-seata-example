package net.hm.seata.biz.controller;

import net.hm.seata.biz.dto.BIzDTO;
import net.hm.seata.biz.service.IBusinessService;
import net.hm.seata.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@RestController
@RequestMapping("/api/biz")
public class BusinessController {
    @Autowired
    private IBusinessService bizService;

    @PostMapping("/purchase")
    public R<Void> purchase(@RequestBody BIzDTO dto) {
        bizService.purchase(dto.getUserId(), dto.getCommodityCode(), dto.getCount());
        return R.ok();
    }
}
