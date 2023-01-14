//package com.cevier.shop.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//public class Swagger2 {
//
//    /**
//     * 配置swagger2 docker
//     */
//    @Bean
//    public Docket creatRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)  // 制定api类型为swagger2
//                .apiInfo(apiInfo())  // 指定api文档信息
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.cevier.shop.controller"))  // 指定接口所在包
//                .paths(PathSelectors.any())  // 指定需要选择的controller
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("CvrShop接口API")
//                .contact(new Contact("cevier", "https://cevier.com", "mail@cevier.com"))
//                .description("电商项目学习")
//                .version("0.0.1")
//                .termsOfServiceUrl("https://shop.cevier.com")
//                .build();
//    }
//}
