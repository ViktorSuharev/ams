package com.visu.sb.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
public class Application {

	private static final String H2_CONNECTION_URL = "jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE;MVCC=FALSE;LOCK_MODE=1";
	private static final String H2_USERNAME = "h2";
	private static final String H2_PASSWORD = "h2";

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

		createTestData();
    }

    private static void createTestData() throws Exception {
		Connection connection = DriverManager.getConnection(H2_CONNECTION_URL, H2_USERNAME, H2_PASSWORD);
		Statement stmt = connection.createStatement();

		stmt.executeUpdate(QUERY_INSERT_BA_TEST_DATA_USER1);
		stmt.executeUpdate(QUERY_INSERT_BA_TEST_DATA_USER2);
		stmt.executeUpdate(QUERY_INSERT_BA_TEST_DATA_USER3);
		connection.commit();

		connection.close();
		stmt.close();
	}
}