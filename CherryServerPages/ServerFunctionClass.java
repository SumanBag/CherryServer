/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CherryServerPages;

import java.io.ByteArrayOutputStream;
import java.sql.Driver;

/**
 *
 * @author SUMAN
 */
abstract class ServerFunctionClass implements HTTPServerInterface {
    
    private ByteArrayOutputStream buff;
    private ServerClass Request;
    private DBClass sql;
    private Driver d;
    
    
    /*************************************************************************/
    
    final public void init()
    {
        Request=new ServerClass();
    }
    
    final public void init_HEAD(String[][] s)
    {
        
        Request.initHEAD(s);
    }
    
    final public void init_GET(String[][] s)
    {
        Request.initGET(s);
    }
    
    final public void init_POST(String[][] s)
    {
        Request.initPOST(s);
    }
    
    final public void init_SQL(Driver c)
    {
        d=c;
    }
    
    final public void init_BODY()
    {
        initBuff();
        body();
    }
    
    final public byte[] get_BODY()
    {
        return buff.toByteArray();
    }
    
    /*************************************************************************/ 
   
    
    
    abstract public void body();
   
    final private void initBuff()
    {
       buff=new ByteArrayOutputStream(); 
    } 
    

    final public void writeOnBody(String str)
    {
        try{
        buff.write(str.getBytes());
        }catch(Exception e)
        {
            System.err.println(e);
        }
    }
    
   
   final public String GET(String str)
   {  
       return Request.val_GET(str);
   }
   
   
   final public String POST(String str)
   {
       return Request.val_POST(str);
   }
   
   
   final public boolean issetGET()
   {
       return Request.isGET();
   }
   
   
   final public boolean issetPOST()
   {
       return Request.isPOST();
   }
  
   
   final public boolean mysql(String url,String uname,String pass,String db)
   {
       
       sql=new DBClass(d,url,uname,pass,db);
       return sql.connect();
   }
   
   final public boolean mysql_query(String query)
   {
       sql.stmt();
       return sql.query(query);
   }
   
   final public int mysql_num_rows()
   {
       return sql.numRows();
   }
   
   final public boolean mysql_next_result()
   {
       return sql.next();
   }
   
   final public String mysql_result(String col)
   {
       return sql.result(col);
   }
   
   final public String mysql_result(int row,String col)
   {
       return sql.result(row, col);
   }
  
   final public void mysql_close()
   {
       sql.close();
   }
    
    
}
