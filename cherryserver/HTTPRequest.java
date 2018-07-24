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


import java.net.URLDecoder;
import java.io.ByteArrayOutputStream;

class HTTPRequest {
    
    private String request;
    private String[] reqH;
    private String[][] header;
    private ByteArrayOutputStream buff;
    
    HTTPRequest(ByteArrayOutputStream a)
    {
        //System.out.println(a);
        buff=a;
        request=new String(buff.toByteArray());
        reqH=request.split("\r\n");
    }
    public String getRAW()
    {
        return request;
    }
            
    
    public String[][] getPOST()
    {
        String[][] a=null;
        String[] cache,b;
        int i;
        if(reqH[0].contains("POST"))
        {
            if(isMULTIPART())
            {
               /*String boundary=getHeader("Content-Type").split(";")[1].split("=")[1];
               String [] a=request.split(boundary);*/
               System.out.println("Multipart");
               
            }
            else
            {
                cache=reqH[reqH.length-1].split("&");
                i=cache.length;
                a=new String[i][2];
                for (int j=0;j<i;j++) 
                {
   
                  b=cache[j].split("=");
                  a[j][0]=b[0];
                  try{
                       a[j][1]=URLDecoder.decode(b[1],"UTF-8");
                  }catch(Exception e)
                  {
                    e.printStackTrace();
                    a[j][1]=null;
                 }
        
               }
            }
            
        }
        /*
        System.out.println("**************************** POST ************************");
        for(String[] m:a)
        {
            for(String n: m)
             System.out.print(n+"  ");
            System.out.print("\n");
        }
        System.out.println("********************************************************");
        */
        return a;
    }
    
    public String[][] getGET()
    {
        String[][] a=null;
        String[] cache,b;
        
        int i;
        if(reqH[0].contains("GET")&&reqH[0].contains("?"))
        {
            cache=reqH[0].split("?")[1].split(" ")[0].split("&");
            i=cache.length;
            a=new String[i][2];
           
            for(int j=0;j<i;j++)
            {
                b=cache[j].split("=");
                a[j][0]=b[0];
                try{
                a[j][1]=URLDecoder.decode(b[1],"UTF-8");
                }catch(Exception e)
                {
                    e.printStackTrace();
                    a[j][1]=null;
                }
                
            }           
        }
        /*
        System.out.println("**************************** GET ************************");
        for(String[] m:a)
        {
            for(String n: m)
             System.out.print(n+"  ");
            System.out.print("\n");
        }
        System.out.println("********************************************************");
        */
        return a;
    }
    
    public String[][] getHEAD()
    {
        
        header=null;
        String[] cache,b;
        int i;
        String head=request.split("\r\n\r\n")[0];

          cache=head.split("\r\n");
            i=cache.length;
            header=new String[i][2];
           
            for(int j=1;j<i;j++)
            {
                b=cache[j].split(": ");
                header[j][0]=b[0];
                header[j][1]=b[1];
                
            } 
            System.out.println("**************************** HEAD ************************");
        for(String[] m:header)
        {
            for(String n: m)
             System.out.print(n+"  ");
            System.out.print("\n");
        }
        System.out.println("********************************************************");
        return header;
    }
  
    public String getFile()
    {
        String a;
        try{
        if(reqH[0].indexOf('?')!=-1)
          a=reqH[0].split(" ")[1].split("?")[0];
        else
          a=reqH[0].split(" ")[1]; 
        
      }catch(Exception e)
      {
          //System.out.println("Header: "+reqH[0]);
          a="/";
      }
        return a;
    }
    
    public String getHeader(String str)
    {
        String a=null;
        for(int i=1;i<(reqH.length -1);i++)
        {
              if(reqH[i].indexOf(str)!=-1)
              {
                  a=reqH[i].split(": ")[1];
                  break;            
              }
        }
       
        return a;    
    }
   
    private boolean isMULTIPART()
    {
        boolean b=false;
        
        for(String a:reqH)
        {
            if(a.contains("Content-Type: multipart/form-data;"))
            {
                b=true;
                break;
            }
        }
        
        return b;
    }
    
    
    
    
}
