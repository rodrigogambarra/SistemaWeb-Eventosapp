package com.eventosapp.eventosapp;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;

@Controller
public class DataConfiguration {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/eventosapp");
        dataSource.setUsername("rodrigo");
        dataSource.setPassword("Rodrigo_123");
        return dataSource;
    }


	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);//mostra todas as percistencias no terminal durante a execução
        adapter.setGenerateDdl(true);//cria automaticamente as tabelas no banco
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");//Dialect.MySQL5Dialect - força o hibernate utilizar Engine em vez de Type
        adapter.setPrepareConnection(true);//hibernate se conectar automaticamente
        return adapter;
    }
}
