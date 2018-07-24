package CherryServerPages;

import java.sql.Driver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SUMAN
 */
public interface HTTPServerInterface {
    
    
    void init();
      
    void init_HEAD(String[][] s);
    
    void init_GET(String[][] s);
    
    void init_POST(String[][] s);

    void init_SQL(Driver c);
    
    void init_BODY();
    
    byte[] get_BODY();
    
    
}
