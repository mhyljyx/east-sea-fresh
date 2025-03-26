package com.east.sea.core;

import com.east.sea.constant.Constant;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @program:
 * @description: spring工具
 * @author: tztang
 **/
public class SpringUtil implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static ConfigurableApplicationContext configurableApplicationContext;
    
    
    public static String getPrefixDistinctionName(){
        return configurableApplicationContext.getEnvironment().getProperty(Constant.PREFIX_DISTINCTION_NAME,
                Constant.DEFAULT_PREFIX_DISTINCTION_NAME);
    }
    
    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        configurableApplicationContext = applicationContext;
    }

}
