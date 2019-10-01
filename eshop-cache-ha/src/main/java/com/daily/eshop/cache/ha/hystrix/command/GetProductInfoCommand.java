package com.daily.eshop.cache.ha.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.daily.eshop.cache.ha.http.HttpClientUtils;
import com.daily.eshop.cache.ha.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * 获取商品信息
 * @author Administrator
 *
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

	private Long productId;
	
	public GetProductInfoCommand(Long productId) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetProductInfoCommand"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
						.withCoreSize(10)
						.withMaxQueueSize(12)
						.withQueueSizeRejectionThreshold(15)) 
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withCircuitBreakerRequestVolumeThreshold(30)
						.withCircuitBreakerErrorThresholdPercentage(40)
						.withCircuitBreakerSleepWindowInMilliseconds(3000)
						.withExecutionTimeoutInMilliseconds(500)
						.withFallbackIsolationSemaphoreMaxConcurrentRequests(30))  
				);  
		this.productId = productId;
	}
	
	@Override
	protected ProductInfo run() throws Exception {
		System.out.println("调用接口，查询商品数据，productId=" + productId); 
		
		if(productId.equals(-1L)) {
			throw new Exception();
		}
		
		if(productId.equals(-2L)) {
			Thread.sleep(3000);  
		}
		
		if(productId.equals(-3L)) {
//			Thread.sleep(250); 
		}
		
		String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
		String response = HttpClientUtils.sendGetRequest(url);
		return JSONObject.parseObject(response, ProductInfo.class);  
	}
	
//	@Override
//	protected String getCacheKey() {
//		return "product_info_" + productId;
//	}
	
	@Override
	protected ProductInfo getFallback() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setName("降级商品");  
		return productInfo;
	}
	
}
