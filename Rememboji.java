import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Rememboji {
    private static JLabel cardBack;
    private static JLabel startButton;

    public static void main(String[] args) {
        // Initialize Frame
        JFrame frame = new JFrame("Start Screen");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add Title Screen
        frame.setContentPane(new JLabel(new ImageIcon("C:\\Users\\student\\Desktop" +
                "\\School Work\\Year 4\\Semester 1\\CPSC 3720\\Memory Game\\img\\titleScreen.png"))); // TODO: Change File Location

        // Add Start Button
        startButton = new JLabel(new ImageIcon("C:\\Users\\student\\Desktop" +
                "\\School Work\\Year 4\\Semester 1\\CPSC 3720\\Memory Game\\img\\start.PNG")); // TODO: Change File Location
        startButton.setBounds(634, 550, 268, 88);
        /*
         * MouseEvent code retrieved from:
         * https://stackoverflow.com/questions/2275277/how-to-put-clickable-image-jframe
         */
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                frame.setVisible(false);
                startGame();
            }
        });
        frame.add(startButton);

        frame.setVisible(true);
    }

    private static void startGame() {
        // Initialize Frame
        JFrame frame = new JFrame("Game Screen");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add Background
        frame.setContentPane(new JLabel(new ImageIcon("C:\\Users\\student\\Desktop" +
                "\\School Work\\Year 4\\Semester 1\\CPSC 3720\\Memory Game\\img\\background.png"))); // TODO: Change File Location 

        // Setup Card Layout
        for (int i = 118; i < 1418; i += 227) {
            for (int j = 100; j < 478; j += 377) {
                cardBack = new JLabel(new ImageIcon("C:\\Users\\student\\Desktop" +
                        "\\School Work\\Year 4\\Semester 1\\CPSC 3720\\Memory Game\\img\\cardBack.png")); // TODO: Change File Location
                cardBack.setBounds(i, j, 177, 277);

                /*
                 * MouseEvent code retrieved from:
                 * https://stackoverflow.com/questions/2275277/how-to-put-clickable-image-jframe
                 */
                cardBack.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent me) {
                        // TODO: Add what happens when you click the card.
                    }
                });
                frame.add(cardBack);
            }
        }

        frame.setVisible(true);
    }
}
