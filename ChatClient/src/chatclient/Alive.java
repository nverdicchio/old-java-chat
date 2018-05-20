/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import common.*;
import java.io.*;

/**
 *
 * @author Nico
 */
public class Alive  implements Runnable {
    String localUser;
    ObjectOutputStream out;
    
    public Alive(String user, ObjectOutputStream output)
    {
        out = output;
        localUser = user;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            synchronized (this)
            {
                Message msg = new Message("alive","",localUser);
                try
                {
                    out.writeObject(msg); 
                    wait(500);
                }
                catch (IOException e)
                {
                    e.printStackTrace(System.err);
                } 
                catch (Exception ex)
                {
                    ex.printStackTrace(System.err);
                }    
            }

        }
     
    }
    
}
