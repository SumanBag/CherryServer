/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CherryServerPages;


/**
 *
 * @author SUMAN
 */
class ServerClass {
    
    private String[][] GET;
    private String[][] POST;
    private String[][] HEAD;
    
    public void initGET(String[][] a)
    {
          GET=a;   
    }
    
    public void initPOST(String[][] a)
    {
          POST=a;
    }
    
    public void initHEAD(String[][] a)
    {
         HEAD=a;
    }
    
    public boolean isPOST()
    {
        return POST!=null;
    }
    
    public boolean isGET()
    {
        return GET!=null;
    }
      
    public String val_POST(String str)
    {
       String a=null;
       
       if(isPOST())
       {
        for(String[] b:POST)
        {
              if(b[0].equals(str))
              {
                  a=b[1];
                  break;            
              }
        }
       }
       else
        {
            System.out.println("Post NULL");
        }
        return a;
    }
    
    public String val_GET(String str)
    {
       String a=null;
       if(isGET())
       {
        for(String[] b:GET)
        {
              if(b[0].equals(str))
              {
                  a=b[1];
                  break;            
              }
        }
       }
       else
        {
            System.out.println("Get NULL");
        }
        return a; 
    }
   
    
    
}
