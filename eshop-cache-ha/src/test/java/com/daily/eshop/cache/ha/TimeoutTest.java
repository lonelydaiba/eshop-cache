package com.daily.eshop.cache.ha;


import com.daily.eshop.cache.ha.http.HttpClientUtils;

public class TimeoutTest {
	
	public static void main(String[] args) throws Exception {
		HttpClientUtils.sendGetRequest("http://localhost:8081/getProductInfo?productId=-3");
	}
	
}
