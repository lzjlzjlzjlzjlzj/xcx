package com.datapool;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = "com.mapper.read", sqlSessionFactoryRef = "readSqlSessionFactory")
public class DataUser {
    @Value("${spring.datasource.read.url}")
    private String url;
    @Value("${spring.datasource.read.username}")
    private String username;
    @Value("${spring.datasource.read.password}")
    private String password;
    @Value("${spring.datasource.read.driver-class-name}")
    private String driverClass;
    @Bean(name = "readDataSource")
    public DataSource readDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "readTransactionManager")
    public DataSourceTransactionManager readTransactionManager() {
        return new DataSourceTransactionManager(readDataSource());
    }

    @Bean(name = "readSqlSessionFactory")
    public SqlSessionFactory readSqlSessionFactory(@Qualifier("readDataSource") DataSource readDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(readDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/read/*"));//这里为mapper.xml文件的路径
        return sessionFactory.getObject();
    }
}
