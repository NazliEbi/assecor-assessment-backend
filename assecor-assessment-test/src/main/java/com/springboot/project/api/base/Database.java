package com.springboot.project.api.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/** class to store a database configuration
 * @author nbibak
 *
 */
//@Component
public class Database {
	
	private static Logger log = LoggerFactory.getLogger(Database.class);
	
	private String url;
    private String username;
    private String password;
    private Type type;
    private String driverClassName;
	private DriverManagerDataSource datasource;
	private JdbcTemplate jdbcTemplate;
	
	public enum Type {
        MICROSOFT_SQL,
        ORACLE,
        MY_SQL
    }
	
	void validate() {
        if (url == null)
            throw new IllegalStateException("'url' must not be null");
        if (username == null)
            throw new IllegalStateException("'username' must not be null");
        if (password == null)
            throw new IllegalStateException("'password' must not be null");
        if (type == null)
            throw new IllegalStateException("'type' must not be null");
    }
	
	@Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("url", url)
                .append("username", username)
                .append("type", type)
                .append("driverClassName", driverClassName)
                .toString();
    }
	
	public String getDbProvider() {
    	if(type == Type.MICROSOFT_SQL) {
    		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    	}
    	else if(type == Type.ORACLE) {
    		return "oracle.jdbc.OracleDriver";
    	}
    	else if(type == Type.MY_SQL) {
    		return "com.mysql.cj.jdbc.Driver";
    	}
    	else {
    		return null;
    	}
    }
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
    	Class.forName(this.getDbProvider());
    	Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
    	return conn;
    }
	
	public DataSource initDataSource() {
		switch (type) {
        case MICROSOFT_SQL:
        	log.info("DataSource MSSQL ...");
            DriverManagerDataSource sqlServerDataSource = new DriverManagerDataSource();
            sqlServerDataSource.setUrl(url);
            sqlServerDataSource.setDriverClassName(driverClassName);
            sqlServerDataSource.setUsername(username);
            sqlServerDataSource.setPassword(password);;
            this.datasource =  sqlServerDataSource;
   		 	this.jdbcTemplate = new JdbcTemplate(datasource);
   		   	break;	 	
        case ORACLE:
            try {
            	log.info("DataSource ORACLE ...");
                Properties prop = new Properties();
                prop.setProperty("MinLimit", "5");
                prop.setProperty("MaxLimit", "25");
                prop.setProperty("InitialLimit", "3");

                DriverManagerDataSource oracleDataSource = new DriverManagerDataSource();
                oracleDataSource.setDriverClassName(driverClassName);
                oracleDataSource.setUrl(url);
                oracleDataSource.setUsername(username);
                oracleDataSource.setPassword(password);
                oracleDataSource.setConnectionProperties(prop);
                this.datasource = oracleDataSource;
                this.jdbcTemplate = new JdbcTemplate(datasource);	            
                break;
            } catch (Exception e) {
                System.err.println("Connection failed: " + e.getMessage());
            }
        case MY_SQL:
        	log.info("DataSource MY_SQL ...");
            DriverManagerDataSource mySqlDataSource = new DriverManagerDataSource();
            mySqlDataSource.setUrl(url);
            mySqlDataSource.setDriverClassName(driverClassName);
            mySqlDataSource.setUsername(username);
            mySqlDataSource.setPassword(password);;
            this.datasource =  mySqlDataSource;
   		 	this.jdbcTemplate = new JdbcTemplate(datasource);
   		    break;
        default:
            throw new IllegalStateException("Unknown Database type: " + type);
		}
		return null;
	}
	
	/** close db connection
	 * @throws SQLException
	 */
	public void closeConnection() {
		try {
			this.jdbcTemplate.getDataSource().getConnection().close();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
       
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String provider) {
		this.driverClassName = provider;
	}

	public DriverManagerDataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DriverManagerDataSource datasource) {
		this.datasource = datasource;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
