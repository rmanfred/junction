package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecondWarningWindow implements ActionListener {
    JFrame frame = new JFrame();
    JLabel label = new JLabel("Last warning");
    JButton okButton = new JButton("Yes, I take full responsibility");
    JButton noButton = new JButton("I've changed my mind");

    SecondWarningWindow() {
        label.setBounds(0, 0, 400, 55);
        label.setFont(new Font(null, Font.PLAIN, 50));
        frame.add(label);
        okButton.addActionListener(this);
        noButton.addActionListener(this);
        okButton.setBounds(0, 500, 600, 100);
        noButton.setBounds(700, 500, 600, 100);
        frame.add(okButton);
        frame.add(noButton);
        frame.setSize(1280, 720);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            frame.dispose();
        }
        if (e.getSource() == noButton) {
            frame.dispose();
        }
    }
}
