package com.daily.eshop.cache.ha.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.daily.eshop.cache.ha.http.HttpClientUtils;
import com.daily.eshop.cache.ha.model.ProductInfo;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

/**
 * 商品信息Commond
 */
public class GetProductInfoCommond extends HystrixCommand<ProductInfo> {

    public static final HystrixCommandKey KEY = HystrixCommandKey.Factory.asKey("GetProductInfoCommond");

    private Long productId;

    public GetProductInfoCommond(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
        .andCommandKey(KEY)
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withCoreSize(15)
                .withQueueSizeRejectionThreshold(10)));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        System.out.println("调用接口查询商品信息,productId="+productId);
        return JSONObject.parseObject(response,ProductInfo.class);
    }

    @Override
    protected String getCacheKey() {
        return "product_info_"+productId;
    }

    @Override
    protected ProductInfo getFallback() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("降级商品");
        return productInfo;
    }

    /**
     * Allow the cache to be flushed for this object.
     *
     * @param productId
     *            argument that would normally be passed to the command
     */
    public static void flushCache(Long productId) {
        HystrixRequestCache.getInstance(KEY,
                HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(productId));
    }
}
