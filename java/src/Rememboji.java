//import api.*;
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
//import java.lang.*;

public class Rememboji implements ir{

    //images to change quickly
    public static String titlePicture = "images/title.png";
    public static String startbuttonpic = "images/start.png";
    public static String cardBackPicture = "images/cardBack.png";
    public static ImageIcon cardBackImage = new ImageIcon(cardBackPicture);
    public static ImageIcon startBackground = new ImageIcon(cardBackPicture);
    public static ImageIcon gameBackground = new ImageIcon(cardBackPicture);

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
    public static long timer;

    //Strings and lists for emoji
    public static String category, cat, lil, letter;
    public static List<String> uni, temp = new ArrayList<>();

    //categories
    enum category {
        SMILEYS, PEOPLE, ANIMALS, FOOD, FLAGS,
        TRAVEL, ACTIVITIES, OBJECTS, SYMBOLS
    }

    public static String[] optionsToChoose = {"SMILEYS", "PEOPLE", "ANIMALS", "FOOD", "FLAGS",
        "TRAVEL", "ACTIVITIES", "OBJECTS", "SYMBOLS"};
    
    public static String chosenCategory;

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
        
        
        //list of emoji to shuffle in codepoint
        cat = getUrlContents(host + "categories/" + category + key);
        List<String> cp = Arrays.asList(cat.split("\\s*,\\s*"));
        List<String> lil = ProcessListcodepoint(cp);//codepoint
        temp = new ArrayList<String>();//clear
        Collections.shuffle(lil);

        //image
        for(int i=0; i<1/* lil.size() */; i++){
            System.out.print(lil.get(i) + " ");
            fontage((String) lil.get(i));
        //    fontage((String) lil.get(i));
        }

        //list of emoji to shuffle in unicode
        uni = ProcessListunicode(cp);
        temp = new ArrayList<String>();//clear
        Collections.shuffle(uni);//unicode
        //arraypic = uni.toArray(new String[0]);
        
        //space image
        URL spaceimage = null;
        try {spaceimage = space();
        } catch (MalformedURLException e) {//e.printStackTrace();
        } finally{}
        ImageIcon si = new ImageIcon("images/background.png");
        try {if(spaceimage!= null) si = new ImageIcon(ImageIO.read(spaceimage));
        } catch (IOException e) {e.printStackTrace();}
        startBackground = si;
        gameBackground = si;

        // Initialize Frame
        frame = new JFrame("Start Screen");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add Title Screen
        frame.setContentPane(new JLabel(startBackground));
        
        // Categories Drop Down menu -  inspired by https://www.delftstack.com/howto/java/java-drop-down-menu/ 
        JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
        jComboBox.setBounds(80, 50, 140, 20);

        JLabel jLabel = new JLabel();
        jLabel.setBounds(90, 100, 400, 100);

        frame.add(jComboBox);
        
        JLabel titleLabel = new JLabel(new ImageIcon(titlePicture));
        titleLabel.setBounds(460, 200, 598, 222);
        frame.add(titleLabel);

        frame.setLayout(null);
        frame.setSize(350, 250);
        frame.setVisible(true);
        // Add Start Button
        JLabel startButton = new JLabel(new ImageIcon(startbuttonpic));
        startButton.setBounds(634, 550, 268, 88);
        
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                frame.setVisible(false);
                // when user press start button, the chosen category is the one they selected 
                chosenCategory = jComboBox.getItemAt(jComboBox.getSelectedIndex());
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
    
    //unicode
    private static List<String> ProcessListunicode(List<String> un){
        String ucase = "";
        for(int i=1; i<un.size(); i+=6){
            ucase = un.get(i).substring(13,un.get(i).length()-1).toUpperCase();
            ucase = ucase.replace('U','u');
            temp.add(ucase);
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
      frame.setLocation(50, 50);
      JLabel label = new JLabel(new ImageIcon(image));
      frame.add(label);
      frame.setVisible(true);
    }

    //get today's space image
    public static <BufferedImage> URL space() throws MalformedURLException{
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
            if(i==json.size())return(null);
        }
        return (new URL(pic));
      }

    //url data
    private static String getUrlContents(String it){
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(it);
            URLConnection urlc = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String line;
            while ((line = br.readLine()) != null)
                content.append(line + "\n");
            br.close();
        }
        catch(Exception e){e.printStackTrace();}
        return content.toString();
    }

    public static void startGame() {
        //timer
        timer = System.currentTimeMillis();

        // Initialize Frame
        frame = new JFrame("Game Screen");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        arraypic = shuffle(uni);
        // Add Background
        frame.setContentPane(new JLabel(gameBackground));
        //array for the JLabels
        cardback3 = new JButton[12];
        // Setup Card Layout

        frame.setVisible(true);

        int x = 0;
        for (int j=100; j<478; j+=377)
            for (int i=118; i<1418; i+=227) {
                cardback3[x] = new JButton(arraypic[x]);
                cardback3[x].setFont(new Font(cardback3[x].getFont().getName(), Font.PLAIN, 120));
                cardback3[x].setBounds(i, j, 177, 277);
                frame.add(cardback3[x]);
                x++;
            }

        flipCards = new JButton("Flip Cards Over");
        flipCards.addActionListener(buttonListener);
        flipCards.setBounds(650,50,200,50);
        frame.add(flipCards,0);
        frame.validate();
        frame.repaint();
        flipCards.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {}});

        //Randomizes the array of pictures and puts them back into the array
        x1 = x2 = null;
        //frame.setVisible(true);
        for(int k=0; k<12 ; k++)
            cardback3[k].addActionListener(buttonListener);
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
                for(int i=0; i<12; i++) {
                    cardback3[i].setFont(new Font(cardback3[i].getFont().getName(), Font.PLAIN,12));
                    cardback3[i].setIcon(cardBackImage);
                    //changes when cards flipped down so cards can't get selected before that button is pushed.
                    cardtrack[12]=1;
                }
                frame.validate();
                frame.repaint();
            }


            checkForCorrect();
            if(cardtrack[12]!=1)
                return;
            
            for(int i=0; i<12; i++){
                if(o == cardback3[i] && cardtrack[i]==0) {
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
    private static void checkForCorrect() {
        //checks if two different cards have been picked
        if(x2!=null) {
            turns = turns+2;
            for (int i=0; i<12; i++)
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
            x1=x2=null;
        }
        frame.validate();
        frame.repaint();
    }

    /*
    
     */
    private static String[] shuffle(List<String> un) {
        ArrayList<String> unic = new ArrayList<>();
        //for(String i : un)
            unic.add("\uD83D\uDE00");//i
        //unic.addAll(un);
        //lil.codePointAt();
        String[] s = new String[] {unic.get(0)};
        /* var */ String[] arraypic = new String[]{
        /* unic.get(0), */ s[0],
        /* "\uD83D\uDE00", */"\uD83D\uDE00",
        "\uD83D\uDC4C","\uD83D\uDC4C",
        "\uD83D\uDE43","\uD83D\uDE43",
        "\uD83D\uDE30","\uD83D\uDE30",
        "\uD83D\uDE08","\uD83D\uDE08",
        "\uD83E\uDD21","\uD83E\uDD21"};
        System.out.println(Arrays.toString(arraypic));
        List<String> strList = Arrays.asList(arraypic);
        Collections.shuffle(strList);
        arraypic = strList.toArray(new String[0]);
        return arraypic;
    }

    private static void checkFinished() {
        boolean checkfin = true;
        for(int l=0; l<12; l++)
            if (cardtrack[l] == 0)
                checkfin = false;
        if(checkfin) {
            //add 2 for last two guesses to finish and divide to make each turn two guesses
            turns = turns+2;
            turns = turns/2;
            timer = (System.currentTimeMillis() - timer)/1000;
            JOptionPane.showMessageDialog(frame.getComponent(0),
            "You won in " + turns + " turns\r\n" + timer + " seconds\r\n" + "PLAY AGAIN");
            //board.writeboard();
            System.gc();
        }
    }

    @Override
    public List<String> ProcessListcodepoint2(List<String> lil) {
        //  Auto-generated method stub
        return null;
    }
}
