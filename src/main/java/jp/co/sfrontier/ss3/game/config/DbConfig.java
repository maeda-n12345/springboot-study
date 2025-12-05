package jp.co.sfrontier.ss3.game.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * DB 関連の初期化を行う。

 * 

 */
@Configuration
@EnableTransactionManagement
@MapperScan({ "jp.co.sfrontier.ss3.game.mapper" })
public class DbConfig {
	//
	// @Bean
	// @ConfigurationProperties(prefix = "spring.datasource")
	// protected DataSource dataSource() {
	//  return new com.zaxxer.hikari.HikariDataSource();
	// }

	@Bean
	protected SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml")); // MyBatis設定ファイル
		// Mapper XMLの場所
		// クラスファイルと同じ場所、同じ名前なので今回はコメントアウトする
		// factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
		// .getResources("classpath*:mapper/*.xml"));
		return factoryBean.getObject();
	}

	@Bean
	protected SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	protected PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
