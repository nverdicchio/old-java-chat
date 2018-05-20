/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJFrame.java
 *
 * Created on 05-dic-2012, 15:32:50
 */
package server;

import common.Message;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Nico
 */
public class FServer extends javax.swing.JFrame {
    DefaultListModel listModel;
    Message internalMsg;
    /* Constructor */
    public FServer() {
        internalMsg = new Message("empty",null,null);
        listModel = new DefaultListModel();
        initComponents();
        listUser.setModel(listModel);
        textChat.setEditable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        scrollTextChat.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
            public void adjustmentValueChanged(AdjustmentEvent e) {  
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
            }});  
  
    }
        
        

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupUser = new javax.swing.JPopupMenu();
        itemKick = new javax.swing.JMenuItem();
        itemBan = new javax.swing.JMenuItem();
        scrollTextChat = new javax.swing.JScrollPane();
        textChat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        listUser = new javax.swing.JList();

        itemKick.setText("Kick");
        itemKick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                itemKickMouseReleased(evt);
            }
        });
        popupUser.add(itemKick);

        itemBan.setText("ban");
        itemBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                itemBanMouseReleased(evt);
            }
        });
        popupUser.add(itemBan);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");

        textChat.setColumns(20);
        textChat.setEditable(false);
        textChat.setRows(5);
        scrollTextChat.setViewportView(textChat);

        listUser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listUserMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(listUser);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(299, 299, 299)
                .addComponent(scrollTextChat, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollTextChat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listUserMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listUserMouseReleased
        if(!listUser.isSelectionEmpty()&&evt.getButton()==MouseEvent.BUTTON3)
        {
            popupUser.setLocation(evt.getLocationOnScreen());
            popupUser.setVisible(true);
        }
    }//GEN-LAST:event_listUserMouseReleased

    private void itemKickMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemKickMouseReleased
        if(evt.getButton() == MouseEvent.BUTTON1)
        {
            internalMsg = new Message("kicked",(String)listUser.getSelectedValue(),"server");
            popupUser.setVisible(false);
        }
    }//GEN-LAST:event_itemKickMouseReleased

    private void itemBanMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemBanMouseReleased
        if(evt.getButton() == MouseEvent.BUTTON1)
        {
            internalMsg = new Message("banned",(String)listUser.getSelectedValue(),"server");
            popupUser.setVisible(false);
        }
    }//GEN-LAST:event_itemBanMouseReleased

    /**
     * @param args the command line arguments
     */
    /* Methods */
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
        else if(msg.getId().equals("logout"))
                textChat.append(msg.getUser()+" "+msg.getMessage()+"\n"); 
            else 
                textChat.append("<"+msg.getUser()+">"+msg.getMessage()+"\n");
    }
    
    public Message getInternalMessage()
    {
        return internalMsg;
    }

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemBan;
    private javax.swing.JMenuItem itemKick;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listUser;
    private javax.swing.JPopupMenu popupUser;
    private javax.swing.JScrollPane scrollTextChat;
    private javax.swing.JTextArea textChat;
    // End of variables declaration//GEN-END:variables
}
