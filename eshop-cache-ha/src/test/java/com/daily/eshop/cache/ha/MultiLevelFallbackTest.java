package com.daily.eshop.cache.ha;


import com.daily.eshop.cache.ha.hystrix.command.GetProductInfoCommand;

public class MultiLevelFallbackTest {
	
	public static void main(String[] args) throws Exception {
		GetProductInfoCommand getProductInfoCommand1 = new GetProductInfoCommand(-1L);
		System.out.println(getProductInfoCommand1.execute());  
		GetProductInfoCommand getProductInfoCommand2 = new GetProductInfoCommand(-2L);
		System.out.println(getProductInfoCommand2.execute());  
	}
	
}
