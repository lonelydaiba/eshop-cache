package com.daily.eshop.cache.ha.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * @author daili
 * @description hystrix各院隔离、降级、熔断
 */
public class CommandHelloWorld extends HystrixCommand {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    public CommandHelloWorld(String name,String example) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerEnabled(true)
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(10)));
        this.name = name;
    }


    @Override
    protected Object run() throws Exception {
        //int i = 1/0;
        throw new RuntimeException("this command always fails");
        //return "hello "+ name + "!";
    }

    /**
     * 降级。Hystrix会在run()执行过程中出现错误、超时、线程池拒绝、断路器熔断等情况时，
     * 执行getFallBack()方法内的逻辑
     */
    @Override
    protected Object getFallback() {
        return "Hello Failure " + name + "!";
        //return "failed";
    }
}
