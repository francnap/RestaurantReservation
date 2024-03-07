package com.restaurant.RestaurantReservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.restaurant.RestaurantReservation"))              
//          .paths(PathSelectors.regex("/.*")) 
          .paths(PathSelectors.any())  
          .build().apiInfo(apiInfoMetadata());                                           
    }
    
    private ApiInfo apiInfoMetadata() {
    	return new ApiInfoBuilder().title("RisTop")
    			.description("Swagger del RisTop")
    			.contact(new Contact("Dev-team", "Dev-team site", "Dev-team mail"))
    			.license("Apache 2.0")
    			.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
    			.version("1.0.0")
    			.build();
    }
}