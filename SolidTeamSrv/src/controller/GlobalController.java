/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.swing.JTextArea;
/**
 *
 * @author lazha
 */
public class GlobalController {
    private static int clientID;
    private static int fileID;
    private static ArrayList<SocketClientController> clients;
    private static JTextArea txt;
    
    public static int getFileID() {
        System.out.println("GlobalController.getFileID.1");
        return fileID;
    }

    public static void setFileID(int aFileID) {
        System.out.println("GlobalController.setFileID.1");
        fileID = aFileID;
    }

    public static JTextArea getTxt() {
        return txt;
    }

    public static void setTxt(JTextArea aTxt) {
        txt = aTxt;
    }

    public static int getClientID() {
        return clientID;
    }

    public static void setClientID(int aClientID) {
        clientID = aClientID;
    }

    public static ArrayList<SocketClientController> getClients() {
        System.out.println("GlobalController.getClients.1");
        return clients;
    }

    public static void setClients(ArrayList<SocketClientController> aClients) {
        System.out.println("GlobalController.setClients.1");
        clients = aClients;
        fileID = 1;
    }

    public static void addClient(SocketClientController client,int id) {
        clients.add(client);
        clientID=id;
       // return clientID++;
    }

}
