/*
 * DLogin.java
 *
 * Created on 10-dic-2012, 18:59:45
 */
package chatclient;

import common.Message;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/**
 *
 * @author Nico
 */
public class DLogin extends javax.swing.JDialog {
    BufferedReader fileIn;
    BufferedWriter fileOut;
    
    DefaultComboBoxModel comboModel;
    
    String user, ip, serverName;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;  
    FChat chatFrame;
    
    
    /** Creates new form DLogin */
    public DLogin(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        comboModel = new DefaultComboBoxModel();
        comboIP.setModel(comboModel);
        bConnect.setEnabled(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        loadComboBox();
        setTitle("Login");        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fieldUser = new javax.swing.JTextField();
        bConnect = new javax.swing.JButton();
        labelUser = new javax.swing.JLabel();
        comboIP = new javax.swing.JComboBox();
        bAdd = new javax.swing.JButton();
        labelStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setText("Username:");

        jLabel2.setText("Server IP:");

        bConnect.setText("Connect");
        bConnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bConnectMousePressed(evt);
            }
        });

        bAdd.setFont(new java.awt.Font("Tahoma", 1, 12));
        bAdd.setText("+");
        bAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bAddMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(comboIP, 0, 125, Short.MAX_VALUE)
                                    .addComponent(fieldUser, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                                .addGap(4, 4, 4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bConnect)
                                .addGap(56, 56, 56)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelStatus)
                            .addComponent(bAdd)
                            .addComponent(labelUser, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelUser, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(fieldUser, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bConnect)
                    .addComponent(labelStatus))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bConnectMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bConnectMousePressed
        connect();
    }//GEN-LAST:event_bConnectMousePressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        System.exit(0);
    }//GEN-LAST:event_formWindowClosed

    private void bAddMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAddMouseReleased
        DGetIP dGetip = new DGetIP(null, true);
        dGetip.setVisible(true);
        if(dGetip.wasSaved())
        {
            comboModel.addElement(dGetip.getIP());
            bConnect.setEnabled(true);
        }    

    }//GEN-LAST:event_bAddMouseReleased

    
    /* Methods */
    private void connect()
    {
        Message msg;
        File file;
        String line;
        boolean found = false;
        
        if(bConnect.isEnabled()&&!fieldUser.getText().isEmpty()&& comboIP.getSelectedIndex() != -1)
        {
            ip = ((String)comboModel.getSelectedItem()).split(" - ")[0]; //read ip
            user = fieldUser.getText();
            try {
                /* Connection Attempt */    
                socket = new Socket(ip,5000);
                out = new ObjectOutputStream(socket.getOutputStream());
                
                msg = new Message("login",user,user);
                out.writeObject(msg);
                in = new ObjectInputStream(socket.getInputStream());
                msg = (Message)in.readObject();
                if(msg.getId().equals("banned"))
                {
                    labelStatus.setText("You have been banned from this server.");
                }
                else
                {
                    /* Updating title and file */
                    serverName = msg.getUser();
                    file = new File("serverList.txt");
                    file.setWritable(true);
                    fileIn = new BufferedReader(new FileReader(file));
                    fileOut = new BufferedWriter(new FileWriter(file));
                    line = fileIn.readLine();
                    while(line != null)
                    {
                        if(line.split("=")[0].equals(ip))
                        {
                            fileOut.write(ip+"="+serverName);
                            found = true;
                        }
                        line = fileIn.readLine();
                    }
                    if(!found)
                    {
                        if(file.length()>0)
                            fileOut.newLine();
                        fileOut.write(ip+"="+serverName);
                    } 
                    fileIn.close();
                    fileOut.close();
                    chatFrame.setTitle(serverName + " ["+user+"]");

                    /* Receiving user list */
                    while(!msg.getId().equals("end"))
                    {
                      chatFrame.addUser(msg.getMessage());
                      msg.setId("ready");               // send ready message after processing the info
                      out.writeObject(msg);
                      msg = (Message)in.readObject();
                    }
                    /* Enable FChat */
                    chatFrame.setVisible(true);
                    /* Adding a data receiver */
                    new Thread(new Alive(user,out)).start();
                    new ReceiverHandler(socket, in).run();    
                    
                    /* Hide self */
                    setVisible(false);
                    
                    
                }
      }
      catch (ConnectException e)
      {
        labelStatus.setText("Connection timed out.");
      }
      catch(Exception e) 
      { 
          e.printStackTrace(System.err);
      }                
            
            
        }
        else if(fieldUser.getText().isEmpty())
                labelUser.setText("Invalid username.");
    }
    
    private void loadComboBox()
    {
        String read, line[];
        File file = new File("serverList.txt");
        file.setWritable(false);
        try{     
            if(file.exists())
            {   
                fileIn = new BufferedReader(new FileReader(file));
                read = fileIn.readLine();
                while(read != null)
                {
                    line = read.split("=");
                    comboModel.addElement(line[0]+ " - "+ line[1]);
                    read = fileIn.readLine();
                }
            }
            else
            {
               file.createNewFile(); 
            }
            if(comboIP.getItemCount()>0)
                bConnect.setEnabled(true);
            
            fileIn.close();
        }
        catch(Exception e)
        {
            System.err.println("Exception while loading serverList file.");
        }
    }
  
////////////////////////////////////////////////////////////////////////////////    
////////////////////////////////////////////////////////////////////////////////    
////////////////////////////////////////////////////////////////////////////////
    /* Handlers, sub-class */
    public class ReceiverHandler implements Runnable
    {
        private ObjectInputStream in;
        private Socket server;
        
        public ReceiverHandler(Socket socket, ObjectInputStream in)
        {
            server = socket;
            this.in = in;
        }
        
        @Override
        public void run() 
        {
           Message msg;
            try
            {                
                while(server.isConnected())
                {
                    msg = (Message)in.readObject();
                    if(msg.getId().equals("say"))
                        chatFrame.writeMessage(msg);
                    if(msg.getId().equals("logout"))
                    {
                        chatFrame.removeUser(msg.getUser());
                        chatFrame.writeMessage(msg);
                    }
                    if(msg.getId().equals("login"))
                    {
                        chatFrame.addUser(msg.getUser());
                    }
                    
                    if(msg.getId().equals("kicked"))
                    {
                       if(msg.getMessage().equals(user))
                       {
                           chatFrame.writeMessage("You have been kicked by " + msg.getUser());
                           chatFrame.lock();
                           in.close();
                           out.close();
                       }
                       else
                           chatFrame.writeMessage(msg);
                       chatFrame.removeUser(msg.getMessage());
                    }
                    
                    if(msg.getId().equals("banned"))
                    {
                    
                        if(msg.getMessage().equals(user))
                        {
                           chatFrame.writeMessage("You have been banned by " + msg.getUser());
                           chatFrame.lock();
                           in.close();
                           out.close();
                        }
                       else
                            chatFrame.writeMessage(msg);
                       chatFrame.removeUser(msg.getMessage());
                    }
                }
                chatFrame.writeMessage("** Connection lost! **");
                chatFrame.lock();
            }
            catch (SocketException e)
            {
                chatFrame.writeMessage("** Connection lost! **");
                chatFrame.lock();
            }
            
            catch (Exception e)
            {
                System.err.println("** Connection lost! **");
            }
        }
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JButton bConnect;
    private javax.swing.JComboBox comboIP;
    private javax.swing.JTextField fieldUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelUser;
    // End of variables declaration//GEN-END:variables
}
