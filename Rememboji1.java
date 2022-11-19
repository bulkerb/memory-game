package cpsc3720.memoryGame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Rememboji1 {
    //keep track of how many cards selected
    private static boolean click1 = false;
    private static boolean click2= false;
    private static JLabel cardBack,c2,c3,a,b,c,d,e,f,g,h,i,j,k,l;
    private static JLabel startButton;
    //array to hold pictures
    private static String[] arraypic;
    //array keeps track of what cards have been selected
    private static int[] cardtrack = new int[12];
    //for making comparison of the pictures
    private static String x1 = null, x2 = null;
    private static JLabel[] cardback2;


    public static void main(String[] args) {

        // Initialize Frame
        JFrame frame = new JFrame("Start Screen");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add Title Screen
        frame.setContentPane(new JLabel(new ImageIcon("C:\\Users\\Zack\\Desktop\\pics\\emoji.png")));

        // Add Start Button
        startButton = new JLabel(new ImageIcon("C:\\Users\\Zack\\Desktop\\pics\\start.png"));
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
        frame.setContentPane(new JLabel(new ImageIcon("C:\\Users\\Zack\\Desktop\\pics\\background1.png")));
        //array for the JLabels
        cardback2 = new JLabel[]{a,b,c,d,e,f,g,h,i,j,k,l};
        // Setup Card Layout
        int x=0;
        for (int j = 100; j < 478; j += 377) {
            for (int i = 118; i < 1418; i += 227) {
                //cardBack = new JLabel(new ImageIcon("C:\\Users\\Zack\\Desktop\\pics\\card back1.png"));
                //cardBack.setBounds(i, j, 177, 277);

                cardback2[x] = new JLabel(new ImageIcon("C:\\Users\\Zack\\Desktop\\pics\\card back1.png"));
                cardback2[x].setBounds(i, j, 177, 277);

                /*
                 * MouseEvent code retrieved from:
                 * https://stackoverflow.com/questions/2275277/how-to-put-clickable-image-jframe
                 */
                /*cardBack.addMouseListener(new MouseAdapter() {

                });*/
                frame.add(cardback2[x]);
                x++;

            }
        }
        arraypic = new String[]{"C:\\Users\\Zack\\Desktop\\pics\\stars.png",
                "C:\\Users\\Zack\\Desktop\\pics\\stars.png", "C:\\Users\\Zack\\Desktop\\pics\\orange.png",
                "C:\\Users\\Zack\\Desktop\\pics\\orange.png", "C:\\Users\\Zack\\Desktop\\pics\\purple.png",
                "C:\\Users\\Zack\\Desktop\\pics\\purple.png", "C:\\Users\\Zack\\Desktop\\pics\\red.png",
                "C:\\Users\\Zack\\Desktop\\pics\\red.png", "C:\\Users\\Zack\\Desktop\\pics\\yellow.png",
                "C:\\Users\\Zack\\Desktop\\pics\\yellow.png", "C:\\Users\\Zack\\Desktop\\pics\\emoji.png",
                "C:\\Users\\Zack\\Desktop\\pics\\emoji.png"};
        //Randomizes the array of pictures and puts them back into the array
        List<String> strList = Arrays.asList(arraypic);
        Collections.shuffle(strList);
        arraypic = strList.toArray(new String[strList.size()]);
        frame.setVisible(true);
            frame.addMouseListener(new MouseAdapter(){
               public void mouseClicked(MouseEvent me2){
                   //Keep track of where the mouse was clicked
                   int x = me2.getX();
                   int y = me2.getY();

                   //runs after both clicks have been made
                   //really runs after the third click if put at the end so it runs after the second card is selected
                   //the second picture doesn't show up
                   if(click1 && click2) {
                       frame.revalidate();
                       frame.repaint();
                       //if the cards are the same it changes the variable to 2 so the card is never selected again
                       if (x1 == x2) {
                           for(int i = 0; i < 12; i++) {
                               if (cardtrack[i] == 1) {
                                   cardtrack[i] = 2;
                               }
                           }
                       }
                       //if the cards don't match
                       else {
                           //removes the two pictures showing the back of the cards again
                           frame.remove(c2);
                           frame.remove(c3);

                       }

                       //resets all variables back
                       click1 = false;
                       click2 = false;
                       //if a card is still at one it means it was selected this turn and they didn't match
                       //this sets them back to zero so they can be selected again
                       for(int i = 0; i < 12; i++){
                           if (cardtrack[i]==1) {
                               cardtrack[i] = 0;

                               //puts the cards back for the missed guesses
                               if(i<=5){
                                   cardback2[i] = new JLabel(new ImageIcon("C:\\Users\\Zack\\Desktop\\pics\\card back1.png"));
                                   cardback2[i].setBounds(118 + i*227, 100, 177, 277);
                               }
                               else{
                                   cardback2[i] = new JLabel(new ImageIcon("C:\\Users\\Zack\\Desktop\\pics\\card back1.png"));
                                   cardback2[i].setBounds(118 + (i-6)*227, 477, 177, 277);
                               }
                               frame.add(cardback2[i]);
                           }
                       }
                       //reloads image
                       frame.revalidate();
                       frame.repaint();
                       //I think it is unnecessary but haven't messed with it yet
                       x1 = null;
                       x2 = null;
                   }


                   //if statements seeing if click was on a card and that card hadn't been selected
                   //All 12 work the same for the 12 cards just changing position on screen or position in array

                   if(x>=118 && x<=295 && y>=100 && y <= 377 && cardtrack[0]==0){

                       frame.remove(cardback2[0]);

                       //if it is the first picture selected
                       if(!click1) {
                           //puts the picture matching to this card in the randomized array over the card
                           c2 = new JLabel(new ImageIcon(arraypic[1]));
                           c2.setBounds(118, 100, 177, 277);
                           frame.add(c2, 0);
                           //to track which picture was used
                           x1 = arraypic[1];
                       }
                       //same but for second click
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[1]));
                           c3.setBounds(118, 100, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[1];
                       }
                       //refreshes the image to show new picture
                       //frame.remove(cardback2[0]);
                       frame.revalidate();
                       frame.repaint();
                       //keeps track of if this is the first or second picture selected
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       //sets this card to 1 to track that it was selected this turn
                       cardtrack[0]=1;
                   }
                   if(x>=345 && x<= 522 && y>=100 && y<= 377 && cardtrack[1]==0) {

                       frame.remove(cardback2[1]);

                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[2]));
                           c2.setBounds(345, 100, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[2];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[2]));
                           c3.setBounds(345, 100, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[2];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[1]=1;
                   }
                   if(x>=572 && x<= 749 && y>=100 && y <= 377 && cardtrack[2]==0) {
                       frame.remove(cardback2[2]);

                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[3]));
                           c2.setBounds(572, 100, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[3];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[3]));
                           c3.setBounds(572, 100, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[3];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[2]=1;
                   }
                   if(x>=799 && x<= 976 && y>=100 && y <= 377 && cardtrack[3]==0) {
                       frame.remove(cardback2[3]);
                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[4]));
                           c2.setBounds(799, 100, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[4];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[4]));
                           c3.setBounds(799, 100, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[4];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[3]=1;
                   }
                   if(x>=1026 && x<= 1203 && y>=100 && y <= 377 && cardtrack[4]==0) {
                       frame.remove(cardback2[4]);
                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[5]));
                           c2.setBounds(1026, 100, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[5];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[5]));
                           c3.setBounds(1026, 100, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[5];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[4]=1;
                   }

                   if(x>=1253 && x<= 1430 && y>=100 && y <= 377 && cardtrack[5]==0) {
                       frame.remove(cardback2[5]);
                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[6]));
                           c2.setBounds(1253, 100, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[6];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[6]));
                           c3.setBounds(1253, 100, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[6];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[5]=1;
                   }
                   if(x>=118 && x<=295 && y>=477 && y<= 754 && cardtrack[6]==0){
                       frame.remove(cardback2[6]);
                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[7]));
                           c2.setBounds(118, 477, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[7];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[7]));
                           c3.setBounds(118, 477, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[7];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[6]=1;
                   }
                   if(x>=345 && x<= 522 && y>=477 && y<= 754 && cardtrack[7]==0) {
                       frame.remove(cardback2[7]);
                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[8]));
                           c2.setBounds(345, 477, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[8];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[8]));
                           c3.setBounds(345, 477, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[8];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[7]=1;
                   }
                   if(x>=572 && x<= 749 && y>=477 && y<= 754 && cardtrack[8]==0) {
                       frame.remove(cardback2[8]);
                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[9]));
                           c2.setBounds(572, 477, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[9];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[9]));
                           c3.setBounds(572, 477, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[9];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[8]=1;
                   }
                   if(x>=799 && x<= 976 && y>=477 && y<= 754 && cardtrack[9]==0) {
                       frame.remove(cardback2[9]);
                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[10]));
                           c2.setBounds(799, 477, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[10];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[10]));
                           c3.setBounds(799, 477, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[10];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[9]=1;
                   }
                   if(x>=1026 && x<= 1203 && y>=477 && y<= 754 && cardtrack[10]==0) {
                       frame.remove(cardback2[10]);
                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[11]));
                           c2.setBounds(1026, 477, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[11];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[11]));
                           c3.setBounds(1026, 477, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[11];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[10]=1;
                   }

                   if(x>=1253 && x<= 1430 && y>=477 && y<= 754 && cardtrack[11]==0) {
                       frame.remove(cardback2[11]);
                       if(!click1) {
                           c2 = new JLabel(new ImageIcon(arraypic[0]));
                           c2.setBounds(1253, 477, 177, 277);
                           frame.add(c2, 0);
                           x1 = arraypic[0];
                       }
                       else{
                           c3 = new JLabel(new ImageIcon(arraypic[0]));
                           c3.setBounds(1253, 477, 177, 277);
                           frame.add(c3, 0);
                           x2 = arraypic[0];
                       }
                       frame.revalidate();
                       frame.repaint();
                       if(!click1)
                           click1 = true;
                       else
                           click2 = true;
                       cardtrack[11]=1;
                   }
               }
            });
    }

}