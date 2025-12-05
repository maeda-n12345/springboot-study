package jp.co.sfrontier.ss3.game.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "jp.co.sfrontier.ss3.game.config" })
public class GameWebAppInitializer extends SpringBootServletInitializer {

	/**
	 * 外部サーブレット用の設定をする<br>
	 * <br>
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GameWebAppInitializer.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(GameWebAppInitializer.class, args);
	}
}