package com.hjp.shiro.dao;

import javax.sql.DataSource;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BootstrapDataPopulator implements InitializingBean {

	private DataSource dataSource;
	@SuppressWarnings({ "FieldCanBeLocal" })
	private SessionFactory sessionFactory;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Session factory is only injected to ensure it is initialized before this
	// runs
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void afterPropertiesSet() throws Exception {
		// because we're using an in-memory hsqldb for the sample app, a new one
		// will be created each time the
		// app starts, so insert the sample admin user at startup:
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);

		jdbcTemplate.execute("insert into roles values (1, 'user', 'The default role given to all users.')");
		jdbcTemplate
				.execute("insert into roles values (2, 'admin', 'The administrator role only given to site admins')");
		jdbcTemplate.execute("insert into roles_permissions values (2, 'user:*')");
		jdbcTemplate.execute(
				"insert into users(id,username,email,password) values (1, 'admin', 'hardon123a@163.com', '"
						+ new Sha256Hash("admin").toHex() + "')");
		jdbcTemplate.execute("insert into users_roles values (1, 2)");

	}
}
