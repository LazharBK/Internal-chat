/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import include.FileChooser;
import include.Msg_Box;
import include.User_Connected_Box;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import model.Message;
import my_swing.Send_File;
import my_swing.Send_File_New;
import my_swing.Send_Photo_Box;
import my_swing.Send_Photo_Box_New;
import view.Login;
import view.Main;

/**
 *
 * @author lazha
 */
public class MainController {
    private Thread th;
    private int currentID = 0;
    private Main main;
    
    public MainController(Main main){
        this.main=main;
    }
    
    public void start() {
        System.out.println("MainController.start.1");
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("MainController.start.2");
                        System.out.println("waiting message ...");        
                        Message ms = (Message) SocketClientController.getIn().readObject();
                        String status = ms.getType();
                        if (status.equals("ErrorAuth")) {
                            System.out.println("MainController.start:ms.getType=ErrorAuth");
                            JOptionPane.showMessageDialog(main, "Username or password wrong", "Error", JOptionPane.ERROR_MESSAGE);
                            main.dispose();
                            String[] args={"Error","Username or password wrong"};
                            Login.main(args);
                            return;
                        }
                        System.out.println("MainController.start.3");       
                        if (status.equals("New")) {
                            System.out.println("MainController: ms.getType=New");
                            if(ms.getSrcName().equals(SocketClientController.getMyName())){
                                SocketClientController.setMyID(ms.getSrcId());
                            }
                            System.out.println("++++++++++++++***********ms.getSrcId()+++++++++++++*************"+ms.getSrcId());
                            addNewConnected(null, ms.getSrcId(), ms.getSrcName(), ms.getDateInserted());
                            System.out.println("ms.getName"+ms.getSrcName());
                            System.out.println(ms.getMessage());
                            System.out.println("MainController.start.7");
                        }else if (status.equals("Text")) {
                            System.out.println("MainController.start.4");
                            getMessage(ms.getID(), ms.getMessage());
                            System.out.println("MainController.start.5");
                        } else if (status.equals("Photo")) {
                           getPhoto(ms.getID(), ms.getImage());
                        } else if (status.equals("File")) {
                           getFile(ms.getID(), ms.getSrcName(), ms.getImage());
                        } else if (status.equals("Error")) {
                            System.err.println("MainController.start:ms.getType=Error");
                            main.getDpConnected().remove((Component) SocketClientController.getFriends().get(ms.getID()));
                            SocketClientController.getFriends().remove(ms.getID());
                            main.getDpConnected().revalidate();
                            main.getDpConnected().repaint();
                        } else if (status.equals("GetFile")) {
                            download(ms);
                        }
                        System.out.println("MainController.start : end");
                    }
                } catch (Exception e) {
                    String ms = e.getMessage();
                    if (ms.equals("Socket closed")) {
                        signOut("Sign out");
                    } else if (ms.equals("Connection reset")) {
                        signOut("Server has error");
                    } else {
                        signOut("Error : " + ms);
                    }
                }
            }
        });
        th.start();
    }
    
     public void addNewConnected(Image image,int ID, String name, LocalDateTime time) {
        System.out.println("MainController.addNewConnected.1");
        User_Connected_Box connected_Box = new User_Connected_Box();
        System.out.println("MainController.addNewConnected.2");
        connected_Box.set(null, ID, name, time);
        System.out.println("MainController.addNewConnected.3");
        SocketClientController.getFriends().put(ID, connected_Box);
        System.out.println("MainController.addNewConnected.4");
        if (SocketClientController.getMyName().equalsIgnoreCase(name)) {
            SocketClientController.setMyID(ID);
            connected_Box.itMe();
        }
        System.out.println("MainController.addNewConnected.5");
        main.getDpConnected().add(connected_Box);
        System.out.println("MainController.addNewConnected.6");
        main.getDpConnected().revalidate();
        main.getDpConnected().repaint();
    }
     
     public void signOut(String ms) {
        System.out.println("MainController.signOut.1");
        try {
            main.dispose();
            String[] v = {ms};
            Login.main(v);
        } catch (Exception e) {
        }
    }
      
    public void getMessage(int ID, String ms) {
        System.out.println("MainController.getMessage.1");
        if (ID == SocketClientController.getMyID()) {
            System.out.println("MainController.getMessage.2");
            Msg_Box box = new Msg_Box();
            System.out.println("MainController.getMessage.3");
            box.setGetMessage(ID, ms);
            System.out.println("MainController.getMessage.4");
            main.getDpChat().add(box);
        } else {
            System.out.println("MainController.getMessage.5");
            Msg_Box box = new Msg_Box();
            box.setGetMessage(ID, ms);
            main.getDpChat().add(box);
        }
        currentID = ID;
        main.getDpChat().revalidate();
        main.getDpChat().repaint();
        //scrollToBottom(spChat);
    }
      
    public void getPhoto(int ID, ImageIcon image) {
        System.out.println("MainController.getPhoto.1");
        if (ID == SocketClientController.getMyID()) {
            if (ID == currentID) {
                Send_Photo_Box box = new Send_Photo_Box();
                box.setPhoto(image);
                main.getDpChat().add(box);
            } else {
                Send_Photo_Box_New box = new Send_Photo_Box_New();
                box.setPhoto(ID, image);
                main.getDpChat().add(box);
            }
        }
        currentID = ID;
        main.getDpChat().revalidate();
        main.getDpChat().repaint();
        //scrollToBottom(spChat);
    }
      
    public void getFile(int ID, String ms, ImageIcon icon) {
        System.out.println("MainController.getFile.1");
        if (ID == SocketClientController.getMyID()) {
            if (ID == currentID) {
                Send_File box = new Send_File();
                box.set(ms, icon);
                main.getDpChat().add(box);
            } else {
                Send_File_New box = new Send_File_New();
                box.set(ID, ms, icon);
                main.getDpChat().add(box);
            }
        }
        currentID = ID;
        main.getDpChat().revalidate();
        main.getDpChat().repaint();
        //scrollToBottom(spChat);
    }
    
    public void setImage() {
        System.out.println("MainController.setImage.1");
        JFileChooser ch = new JFileChooser();
        FileChooser preview = new FileChooser();
        ch.setAccessory(preview);
        ch.addPropertyChangeListener(preview);
        ch.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName();
                return file.isDirectory() || name.endsWith(".png") || name.endsWith(".PNG") || name.endsWith("jpg") || name.endsWith("JPG");
            }

            @Override
            public String getDescription() {
                return "png,jpg";
            }
        });
        int c = ch.showOpenDialog(main);
        if (c == JFileChooser.APPROVE_OPTION) {
            ImageIcon image = new ImageIcon(ch.getSelectedFile().getAbsolutePath()) {};
            try {
                SocketClientController.sendPhoto(image);
            } catch (Exception e) {
                System.out.println("Error : Can't send photo");
            }
        }
        
    }
    
    public void setFile() throws Exception {
        System.out.println("MainController.setFile.1");
        JFileChooser ch = new JFileChooser();
        FileChooser preview = new FileChooser();
        ch.setAccessory(preview);
        ch.addPropertyChangeListener(preview);
        int c = ch.showOpenDialog(main);
        if (c == JFileChooser.APPROVE_OPTION) {
            SocketClientController.sendFile(ch.getSelectedFile());
        }
    }
      
    public void download(Message ms) {
        System.out.println("MainController.download.1");
        try {
            File file = new File(ms.getSrcName());
            FileOutputStream out = new FileOutputStream(file);
            out.write(ms.getData());
            out.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(main, e, "Error : can't download", JOptionPane.ERROR_MESSAGE);
        }
    }
}
