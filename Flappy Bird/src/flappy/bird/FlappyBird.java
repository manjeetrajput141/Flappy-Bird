
package flappy.bird;

import javax.swing.*;



public class FlappyBird {

   
    public static void main(String[] args) {
        
        JFrame frame=new JFrame("Flappy Bird Game");
        
        frame.setSize(360, 640);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlappyBirdPanel flappyBirdPanel=new FlappyBirdPanel();
        flappyBirdPanel.requestFocus();
        frame.add(flappyBirdPanel);
        frame.pack();
        
        frame.setVisible(true);
        
        
    }
    
}
