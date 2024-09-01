/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.filechooser.FileSystemView;
import controller.GlobalController;
import java.awt.Image;
import java.time.LocalDateTime;
import javax.swing.ImageIcon;
import model.Message;
/**
 *
 * @author lazha
 */
public class SocketClientController extends Thread {

    private Socket socket;
    private String username;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ImageIcon profile;
    private int ID;
    private LocalDateTime time;
    UserController uc;
    MessageController mc;

    public SocketClientController(Socket socket) {
        this.socket = socket;
        uc= new UserController();
        mc= new MessageController();
        this.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("SocketClientController.run.1");
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            //  loop starting get message from client
            while (true) {
                System.out.println("SocketClientController.run.2");
                Message ms = (Message)in.readObject();
                System.out.println("SocketClientController: ms.getType() : "+ms.getType());
                System.out.println(ms.toString());
                String status = ms.getType();
                if (status.equals("New")) {
                    System.out.println("SocketClientController: ms.getType=New");
                    username = ms.getSrcName();
                    String password = ms.getMessage();
                    UserController uc= new UserController();
                    ID=uc.checkAuthentication(username,password);
                    if(ID>0){
                        System.out.println("checkAuthentication true");
                        GlobalController.addClient(this,ID);
                    }else{
                        System.err.println("Authentication error");
                        ms = new Message();
                        ms.setType("ErrorAuth");
                        ms.setSrcName("server");
                        ms.setMessage("Authentication error");
                        out.writeObject(ms);
                        out.flush();
                        return ;
                    }
                    time = ms.getDateInserted();
                    profile = ms.getImage();
                    GlobalController.getTxt().append("New Client name : " + username + " has connected ...\n");                  
                    //  list all connected send to new client login
                    for (SocketClientController client : GlobalController.getClients()) {
                        System.out.println("SocketClientController: send connected to new client");
                        ms = new Message();
                        ms.setType("New");
                        ms.setID(client.getID());
                        ms.setSrcId(client.getID());
                        ms.setSrcName(client.getUsername());
                        ms.setDateInserted(client.getTime());
                        ms.setImage(client.getProfile());
                        out.writeObject(ms);
                        out.flush();
                    }
                    //  send new client to old clients
                    for (SocketClientController client : GlobalController.getClients()) {
                        System.out.println("SocketClientController: send new client to old clients");
                        if (client != this) {
                            ms = new Message();
                            ms.setType("New");
                            ms.setSrcName(username);
                            ms.setDateInserted(time);
                            ms.setID(ID);
                            ms.setSrcId(ID);
                            ms.setSrcId(client.getID());
                            ms.setImage(profile);
                            client.getOut().writeObject(ms);
                            client.getOut().flush();
                        }
                    }
                } else if (status.equals("File")) {
                    System.out.println("SocketClientController: ms.getType=File");
                    int fileID = GlobalController.getFileID();
                    String fileN = ms.getSrcName();
                    SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyhhmmssaa");
                    String fileName = fileID + "!" + df.format(new Date()) + "!" + ms.getSrcName().split("!")[0];
                    GlobalController.getTxt().append(fileName);
                    FileOutputStream output = new FileOutputStream(new File("data/" + fileName));
                    output.write(ms.getData());
                    output.close();
                    GlobalController.setFileID(fileID + 1);
                    ms = new Message();
                    ms.setType("File");
                    ms.setSrcName(fileID + "!" + fileN);
                    ms.setImage((ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(new File("data/" + fileName)));
                    ms.setID(ID);
                    for (SocketClientController client : GlobalController.getClients()) {
                        System.out.println("SocketClientController: send file to clients");
                        client.getOut().writeObject(ms);
                        client.getOut().flush();
                    }
                } else if (status.equals("download")) {
                    System.out.println("SocketClientController: ms.getType=download");
                    sendFile(ms);
                } else {
                    System.out.println("SocketClientController: ms.getType other else");
                    
                    for (SocketClientController client : GlobalController.getClients()) {
                        System.out.println("SocketClientController: send message txt to clients");
                        System.out.println("SocketClientController.message.toString: "+ms.toString());
                        mc.creat(ms);   
                        client.getOut().writeObject(ms);
                        client.getOut().flush();
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error adding car to server: " + e.getMessage());
            try {
                System.out.println("SocketClientController.run.tryException.1");
                GlobalController.getClients().remove(this);
                System.out.println("SocketClientController.run.tryException.2");
                GlobalController.getTxt().append("Client Name : " + username + " has been out of this server ...\n");
                System.out.println("SocketClientController.run.tryException.3");
                for (SocketClientController s : GlobalController.getClients()) {
                    System.out.println("SocketClientController.run.tryException.4");
                    Message ms = new Message();
                    System.out.println("SocketClientController.run.tryException.5");
                    ms.setType("Error");
                    System.out.println("SocketClientController.run.tryException.6");
                    ms.setID(ID);
                    System.out.println("SocketClientController.run.tryException.7");
                    ms.setSrcName(username);
                    System.out.println("SocketClientController.run.tryException.8");
                    s.getOut().writeObject(ms);
                    System.out.println("SocketClientController.run.tryException.9");
                    s.getOut().flush();
                }
            } catch (Exception e1) {
                System.out.println("SocketClientController.run.tryException.10");
                GlobalController.getTxt().append("Error : " + e1);
            }
        }
    }

    private void sendFile(Message ms) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("SocketClientController.sendFile.1");
                String fID = ms.getMessage();
                File file = new File("data");
                for (File f : file.listFiles()) {
                    if (f.getName().startsWith(fID)) {
                        try {
                            FileInputStream ins = new FileInputStream(f);
                            byte data[] = new byte[ins.available()];
                            ins.read(data);
                            ins.close();
                            ms.setData(data);
                            ms.setType("GetFile");
                            out.writeObject(ms);
                            out.flush();
                            break;
                        } catch (Exception e) {
                            System.err.println("SocketClientController.sendFile.exception :"+e.getMessage());

                        }
                    }
                }
            }
        }).start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public ImageIcon getProfile() {
        return profile;
    }

    public void setProfile(ImageIcon profile) {
        this.profile = profile;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}

