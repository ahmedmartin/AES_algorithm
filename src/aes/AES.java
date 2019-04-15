/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

/**
 *
 * @author Ahmed_Martin
 */
public class AES {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        AES_algorithm aes = new AES_algorithm();
        System.out.println("cipher text is : "+aes.encrypt("54776F204F6E65204E696E652054776F"));       
    }
    
    
    


    
    
}
