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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
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
                user.setDateInserted(rs.getDate("dateInserted").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                user.setDateUpdated(rs.getDate("dateUpdated").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                listUsers.add(user);
            }
            return listUsers;
        }catch(Exception e){
            System.err.println("UserController.findAll.exception :"+e.getMessage());
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
                user.setDateInserted(rs.getDate("dateInserted").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                user.setDateUpdated(rs.getDate("dateUpdated").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
            return user;
        }catch(Exception e){
            System.err.println("UserController.find.exception :"+e.getMessage());
            return null;
        }
    }
    
    public int creat(User user){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into users(username,password) values(?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
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
            System.err.println("UserController.creat.exception :"+e.getMessage());
        }
        return -1;
    }
    
    public boolean update(User user){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("update users set username=?,password=?,dateUpdated=datetime('now','localtime') where id=?");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps.executeUpdate()>0;
        }catch(Exception e){
            System.err.println("UserController.update.exception :"+e.getMessage());
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
            System.err.println("UserController.delete.exception :"+e.getMessage());
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
    
    public int checkAuthentication(String username,String password){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("select id from users where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, passwordToHash(password));   
            System.out.println("username & password: "+username+" & "+passwordToHash(password) );
            ResultSet rs = ps.executeQuery();
            return rs.getInt("id");
        }catch(Exception e){
            System.err.println("UserController.checkAuthentication.exception :"+e.getMessage());
            return 0;
        }
    }
    
     public String passwordToHash(String password){
        String generatedPassword = null;
         try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
