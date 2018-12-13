package com.tlg.core.config;

import com.tlg.core.utils.RouteCalcParameters;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

@Configuration
@ComponentScan({"com.tlg.core"})
@PropertySource("classpath:core.properties")
@EnableTransactionManagement
//@Import({CoreMappers.class})
public class CoreConfig {

    @Value("${jdbc.driverClass}")
    private String driverClass;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUserName;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jdbc.schema}")
    private String jdbcSchema;

    @Value("${vehicle.average.speed}")
    private Integer averageSpeed;

    @Value("${driver.month.work.time}")
    private Integer monthWorkTime;

    @Value("${driver.day.work.time}")
    private Integer dayWorkTime;

    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource getDriverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUserName);
        dataSource.setPassword(jdbcPassword);
        dataSource.setSchema(jdbcSchema);
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    @DependsOn(value = {"flyway"})
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.tlg.core");
        emf.setDataSource(getDriverManagerDataSource());
        emf.setJpaVendorAdapter(getJpaVendorAdapter());
        emf.setJpaProperties(hibernateProperties());
        return emf;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager getTransactionManager() {
        JpaTransactionManager jpa = new JpaTransactionManager();
        jpa.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean().getObject());
        return jpa;
    }

    @Bean(name = "jpaVendorAdapter")
    public JpaVendorAdapter getJpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLInnoDBDialect");
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    @Bean(name = "flyway")
    public Flyway getFlyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(getDriverManagerDataSource()).load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");

        return hibernateProperties;
    }

    //<tx:annotation-driven>
    @Bean
    public AnnotationMBeanExporter annotationMBeanExporter() {
        return new AnnotationMBeanExporter();
    }

    @Bean
    public TransactionAttributeSource annotationTransactionAttributeSource() {
        return new AnnotationTransactionAttributeSource();
    }

    @Bean
    public TransactionInterceptor transactionInterceptor() {
        return new TransactionInterceptor(getTransactionManager(), annotationTransactionAttributeSource());
    }

    @Bean
    public RouteCalcParameters getRouteCalcParameters() {
        return new RouteCalcParameters(averageSpeed, monthWorkTime, dayWorkTime);
    }

}