package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.process.NxtProcessReviews;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/7
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminProductReviewsDetailController {

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @Resource
    private NxtProcessReviews nxtProcessReviews;

    @RequestMapping(value = "/api/admin/product_reviews/detail", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        //订单id，注意这里是订单id，根据订单id得到该订单的所有评论和订单详情
        Long orderFormId = jsonParam.getLong("orderFormId");

        try {

            NxtStructOrderForm nxtStructOrderForm = nxtProcessOrderForm.orderFormDetail(orderFormId);

            if (nxtStructOrderForm == null){
                return new NxtStructApiResult(54,"找不到该订单").toMap();
            }

            //给订单详情中的物品，查询添加评论数据
            nxtProcessReviews.queryReviewsPutIntoStructOrderForm(nxtStructOrderForm);

            return new NxtStructApiResult(nxtStructOrderForm).toMap();

        }
        catch (NxtException e){

            return new NxtStructApiResult(54,e.getNxtExecptionMessage()).toMap();

        }

    }


}
