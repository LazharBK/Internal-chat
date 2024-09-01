/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import extra.Connect;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.transform.stream.StreamSource;
import model.Message;

/**
 *
 * @author lazha
 */
public class MessageController {
    public List<Message> findAll(){
        try{
            List<Message> listMessage = new ArrayList();
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from messages");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message();
                message.setID(rs.getInt("id"));
                message.setType(rs.getString("type"));
                message.setSrcName(rs.getString("srcName"));
                message.setSrcId(rs.getInt("srcId"));
                message.setDistId(rs.getInt("distId"));
                message.setMessage(rs.getString("message"));
                //--------------
                InputStream is = rs.getBinaryStream("image");
                BufferedImage bufImg = null;
                Image image= ImageIO.read(is);
                //ImageIcon imageIcon =new ImageIcon(image);
                message.setImage(new ImageIcon(image));
                //--------------
                message.setDateInserted(rs.getDate("dateInserted").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                message.setDateUpdated(rs.getDate("dateUpdated").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                listMessage.add(message);
            }
            return listMessage;
        }catch(Exception e){
            System.err.println("MessageController.findAll.exception :"+e.getMessage());
            return null;
        }
    }
    
    public Message find(int id){
        try{
            Connection conn=Connect.getConnection();
            Message message = new Message();
            PreparedStatement ps = conn.prepareStatement("select * from messages where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                message.setID(rs.getInt("id"));
                message.setType(rs.getString("type"));
                message.setSrcName(rs.getString("srcName"));
                message.setSrcId(rs.getInt("srcId"));
                message.setDistId(rs.getInt("distId"));
                message.setMessage(rs.getString("message"));
                //--------------
                InputStream is = rs.getBinaryStream("image");
                //BufferedImage bufImg = null;
                Image image= ImageIO.read(is);
                //ImageIcon imageIcon =new ImageIcon(image);
                message.setImage(new ImageIcon(image));
                //--------------
                message.setDateInserted(rs.getDate("dateInserted").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                message.setDateUpdated(rs.getDate("dateUpdated").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
            return message;
        }catch(Exception e){
            System.err.println("MessageController.find.exception :"+e.getMessage());
            return null;
        }
    }
    
    public int creat(Message message){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into messages(type,srcName,srcId,distId,message,image,data) values(?,?,?,?,?,?,?)");
            ps.setString(1, message.getType());
            ps.setString(2, message.getSrcName());
            ps.setInt(3, message.getSrcId());
            ps.setInt(4, message.getDistId());
            ps.setString(5, message.getMessage());
            //ps.setBlob(6, new InputStream());
            ps.setObject(6, message.getImage());
            ps.setObject(7, message.getData());
            int affectedRows = ps.executeUpdate();
            if (affectedRows != 0) {
                ResultSet rs= ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("message inserted id :" + id); // display inserted record
                    return id;
                }
            }
        }catch(Exception e){
            System.err.println("MessageController.creat.exception :"+e.getMessage());
        }
        return -1;
    }
    
    public boolean update(Message message){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("update messages set type=?,srcName=?,srcId=?,distId=?,message=?,image=?,data=?,dateUpdated=datetime('now','localtime') where id=?");
            ps.setString(1, message.getType());
            ps.setString(2, message.getSrcName());
            ps.setInt(3, message.getSrcId());
            ps.setInt(4, message.getDistId());
            ps.setString(5, message.getMessage());
            ps.setObject(6, message.getImage());
            ps.setObject(7, message.getData());
            return ps.executeUpdate()>0;
       
        }catch(Exception e){
            System.err.println("MessageController.update.exception :"+e.getMessage());
            return false;
        }
    }
    
    public boolean delete(Message message){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from messages where id=?");
            ps.setInt(1, message.getID());    
            return ps.executeUpdate()>0;
        }catch(Exception e){
            System.err.println("MessageController.delete.exception :"+e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int id){
        try{
            Connection conn=Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from messages where id=?");
            ps.setInt(1, id);    
            return ps.executeUpdate()>0;
        }catch(Exception e){
            System.err.println("MessageController.delete.exception :"+e.getMessage());
            return false;
        }
    }
}
