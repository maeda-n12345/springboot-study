package jp.co.sfrontier.ss3.game.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring Web MVC の初期化を行う
 */
@Configuration
@EnableWebMvc
// すべてのコンポーネントをスキャンしたいわけではないので、明示的にスキャンする
@ComponentScan(basePackages = { "jp.co.sfrontier.ss3.game.controller" ,"jp.co.sfrontier.ss3.game.service"})
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * JSP ファイルのマッピング定義をする
	 * @return
	 */
	@Bean
	protected InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
}
