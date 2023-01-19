import javax.swing.JFrame;
public class snakeframe extends JFrame {
    snakeframe(){
        // initialising the panel
        this.add(new panel());
        this.setTitle("Snake Game");
        // ensuring that the size of the frame window cannot be changed
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        // spawning the frame
        this.setLocationRelativeTo(null);
    }
}
