package com.step.hryshkin.config.springConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@EnableTransactionManagement
@PropertySource(ignoreResourceNotFound = true, value = "classpath.dataBase/hibernate.properties")
@ComponentScan(value = {"com.step.hryshkin.service","com.step.hryshkin.dao"})
public class RootConfig {

    public static final String MODELS_LOCATION = "com.step.hryshkin.model";

    @Value("${db.name}")
    private String dbName;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${hibernate.hbm2ddl.import_files}")
    private String hbm2ddlImport;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${show_sql}")
    private String showSql;

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(h2DataSource());
        sessionFactory.setPackagesToScan(MODELS_LOCATION);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return  sessionFactory;
    }

    public DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder()
                .setName(dbName)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding(String.valueOf(UTF_8))
                .build();
    }

    private Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();

        return hibernateProperties;
    }
}
