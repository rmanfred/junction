package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstWarningWindow implements ActionListener {
        JFrame frame = new JFrame();
        JLabel label = new JLabel("First warning");
        JLabel firstLink = new JLabel("https://www.techlearning.com/tl-advisor-blog/10449");
        JLabel secondLink = new JLabel("https://www.rollingstone.com/culture/culture-lists/a-brief-history-of-people-getting-fired-for-social-media-stupidity-73456/");
        JButton okButton = new JButton("Yes, I want to send this message");
        JButton noButton = new JButton("I've changed my mind");

        FirstWarningWindow() {
            label.setBounds(0, 0, 400, 55);
            label.setFont(new Font(null, Font.PLAIN, 50));
            firstLink.setBounds(0, 60, 1200, 60);
            firstLink.setFont(new Font(null, Font.PLAIN, 35));
            secondLink.setBounds(0, 120, 1200, 60);
            secondLink.setFont(new Font(null, Font.PLAIN, 15));
            frame.add(label);
            frame.add(firstLink);
            frame.add(secondLink);
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
        if(e.getSource() == okButton) {
            frame.dispose();
            SecondWarningWindow secWarn = new SecondWarningWindow();
        }
        if(e.getSource() == noButton) {
            frame.dispose();
        }
    }
}
