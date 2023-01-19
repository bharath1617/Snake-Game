import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.Arrays;
import java.util.Random;
public class panel extends JPanel implements ActionListener{
    //height and width of the panel
    static int width = 1200;
    static int height = 600;
    //unit size of the single grid unit
    static int unit = 50;
    Timer timer;
    Random random;
    // coordinates for the food
    int fx, fy;
    //
    int foodeaten;
    // length of the snake in start of the game
    int body = 3;
    // flag to check of the game is running
    boolean flag = false;
    // direction of the snake
    char dir = 'R';
    //
    static int delay = 160;
    static int gridesize = (width * height) / (unit * unit); // = 288

    int x_snake[] = new int[gridesize];
    int y_snake[] = new int[gridesize];

    panel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKey());
        random = new Random();
        Game_Start();
    }

    public void Game_Start() {
        spawnfood();
        flag = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void spawnfood() {
        // random integer between 0 - 1200 multiple of 50
        fx = random.nextInt((int) (width / unit)) * unit;
        fy = random.nextInt((int) (height / unit)) * unit;
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        draw(graphic);
    }
    public void draw(Graphics graphic) {
        if (flag) {
            // drawing the food particle
            graphic.setColor(Color.orange);
            graphic.fillOval(fx, fy, unit, unit);
            // drawing the snake
            for (int i = 0; i < body; i++) {
                if (i == 0) {
                    graphic.setColor(Color.red);
                    graphic.fillRect(x_snake[0], y_snake[0], unit, unit);
                } else {
                    graphic.setColor(Color.green);
                    graphic.fillRect(x_snake[i], y_snake[i], unit, unit);
                }
            }
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
            FontMetrics fm = getFontMetrics(graphic.getFont());
            graphic.drawString("Score"+foodeaten, (width - fm.stringWidth("score:" + foodeaten))/2, graphic.getFont().getSize());
        }
        else{
            gameover(graphic);
        }
    }
    public void gameover(Graphics graphic){
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics fm = getFontMetrics(graphic.getFont());
        graphic.drawString("Score"+foodeaten, (width - fm.stringWidth("score:" + foodeaten))/2, graphic.getFont().getSize());

        // gameover display
        graphic.setColor(Color.red);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
        FontMetrics fm2 = getFontMetrics(graphic.getFont());
        graphic.drawString("GameOver",(width - fm2.stringWidth("game over"))/2,height/2);

        // press r to replay
        graphic.setColor(Color.CYAN);
        graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
        FontMetrics fm3 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press r to replay", (width - fm3.stringWidth("Press R to replay"))/2, height/2-150);
    }
    public void move(){
        // update the body
        for(int i=body;i>0;i--){
            x_snake[i] = x_snake[i-1];
            y_snake[i] = y_snake[i-1];

        }
        // update the head
        switch(dir){
            case 'U':
                y_snake[0] = y_snake[0]-unit;
                break;
            case 'D':
                y_snake[0] = y_snake[0]+unit;
                break;
            case 'L':
                x_snake[0] = x_snake[0]-unit;
                break;
            case 'R':
                x_snake[0] = x_snake[0]+unit;
                break;
        }
    }
    public void check(){
        // to check with its body
        for(int i=body;i>0;i--){
            if((x_snake[0] == x_snake[i])&& (y_snake[0] == y_snake[i])){
                flag = false;
            }
        }
        // to check with walls
        if(x_snake[0]<0){
            flag = false;
        }
        else if(x_snake[0]>width){
            flag = false;
        }
        else if(y_snake[0]<0){
            flag = false;
        }
        else if(y_snake[0] > height){
            flag = false;
        }
        if(!flag){
            timer.stop();
        }
    }
    public void food(){
        for(int i=body;i>0;i--){
            if((x_snake[0] == fx) && (y_snake[0] == fy)){
                body++;
                foodeaten++;
                spawnfood();
            }
        }
    }
    public class MyKey extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir!= 'R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir!= 'D'){
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!= 'U'){
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!= 'L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag){
                        foodeaten = 0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(x_snake, 0);
                        Arrays.fill(y_snake, 0);
                        Game_Start();

                    }
            }

       }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
          if(flag){
              move();
              food();
              check();
          }
          repaint();
    }
}
