import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Snakegame extends JPanel implements ActionListener,KeyListener{
    public class Tile{
        int x;
        int y;

        Tile(int x,int y){
            this.x=x;
            this.y=y;
        }
    }

    int boardheight;
    int boardwidth;

    int tileSize=25;
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //snakefood
    Tile food;
    Random random;

    int velocityX;
    int velocityY;
    boolean gameOver=false;
    Timer gameloop;
    Snakegame(int boardwidth,int boardheight){
        this.boardheight=boardheight;
        this.boardwidth=boardwidth;
        setPreferredSize(new Dimension(this.boardwidth,this.boardheight));
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.black);
        snakeHead=new Tile(5,5);
        snakeBody=new ArrayList<Tile>();

        food = new Tile(10,10);
        random = new Random();

        placeFood();

        velocityX=0;
        velocityY=0;

        gameloop = new Timer(100,this);
        gameloop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //grid
//        for(int i=0;i<boardwidth/tileSize;i++){
//            //(x1,x2,y1,y2)
//            g.drawLine(i*tileSize , 0 , i*tileSize , boardheight);
//            g.drawLine( 0, i*tileSize , boardwidth , i*tileSize);
//        }
        //food
        g.setColor(Color.blue);
       // g.fillRect(food.x *tileSize,food.y*tileSize,tileSize,tileSize );
        g.fill3DRect(food.x *tileSize,food.y*tileSize,tileSize,tileSize,true);
        //snakehead
        g.setColor(Color.green);
       // g.fillRect(snakeHead.x *tileSize,snakeHead.y *tileSize, tileSize,tileSize);
        g.fill3DRect(snakeHead.x *tileSize,snakeHead.y *tileSize, tileSize,tileSize,true);
        //snakebody
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart=snakeBody.get(i);
           // g.fillRect(snakePart.x*tileSize,snakePart.y*tileSize,tileSize,tileSize);
            g.fill3DRect(snakePart.x*tileSize,snakePart.y*tileSize,tileSize,tileSize,true);
        }
        //score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameOver){
            g.setColor(Color.orange);
            g.drawString("GAME OVER : "+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }
        else{
            g.drawString("SCORE :"+ String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }
    }
    public void placeFood(){
        food.x=random.nextInt(boardwidth/tileSize);
        food.y=random.nextInt(boardheight/tileSize);
    }
    public boolean Collision(Tile tile1,Tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;
    }
    public void move(){
        if(Collision(snakeHead,food)){
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
        }
        //snakebody
        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart = snakeBody.get(i);
                if(i==0){
                    snakePart.x=snakeHead.x;
                    snakePart.y=snakeHead.y;
                }
                else{
                    Tile prevsnakePart = snakeBody.get(i-1);
                    snakePart.x=prevsnakePart.x;
                    snakePart.y=prevsnakePart.y;
                }
        }
        //snakehead
        snakeHead.x+=velocityX;
        snakeHead.y+=velocityY;
        //gameover condition
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            //collide with the snake head
            if(Collision(snakeHead,snakePart)){
                gameOver=true;
            }
        }
        if(snakeHead.x*tileSize<0 || snakeHead.x*tileSize > boardwidth ||snakeHead.y*tileSize<0 || snakeHead.x*tileSize>boardheight){
            gameOver=true;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if(gameOver){
            gameloop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityX !=1){
            velocityX=0;
            velocityY=-1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityX !=-1){
            velocityX= 0;
            velocityY= 1;
        }
        else if(e.getKeyCode()== KeyEvent.VK_LEFT && velocityX !=1){
            velocityX= -1;
            velocityY = 0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX !=-1){
            velocityX = 1;
            velocityY = 0;
        }
    }
    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void keyReleased(KeyEvent e){

    }
}