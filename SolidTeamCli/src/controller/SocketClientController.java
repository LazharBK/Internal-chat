package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import model.Message;
import extra.ImageViewer;
import java.io.IOException;
import java.time.LocalDateTime;
import view.Main;
import model.UserInterface;

public class SocketClientController {

    private static HashMap<Integer, UserInterface> friends = new HashMap<>();
    private static Socket client;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static int myID;
    private static String myName;
    private static LocalDateTime  time;
    private static JFrame fram;

    public static void setTextFieldSyle(JTextField txt, String style) {
        System.out.println("SocketClientController.setTextFieldSyle.1");
        txt.setName("");
        txt.setForeground(new Color(186, 186, 186));
        txt.setText(style);
        txt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                if (txt.getName().equals("")) {
                    txt.setForeground(new Color(51, 51, 51));
                    txt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (txt.getText().trim().equals("")) {
                    txt.setForeground(new Color(186, 186, 186));
                    txt.setText(style);
                    txt.setName("");
                } else {
                    txt.setName("have");
                }
            }
        });
        System.out.println("SocketClientController.setTextFieldSyle.2");
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (txt.getText().trim().equals("")) {
                    txt.setName("");
                } else {
                    txt.setName("have");
                }
            }
        });
    }

    public static void connect(ImageIcon icon, String username, String password, String IP) throws IOException {
        System.out.println("SocketClientController.connect.1");
        client = new Socket(IP, 5000);
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());

        /*
        System.setProperty("javax.net.ssl.trustStore","gr.store");
        System.setProperty("javax.net.ssl.keyStorePasword","123456");
        SSLSocketFactory factory=(SSLSocketFactory) SSLSocketFactory.getDefault();          
        client = factory.createSocket("127.0.0.1", 5000);
        */
        System.out.println("SocketClientController.connect.2");
        Message ms = new Message();
        ms.setType("New");
        ms.setImage(icon);
        ms.setSrcName(username);
        ms.setMessage(password);
        ms.setDateInserted(LocalDateTime.now());
        time = LocalDateTime.now();
        out.writeObject(ms);
        out.flush();
        myName = username;
    }

    public static void sendMessage(String text) throws Exception {
        System.out.println("SocketClientController.sendMessage.1");
        Message ms = new Message();
        ms.setType("Text");
        ms.setSrcName(myName);
        ms.setSrcId(myID);
        ms.setID(SocketClientController.getMyID());
        ms.setMessage(text);
        out.writeObject(ms);
        out.flush();
    }

    public static void sendPhoto(ImageIcon photo) throws Exception {
        System.out.println("SocketClientController.sendPhoto.1");
        Message ms = new Message();
        ms.setType("Photo");
        ms.setID(SocketClientController.getMyID());
        ms.setImage(photo);
        out.writeObject(ms);
        out.flush();
    }

    public static void sendFile(File file) throws Exception {
        System.out.println("SocketClientController.sendFile.1");
        FileInputStream in = new FileInputStream(file);
        byte data[] = new byte[in.available()];
        in.read(data);
        in.close();
        String fileSize = convertSize(file.length());
        Message ms = new Message();
        ms.setType("File");
        ms.setID(SocketClientController.getMyID());
        ms.setData(data);
        ms.setSrcName(file.getName() + "!" + fileSize);
        out.writeObject(ms);
        out.flush();
    }

    private static String twoDigitString(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public static void downloadFile(int ID, String name) {
        System.out.println("SocketClientController.downloadFile.1");
        try {
            String ex[] = name.split("\\.");
            String x = ex[ex.length - 1];
            JFileChooser ch = new JFileChooser();
            ch.setSelectedFile(new File(name));
            int c = ch.showSaveDialog(Main.getFrames()[0]);
            if (c == JFileChooser.APPROVE_OPTION) {
                File f = ch.getSelectedFile();
                if (f.exists()) {
                    int click = JOptionPane.showConfirmDialog(Main.getFrames()[0], "This file name has already do you want to replace", "Save File", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (click != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                String parth = f.getAbsolutePath();
                if (!parth.endsWith("." + x)) {
                    parth += "." + x;
                }
                Message ms = new Message();
                ms.setType("download");
                ms.setID(SocketClientController.getMyID());
                ms.setSrcName(parth);
                ms.setMessage(ID + "");
                out.writeObject(ms);
                out.flush();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(fram, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showImage(ImageIcon image) {
        System.out.println("SocketClientController.showImage.1");
        JPopupMenu pop = new JPopupMenu();
        pop.setBackground(new Color(0, 0, 0, 0));
        ImageViewer obj = new ImageViewer();
        obj.setImage(image);
        pop.add(obj);
        int w = (int) obj.getPreferredSize().getWidth();
        int h = (int) obj.getPreferredSize().getHeight();
        pop.show(fram, fram.getWidth() / 2 - w / 2, fram.getHeight() / 2 - h / 2);
    }
    private static final String[] fileSizeUnits = {"bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};

    private static String convertSize(double bytes) {
        System.out.println("SocketClientController.convertSize.1");
        String sizeToReturn;
        DecimalFormat df = new DecimalFormat("0.#");
        int index;
        for (index = 0; index < fileSizeUnits.length; index++) {
            if (bytes < 1024) {
                break;
            }
            bytes = bytes / 1024;
        }
        System.out.println("Systematic file size: " + bytes + " " + fileSizeUnits[index]);
        sizeToReturn = df.format(bytes) + " " + fileSizeUnits[index];
        return sizeToReturn;
    }

    public static Font getFount() {
        return new java.awt.Font("Khmer SBBIC Serif", 0, 12);
    }

    public static Font getFountBold() {
        return new java.awt.Font("Khmer SBBIC Serif", 1, 12);
    }

    public static HashMap<Integer, UserInterface> getFriends() {
        return friends;
    }

    public static void setFriends(HashMap<Integer, UserInterface> aFriends) {
        friends = aFriends;
    }

    public static ObjectOutputStream getOut() {
        return out;
    }

    public static void setOut(ObjectOutputStream aOut) {
        out = aOut;
    }

    public static ObjectInputStream getIn() {
        return in;
    }

    public static void setIn(ObjectInputStream aIn) {
        in = aIn;
    }

    public static int getMyID() {
        return myID;
    }

    public static void setMyID(int aMyID) {
        myID = aMyID;
    }

    public static String getMyName() {
        return myName;
    }

    public static void setMyName(String aMyName) {
        myName = aMyName;
    }

    public static Socket getClient() {
        return client;
    }

    public static void setClient(Socket aClient) {
        client = aClient;
    }

    public static LocalDateTime getTime() {
        return time;
    }

    public static void setTime(LocalDateTime aTime) {
        time = aTime;
    }

    public static JFrame getFram() {
        return fram;
    }

    public static void setFram(JFrame aFram) {
        fram = aFram;
    }
}
