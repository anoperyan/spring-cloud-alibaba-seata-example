package net.hm.seata.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Yan Jiahong
 * Created on 2022/06/16
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("net.hm.seata.account.mapper")
public class AccountApp {
    public static void main(String[] args) {
        SpringApplication.run(AccountApp.class, args);
    }
}
