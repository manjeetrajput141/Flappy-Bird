package flappy.bird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class FlappyBirdPanel extends JPanel implements ActionListener, KeyListener {

    int boardHeight = 640;
    int boardWidth = 360;

    //Images
    Image birdImg;
    Image backgroundImg;
    Image topPipeImg;
    Image bottomPipeImg;
    double score = 0;

    //Bird
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdHeight = 24;
    int birdWidth = 34;

    class Bird {

        int x = birdX;
        int y = birdY;
        int height = birdHeight;
        int width = birdWidth;
        Image img;

        public Bird(Image img) {
            this.img = img;

        }
    }
    Bird bird;

    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeHeight = 512;
    int pipewidth = 64;

    class Pipe {

        int x = pipeX;
        int y = pipeY;
        int height = pipeHeight;
        int width = pipewidth;
        boolean passed = false;
        Image img;

        public Pipe(Image img) {
            this.img = img;

        }

    }
    ArrayList<Pipe> pipes;

    //GameLoop Timer
    Timer gameLoop;

    Timer placedPipeTimer;

    //game logic
    int velocityY = -9;
    int gravity = 1;
    int pipeVelocity = -4;

    boolean gameOver = false;

    public FlappyBirdPanel() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        addKeyListener(this);
        setFocusable(true);
        //laod the images
        birdImg = new ImageIcon(getClass().getResource("/images/flappybird.png")).getImage();
        backgroundImg = new ImageIcon(getClass().getResource("/images/flappybirdbg.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/images/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/images/bottompipe.png")).getImage();

        bird = new Bird(birdImg);

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        pipes = new ArrayList<Pipe>();

        placedPipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                placedPipe();
            }
        });
        placedPipeTimer.start();

    }

    public void placedPipe() {

        int randomSize = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomSize;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + openingSpace + pipeHeight;
        pipes.add(bottomPipe);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
//        System.out.println("Repainting");

    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setFont(new Font("Raleway", Font.BOLD, 32));
        g.drawString("Score :" + (int) score, 10, 30);

    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += pipeVelocity;

            if (collision(bird, pipe)) {
                gameOver = true;
            }
            if (!pipe.passed && bird.x > pipe.x + pipewidth) {
                pipe.passed = true;
                score += 0.5;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;

        }

    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width
                && a.x + a.width > b.x
                && a.y < (b.y + b.height)
                && a.y + a.height > b.y;
    }

    //ActionListener Override Method
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
        if (gameOver) {
            gameLoop.stop();
            placedPipeTimer.stop();
        }
    }

    //KeyListener Override Method
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            
            if(gameOver)
            {
                score=0;
                velocityY=-9;
                bird.y=birdY;
                pipes.clear();
                gameOver=false;
                gameLoop.start();
                placedPipeTimer.start();
            }
        }
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
