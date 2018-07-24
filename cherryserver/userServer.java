/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cherryserver;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author SUMAN
 */
class userServer implements Runnable{
    
    private Thread T;
    private boolean EXIT;
    private ServerSocket srvr;
    private Socket sc;
    private ServerHandler svh;
    private String root,site;
    
    userServer(String r, String s)
    {
        root=r;
        site=s;
        
        EXIT=false;
        T=new Thread(this);
        T.setPriority(10);
    }
    
    public void start()
    {
        System.out.println("[$] HTTP (8008) running..");
        T.start();
        
    }
    
    public void stop()
    {
        EXIT=true;
    }
    
    public void init()
    {
        
         try{
            srvr=new ServerSocket(8008);
       }catch(Exception e)
       {
           e.printStackTrace();
           System.out.print("\n[$] HTTP (8008) Error!\n[$] ");
       }
         
    }
    
    public void run()
    {
        while(!EXIT)
        {
           try{
            if(!srvr.isClosed())
            {
                sc=srvr.accept();
                svh=new ServerHandler(sc,root,site);   
            }
            Thread.sleep(100);
           }catch(Exception e)
           {
                   e.printStackTrace();
           }    
        }
    }
    
}
