/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.*;
//import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import extra.Connect;
import model.User;

/**
 *
 * @author lazha
 */
public class UserController {
    public List<User> findAll(){
        try{
            List<User> listUsers = new ArrayList();
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User user = new User();
                
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setLastConn(rs.getDate("last_conn"));
                /*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                Date date = new Date();
                //user.setLastConn( df.format(date));
                user.setLastConn( date);*/
                listUsers.add(user);
            }
            return listUsers;
        }catch(Exception e){
            return null;
        }
    }
    
    public User find(int id){
        try{
            Connection conn=Connect.getConnection();
            User user = new User();
            PreparedStatement ps = conn.prepareStatement("select * from users where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setLastConn(rs.getDate("last_conn"));
            }
            return user;
        }catch(Exception e){
            return null;
        }
    }
    
    public int creat(User user){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into users(username,password,last_conn) values(?,?,?)");
            //ps.setInt(1, user.getId());
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setDate(3, (java.sql.Date) user.getLastConn());
            int affectedRows = ps.executeUpdate();
            if (affectedRows != 0) {
                ResultSet rs= ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("UserController.creat.inserted ID -" + id); // display inserted record
                    return id;
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }
    
    public boolean update(User user){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("update users set username=?,password=?,last_conn=? where id=?");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setDate(3, (java.sql.Date)user.getLastConn());
            return ps.executeUpdate()>0;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean delete(User user){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from users where id=?");
            ps.setInt(1, user.getId());    
            return ps.executeUpdate()>0;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean delete(int id){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from users where id=?");
            ps.setInt(1, id);    
            return ps.executeUpdate()>0;
        }catch(Exception e){
            return false;
        }
    }
}
