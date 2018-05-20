/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PConsole.java
 *
 * Created on 06-mar-2013, 19:39:07
 */
package server;

import common.Message;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 *
 * @author Nico
 */
public class PConsole extends javax.swing.JPanel {

    /** Creates new form PConsole */
    public PConsole() {
        initComponents();
        setVisible(true);
        consoleTextArea.setEditable(false);
        
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {  
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
        }});  
        
    }
    
    /* Metodos */
    public void write(Message msg)
    {
        String s = null;
        if(msg.getId().equals("say"))
            s = "<"+msg.getUser()+"> "+msg.getMessage()+"\n";
        if(msg.getId().equals("logout"))
            s = msg.getUser()+" has logged out.";
        
        consoleTextArea.append(s);
    }
    
    public void write(String s)
    {
        consoleTextArea.append(s + "\n");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();

        consoleTextArea.setColumns(20);
        consoleTextArea.setRows(5);
        consoleTextArea.setName("consoleTextArea"); // NOI18N
        jScrollPane1.setViewportView(consoleTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea consoleTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
