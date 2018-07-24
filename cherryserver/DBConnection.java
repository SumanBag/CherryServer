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
import java.sql.*;
import com.mysql.jdbc.Driver;
class DBConnection {
    
    
    private String url,uname,pass,db;
    private Driver D;
    private Connection con;
    private Statement stmt;
    private PreparedStatement prestmt;
    private String query;
    private ResultSet rs;
    //ResultSetMetaData rsmd;
    private int num;
    
    DBConnection(String a,String b,String c,String d)
    {
        db=d;
        url="jdbc:mysql://"+a+":3306/"+db;
        //url="jdbc:mysql://"+a+":3306/"+db+"?autoReconnect=true&useSSL=false";
        //url="jdbc:mysql://"+a+":3306/"+db+"?verifyServerCertificate=false&useSSL=true";
        uname=b;
        pass=c;
        num=0;
    }
    
    public boolean connect()
    {   
        boolean k;
        try{
            D=new Driver();
            DriverManager.registerDriver(D);
            con=DriverManager.getConnection(url,uname,pass);
            k=true;
        }catch(Exception e)
        {
            e.printStackTrace();
            k=false;
        }
        
        return k;
    }
    public void stmt()
    {    
        try{
            stmt=con.createStatement();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
  
    
    public boolean query(String query)
    {
          boolean k;
       try{
           
           if(stmt.execute(query))
           {
               rs=stmt.getResultSet();
               rs.last();
               num=rs.getRow();
               rs.beforeFirst();
           }
           else
           {
               num=stmt.getUpdateCount();
           }
           k=true;
       }catch(SQLException e)
       {
           e.printStackTrace();
           k=false;
       }
       return k;
    }
    
    public int numRows()
    {
        return num;
    }
    
    public void close()
    {
        try{
            stmt.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public String result(String col)
    {   
        String s;
        try{
            s=rs.getString(col);
        }catch(SQLException e)
        {
            System.err.println(e);
            s="";
        }
        return s;
    }
    
    public String result(int row,String col)
    {
        String s;
        try{
            rs.beforeFirst();
        if(rs.isBeforeFirst())
        {
            if((row<1)&&(row>num))
            {
                s="";
            }
            else{
            rs.absolute(row);
            s=rs.getString(col);
            }
        }
        else
            s="";
        }catch(SQLException e)
        {
            System.err.println(e);
            s="";
        }
        return s;
    }
    
    public boolean next()
    {   
        boolean n;
        try{
            n=rs.next();
            if(rs.isAfterLast())
                rs.beforeFirst();
                
        }catch(SQLException e)
        {
            e.printStackTrace();
            n=false;
        }
        return n;
    }
    
}
