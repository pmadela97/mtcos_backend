package com.common.hibernate;

import org.hibernate.boot.SessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class HibernateConfiguration {

}
