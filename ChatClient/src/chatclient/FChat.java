/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FChat.java
 *
 * Created on 10-dic-2012, 12:10:40
 */
package chatclient;

import javax.swing.*;
import common.*;
import java.awt.event.*;
import java.io.*;

/**
 *
 * @author Nico
 */
public class FChat extends javax.swing.JFrame {
    DefaultListModel listModel;
    ObjectOutputStream out;
    String localUser;
   
    /* Creates new form FChat */
    public FChat() {
        listModel = new DefaultListModel();
        initComponents();
        userList.setModel(listModel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        textChat.setEditable(false);
        
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
            public void adjustmentValueChanged(AdjustmentEvent e) {  
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
            }});  
    }

    /* Methods */
    public void setLocalUser(String user)
    {
        localUser = user;
    }
    
    public void setOutputStream(ObjectOutputStream out)
    {
        this.out = out;
    }
    
    public void addUser(String user)
    {
        listModel.addElement(user);
    }
    
    public void removeUser(String user)
    {
        listModel.removeElement(user);
    }
    
    public void writeMessage(Message msg)
    {
        if(msg.getId().equals("kicked"))
            textChat.append(msg.getMessage() + " was kicked by " + msg.getUser() + ".\n");
        if(msg.getId().equals("logout"))
            textChat.append(msg.getUser()+" "+" logged out."+"\n");
        if(msg.getId().equals("banned"))
            textChat.append(msg.getMessage() + " was banned by " + msg.getUser() + ".\n");
        else 
            textChat.append("<"+msg.getUser()+">"+msg.getMessage()+"\n");
        
    }
    
    public void writeMessage(String s)
    {
        textChat.append(s+"\n");
    }
    
    public void lock()
    {
        textField.setEnabled(false);
        bSend.setEnabled(false);
    }
    
    private void sendMessage()
    {
        if(!textField.getText().equals(""))
        {
            Message msg = new Message("say",textField.getText(),localUser);
            try
            {
                out.writeObject(msg);   
            }
            catch (IOException e)
            {
                e.printStackTrace(System.err);
            }
            textField.setText("");
            textField.requestFocusInWindow();
        } 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textChat = new javax.swing.JTextArea();
        textField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList();
        bSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        textChat.setColumns(20);
        textChat.setRows(5);
        jScrollPane1.setViewportView(textChat);

        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldKeyReleased(evt);
            }
        });

        jScrollPane2.setViewportView(userList);

        bSend.setText("Send");
        bSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bSendMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(bSend))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bSend))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bSendMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSendMousePressed
        sendMessage();
    }//GEN-LAST:event_bSendMousePressed

    private void textFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            sendMessage();
    }//GEN-LAST:event_textFieldKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Message msg = new Message("logout","",localUser);
        try
        {
            out.writeObject(msg);   
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }    
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bSend;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea textChat;
    private javax.swing.JTextField textField;
    private javax.swing.JList userList;
    // End of variables declaration//GEN-END:variables
}
