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
import java.io.ByteArrayOutputStream;

class HTTPResponse {
    
    
    private ByteArrayOutputStream repB;
   
    HTTPResponse()
    {
        repB=new ByteArrayOutputStream();
    
    }
    
    public void setHeader(String str)throws Exception
    {
        //repH.append(str);
        repB.write(str.getBytes());
    }
    
    public void setContentType(String str)throws Exception
    {
        
        if(str.contains(".jpg"))
            setHeader("Content-Type: image/jpeg \r\n");
        else if(str.contains(".png"))
            setHeader("Content-Type: image/png \r\n");
        else if(str.contains(".ico"))
            setHeader("Content-Type: image/x-icon \r\n");
        else if(str.contains(".css"))
            setHeader("Content-Type: text/css \r\n");
        else if(str.contains(".js"))
            setHeader("Content-Type: application/javascript \r\n");
        else
            setHeader("Content-Type: text/html \r\n");
            
    }
    
    public void setResponse(byte[] a)throws Exception
    {
       repB.write(a);       
    }
    
    
    public byte[] Respond()
    {
        return repB.toByteArray();
    }
    
    
    
    public byte[] responseTest(byte[] a)
    {   
        try{
            //setFile();
            setHeader("HTTP/1.1 200 \r\n");
            setHeader("MIME-Version: 1.0 \r\n");
            setHeader("Server: CherryHTTPServer 1.0 \r\n");
            setHeader("Connection: keep-alive \r\n");
            setHeader("Keep-Alive: timeout=5, max=100 \r\n");
            setHeader("Accept-Ranges: bytes \r\n");
            setHeader("Content-Length: "+a.length+" \r\n");
            //setHeader("Content-Length: 1150 \r\n");
            setContentType("");
            repB.write(a);
           
        }catch(Exception e)
        {
            e.printStackTrace();
        }
            return repB.toByteArray();
    }
}
