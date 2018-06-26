import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Snake  extends JPanel implements ActionListener {

    private Point snakeHead;
    private Point frameSize;
    private int scale;
    private Point apple;
    private ArrayList<Point> snakeBody;

    public Snake(Point size){
        this.setSize(size.x,size.y);
        this.setBackground(Color.BLACK);
        frameSize = size;
        this.setFocusable(true);
        scale = 10;
        drawStartSnake();

    }

    private void drawStartSnake() {
        snakeHead = new Point(15, 15);
        apple = new Point(20,20);
        snakeBody = new ArrayList<>();
        for(int i = 0 ; i <=3; i++){
            snakeBody.add(new Point(snakeHead.x+i, snakeHead.y));
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, frameSize.x, frameSize.y);


        graphics.setColor(Color.RED);
        graphics.fillRect(apple.x*scale, apple.y*scale,scale,scale);

        graphics.setColor(Color.RED);
        for(Point snakeBodyElemnt :snakeBody)
            graphics.fillRect(snakeBodyElemnt.x*scale, snakeBodyElemnt.y*scale,scale,scale);

        graphics.setColor(Color.GREEN);
        graphics.fillRect(snakeHead.x*scale, snakeHead.y*scale,scale,scale);


    }
}
