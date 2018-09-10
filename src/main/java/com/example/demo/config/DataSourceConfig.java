package com.example.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource routingDataSource() {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(createDataSource("jdbc:mysql://2.1.1.0:3306/commonDB"));

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(0, createDataSource("jdbc:mysql://1.1.1.0:3306/userDB"));
        targetDataSources.put(1, createDataSource("jdbc:mysql://1.1.1.1:3306/userDB"));
        targetDataSources.put(2, createDataSource("jdbc:mysql://1.1.1.2:3306/userDB"));
        targetDataSources.put(3, createDataSource("jdbc:mysql://1.1.1.3:3306/userDB"));
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

    private DataSource createDataSource(String url) {
        //Atomikos 데이터소스로 랩핑
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();

        Properties properties = new Properties();
        properties.setProperty("user", "editor");
        properties.setProperty("password", "dpelxj^-^");
        properties.setProperty("url", url);

        //XA처리를 위한 MySQL 드라이버 변경 : AtomikosDataSourceBean 는 내부적으로 XADataSource를 가지고 있다
        dataSource.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        //XA리소스를 식별할 고유이름을 지정한다. 각 데이터소스별 고유한값을지정해도 되고 url 각각다르다면 식별가능한 url로 지정해도 무방하다.
        dataSource.setUniqueResourceName(url);
        dataSource.setXaProperties(properties);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        //문제 해결을 위한 접근 C. 항목에 해당 되는 내용이다.
        //MyBatis 에서 SQLSession에서 커넥션을 얻어오는 `TransactionFactory` 구현체를 `TransactionSynchronizationManager`를 이용하지 않는 `ManagedTransactionFactory` 로 교체한다.
        //AutoConfig로 설정하거나, 아무것도 설정하지 않으면 기본값은 `SpringManagedTransactionFactory` 로 주입된다.
        factory.setTransactionFactory(new ManagedTransactionFactory());
        return factory.getObject();
    }
}
