/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 *
 * @author lazha
 */
public class User {
    private int id;
    private String username;
    private String password;
    private ImageIcon image;
    private LocalDateTime dateLastLogin;
    private LocalDateTime dateInserted;
    private LocalDateTime dateUpdated;

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getDateLastLogin() {
        return dateLastLogin;
    }

    public void setDateLastLogin(LocalDateTime dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
    }

    public LocalDateTime getDateInserted() {
        return dateInserted;
    }

    public void setDateInserted(LocalDateTime dateInserted) {
        this.dateInserted = dateInserted;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    
    
}
