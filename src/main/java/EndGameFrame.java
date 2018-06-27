import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class EndGameFrame extends JDialog {

    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel score;
    private JButton okButton;
    private JLabel congratulationLabel;

    private int finalScore;
    private List<Score> resultList;

    private Dimension dim;

    public EndGameFrame(int score, List<Score> list) {
        finalScore = score;
        resultList = list;
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width/2-this.getSize().width/2)- 100, (dim.height/2-this.getSize().height/2) - 100);
        setTitle("Congratulations!!");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(270, 160);
        setModal(true);
        setResizable(false);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(15, 40, 65, 20);
        nameLabel.setBackground(Color.white);

        nameField = new JTextField("");
        nameField.setBounds(100, 40, 100, 20);

        this.score = new JLabel("Score:       " + String.valueOf(score));
        this.score.setBounds(15, 60, 110, 20);
        this.score.setBackground(Color.white);

        okButton = new JButton("OK");
        okButton.setBounds(110, 85, 50, 30);
        okButton.setFocusable(false);
        okButton.setMargin(new Insets(1, 1, 1, 1));
        okButton.addActionListener(new EndGame());

        congratulationLabel = new JLabel("You made it into top 10!!");
        congratulationLabel.setBounds(10, 2, 250, 30);
        congratulationLabel.setBackground(Color.white);
        congratulationLabel.setFont(new Font("Verdana", Font.BOLD, 13));

        add(okButton);
        add(nameLabel);
        add(nameField);
        add(this.score);
        add(congratulationLabel);

        setVisible(true);
    }

    private class EndGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Score score;
            if (nameField.getText().isEmpty())
                score = new Score(finalScore, "Stefan");
            else
                score = new Score(finalScore, nameField.getText());
            if(resultList.size() < 10)
                resultList.add(score);
            if(resultList.size() >=10){
                Collections.sort(resultList);
                resultList.set(9, score);
            }
            Collections.sort(resultList);
            new ResultTableFrame(resultList);

            dispose();
        }
    }
}
