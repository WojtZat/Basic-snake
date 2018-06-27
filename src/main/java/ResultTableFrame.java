import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ResultTableFrame extends JDialog {

    private JTable listField;
    private JButton okButton;
    private Dimension dim;

    public ResultTableFrame(List<Score> list) {
        System.out.println(list.size());
        setTitle("Top 10");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(240, 250);
        setResizable(false);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width/2-this.getSize().width/2), (dim.height/2-this.getSize().height/2) );
        this.setAlwaysOnTop(true);

        String[] table = {"Name", "Score"};
        Object[][] data = new Object[10][2];
        if(!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).name.isEmpty()) {
                    data[i][0] = list.get(i).name;
                    data[i][1] = list.get(i).score;
                } else
                    data[i][0] = "";
                data[i][1] = list.get(i).score;

            }
        }

        listField = new JTable(data, table);
        listField.setEnabled(false);
        listField.setBounds(10, 10, 220, 183);
        listField.setBackground(Color.white);
        listField.setFont(new Font("Verdana", Font.BOLD, 12));
        listField.setRowMargin(2);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        listField.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        listField.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        okButton = new JButton("OK");
        okButton.setBounds(85, 190, 50, 30);
        okButton.setFocusable(false);
        okButton.setMargin(new Insets(1, 1, 1, 1));
        okButton.addActionListener(new Close());

        JScrollPane pane = new JScrollPane(listField);
        pane.setSize(listField.getSize());
        pane.setBounds(5, 5, 225, 183);

        add(pane);
        add(okButton);

        setVisible(true);
    }

    private class Close implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
