package com.visu.sb.ams.util;

public class TestQuery {
    public static String INSERT_ACCOUNT_DATA_USER1 =
            "INSERT INTO accounts \n" +
                    "   (id, balance)" +
                    "   VALUES ( 1, 1000)";

    public static String INSERT_ACCOUNT_DATA_USER2 =
            "INSERT INTO accounts \n" +
                    "   (id, balance)" +
                    "   VALUES ( 2, 1000)";

    public static String DELETE_ALL_ROWS =
            "DELETE FROM accounts";

    private TestQuery() {}
}
