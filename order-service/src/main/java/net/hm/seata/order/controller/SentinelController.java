package net.hm.seata.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@RestController
public class SentinelController {
    @GetMapping("/sentinel")
    public String sentinel() {
        return "hello, sentinel dashboard....";
    }
}
