/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package castlevania;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author aluk
 */
public class GamePanel extends JPanel implements Runnable {

    public static final int width = 1600;
    public static final int height = 900;
    final int FPS = 60;
    Thread gameThread;

    public GamePanel() {

    }

    public void launchGame(){
        gameThread =new Thread(this);
        gameThread.start();
        
    }
    
    
    @Override
    public void run() {
        //loop
        double drawInterval= 1000000000/FPS;
        double delta = 0;
        long lastTime= System.nanoTime();
        long currentTime;
        
        
        while(gameThread != null){
            currentTime= System.nanoTime();
        }
    }

    
    
    
    private void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
