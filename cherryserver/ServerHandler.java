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

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import com.mysql.jdbc.Driver;
import CherryServerPages.HTTPServerInterface;

class ServerHandler implements Runnable{

     private Socket sc;
     private InputStream in;
     private OutputStream out;
     private File file;
     private FileInputStream fin;
     private Thread t;
     private HTTPRequest reqobj;
     private HTTPResponse repobj;
     private byte[] output;
     private String root,site;
     private Date date;
     private ByteArrayOutputStream Fbuff,buff;
     
     public ServerHandler(Socket a, String r, String s)
     {
         try{
             sc=a;
             in=sc.getInputStream();
             out=sc.getOutputStream();
             reqobj=null;
             repobj=null;
             root=r;
             site=s;
             date=new Date();
             buff=new ByteArrayOutputStream();
             Fbuff=new ByteArrayOutputStream();
             t=new Thread(this);
             t.setPriority(10);
             t.start();
         }catch(Exception e)
         {
            e.printStackTrace();
         }
     }
     
     private String sanitizepath(String s)
     {
         String path="";
         for(String a:s.split("\\.\\./"))
              path+=a;
         
         return path;
                 
     }
     
     private void getRequest()throws Exception
     {   
         //StringBuffer str=new StringBuffer();
         int a;
        
         while(in.available()>0)
         {
             //str.append((char)in.read());
             buff.write(in.read());
         }
         //System.out.println("Size: "+str.length());
         System.out.println("\n\nRequest Payload: \n"+buff.toString("UTF-8"));
         
         //if(str.length()>0)
         if(buff.size()>0)
         {
             reqobj=new HTTPRequest(buff);
             
             repobj=new HTTPResponse();
         }
        
     }
     
     private void error404()
     {  try{
         
         byte[] a="<!DOCTYPE HTML><html><head><title>404 Not Found</title></head><body><center><h1>404 Not Found</h1></center></body></html>".getBytes();
         
         repobj.setHeader("HTTP/1.1 404 \r\n");
         repobj.setHeader("MIME-Version: 1.0 \r\n");
         repobj.setHeader("Server: CherryServer v1.0 \r\n");
         repobj.setHeader("Date: "+date.toString()+" \r\n");
         repobj.setHeader("Connection: keep-alive \r\n");
         repobj.setHeader("Keep-Alive: timeout=5, max=100 \r\n");
         repobj.setHeader("Accept-Ranges: bytes \r\n");
         repobj.setHeader("Content-Length: "+a.length+" \r\n");
         repobj.setHeader("Content-Type: text/html \r\n\r\n");
         repobj.setResponse(a);
         output=repobj.Respond();
         
       }catch(Exception e)
       {
           e.printStackTrace();
       }
     }
     
     private void readFile()throws Exception
     {
         fin=new FileInputStream(file);
         while(fin.available()>0)
         {
            Fbuff.write(fin.read());
         }
         fin.close();
         
     }
     
     private boolean checkIndex(String s)
     {
             File check=new File(root+site+"/index."+s);
             return check.exists();       
     }
     
     private void process()
     {     
           String classfile;
           String dir;
           String path;
           String[] f;
           if(reqobj!=null&&repobj!=null)
           {
               path=sanitizepath(reqobj.getFile().toLowerCase());
               f=path.split("/");
               if(f.length==0)
               {   
                   dir="/";
                   if(checkIndex("class"))
                   {
                       classfile="index.csp";    
                       
                   }
                   else if(checkIndex("html"))
                   {
                        classfile="index.html";
                   }
                   else
                   {
                       classfile="xyz.xyz"; 
                   }
                   
               }
               else{
               
                   classfile=f[f.length-1];
                   dir="";
                   for(int i=0;i<(f.length-1);i++)
                        dir+="\\"+f[i];
                   
               }
               
           
               if(classfile.contains(".csp"))
               {
                    try{
                    
                          HTTPServerInterface o;
                          file=new File(root+site+dir);
                          URL[] url={file.toURI().toURL()};
                          URLClassLoader ch=new URLClassLoader(url,this.getClass().getClassLoader());
                          Class c=Class.forName(classfile.split("\\.")[0], true, ch);
                          o=(HTTPServerInterface)c.newInstance();
                          /*
                          o.init(reqobj);
                          o.body();
                          byte[] a=o.getbuff();
                          */
                          o.init();
                          o.init_HEAD(reqobj.getHEAD());
                          o.init_GET(reqobj.getGET());
                          o.init_POST(reqobj.getPOST());
                          o.init_SQL(initDriver());
                          o.init_BODY();
                          byte[] a=o.get_BODY();
                          repobj.setHeader("HTTP/1.1 200 \r\n");
                          repobj.setHeader("MIME-Version: 1.0 \r\n");
                          repobj.setHeader("Server: CherryServer v1.0 \r\n");
                          repobj.setHeader("Date: "+date.toString()+" \r\n");
                          repobj.setHeader("Connection: keep-alive \r\n");
                          repobj.setHeader("Keep-Alive: timeout=5, max=100 \r\n");
                          repobj.setHeader("Accept-Ranges: bytes \r\n");
                          repobj.setHeader("Content-Length: "+a.length+" \r\n");
                          repobj.setContentType("Cherry");
                          repobj.setHeader("\r\n");
                          repobj.setResponse(a);
                          output=repobj.Respond();
                      
                     // Method m=c.getDeclaredMethod("show");
                     // m.invoke(o);
       
        
                   }catch(Exception e)
                   {
                          e.printStackTrace();
                          error404();
                   }
           }
           else if(classfile.contains(".class"))
           {
               
               
               error404();
              
           }
           else
           {
               
               try{
                  
                  file=new File(root+site+dir+"\\"+classfile);
                  readFile();
                  byte[] a=Fbuff.toByteArray();
                  
                  repobj.setHeader("HTTP/1.1 200 \r\n");
                  repobj.setHeader("MIME-Version: 1.0 \r\n");
                  repobj.setHeader("Server: CherryServer v1.0 \r\n");
                  repobj.setHeader("Date: "+date.toString()+" \r\n");
                  repobj.setHeader("Connection: keep-alive \r\n");
                  repobj.setHeader("Keep-Alive: timeout=5, max=100 \r\n");
                  repobj.setHeader("Accept-Ranges: bytes \r\n");
                  repobj.setHeader("Content-Length: "+a.length+" \r\n");
                  repobj.setContentType(classfile);
                  repobj.setHeader("\r\n");
                  repobj.setResponse(a);
                  output=repobj.Respond();
                  
               }catch(Exception e)
               {
                   e.printStackTrace();
                   error404();
               }
               
               
           }
           
        }
     }
    
    
     private void sendResponse()throws Exception
     {
       /* String data="<html><head><title>Test</title></head><body><h1>HELLO WORLD</h1><br>"
                   +"<p>Name: "+reqobj.val_POST("name")+"<br>"
                   +"Pass: "+reqobj.val_POST("pass")+"<br>"
                   +"File: "+reqobj.val_POST("file")+"</p></body></html>"; */
        
        
        
            //repobj.loadData("C:/Users/SUMAN/Documents/NetBeansProjects/CherryServer/publicHTML");
            if(reqobj!=null&&repobj!=null)
            {
                //out.write(repobj.responseTest());
                out.write(output);
                //System.out.println("Payload:\n");
                //for(byte b: repobj.responseTest())
                    //System.out.print((char)b);
                //System.out.println("Response Send");
            }
            
            
            //sc.shutdownOutput();
            out.close();
     }
     
     private Driver initDriver()
     {
         Driver d=null;
         try{
             d=new Driver();
         }catch(Exception e)
         {
             e.printStackTrace();
         }
         
         return d;
     }

    public void run()
    {
      try{
          getRequest();
          process();
          sendResponse();
          in.close();
          sc.close();
      }catch(Exception e)
      {
          e.printStackTrace();
      }
    }
    
}
