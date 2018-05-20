/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.*;

/**
 *
 * @author Nico
 */
public class Message implements Serializable {
    private String id, message, user;
    
    /* Constructor */
    public Message(String id, String msg, String user)
    {
        this.id = id;           //Constantes de ID deberian estar en un ENUM?
        message = msg;
        this.user = user;
    }
    
    /* Methods */
    public String getId()
    {
        return id;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public String getUser()
    {
        return user;
    }
    
    public void setId(String s)
    {
        id = s;
    }
    
    public void setMessage(String s)
    {
        message = s;
    }
    
    public void setUser(String s)
    {
        user = s;
    }
    
    public boolean isEmpty()
    {
        if(id.equals("empty"))
            return true;
        return false;
    }
}
