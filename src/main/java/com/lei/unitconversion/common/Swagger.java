package com.lei.unitconversion.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger {

   @Bean
   public Docket docket(){
       return new Docket(DocumentationType.SWAGGER_2)
         .apiInfo(apiInfo())
         .select()
         .apis(RequestHandlerSelectors.basePackage("com.lei.unitconversion.controller"))
         .paths(PathSelectors.any())
         .build();
   }

   public ApiInfo apiInfo(){
       String resultCodeStr = "";
       for (ResultCode resultCode : ResultCode.values()) {
           resultCodeStr += resultCode.getCode()+":"+ resultCode.getMsg()+";  \n";
       }
       return new ApiInfoBuilder()
         .title("单位换算API文档")
         .description("共有增删改查、转换五个接口  \n" +

                 "特殊单位实现：每个单位类型有一个标准中间单位，如温度，摄氏度为标准单位，" +
                 "非标准单位的特殊单位可以设置公式用来和标准单位转换，如温度：X-273.15;X+273.15，" +
                 "X为转换值，使用；分割，左边的公式用来转换为目标单位，右边公式由目标单位转换为特殊单位，" +
                 "设置时使用%替代除号/,@替代加号+，使用ScriptEngine将字符串解析为运算公式；  \n" +
                 "返回状态码：  \n" + resultCodeStr )
         .termsOfServiceUrl("")
         .version("1.0")
         .build();
   }
}