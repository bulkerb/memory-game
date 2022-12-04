import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// inspired by https://www.delftstack.com/howto/java/java-drop-down-menu/
// works but is boring to look at 
// made in case the html js version does not work with our program
public class DropDown {
    public static void main(String[] args) {
        String[] optionsToChoose = {"SMILEYS", "PEOPLE", "ANIMALS", "FOOD", "FLAGS",
        "TRAVEL", "ACTIVITIES", "OBJECTS", "SYMBOLS"};

        JFrame jFrame = new JFrame();

        //JTextPane text = new JTextPane();
        JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
        jComboBox.setBounds(80, 50, 140, 20);

        JButton jButton = new JButton("Play");
        jButton.setBounds(100, 100, 90, 20);

        JLabel jLabel = new JLabel();
        jLabel.setBounds(90, 100, 400, 100);

        jFrame.add(jButton);
        jFrame.add(jComboBox);
        jFrame.add(jLabel);
        
        jFrame.setLayout(null);
        jFrame.setSize(350, 250);
        jFrame.setVisible(true);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String selectedFruit = "You selected " + jComboBox.getItemAt(jComboBox.getSelectedIndex());
                //jLabel.setText(selectedFruit);
            }
        });

    }
}