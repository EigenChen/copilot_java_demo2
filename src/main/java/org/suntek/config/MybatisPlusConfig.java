package org.suntek.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus配置类
 *
 * @author GitHub Copilot
 * @date 2025-08-06
 */
@Configuration
@MapperScan("org.suntek.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件配置
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
