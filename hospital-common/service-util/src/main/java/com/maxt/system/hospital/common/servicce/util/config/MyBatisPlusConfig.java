package com.maxt.system.hospital.common.servicce.util.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午4:31
 * @Version 1.0
 * @Description  MyBatisPlus 配置类
 */
//事务处理
@EnableTransactionManagement
@Configuration
@MapperScan("com.maxt.system.hospital.*.mapper")
public class MyBatisPlusConfig {

    /**
     * 分页插件与乐观锁插件，一缓和二缓遵循mybatis的规则，需要设置MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题，(该属性会在旧插件移除后一同移除)
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
