import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private  Dimension dimentions;
    private Snake snake;
    private JButton startButton;

    public GameFrame(int a, int b) {
        dimentions = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(a,b);
        this.setName("Basic Snake");
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(new Point(dimentions.width/2 - this.getSize().width/2, dimentions.height/2 - this.getWidth()/2));

        Insets insets = new Insets(1, 1, 1, 1);

        startButton= new JButton("Start");
        startButton.setBounds(a -90, 10, 70, 20);
        startButton.setMargin(insets);
        setFocusable(false);
        this.add(startButton);



        snake = new Snake(new Point(a-100,b));
        snake.setBounds(0,0,a-100,b);
        this.add(snake);



        this.setVisible(true);
    }

}
