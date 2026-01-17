package com.github.hondams.fw.confifg;

import com.github.hondams.fw.repository.ibatis.CodeEnumTypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(name = "org.apache.ibatis.session.SqlSessionFactory")
@Configuration
public class MyBatisBootConfig {

    @Bean
    @ConditionalOnMissingBean
    public ConfigurationCustomizer enumTypeHandlerCustomizer() {
        return configuration -> configuration.getTypeHandlerRegistry()
                .setDefaultEnumTypeHandler(CodeEnumTypeHandler.class);
    }
}
