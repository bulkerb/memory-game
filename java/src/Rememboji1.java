//package cpsc3720.memoryGame;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Rememboji1 {
    //images to change quickly
    public static String startBackground = "C:\\Users\\Zack\\Desktop\\pics\\titleScreen.png";
    public static String startbuttonpic = "C:\\Users\\Zack\\Desktop\\pics\\start.png";
    public static String gameBackground = "C:\\Users\\Zack\\Desktop\\pics\\background.png";
    public static String cardBackPicture = "C:\\Users\\Zack\\Desktop\\pics\\cardBack.png";
    public static ImageIcon cardBackImage = new ImageIcon(cardBackPicture);


    //array to hold pictures
    private static String[] arraypic;
    private static JButton flipCards;
    //array keeps track of what cards have been selected
    private static final int[] cardtrack = new int[13];
    //for making comparison of the pictures
    private static String x1 = null, x2 = null;
    private static JButton[] cardback3;
    private static JFrame frame;
    private static int turns;


    public static void main(String[] args) {

        // Initialize Frame
        frame = new JFrame("Start Screen");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add Title Screen
        frame.setContentPane(new JLabel(new ImageIcon(startBackground)));

        // Add Start Button
        JLabel startButton = new JLabel(new ImageIcon(startbuttonpic));
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

    public static void startGame() {
        // Initialize Frame
        frame = new JFrame("Game Screen");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shuffle();
        // Add Background
        frame.setContentPane(new JLabel(new ImageIcon(gameBackground)));
        //array for the JLabels
        cardback3 = new JButton[12];
        // Setup Card Layout

        frame.setVisible(true);

        int x = 0;
        for (int j = 100; j < 478; j += 377) {
            for (int i = 118; i < 1418; i += 227) {

                cardback3[x] = new JButton(arraypic[x]);
                cardback3[x].setFont(new Font(cardback3[x].getFont().getName(), Font.PLAIN, 120));
                cardback3[x].setBounds(i, j, 177, 277);
                frame.add(cardback3[x]);
                x++;
            }
        }

        flipCards = new JButton("Flip Cards Over");
        flipCards.addActionListener(buttonListener);
        flipCards.setBounds(650,50,200,50);
        frame.add(flipCards,0);
        frame.validate();
        frame.repaint();
        flipCards.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {

            }
        });

        //Randomizes the array of pictures and puts them back into the array
        x1 = x2 = null;
        //frame.setVisible(true);
        for(int k = 0; k<12 ; k++){
            cardback3[k].addActionListener(buttonListener);
        }


    }

    //runs everytime a button is clicked
    static ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //which button was pressed
            Object o = e.getSource();
            //checks if flip the cards button was pressed. Removes it and puts all cards facedown
            if( o == flipCards){
                frame.remove(flipCards);
                for(int i =0; i < 12; i++){
                    cardback3[i].setFont(new Font(cardback3[i].getFont().getName(), Font.PLAIN,12));
                    cardback3[i].setIcon(cardBackImage);
                    //changes when cards flipped down so cards can't get selected before that button is pushed.
                    cardtrack[12]=1;
                }
                frame.validate();
                frame.repaint();
            }
            checkForCorrect();
            if(cardtrack[12]!=1) {
                return;
            }
            for(int i = 0; i < 12; i++){
                if(o == cardback3[i] && cardtrack[i]==0){
                    if(x1 == null)
                        x1 = arraypic[i];
                    else
                        x2 = arraypic[i];
                    cardback3[i].setFont(new Font(cardback3[i].getFont().getName(),cardback3[i].getFont().getStyle(), 120));
                    cardback3[i].setText(arraypic[i]);
                    cardback3[i].setIcon(null);
                    frame.validate();
                    frame.repaint();
                    cardtrack[i]=1;
                }
            }
            checkFinished();
        }
    };
    //checks if the two cards were a match
    private static void checkForCorrect(){
        //checks if two different cards have been picked
        if(x2!=null) {
            turns = turns + 2;
            for (int i = 0; i < 12; i++) {
                if (cardtrack[i] == 1) {
                    if (Objects.equals(x1, x2)) {
                        cardtrack[i] = 2;
                        cardback3[i].removeActionListener(buttonListener);
                    } else {
                        cardtrack[i] = 0;
                        cardback3[i].setFont(new Font(cardback3[i].getFont().getName(),cardback3[i].getFont().getStyle(), 12));
                        cardback3[i].setIcon(cardBackImage);
                        cardback3[i].setBorderPainted(false);
                    }

                }
            }
            x1=x2=null;
        }
        frame.validate();
        frame.repaint();
    }
    //shuffles array
    private static void shuffle(){
        arraypic = new String[]{"\uD83D\uDE00",
                "\uD83D\uDE00", "\uD83D\uDC4C",
                "\uD83D\uDC4C", "\uD83D\uDE43",
                "\uD83D\uDE43", "\uD83D\uDE30",
                "\uD83D\uDE30", "\uD83D\uDE08",
                "\uD83D\uDE08", "\uD83E\uDD21",
                "\uD83E\uDD21"};
        List<String> strList = Arrays.asList(arraypic);
        Collections.shuffle(strList);
        arraypic = strList.toArray(new String[0]);
    }

    private static void checkFinished(){
        boolean checkfin = true;
        for(int l =0; l<12; l++) {
            System.out.println(cardtrack[l]);

            if (cardtrack[l] == 0)
                checkfin = false;
        }
            if(checkfin) {
                //add 2 for last two guesses to finish and divide to make each turn two guesses
                turns = turns + 2;
                turns = turns/2;
                JOptionPane.showMessageDialog(frame.getComponent(0), "You won in " + turns + " turns");
            }
    }
}



