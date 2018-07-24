/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cherryserver;

/**
 *
 * @author SUMAN
 */

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class CherryServer implements Runnable {

    /**
     * @param args the command line arguments
     */ 
    
    private ServerSocket srvr;
    private Socket sc;
    private Scanner kb;
    private String kbimp;
    private String[] cmd;
    private Thread T;
    private boolean EXIT;
    private ServerHandler SH;
    private Process mysql,ftp;
    private String basedir;
    private userServer usv;
    
    CherryServer(String s)
    {
      basedir=s;
      EXIT=false;
      kb=new Scanner(System.in);
      T=new Thread(this);
      T.setPriority(10);
      //T.start();
      
    }
    
    public void init()
    {
        
          T.start();
          usv=new userServer(basedir,"/users");
          usv.init();
          startConsole();
          ;
    }
    
    public void run()
    {
        while(!EXIT)
        {
           try{
            if(!srvr.isClosed())
            {
                sc=srvr.accept();
                SH=new ServerHandler(sc,basedir,"/publicHTML");   
            }
            Thread.sleep(100);
           }catch(Exception e)
           {
                   
           }    
        }
    }
  
    private void startHTTP()
    {
        try{
            srvr=new ServerSocket(80);
            System.out.println("[$] HTTP running..");
            usv.start();
       }catch(Exception e)
       {
           e.printStackTrace();
           System.out.print("\n[$] ");
       }
      
    }
    
    private void stopHTTP()
    {
        try{
            srvr.close();
            System.out.println("[$] HTTP stopped..");
            usv.stop();
       }catch(Exception e)
       {
           e.printStackTrace();
           System.out.print("\n[$] ");
       }
       
    }
    
    private void startSQL()
    {      
           try{
           
           String cmd=basedir+"/mysql/scripts/mysql_start.bat "+basedir;
           mysql=Runtime.getRuntime().exec(cmd);
           System.out.println("[$] MySQL running..");
           }catch(Exception e)
           {
               e.printStackTrace();
               System.out.print("\n[$] ");
           }
           
    }
    
    private void stopSQL()
    {
        try{
           
           String cmd=basedir+"/mysql/scripts/mysql_stop.bat "+basedir;
           mysql=Runtime.getRuntime().exec(cmd);
           System.out.println("[$] MySQL stopped..");
           }catch(Exception e)
           {
               e.printStackTrace();
               System.out.print("\n[$] ");
           }
           
    }
    
    private void startFTP()
    {
        try{
           
           String cmd=basedir+"/filezilla/scripts/filezilla_start.bat "+basedir+"/filezilla";
           ftp=Runtime.getRuntime().exec(cmd);
           System.out.println("[$] FTP running..");
           }catch(Exception e)
           {
               e.printStackTrace();
               System.out.print("\n[$] ");
           }
           
    }
    
    private void stopFTP()
    {
        try{
           
           String cmd=basedir+"/filezilla/scripts/filezilla_stop.bat "+basedir+"/filezilla";
           ftp=Runtime.getRuntime().exec(cmd);
           System.out.println("[$] FTP stopped..");
           }catch(Exception e)
           {
               e.printStackTrace();
               System.out.print("\n[$] ");
           }
           
        
    }
    /*
    private void adminFTP()
    {
         
    }
    */
    
    private void heading()
    {
        System.out.println("[:] CHERRY SERVER v1.0");
        System.out.println(" *  Copyright (c) SUMAN");
        System.out.println(" *  Developer: SUMAN KUMAR BAG");
        System.out.println(" *  Java based HTTP Server Program.");
        System.out.println(" *  Running MySQL and Filezilla ftp.");
        System.out.println("[:]");
    }
    
    private void commanderr()
    {
        System.out.println("[:] HELP");
        System.out.println("    Services.");
        System.out.println("        http : Service HTTP.");
        System.out.println("        sql  : Service SQL.");
        System.out.println("        ftp  : Service FTP.");
        
    }
    public void startConsole()
    {
        heading();
        while(true)
        {   
            System.out.print("[$] ");
            kbimp=kb.nextLine();
            kbimp=kbimp.toLowerCase();
            cmd=kbimp.split(" ");
            if(cmd[0].trim().equals("start"))
            {
                //startServer();
                try{
                if(cmd[1].trim().equals("http"))
                {
                    startHTTP();
                }
                else if(cmd[1].trim().equals("sql"))
                {
                    startSQL();
                }
                else if(cmd[1].trim().equals("ftp"))
                {
                    startFTP();
                }
                else
                {
                    
                    
                }
                }catch(Exception e)
                {
                    commanderr();
                }
            }
            else if(cmd[0].equals("stop"))
            {
      
                try{
                if(cmd[1].trim().equals("http"))
                {
                    stopHTTP();
                }
                else if(cmd[1].trim().equals("sql"))
                {
                    stopSQL();
                    
                }
                else if(cmd[1].trim().equals("ftp"))
                {
                    stopFTP();
                }
                else
                {
                    commanderr();
                    
                }
                }catch(Exception e)
                {
                    commanderr();
                }
            }
            else if(cmd[0].equals("admin"))
            {
                System.out.print("\n[$] ");
            }
            else if(cmd[0].equals("exit"))
            {
                
                EXIT=true;
                stopHTTP();
                stopSQL();
                stopFTP();
                kb.close();
                break;
            }
            else
            {
                System.out.println("[:] HELP");
                System.out.println("    Commands.");
                System.out.println("       start : Start Server Service.");
                System.out.println("       stop  : Stop Server Service.");
                System.out.println("       admin : Administrative tool.");
                System.out.println("       exit  : Exit program.");
                
                        
            }
            
            System.gc();
        }
        
        System.gc();
    }   
    public static void main(String[] args) {
        // TODO code application logic here
        try{
        if(args[0].startsWith("--basedir="))
        {
            String[] a=args[0].split("=");
            
            if(a[1].contains("\\"))
               a[0]=a[1].replace("\\", "/");
            
            //System.out.println(a[0]);
            new CherryServer(a[0]).init();
            
        }
        
        }catch(Exception e)
        {
            //e.printStackTrace();
            System.out.println("Error!");
        }
    }
 
    
}
