package com.example;

import java.io.*;
import java.awt.*;
import java.net.*;
import javax.swing.*;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;

public class Rememboji {
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
    private static long time;

    public static ImageIcon cardBackImage = null;
    public static ImageIcon startBackground = null;
    public static ImageIcon gameBackground = null;
    
    //Strings and lists for emoji
    private static String slug, cat;//, letter;
    private static List<String> lil;
    private static List<String> uni, temp = new ArrayList<>();
    private static List<String> cp;

    private enum categories {
        SMILEYS("smileys-emotion"),
        ANIMALS("animals-nature"),
        FOOD("food-drink"),
        TRAVEL("travel-places"),
        ACTIVITIES("activities"),
        OBJECTS("objects"),
        SYMBOLS("symbols");

        private final String slug;

        private categories(String slug) {
            this.slug = slug;
        }
    }


    private static categories chosenCategory;

    //references
    private static String host = "https://emoji-api.com/";
    private static String key =
            "?access_key=f48301a44b0c8d06490563f08004880e0de02e51";
    private static ImageIcon backgroundPicture, titlePicture, startbuttonpic, cardBackPicture, shufflingPic;

        static{
            // Default Background Image
            try {
                        backgroundPicture = getpic("https://github.com/bulkerb/memory-game/raw/main/images/background.png");
                        titlePicture = getpic("https://github.com/bulkerb/memory-game/raw/main/images/title.png");
                        startbuttonpic = getpic("https://github.com/bulkerb/memory-game/raw/main/images/start.PNG");
                        cardBackPicture =  getpic("https://github.com/bulkerb/memory-game/raw/main/images/cardBack.png");
                        shufflingPic = getpic("https://github.com/bulkerb/memory-game/raw/main/images/shufflingPic.PNG");
            } catch (IOException e) {}
        }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            slug = args[0];
        } else slug = "animals-nature";


        //space image
        URL spaceimage = null;
        try {spaceimage = space();
        } catch (MalformedURLException e) {
        } finally {}
        ImageIcon si = backgroundPicture;
        try {
            if (spaceimage != null) si = new ImageIcon(ImageIO.read(spaceimage));
        } catch (IOException e) {}

        cardBackImage = cardBackPicture;

        startBackground = si;
        gameBackground = si;

        int close = 0, x = 1;

        while(close == 0) {
            switch(x) {
                case 1:
                    x = 2;
                    x = firstframe();
                    break;
                case 9:
                    close = 1;
                    //getimages();
                    break;
                case 2:
                default:
                    break;
            }
        }
    }

    private static ImageIcon getpic(String z) throws MalformedURLException, IOException {
        return (new ImageIcon(ImageIO.read( new URL(z))));
    }

    static int q = 2;

    private static int firstframe() {
        // Initialize Frame
        frame = new JFrame("Screen");
        frame.setUndecorated(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add Title Screen
        frame.setContentPane(new JLabel(startBackground));

        // Categories Drop Down menu -  inspired by https://www.delftstack.com/howto/java/java-drop-down-menu/
        final JComboBox<categories> jcb = new JComboBox<>(categories.values());
        jcb.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
        jcb.setForeground(new Color(178, 74, 111));
        jcb.setBounds(615, 450, 250, 70);

        JLabel jLabel = new JLabel("Select Theme", JLabel.CENTER);
        jLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 26));
        jLabel.setOpaque(true);
        jLabel.setBackground(new Color(178, 74, 111));
        jLabel.setForeground(new Color(254, 249, 210));
        jLabel.setBounds(635, 400, 200, 50);
        frame.add(jcb);
        frame.add(jLabel);

        JLabel titleLabel = new JLabel(titlePicture);
        titleLabel.setBounds(460, 200, 598, 222);
        frame.add(titleLabel);
        frame.setLayout(null);
        frame.setSize(350, 250);
        frame.setVisible(true);

        // add exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setBounds(0, 0, 80, 40);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                frame.dispose();
                System.exit(0);
            }
        });

        // Add Start Button
        final JToggleButton button = new JToggleButton(startbuttonpic);
        button.setBounds(600, 550, 268, 88);

        if (q == 2)
            frame.add(button);
        button.setSelectedIcon(shufflingPic);

        frame.add(exitButton);
        frame.setVisible(true);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                // when user press start button, the chosen category is the one they selected
                chosenCategory = jcb.getItemAt(jcb.getSelectedIndex());
                slug = chosenCategory.slug;
                getimages();
                q = 9;
            }
        });

        if (q == 9) {
            button.removeMouseListener(null);
        }

        frame.setVisible(true);
        return q;

    }

    //codepoint
    private static List<String> ProcessListcodepoint(List<String> lil) {
        String letter = "";
        for (int i = 3; i < lil.size(); i++) {
            letter = (String) lil.get(i);
            if (!letter.contains(" ") && letter.contains("codePoint")) {
                letter = letter.replace("codePoint", "");
                letter = letter.replace("\"", "");
                letter = letter.replace(":", ""); //0x
                temp.add(letter);
            }
        }
        return (temp);
    }

    //unicode
    private static List<String> ProcessListunicode(List<String> un) {
        String ucase = "";
        for (int i = 1; i < un.size(); i += 6) {
            ucase = un.get(i).substring(13, un.get(i).length() - 1).toUpperCase();
            ucase = ucase.replace('U', 'u');
            temp.add(ucase);
        }
        return (temp);
    }

    //get today's space image
    private static <BufferedImage> URL space() throws MalformedURLException {
        //references
        String hostspace = "https://api.nasa.gov/planetary/apod";
        String keyspace = "?api_key=qxxxDZYEZaVxSUQbqKsgPpJjYUjZC6MsUjBsmg8U";
        //imageurl
        String pic = getUrlContents(hostspace + keyspace);
        List<String> json = Arrays.asList(pic.split("\\s*,\\s*"));
        for (int i = 0; i < json.size(); i++) {
            if (json.get(i).contains("hdurl")) {
                pic = json.get(i).substring(9, json.get(i).length() - 1);
                break;
            }
            if (i == json.size()) return (null);
        }
        return (new URL(pic));
    }

    //url data
    private static String getUrlContents(String it) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(it);
            URLConnection urlc = url.openConnection();
            BufferedReader br = new BufferedReader(
                new InputStreamReader(urlc.getInputStream())
            );
            String line;
            while ((line = br.readLine()) != null)
                content.append(line + "\n");
            br.close();
        } catch (Exception e) {}
        return content.toString();
    }

    //gets a list of emoji in the category
    private static void getimages() {
        cat = getUrlContents(host + "categories/" + slug + key);
        //list of emoji to shuffle in codepoint
        cp = Arrays.asList(cat.split("\\s*,\\s*"));
        lil = ProcessListcodepoint(cp); //codepoint
        temp = new ArrayList<String>(); //clear
        //list of emoji to shuffle in unicode
        uni = ProcessListunicode(cp);
        temp = new ArrayList<String>(); //clear
        startGame();
    }

    private static Runnable startGame() {
        frame.getContentPane().removeAll();
        frame.repaint();
        arraypic = shuffle(uni);
        // Add Background
        frame.setContentPane(new JLabel(gameBackground));
        //array for the JLabels
        cardback3 = new JButton[12];
        // Setup Card Layout

        // add exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setBounds(0, 0, 80, 40);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                frame.dispose();
                System.exit(0);
            }
        });

        frame.add(exitButton);
        frame.setVisible(true);
        int x = 0;

        for (int j = 100; j < 478; j += 377)
            for (int i = 118; i < 1418; i += 227) {
                cardback3[x] = new JButton();
                Image image = null;
                URL url = null;
                try {
                    url = new URL("https://emojiapi.dev/api/v1/" + arraypic[x] + "/512.png");
                    image = ImageIO.read(url);
                } catch (IOException e) {}
                //from https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
                ImageIcon imageIcon = new ImageIcon(image); // load the image to a imageIcon
                image = imageIcon.getImage(); // transform it
                Image newimg = image.getScaledInstance(
                        177, 277,
                        java.awt.Image.SCALE_SMOOTH
                ); // scale it the smooth way
                imageIcon = new ImageIcon(newimg);
                cardback3[x].setIcon(imageIcon);
                cardback3[x].setBounds(i, j, 177, 277);
                frame.add(cardback3[x]);
                x++;
            }

        //timer
        time = System.currentTimeMillis();
        flipCards = new JButton("Flip Cards Over");
        flipCards.addActionListener(buttonListener);
        flipCards.setBounds(650, 50, 200, 50);
        frame.add(flipCards, 0);
        frame.validate();
        frame.repaint();
        flipCards.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {}
        });

        //Randomizes the array of pictures and puts them back into the array
        x1 = x2 = null;
        for (int k = 0; k < 12; k++)
            cardback3[k].addActionListener(buttonListener);
        return null;
    }

    //runs everytime a button is clicked
    static ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //which button was pressed
            Object o = e.getSource();
            //checks if flip the cards button was pressed. Removes it and puts all cards facedown
            if (o == flipCards) {
                frame.remove(flipCards);
                for (int i = 0; i < 12; i++) {
                    cardback3[i].setFont(
                        new Font(cardback3[i].getFont().getName(), Font.PLAIN, 12)
                    );
                    cardback3[i].setIcon(cardBackImage);
                    //changes when cards flipped down so cards can't get selected before that button is pushed.
                    cardtrack[12] = 1;
                }
                frame.validate();
                frame.repaint();
            }
            checkForCorrect();
            if(cardtrack[12] != 1)
                return;

            for (int i = 0; i < 12; i++) {
                if (o == cardback3[i] && cardtrack[i] == 0) {
                    if(x1 == null)
                        x1 = arraypic[i];
                    else
                        x2 = arraypic[i];
                    Image image = null;
                    try {
                        URL url = new URL("https://emojiapi.dev/api/v1/" + arraypic[i] + "/512.png");
                        image = ImageIO.read(url);
                    } catch (IOException xe) {}
                    ImageIcon imageIcon = new ImageIcon(image); // load the image to a imageIcon
                    image = imageIcon.getImage(); // transform it
                    Image newimg = image.getScaledInstance(
                            177, 277,
                            java.awt.Image.SCALE_SMOOTH
                    ); // scale it the smooth way
                    imageIcon = new ImageIcon(newimg);
                    cardback3[i].setIcon(imageIcon);
                    frame.validate();
                    frame.repaint();
                    cardtrack[i] = 1;
                }
            }
            checkFinished();
        }
    };

    //checks if the two cards were a match
    private static void checkForCorrect() {
        //checks if two different cards have been picked
        if(x2 != null) {
            turns = turns + 2;
            for (int i = 0; i < 12; i++)
                if (cardtrack[i] == 1) {
                    if (Objects.equals(x1, x2)) {
                        cardtrack[i] = 2;
                        cardback3[i].removeActionListener(buttonListener);
                    } else {
                        cardtrack[i] = 0;
                        cardback3[i].setFont(new Font(
                                cardback3[i].getFont().getName(),
                                cardback3[i].getFont().getStyle(),
                                12
                        ));
                        cardback3[i].setIcon(cardBackImage);
                        cardback3[i].setBorderPainted(false);
                    }
                }
            x1 = x2 = null;
        }
        frame.validate();
        frame.repaint();
    }

    private static String[] shuffle(List<String> un) {
        ArrayList<String> arraytry = new ArrayList<>();
        String[] arraypic = new String[12];
        for (int i = 0; i < 6; i++) {
            int x = i;
            while (arraytry.contains(lil.get(x))) {
                x++;
            }
            arraytry.add(lil.get(x));
            arraypic[i] = lil.get(x);
        }
        for(int i = 6; i < 12; i++){
            arraypic[i] = arraypic[i - 6];
        }
        List<String> strList = Arrays.asList(arraypic);
        Collections.shuffle(strList);
        arraypic = strList.toArray(new String[0]);
        return arraypic;
    }

    private static void checkFinished() {
        boolean checkfin = true;
        for (int l = 0; l < 12; l++)
            if (cardtrack[l] == 0)
                checkfin = false;
        if(checkfin) {
            //add 2 for last two guesses to finish and divide to make each turn two guesses
            turns = turns + 2;
            turns = turns / 2;
            time = (System.currentTimeMillis() - time) / 1000;
            // JOptionPane input code retrived from: https://stackoverflow.com/questions/14407804/how-to-change-the-default-text-of-buttons-in-joptionpane-showinputdialog
            Object[] choices = {"Reshuffle", "Exit"};
            Object defaultChoice = choices[0];
            int input = JOptionPane.showOptionDialog(frame.getComponent(0), "You won in " + turns + " turns!\r\n" +
                    "You took " + time + " seconds...\r\n", "Finished!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices, defaultChoice);
            // Perform action on options retrived from: https://stackoverflow.com/questions/17979438/how-to-perform-action-on-ok-of-joptionpane-showmessagedialog
            if (input == JOptionPane.OK_OPTION) {
                for (int l = 0; l < 12; l++) {
                    cardtrack[l] = 0;
                }
                turns = 0;
                time = 0;
                frame.dispose();
                startGame();
            }
            else if (input == JOptionPane.NO_OPTION) {
                frame.dispose();
                System.exit(0);
            }
            else if (input == JOptionPane.CLOSED_OPTION) {
                frame.dispose();
                System.exit(0);
            }
            System.gc();
        }
    }
}
