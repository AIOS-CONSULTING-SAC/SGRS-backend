package com.aios.sgrs;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@EnableAspectJAutoProxy(proxyTargetClass = false)
@SpringBootApplication
@ComponentScan(basePackages = {
		"com.aios.sgrs",
		"com.aios.sgrs.aop"
})
public class SgrApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SgrApplication.class);
	}

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SgrApplication.class, args);
	}

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	//@Bean
	public TomcatServletWebServerFactory tomcatFactory() {
		return new TomcatServletWebServerFactory() {
			@Override
			protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatWebServer(tomcat);
			}

			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();

				resource.setType(DataSource.class.getName());
				resource.setName(env.getProperty("spring.datasource.jndi-name"));
				//resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
				resource.setProperty("driverClassName", env.getProperty("spring.datasource.driver-class-name"));
				resource.setProperty("url", env.getProperty("spring.datasource.url"));
				resource.setProperty("username", env.getProperty("spring.datasource.username"));
				resource.setProperty("password", env.getProperty("spring.datasource.password"));
				resource.setProperty("initialSize", "1");
				resource.setProperty("maxTotal", "1");
				resource.setProperty("minIdle", "0");
				resource.setProperty("maxIdle", "0");
				resource.setProperty("maxWaitMillis", "600000");
				resource.setProperty("testOnBorrow", "true");
				resource.setProperty("validationInterval", "30000");
				resource.setProperty("validationQuery", "select 1 from dual");
				resource.setProperty("removeAbandoned", "true");
				resource.setProperty("removeAbandonedTimeout", "300");

				context.getNamingResources().addResource(resource);
			}
		};
	}

	//@Bean(name = "transactionManager")
	/*public DataSourceTransactionManager dataSourceTransactionManager() throws Exception {
		return new DataSourceTransactionManager(jndiDataSource());
	}*/
}
