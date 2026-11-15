package com.sky.config;

import com.sky.interceptor.JwtTokenAdminInterceptor;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springdoc.core.models.GroupedOpenApi;



/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    } 
    /**
     *  * 设置静态资源映射
     *  * @param registry
     *  */
    /*protected void addResourceHandlers(ResourceHandlerRegistry registry) {
            log.info(“开始设置静态资源映射...");
            registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
                }

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("苍穹外卖项目接口文档")
                        .version("2.0")
                        .description("苍穹外卖项目接口文档"));
    }

    /*@Bean
    public Docket docket(){
            ApiInfo apiInfo = new ApiInfoBuilder()
                .title(“苍穹外卖项目接口文档”)
                .version(“2.0”)
                .description(“苍穹外卖项目接口文档")
                .build();   
            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo)
                        .select()           //指定生成接口需要扫描的包       
                        .apis(RequestHandlerSelectors.basePackage("com.sky.controller"))
                        .paths(PathSelectors.any())
                        .build();
            return docket;
            }
*/
    @Bean
    public GroupedOpenApi skyApi() {
        return GroupedOpenApi.builder()
                .group("sky")
                .packagesToScan("com.sky.controller")
                .build();
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
