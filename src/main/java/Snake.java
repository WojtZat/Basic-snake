import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Snake extends JPanel implements ActionListener {

    private Point snakeHead;
    private Point frameSize;
    private Point apple;
    private ArrayList<Point> snakeBody;

    private int scale;

    private int ticks;
    private Random random;
    private Timer timer;
    public int score;

    protected Point premiumApple;
    protected int premiumTime;
    protected boolean premiumAppleIsPresent;
    protected int premiumFail;


    protected boolean hasWalls;

    private int snakeDirection;
    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;

    private int snakeSpeed;
    private int snakeTail;

    protected boolean over;
    protected boolean paused;
    public boolean gameIsRunning;

    protected java.util.List<Score> resultList;

    private GameResultsHandler handler = new GameResultsHandler("scores.txt");

    public Snake(int a, int b, java.util.List<Score> list) {
        frameSize = new Point(a, b);
        resultList = list;
        scale = 10;
        this.addKeyListener(new keyHandler());
        this.setFocusable(true);
        drawStartSnake();
        gameIsRunning = false;
    }

    public void initGame() {
        over = false;
        paused = false;
        random = new Random();
        drawStartSnake();
        apple = appleGenerator();
        snakeDirection = LEFT;
        premiumAppleIsPresent = false;
        timer = new Timer(snakeSpeed, this);
    }

    public void startGame(int speed, boolean walls) {
        hasWalls = walls;
        snakeSpeed = speed;
        initGame();
        ticks = 0;
        score = 0;
        timer.start();
        gameIsRunning = true;
    }

    protected Point appleGenerator() {
        Point point = new Point(random.nextInt((frameSize.x - 3 * scale) / scale), random.nextInt((frameSize.y - 3 * scale) / scale));
        if (snakeBody.contains(point) || snakeHead.equals(point) || point.equals(apple) || point.equals(premiumApple))
            point = appleGenerator();
        return point;
    }

    private void drawStartSnake() {
        snakeHead = new Point(15, 15);
        apple = new Point(20, 20);
        snakeBody = new ArrayList<>();
        snakeTail = 3;
        for (int i = snakeTail; i > 0; i--) {
            snakeBody.add(new Point(snakeHead.x + i, snakeHead.y));
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
        ticks++;
        if (ticks % 5 == 0 && null != snakeHead && !over && !paused) {
            snakeBody.add(snakeHead);
            if (snakeDirection == DOWN) {
                if (snakeHead.y + 1 < (frameSize.x - 3 * scale) / scale && !notAtTail(snakeHead.x, snakeHead.y + 1)) {
                    snakeHead = new Point(snakeHead.x, snakeHead.y + 1);
                } else if ((snakeHead.y + 1 == (frameSize.x - 3 * scale) / scale) && !hasWalls) {
                    snakeHead = new Point(snakeHead.x, 0);
                } else {
                    endGame();
                }
            }
            if (snakeDirection == UP) {
                if (snakeHead.y - 1 >= 0 && !notAtTail(snakeHead.x, snakeHead.y - 1)) {
                    snakeHead = new Point(snakeHead.x, snakeHead.y - 1);
                } else if (snakeHead.y == 0 && !hasWalls) {
                    snakeHead = new Point(snakeHead.x, (frameSize.y - 3 * scale) / scale);
                } else {
                    endGame();
                }
            }
            if (snakeDirection == LEFT) {
                if (snakeHead.x - 1 >= 0 && !notAtTail(snakeHead.x - 1, snakeHead.y)) {
                    snakeHead = new Point(snakeHead.x - 1, snakeHead.y);
                } else if (snakeHead.x == 0 && !hasWalls) {
                    snakeHead = new Point((frameSize.x - scale) / scale, snakeHead.y);
                } else {
                    endGame();
                }
            }
            if (snakeDirection == RIGHT) {
                if (snakeHead.x + 1 < frameSize.x / scale && !notAtTail(snakeHead.x + 1, snakeHead.y)) {
                    snakeHead = new Point(snakeHead.x + 1, snakeHead.y);
                } else if ((snakeHead.x + 1 == (frameSize.x) / scale) && !hasWalls) {
                    snakeHead = new Point(0, snakeHead.y);
                } else {
                    endGame();
                }
            }
            if (snakeBody.size() > snakeTail)
                snakeBody.remove(0);
            if (null != apple) {
                if (snakeHead.equals(apple)) {
                    score++;
                    GameFrame.scoreValueLabel.setText(String.valueOf(score));
                    snakeTail++;
                    apple = appleGenerator();
                }
            }
            if (score % 5 == 0 && score != 0 && !premiumAppleIsPresent && premiumFail != score) {
                premiumAppleIsPresent = true;
                premiumApple = appleGenerator();
                premiumTime = ticks + 200;
                premiumFail = score;

            }
            if (premiumTime < ticks)
                premiumAppleIsPresent = false;
            if (null != premiumApple) {
                if (snakeHead.equals(premiumApple) && premiumAppleIsPresent) {
                    score += 5;
                    GameFrame.scoreValueLabel.setText(String.valueOf(score));
                    snakeTail++;
                    premiumAppleIsPresent = false;
                }
            }
        }

    }

    private boolean notAtTail(int x, int y) {
        return snakeBody.contains(new Point(x, y));
    }


    private void endGame() {
        over = true;
        timer.stop();
        if (resultList.size() < 10) {
            new EndGameFrame(score, resultList);
        } else if (score >= Collections.max(resultList).score)
            new EndGameFrame(score, resultList);
        handler.serializeResultList(resultList);
        initGame();
        GameFrame.pauseGameButton.setEnabled(false);
        GameFrame.startButton.setEnabled(true);
        GameFrame.scoresButton.setEnabled(true);
        GameFrame.speedSlider.setEnabled(true);
        GameFrame.wallBox.setEnabled(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, frameSize.x, frameSize.y);


        graphics.setColor(Color.RED);
        graphics.fillRect(apple.x * scale, apple.y * scale, scale, scale);

        graphics.setColor(Color.RED);
        for (Point snakeBodyElement : snakeBody)
            graphics.fillRect(snakeBodyElement.x * scale, snakeBodyElement.y * scale, scale, scale);

        graphics.setColor(Color.GREEN);
        graphics.fillRect(snakeHead.x * scale, snakeHead.y * scale, scale, scale);

        if (premiumAppleIsPresent) {
            graphics.setColor(Color.BLUE);
            graphics.fillRect(premiumApple.x * scale, premiumApple.y * scale, scale, scale);
        }

    }


    private class keyHandler extends KeyAdapter {

        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_UP && snakeDirection != DOWN) {
                snakeDirection = UP;
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN && snakeDirection != UP) {
                snakeDirection = DOWN;
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT && snakeDirection != RIGHT) {
                snakeDirection = LEFT;
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT && snakeDirection != LEFT) {
                snakeDirection = RIGHT;
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                if (over & !gameIsRunning)
                    startGame(snakeSpeed, hasWalls);
                else
                    paused = !paused;
                if (paused && gameIsRunning)
                    timer.stop();
                else if (gameIsRunning)
                    timer.start();
            }
        }
    }

    public void pause() {
        paused = !paused;
        if (paused && gameIsRunning)
            timer.stop();
        else if (gameIsRunning)
            timer.start();
    }
}

