import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class GameFrame extends JFrame {

    static JButton scoresButton;
    static JButton pauseGameButton;
    private static Snake snake;
    static GameResultsHandler resultsHandler;
    private static JLabel scoreLabel;
    static JLabel scoreValueLabel;
    static JSlider speedSlider;
    private static JLabel speedLabel;
    static JCheckBox wallBox;
    private static JLabel wallLabel;

    boolean hasWalls;
    private List<Score> resultList;

    private  Dimension dimentions;
    static JButton startButton;

    public GameFrame(int a, int b) {
        resultsHandler = new GameResultsHandler("scores.txt");
        dimentions = Toolkit.getDefaultToolkit().getScreenSize();
        hasWalls = true;
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
        startButton.setFocusable(false);
        startButton.addActionListener(new Start());
        this.add(startButton);

        scoresButton = new JButton("Top 10");
        scoresButton.setMargin(insets);
        scoresButton.setFocusable(false);
        scoresButton.setBounds(a - 90, 35, 70, 20);
        scoresButton.addActionListener(new Scores());
        this.add(scoresButton);

        pauseGameButton = new JButton("Pause (s)");
        pauseGameButton.setMargin(insets);
        pauseGameButton.setEnabled(false);
        pauseGameButton.setFocusable(false);
        pauseGameButton.setBounds(a - 90, 60, 70, 20);
        pauseGameButton.addActionListener(new Pause());
        this.add(pauseGameButton);

        scoreLabel = new JLabel("Score :");
        scoreLabel.setBounds(a - 90, 90, 60, 30);
        this.add(scoreLabel);

        scoreValueLabel = new JLabel("0");
        scoreValueLabel.setBounds(a - 30, 90, 60, 30);
        this.add(scoreValueLabel);

        speedLabel = new JLabel("Speed :");
        speedLabel.setBounds(a - 90, 130, 60, 30);
        this.add(speedLabel);

        speedSlider = new JSlider(JSlider.VERTICAL,10,20,15);
        speedSlider.setSnapToTicks(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setLabelTable(speedSlider.createStandardLabels(5));
        speedSlider.setPaintLabels(true);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setBounds(a-100, 150, 90, 100 );
        speedSlider.setFont(new Font("Verdana", Font.BOLD, 10));
        this.add(speedSlider);


        wallBox = new JCheckBox();
        wallBox.setBounds(a-30, 110, 60, 30);
        wallBox.setSelected(true);
        wallBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(!wallBox.isSelected())
                    hasWalls=false;
            }
        });
        this.add(wallBox);

        wallLabel = new JLabel("Walls off");
        wallLabel.setBounds(a - 90, 110, 60, 30);
        this.add(wallLabel);

        resultsHandler = new GameResultsHandler("scores.txt");
        resultList = resultsHandler.deserializeResultList();

        snake = new Snake(a - 100, b, resultList);
        snake.setBounds(0, 0, a - 100, b);
        this.add(snake);



        this.setVisible(true);
    }

    private class Start implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            pauseGameButton.setEnabled(true);
            startButton.setEnabled(false);
            scoresButton.setEnabled(false);
            speedSlider.setEnabled(false);
            wallBox.setEnabled(false);
            snake.startGame(speedSlider.getValue(), wallBox.isSelected());
        }
    }

    private class Scores implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            new ResultTableFrame(resultList);
        }
    }

    private class Pause implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            snake.pause();
        }
    }
}
