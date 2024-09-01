/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;

/**
 *
 * @author lazha
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private int ID;
    private String type;
    private String srcName;
    private int srcId;
    private int distId=0;
    private String message;
    private ImageIcon image;
    private byte[] data;
    private LocalDateTime dateInserted;
    private LocalDateTime dateUpdated;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String senderName) {
        this.srcName = senderName;
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getDistId() {
        return distId;
    }

    public void setDistId(int distId) {
        this.distId = distId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
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
    
    @Override
    public String toString(){
        if(dateInserted!=null){
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return "Message:{ "+"status: "+type+" ID: "+ID+" senderName: "+srcName+" dateTime: "+dateInserted.format(myFormatObj)+" message : "+message+"}";
        }
        return "Message:{ "+"status: "+type+" ID: "+ID+" senderName: "+srcName+" dateTime: null message : "+message+"}";
    }
}