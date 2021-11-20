package Client;

import javax.swing.*;
import java.awt.*;

public class FirstWarningWindow {
        JFrame frame = new JFrame();
        JLabel label = new JLabel("First warning");
        JButton okButton = new JButton("Yes, I want to send this message");
        JButton noButton = new JButton("I've changed my mind");

        FirstWarningWindow() {
            label.setBounds(0, 0, 100, 50);
            label.setFont(new Font(null, Font.PLAIN, 25));
            frame.add(label);
            frame.setSize(420, 420);
            frame.setLayout(null);
            frame.setVisible(true);

        }

}
