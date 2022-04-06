package com.maxt.system.hospital.common.servicce.util.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author Maxt
 * @Date 2022/3/30 下午12:27
 * @Version 1.0
 * @Description  Swagger2配置信息
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket webApiConfig(){

        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(Sets.newHashSet("application/json", "text/html"))
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("网站-API文档")
                .description("本文档描述了网站微服务接口定义")
                .version("1.0")
                .contact("Maxt")
                .build();
    }

    @Bean
    public Docket adminApiConfig(){

        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(Sets.newHashSet("application/json", "text/html"))
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理系统-API文档")
                .description("本文档描述了后台管理系统微服务接口定义")
                .version("1.0")
                .contact("Maxt")
                .build();
    }

}
