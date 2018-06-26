import javafx.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Snake  extends JPanel implements ActionListener {

    public Snake(Point size){
        this.setSize(size.x,size.y);
        this.setBackground(Color.BLACK);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {



    }
}
