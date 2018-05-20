/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import common.*;
import java.io.*;
import java.net.*;



/**
 *
 * @author Nico
 */
public class ChatClient {
    FChat chatFrame;

    /* Methods */
    public void go()
    {    
        /* lanzo un modal dialog que tome username e IP */
        chatFrame = new FChat();
        DLogin loginDialog = new DLogin(chatFrame,true);
        loginDialog.setVisible(true);
    }
    
    
    /* Main */
    public static void main(String args[])
    {
        new ChatClient().go();
    }
}
