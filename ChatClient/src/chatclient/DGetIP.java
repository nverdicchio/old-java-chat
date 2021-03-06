/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DGetIP.java
 *
 * Created on 14-dic-2012, 12:00:16
 */
package chatclient;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Nico
 */
public class DGetIP extends javax.swing.JDialog {
    String ip;
    boolean saved;
    /** Creates new form DGetIP */
    public DGetIP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        bSave.setEnabled(false);
        saved = false;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fieldIP.requestFocusInWindow();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bSave = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fieldIP = new javax.swing.JTextField();
        labelIP = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bSave.setText("Save");
        bSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bSaveMouseReleased(evt);
            }
        });

        bCancel.setText("Cancel");
        bCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bCancelMouseReleased(evt);
            }
        });

        jLabel1.setText("Server IP:");

        fieldIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fieldIPKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(labelIP)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fieldIP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                                .addComponent(bSave)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelIP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCancel)
                    .addComponent(bSave))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fieldIPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldIPKeyTyped
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && checkIp(fieldIP.getText()))
        {
            ip = fieldIP.getText();
            saved = true;
            setVisible(false);
        }
        
        if(!checkIp(fieldIP.getText()))
        {
            labelIP.setText("Invalid IP.");
            bSave.setEnabled(false);
        }
        else
        {
            labelIP.setText("");
            bSave.setEnabled(true);
        }
        
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            saved = false;
            setVisible(false);    
        }
             
    }//GEN-LAST:event_fieldIPKeyTyped

    private void bSaveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSaveMouseReleased
        if(bSave.isEnabled() && evt.getButton() == MouseEvent.BUTTON1)
        {
            ip = fieldIP.getText();
            saved = true;
            setVisible(false);
        }
    }//GEN-LAST:event_bSaveMouseReleased

    private void bCancelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCancelMouseReleased
            saved = false;
            setVisible(false);
    }//GEN-LAST:event_bCancelMouseReleased
    
    public boolean wasSaved()
    {
        return saved;
    }
    
    public String getIP()
    {
        return ip;
    }
    
    private boolean checkIp (String sip)
    {
        String [] parts = sip.split ("\\.");
        try
        {
            for (String s : parts)
            {
                int i = Integer.parseInt (s);
                if (i < 0 || i > 255)
                {
                    return false;
                }
            }
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bSave;
    private javax.swing.JTextField fieldIP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelIP;
    // End of variables declaration//GEN-END:variables
}
