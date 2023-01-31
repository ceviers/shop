### Tomcat10中部署Springboot项目war包404问题
![tomcat 404](https://cvr-file.oss-cn-hangzhou.aliyuncs.com/20230131/tomcat-http-404.png)

> 背景
> * tomcat版本：10.0.27
> * springboot版本: 2.7.8
> * java版本: 11

**过程**

* 编写Springboot web项目
* 根据[官方文档修改打包方式](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto.traditional-deployment.war)

pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.8</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.cevier</groupId>
    <artifactId>WarPackageTest</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>WarPackageTest</name>
    <description>WarPackageTest</description>
    <packaging>war</packaging>
    <properties>
        <java.version>11</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```
项目主类
```Java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WarPackageTestApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WarPackageTestApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WarPackageTestApplication.class, args);
    }

}
```
接口
```Java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}

```
* 部署打包好的war包到tomcat10/webapp目录下
* 启动tomcat<br>
> 这里没有看到springboot banner，说明项目没有部署成功<br>
> 原因：tomcat10是JakartaEE规范的实现，而springboot2.x遵循JavaEE规范<br>
> [解决办法](https://stackoverflow.com/questions/74269527/spring-boot-not-running-on-external-tomcat-10)：1.在tomcat根目录创建webapps-javaee文件夹，并将项目部署到该文件夹<br>
> 2.降低tomcat版本，使用tomcat8、9部署项目

![启动日志](https://cvr-file.oss-cn-hangzhou.aliyuncs.com/20230131/tomcat-log.png)
![tomcat 404](https://cvr-file.oss-cn-hangzhou.aliyuncs.com/20230131/tomcat-http-404.png)

* 将项目部署到webapps-javaee文件夹
* 重启项目

项目成功部署
![项目成功部署](https://cvr-file.oss-cn-hangzhou.aliyuncs.com/20230131/war-package-hello.png)
![tomcat10启动日志](https://cvr-file.oss-cn-hangzhou.aliyuncs.com/20230131/tomcat-log2.png)
