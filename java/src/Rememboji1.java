//package cpsc3720.memoryGame;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Image;

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

    //Strings and lists for emoji
    public static String category, cat, lil, letter;
    public static List<String> temp = new ArrayList<>();

    //references
    public static String host = "https://emoji-api.com/";
    public static String key = "?access_key=f48301a44b0c8d06490563f08004880e0de02e51";

    /**
     * @param args
     */
    public static void main(String[] args) {
        if(args.length != 0){
            category = args[0];}
        else category = "smileys-emotion";
        
        //"change category" show user list of categories
        //String allcat = getUrlContents(host + "categories" + key);
        //System.out.println(allcat);

        //list of emoji to shuffle
        cat = getUrlContents(host + "categories/" + category + key);
        List<String> cp = Arrays.asList(cat.split("\\s*,\\s*"));
        List<String> lil = ProcessListcodepoint(cp);//codepoint
        temp = new ArrayList<String>();//clear
        Collections.shuffle(lil);

        //image
        for(int i=0; i<4/* lil.size() */; i++){
            System.out.print(lil.get(i) + " ");
            fontage((String) lil.get(i));
        }

        //space image
        String spaceimageurl = space();
        System.out.println(spaceimageurl);
        startBackground = spaceimageurl; 
        gameBackground = spaceimageurl;


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
        
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                frame.setVisible(false);
                startGame();
            }
        });
        frame.add(startButton);

        frame.setVisible(true);
    }

    //codepoint
    private static List<String> ProcessListcodepoint(List<String> lil){
        String letter = "";
        for(int i=3; i<lil.size(); i+=6){
            letter = (String) lil.get(i);
            if(!letter.contains(" ")){//letter.replace(" ","_");
                letter = letter.replace("codePoint","");
                letter = letter.replace("\"","");
                letter = letter.replace(":","");//0x
                temp.add(letter);
            }
        }
        return(temp);
    }

  //display image
  private static void fontage(String cp){
    Image image = null;
      try {
          URL url = new URL("https://emojiapi.dev/api/v1/" + cp + "/512.png");
          image = ImageIO.read(url);
      } catch (IOException e) {e.printStackTrace();}
      JFrame frame = new JFrame();
      frame.setSize(300, 300);
      JLabel label = new JLabel(new ImageIcon(image));
      frame.add(label);
      frame.setVisible(true);
    }

    //get today's space image
    public static <BufferedImage> String space(){
        //references
        String hostspace = "https://api.nasa.gov/planetary/apod";
        String keyspace = "?api_key=qxxxDZYEZaVxSUQbqKsgPpJjYUjZC6MsUjBsmg8U";
    
        //imageurl
        String pic = getUrlContents(hostspace + keyspace);
        List<String> json = Arrays.asList(pic.split("\\s*,\\s*"));
        for(int i=0; i<json.size(); i++){
            if(json.get(i).contains("hdurl")){
                pic = json.get(i).substring(9,json.get(i).length()-1);
                break;
            }
        }
        return pic;
      }

    //url data
    private static String getUrlContents(String theUrl){
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");}
            bufferedReader.close();
        }
        catch(Exception e){e.printStackTrace();}
        return content.toString();
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



