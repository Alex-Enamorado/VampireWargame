/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package castlevania;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author aluk
 */
public class MyFrame extends JFrame{
    
    public MyFrame(){
                //JFrame = una ventana GUI
       
        this.setTitle("VAMPIRE WARGAME");  //Pone el titulo del frame  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Termina el programa cuando se le da a la X
        this.setResizable(false);       //Hace que no se pueda modificar el tamaño de la ventana
        this.setSize(1600 , 900);         // Le da tamaño a la frame
        ImageIcon image =new ImageIcon(Main.class.getResource("/resources/logo.png"));
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(39, 34, 40));
        this.setLocationRelativeTo(null);
        
       
        
        
        
        
    }
    
    
    
}
