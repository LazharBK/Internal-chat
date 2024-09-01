/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import extra.Connect;
/**
 *
 * @author lazha
 */
public class Init {
    /**
     * Create a new table in the test database
     *
     */
    public static void createNewTable() {
      
        // SQL statement for creating a new table
        String sqlCreateTableUsers = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	username varchar(25) NOT NULL UNIQUE,\n"
                + "	password varchar(35) NOT NULL,\n"
                + "	dateLastLogin DATE,\n"
                + "	dateInserted DATE DEFAULT (datetime('now','localtime')),\n"
                + "     dateUpdated DATE \n"
                + ");";
        
        String sqlCreateTableMessages = "CREATE TABLE IF NOT EXISTS messages (\n"
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                  + "	type varchar(20) NOT NULL,\n"
                + "	srcName varchar(25) NOT NULL,\n"
                + "	srcId int NOT NULL,\n"
                + "	distId int NOT NULL,\n"
                + "	message varchar(30),\n"
                + "	image BLOB,\n"
                + "	data BLOB,\n"
                + "	dateInserted DATE DEFAULT (datetime('now','localtime')),\n"
                + "     dateUpdated DATE \n"
                + ");";
        
        String sqlSeddingUsers1 ="insert into users(username,password,dateUpdated) values('faiza','5f4dcc3b5aa765d61d8327deb882cf99',datetime('now','localtime'))";
        String sqlSeddingUsers2 ="insert into users(username,password,dateUpdated) values('lazhar','5f4dcc3b5aa765d61d8327deb882cf99',datetime('now','localtime'))";
        
        try {
                Connection conn=Connect.getConnection();
                Statement stmt = conn.createStatement();
                // create a new table
                stmt.execute(sqlCreateTableUsers);
                System.out.println("table users created");
                stmt.execute(sqlCreateTableMessages);
                System.out.println("table messages created");
                stmt.execute(sqlSeddingUsers1);
                System.out.println("sedding user1 success");
                stmt.execute(sqlSeddingUsers2);
                System.out.println("sedding user2 success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
