package com.daily.eshop.cache.ha.controller;

import com.alibaba.fastjson.JSONObject;
import com.daily.eshop.cache.ha.http.HttpClientUtils;
import com.daily.eshop.cache.ha.hystrix.command.GetProductInfoCommond;
import com.daily.eshop.cache.ha.model.ProductInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 缓存服务的接口
 * @author Administrator
 *
 */
@Controller
public class CacheController {

	@RequestMapping("/change/product")
	@ResponseBody
	public String changeProduct(Long productId) {
		// 拿到一个商品id
		// 调用商品服务的接口，获取商品id对应的商品的最新数据
		// 用HttpClient去调用商品服务的http接口
		String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
		String response = HttpClientUtils.sendGetRequest(url);
		System.out.println(response);  
		
		return "success";
	}

    /**
     * 一次性批量查询多条商品数据的请求
     * @param productIds
     * @return
     */
    @RequestMapping("/getProductInfos")
    @ResponseBody
	public String getProductInfos(String productIds) {
        String[] productIdArray = productIds.split(",");
        for (String productId:productIdArray) {
            GetProductInfoCommond getProductInfoCommond = new GetProductInfoCommond(
                    Long.valueOf(productId));
            ProductInfo productInfo = getProductInfoCommond.execute();
            System.out.println(JSONObject.toJSON(productInfo));
            System.out.println(getProductInfoCommond.isResponseFromCache());
        }
        return "success";
    }
	
}
