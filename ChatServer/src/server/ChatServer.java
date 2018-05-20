/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import common.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nico
 */
public class ChatServer {
    ArrayList<ObjectOutputStream> outputStreams;
    ArrayList<String> userList;
    ArrayList<ClientHandler> handlerList;
    TreeSet<String> bannedList;
    ClientHandler clientHandler;
    FServer serverFrame;
    PConsole console;
    
    String serverName;
    int serverPort;
    
    public void go()
    {
        String loggedUser,clientIP;
        console = new PConsole();        
        serverFrame = new FServer();
        /* agrego consola y configuro */
        console.setAlignmentX(0.5F);
        console.setAlignmentY(0.5F);
        console.setBounds(2, 0, 300, 418);
        serverFrame.add(console);
        serverFrame.revalidate();
        serverFrame.setResizable(false);
        serverFrame.setVisible(true);
        serverFrame.addWindowListener(new CloseListener());
        userList = new ArrayList();
        outputStreams = new ArrayList();
        handlerList = new ArrayList();         
        Message msg;         
        ObjectInputStream in;
        ObjectOutputStream out;         
        ServerSocket server;
        Socket client;         

        
        /* Lanzamiento de handler de manejo de informacion de GUI */
        new Thread(new GUIHandler()).start();
         
        /* Leo los datos del properties.txt */
        loadProperties(); 
         
       /* Cargo banlist */ 
        loadBans();        
        
        /* Informacion a la consola */
        try{
            server = new ServerSocket(serverPort);
            console.write("Server IP: " + java.net.InetAddress.getLocalHost().getHostAddress());
            console.write("Server port: " + server.getLocalPort());
            console.write("Server name: " + serverName);
            console.write("");  
            serverFrame.setTitle(serverName);
            
            /* Espera y manejo de clientes */
            while(true)
            {
                client = server.accept();
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
                clientIP = client.getInetAddress().toString();
                clientIP = clientIP.replace("/"," ");
                clientIP = clientIP.trim();
                console.write("IP: "+clientIP+" streams: OK.");
                /* Handshake */
                /* obtengo username */
                msg = (Message)in.readObject(); //
                /* Chequeo contra el ban list */ 
                if(bannedList.contains(clientIP))
                {
                    msg = new Message("banned","","server");
                    out.writeObject(msg);
                    client.close();
                }
                else 
                {
                    outputStreams.add(out); //guardo el outputsream del cliente
                    /* agrego a la lista de usuarios */
                    serverFrame.addUser(msg.getUser());
                    loggedUser = msg.getUser();
                    /* envio usuarios online (iterar o usar otro tipo de paquete?) */
                    Iterator it = userList.iterator();
                    msg.setUser(serverName);
                    while(it.hasNext())
                    {
                        msg.setMessage((String)it.next());
                        msg.setId("user");
                        out.writeObject(msg);
                        msg = (Message)in.readObject();     //Espero a que este listo a recibir el proximo
                    }    
                    /* envio señal "end" */
                    msg.setId("end");
                    out.writeObject(msg);
                    console.write("Usuarios enviados: OK");

                    /* lanzo client handler */
                    clientHandler = new ClientHandler(client, loggedUser,in, out);
                    handlerList.add(clientHandler);
                    new Thread(clientHandler).start();  //verificar que tan cabeza es hacer esto.   (nuevo thread con un ClientHandler adentro que recibe la data del user)
                    /* aviso a todos */
                    sayToAll(new Message("login","",loggedUser));
                    userList.add(loggedUser);
                    console.write("Handler: lanzado.");
                    console.write("");    
                }  
            }
         } 
         catch(Exception e) 
         { 
             System.err.println(e.getMessage());
         } 
    }
    
    
    
////////////////////////////////////////////////////////////////////////////////    
////////////////////////////////////////////////////////////////////////////////  
////////////////////////////////////////////////////////////////////////////////
    /* Methods */
    public void sayConsole(Message msg)
    {
        console.write(msg);       
    }
    
    public void removeUser(ObjectOutputStream writer)
    {
        outputStreams.remove(writer);
    }
    
    public void sayToAll(Message msg)
    {
        Iterator it = outputStreams.iterator();
        while(it.hasNext())
        {
            try
            {
                ObjectOutputStream writer = (ObjectOutputStream) it.next();
                writer.writeObject(msg);
            } 
            catch(Exception e) 
            {
                System.err.println(e.getMessage());
            }
        }         
    }
   
   
    public static void main(String args[])
    {
      new ChatServer().go();
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
    
    private void loadBans()
    {
        File banList;
        BufferedReader fileIn;
        String bannedIP;
        int lineNum = 1;
        try{
            console.write("\nOpening banlist.txt");
            bannedList = new TreeSet();
            banList = new File("banlist.txt");
            if(!banList.exists())
                banList.createNewFile();  
            else{
                fileIn = new BufferedReader(new FileReader(banList));
                //Cargo y valido IPs
                bannedIP = fileIn.readLine();
                while(bannedIP != null)
                {
                    if(!checkIp(bannedIP))
                        console.write("ERROR: couldn't read line " + lineNum);
                    else
                    {
                        bannedList.add(bannedIP);
                        console.write("(banned) "+bannedIP);
                    }


                    lineNum++;
                    bannedIP = fileIn.readLine();
                }
                fileIn.close();
            } 
        } 
        catch(Exception ex) 
        { 
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    private void loadProperties()
    {
        File serverProp;
        BufferedReader fileIn;    
        BufferedWriter fileOut;
        String[] line;
        
        try{
            serverProp = new File("properties.txt");
            if(!serverProp.exists())
            {
                serverProp.createNewFile();
                fileOut = new BufferedWriter(new FileWriter(serverProp));
                fileOut.write("name=Default");
                fileOut.newLine();
                fileOut.write("port=5000");
                fileOut.close();
            }
            
            fileIn = new BufferedReader(new FileReader(serverProp));
            line = fileIn.readLine().split("=");
            serverName = line[1];
            line = fileIn.readLine().split("=");
            serverPort = Integer.valueOf(line[1]);
            fileIn.close();
        } 
        catch(Exception ex) 
        { 
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
////////////////////////////////////////////////////////////////////////////////    
////////////////////////////////////////////////////////////////////////////////   
////////////////////////////////////////////////////////////////////////////////    
    /* Handlers, Sub-classes */
    public class ClientHandler implements Runnable{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket client;
    private String IP;
    private Message msg;
    private String user;

    public ClientHandler(Socket client, String user, ObjectInputStream in, ObjectOutputStream out)
    {
        this.client = client;
        this.in = in;
        this.out = out;
        this.user = user;
        
        IP = client.getInetAddress().toString();
        IP = IP.replace("/"," ");
        IP = IP.trim();
    }
    
    @Override
    public void run()
    {
        try{
            client.setSoTimeout(10000);
            while(client.isConnected())
            {
              msg = (Message)in.readObject();
              readMessage(msg);
            }
        }
        catch (SocketTimeoutException e)
        {
            msg = new Message("logout","",user);
            removeUser(out);
            serverFrame.removeUser(user);
            userList.remove(user);
            console.write(user + " disconnected.");
            msg.setMessage("disconnected.");
            serverFrame.writeMessage(msg);
            sayToAll(msg);
            try{
            client.close();
            } catch (IOException ioe) { ioe.printStackTrace(System.err); }
        }
        
        catch (Exception e) 
        { 
            System.err.println(e.getMessage());
            removeUser(out);
            serverFrame.removeUser(msg.getUser());
        }
    }
    
    private void readMessage(Message msg)
    {
        try{
        if(msg.getId().equals("say"))
        {
            sayToAll(msg);
            serverFrame.writeMessage(msg);
        }   
              
        if(msg.getId().equals("logout"))
        {
            removeUser(out);
            serverFrame.removeUser(msg.getUser());
            userList.remove(msg.getUser());
            console.write(msg.getUser() + " logged out.");
            msg.setMessage("logged out.");
            serverFrame.writeMessage(msg);
            sayToAll(msg);                  
            client.close();
         }
             
         if(msg.getId().equals("kicked"))
         {
            sayToAll(msg);
            removeUser(out);
            serverFrame.removeUser(msg.getMessage());
            userList.remove(msg.getMessage());
            console.write(msg.getMessage() + " was kicked by " + msg.getUser() + ".");
            serverFrame.writeMessage(msg);                  
            client.close();    
         }
         
         if(msg.getId().equals("banned"))
         {
            sayToAll(msg);
            removeUser(out);
            serverFrame.removeUser(msg.getMessage());
            userList.remove(msg.getMessage());
            console.write(msg.getMessage() + " was banned by " + msg.getUser() + ".");
            serverFrame.writeMessage(msg);                  
            client.close();    
         }
        } catch (IOException se) {
            se.printStackTrace(System.err);
        }
    }
    
    public String getUser()
    {
        return user;
    }
    
    public String getIP()
    {
        return IP;
    }
    
    public void close(Message msg)
    {
        readMessage(msg);
    }
  }    
    
    /* Conexión con FServer */
    public class GUIHandler implements Runnable{

        @Override
        public void run() {
            Message internalMsg;
            while(true)
            {
                synchronized(this){
                /* internalMsg corresponde a informacion de eventos activados desde la GUI */
                internalMsg = serverFrame.getInternalMessage();
                try {
                    wait(250);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                if(!internalMsg.isEmpty())
                {
                    if(internalMsg.getId().equals("kicked"))
                    {
                        //Busqueda del handler del usuario
                        Iterator it = handlerList.iterator();
                        ClientHandler ch;
                        while(it.hasNext())
                        {
                            ch = (ClientHandler)it.next();
                            if(ch.getUser().equals(internalMsg.getMessage()))
                                //cierre de conexion
                                ch.close(internalMsg);
                        }
                    }
                    
                    if(internalMsg.getId().equals("banned"))
                    {
                        //Busqueda del handler del usuario
                        Iterator it = handlerList.iterator();
                        ClientHandler ch;
                        while(it.hasNext())
                        {
                            ch = (ClientHandler)it.next();
                            if(ch.getUser().equals(internalMsg.getMessage()))
                            {   
                                //agrego a lista
                                bannedList.add(ch.getIP());
                                //cierre de conexion
                                ch.close(internalMsg);
                            }
                        }                        
                    }
                    internalMsg.setId("empty");
                }
            }
        }
        
    }
    
    public class CloseListener implements WindowListener {

        @Override
        public void windowClosing(WindowEvent e) {
            File file = new File("banlist.txt");
            try {
                BufferedWriter fileOut = new BufferedWriter(new FileWriter(file));
                Iterator it = bannedList.iterator();
                while(it.hasNext())
                {
                    fileOut.write((String)it.next());
                }
                fileOut.close();
            } catch (Exception ex) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
        
        public void windowOpened(WindowEvent e) {

        }
        public void windowClosed(WindowEvent e) {

        }
        public void windowIconified(WindowEvent e) {

        }
        public void windowDeiconified(WindowEvent e) {

        }
        public void windowActivated(WindowEvent e) {

        }
        public void windowDeactivated(WindowEvent e) {

        }
        
    }
    
    
}
