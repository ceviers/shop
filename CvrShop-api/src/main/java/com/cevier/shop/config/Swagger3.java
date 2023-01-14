//package com.cevier.shop.config;
//
//import io.swagger.models.auth.In;
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import springfox.documentation.builders.*;
//import springfox.documentation.schema.ScalarType;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import java.lang.reflect.Field;
//import java.util.*;
//
//
//@Configuration
//public class Swagger3 {
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.OAS_30)
////                .pathMapping("/")
//
//                // 定义是否开启swagger，false为关闭，可以通过变量控制
//                .enable(true)
//
//                // 将api的元信息设置为包含在json ResourceListing响应中。
//                .apiInfo(apiInfo())
//
//                // 接口调试地址
////                .host("8088")
//
//                // 选择哪些接口作为swagger的doc发布
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.cevier.shop.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .globalRequestParameters(getGlobalRequestParameters())
//                .globalResponses(HttpMethod.GET, getGlobalResponseMessage())
//                .globalResponses(HttpMethod.POST, getGlobalResponseMessage());
//    }
//
//    /**
//     * API 页面上半部分展示信息
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("CvrShop接口API")
//                .contact(new Contact("cevier", "https://cevier.com", "mail@cevier.com"))
//                .description("电商项目学习")
//                .version("0.0.1")
//                .termsOfServiceUrl("https://shop.cevier.com")
//                .build();
//    }
//
//    /**
//     * 封装全局通用参数
//     */
//    private List<RequestParameter> getGlobalRequestParameters() {
//        List<RequestParameter> parameters = new ArrayList<>();
//        parameters.add(new RequestParameterBuilder()
//                .name("uuid")
//                .description("设备uuid")
//                .required(true)
//                .in(ParameterType.QUERY)
//                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                .required(false)
//                .build());
//        return parameters;
//    }
//
//
//    /**
//     * 封装通用响应信息
//     */
//    private List<Response> getGlobalResponseMessage() {
//        List<Response> responseList = new ArrayList<>();
//        responseList.add(new ResponseBuilder().code("404").description("未找到资源").build());
//        return responseList;
//    }
//
//}
//
//
