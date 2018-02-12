package com.visu.sb.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
public class Application {

/*
	public static String QUERY_CREATE_ACCOUNTS_TABLE =
			"CREATE TABLE accounts \n" +
					"   (id bigint, \n" +
					"   balance decimal)";
*/

	public static String QUERY_INSERT_BA_TEST_DATA_USER1 =
			"INSERT INTO accounts \n" +
					"   (id, balance)" +
					"   VALUES ( 1, 1000)";

	public static String QUERY_INSERT_BA_TEST_DATA_USER2 =
			"INSERT INTO accounts \n" +
					"   (id, balance)" +
					"   VALUES ( 2, 1000)";

	public static String QUERY_INSERT_BA_TEST_DATA_USER3 =
			"INSERT INTO accounts \n" +
					"   (id, balance)" +
					"   VALUES ( 3, 1000)";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);

/*		String DB_CONNECTION_URL = "jdbc:h2:mem:test";
		Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, "h2", "h2");
		Statement stmt = connection.createStatement();

//		stmt.executeUpdate(QUERY_CREATE_ACCOUNTS_TABLE);
		stmt.executeUpdate(QUERY_INSERT_BA_TEST_DATA_USER1);
		stmt.executeUpdate(QUERY_INSERT_BA_TEST_DATA_USER2);
		stmt.executeUpdate(QUERY_INSERT_BA_TEST_DATA_USER3);

		connection.close();
		stmt.close();*/
    }       
}            