package br.com.bancoxyz.config;

import com.google.common.base.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource(encoding = "UTF-8", value = "classpath:swagger.properties")
public class SwaggerConfig extends WebMvcConfigurationSupport {

  // Realiza o build da documentação
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
    .apiInfo(apiInfo())
    .useDefaultResponseMessages(false)
    .select()
    .apis(apis())
    .paths(PathSelectors.any())
    .build();
  }
  // Registra a base onde as notações do swagger tem que ser buscadas
  private Predicate<RequestHandler> apis() {
    return RequestHandlerSelectors.basePackage("br.com.bancoxyz");
  }

  //
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("SPRING REST API")
      .description("Documentação das rotas do banco XYZ")
      .contact(new Contact("Afonso Mateus", "https://github.com/scriptzeiro/api-banco-xyz", null))
      .build();
  }

  // Cria o arquivo UI do Swagger para acessarmos no navegador.
  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
      .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}

