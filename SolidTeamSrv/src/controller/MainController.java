/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.net.ServerSocket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import view.Main;

/**
 *
 * @author lazha
 */
public class MainController {
    
    private ServerSocket server;
    private Thread run;
    public void startServer(Main main) throws Exception {
        System.out.println("SocketClientController.startServer.1");
        GlobalController.setClients(new ArrayList<>());
        run = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*
                    System.setProperty("javax.net.ssl.trustStore","gr.store");
                    System.setProperty("javax.net.ssl.keyStorePasword","123456");
                    SSLServerSocketFactory factory=(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
                    SSLServerSocket server=(SSLServerSocket) factory.createServerSocket(5000);
                    */
                    server = new ServerSocket(5000);
                    main.getLbStatus().setForeground(Color.green);
                    main.getLbStatus().setText("Server Start");
                    main.getTxtAreaDetail().append("Server now Starting ...\n");
                    GlobalController.setTxt(main.getTxtAreaDetail());
                    while (true) {
                        System.out.println("server begin accept");
                        new SocketClientController(server.accept());
                        System.out.println("server accepted");
                    }
                } catch (Exception e) {
                    //JOptionPane.showMessageDialog(main, e, "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("MainController.startServer.exception: "+e.getMessage());
                }
            }
        });
        run.start();
    }
    
    public void stopServer(Main main) throws Exception {
        System.out.println("SocketClientController.stopServer.1");
        int c = JOptionPane.showConfirmDialog(main, "Are you sure to stop server now", "Stop Server", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        main.getLbStatus().setText("Server Stop");
        if (c == JOptionPane.YES_OPTION) {
            main.getLbStatus().setForeground(new Color(255, 51, 51));
            main.getTxtAreaDetail().append("Server now Stoped ...\n");
            run.interrupt();
            server.close();
        }
    }

}
