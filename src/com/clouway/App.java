package com.clouway;

import java.sql.Connection;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class App {
    public static void main(String[] args) {
        Connection connection=ConnectionConfiguration.getConnection();
        if (connection!=null){
            System.out.println("connection is successful");
        }
    }
}
