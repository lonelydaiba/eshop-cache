package com.daily.eshop.product.start;

import com.daily.eshop.cache.ha.service.UserInfoService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserProperties.class)
public class UserAutoConfiguration {
    @Bean
    public UserInfoService getBean(UserProperties userProperties) {
        //创建组件实例
        UserInfoService userInfoService = new UserInfoService();
        userInfoService.setUsername(userProperties.getUsername());
        userInfoService.setPassword(userProperties.getPassword());
        return userInfoService;
    }
}
