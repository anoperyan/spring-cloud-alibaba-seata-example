package net.hm.seata.biz.service;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
public interface IBusinessService {
    void purchase(String userId, String commodityCode, int orderCount);
}
