/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.matchinggame;

/**
 * Course: CSC190
 * Date: 3/24/2025
 * Author:Aarion LLoyd
 * Purpose: I want to make a matching game using the notes from class and lab as a base
 * 
 * 
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.lang.Thread;
import javax.swing.Timer;
public class MatchingGame
{
    //here i want to shuffle the contents of my text, as i used an 
    //array to store letter values for my buttons
    public static void shuffleArray(String [] arr)
    {
        //make my random variable
        Random rand = new Random();
        //loop through my array
        for (int i = 0; i < arr.length; i++) {
            //begin the swap starting at a random index
            int randomIndexToSwap = rand.nextInt(arr.length);
            //make a temp to get the string value
            String temp = arr[randomIndexToSwap];
            //then i switch the random index to wher e i is
            arr[randomIndexToSwap] = arr[i];
            //and i swap again for temp(this is jsut a recreation of bubble sort)
            arr[i] = temp;
    }
    }
    //This is where i will recolor my buttons at the end
    public static void recolor(JButton[][] buttons){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(Color.DARK_GRAY);
            }
            
        }
    }
    //these give us the screen size amd text size universally used in the pannel
    public static class Globals 
    {
        //scrreen size and text_field_size are established here for my grid abd b
    public static final Dimension screen_size = new Dimension(480, 480); 
    public static final Dimension text_field_size  = new Dimension(screen_size.width, 50);
    }
    public static class Text extends JPanel 
    {
    private String message;
    private final boolean isCentered; // New flag for alignment
    private Color fontColor;
    private final int fontSize;
    public Text(int x, int y, String message, boolean isCentered, Color color, Color fontColor, int fontSize) 
    {
        this.message = message;
        this.isCentered = isCentered;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
        
        // Define the bounds of the small panel
        this.setBackground(color);
        this.setOpaque(true); 
    }
    @Override
    protected void paintComponent(Graphics surface) 
    {
        super.paintComponent(surface);
        Graphics2D s2D = (Graphics2D) surface;

        s2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font font = new Font("Arial", Font.BOLD, fontSize);
        s2D.setFont(font);
        s2D.setColor(fontColor);

        FontMetrics fontMetrix = s2D.getFontMetrics();
        
        int textX;
        int textY;

        // Check the boolean flag
        if (isCentered) 
            // Calculation for centering inside the panel
            textX = (getWidth() - fontMetrix.stringWidth(message)) / 2;
        else 
            // Default "Top-Left" alignment (with a tiny 5px margin)
            textX = 5; 
        // center in height for both options
        textY = (getHeight() - fontMetrix.getHeight()) / 2 + fontMetrix.getAscent();

        s2D.drawString(message, textX, textY);
    }
    //return our dismensions
    @Override 
    public Dimension getPreferredSize()
    {
        return new Dimension(Globals.text_field_size.width, Globals.text_field_size.height);
    }
    //used to repaint our message
    public void setNewMessage(String message)
    {
        this.message = message;
        this.repaint();
    }
    }
    //here we reset our buttons to be enabled and the background as well
    public static void reset(JButton[][] buttons){
        for(int rows = 0; rows < 4; rows++){
                for(int collumns = 0; collumns< 4; collumns++){
                   buttons[rows][collumns].setEnabled(true);
                   buttons[rows][collumns].setBackground(Color.DARK_GRAY);
                   buttons[rows][collumns].setForeground(Color.DARK_GRAY);
                }
            }
    }
    public static class Matching
    {
        Timer timer;
        //my board and pannel
        JFrame board = new JFrame("Matching game");
        JPanel grid = new JPanel();
        //making my double array of buttons
        JButton[][] buttons = new JButton[4][4];
        //making my textMessage the user can see
        Text message = new Text(0,0, "Turnover first card", true, Color.darkGray, Color.white, 40); 
        //my if condition(that will run the game forever
        boolean gameOver = false;
        //my two buttons 
        JButton firstButton;
        JButton secondButton;
        //how i keep track of matches and cliceks the player does
        int click = 0;
        int matchFound;
        //an array of letters that will make sure all possible pairs can be made
        //Since i have a 4x4 i need 2 possible combinations for each
        String[] Letters = {"A","A","B","B","C","C","D","D","E","E","F","F","G","G","H","H"};   
        //random variable for my arrays methods outside the function
        Random rand = new Random();
        int count = 0;
        public void play()
    {
        //for borad and grid i set the default layout, a 4x4 with a background coulor of Dark gray, and the 
        //typical, exitOnclose, to close it when done, borderlayout for border along with resizable
        //to make sure 
        board.setSize(Globals.screen_size.width, Globals.screen_size.height);
        board.setLocationRelativeTo(null);
        board.setResizable(false);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setLayout(new BorderLayout());             
        board.add(message, BorderLayout.NORTH);
        grid.setLayout(new GridLayout(4,4));
        grid.setBackground(Color.darkGray);
        // add buttons to the grid
        //we have double arryas, so two for loops one to get the the row
//        gameOver = false; this will keep running since gameOver will always be false
        if(!gameOver){
            //running index would be able to make sure that i place my buttons at random
        int runningIndex = 0;
        //i shuffle my random arrya of letters
        shuffleArray(Letters);
        //make a double array loop going to 4 since i have a 4x4
       for(int i = 0; i < 4; i++)
       {
           for(int j = 0; j < 4; j++)
           {
                //make our button from the button class
                JButton button = new JButton();
                buttons[i][j] = button; // internal game logic
                button.setBackground(Color.darkGray); // bg color
                button.setForeground(Color.white); // text color
                //font of the color
                button.setFont(new Font("Arial", Font.BOLD, 100));
                button.setFocusable(false);
                button.setForeground(Color.darkGray);
                grid.add(button); // visual game
                button.setText(Letters[runningIndex]);
                runningIndex++;
                //button gets clicked so listener here
                button.addActionListener((ActionEvent e) ->
                {
                 //clicked button right here
                JButton clicked_button = (JButton)e.getSource();
                //increment click, as the user has clicked
                click++;
                //first click will be our first button
                if(click == 1)
                {
                    //so i set it to fistButton
                    firstButton = clicked_button;
                    //make it false so they cant click it again
                    firstButton.setEnabled(false);
                    //change the background to show that there was action here
                    firstButton.setBackground(Color.MAGENTA);
                }
                //next onto our second button
                if(click == 2)
                {
                    //renable our firstbutton so it doesnt stay on screen, and also 
                    //to check it against our second, as when set to false, it wouldnt
                    //mess up our "check"
                    firstButton.setEnabled(true);
                    //assign seconButton to clicked_button
                    secondButton = clicked_button;
                    //timer for timerTask
                    //change background to be the magents, to show action 
                    secondButton.setBackground(Color.MAGENTA);
                    //were checking the text content if thers a match
                    if(firstButton.getText().equals(secondButton.getText()))
                    {
                        //tell the user they found a match
                        message.setNewMessage("It's a match");
                        //increment match found
                        matchFound++;
                        //now i chose 8 sinc 2x 8 is 16 menaing there would have to be  8 matches
                        //at once for an ended game
                        if(matchFound==8){
                            //set messgae to game over
                        message.setNewMessage("Game Over!..reseting");
                        //reset by buttons,clicks,matchfound for next round
                        matchFound = 0;
                        click = 0;
                        firstButton = null;
                        secondButton = null;
                        //reshuffle my array again, same as at the start of the if
                        //statement
                        shuffleArray(Letters);
                        int index = 0;
                        for (int x = 0; x < 4; x++) {
                        for (int y = 0; y < 4; y++) {
                        buttons[x][y].setText(Letters[index]);
                        index++;
                        }
                        }
                        //recolor my buttons
                        recolor(buttons);
                        }
                        //disable the buttons so the user cant just click the same buttons
                        if(matchFound!=8){
                        firstButton.setEnabled(false);
                        secondButton.setEnabled(false);
                        }
                        //clicks go back to 0
                        click-=click;
                    }else
                    {
                        //no match in between them
                        message.setNewMessage("No match");
                        //hide the buttons
                        firstButton.setBackground(Color.DARK_GRAY);
                        secondButton.setBackground(Color.DARK_GRAY);
                        //Clicks go back to 0
                        click-=click;
                        //renable the button
                        clicked_button.setEnabled(true);
                    }
                }
                });
           }
       }
        //here i add the grid to the board
        board.add(grid);
        //I set the borad visibilty to true so the user can see it it
        board.setVisible(true);
        //then i call a random button
       }
    }
    }
    public static void main(String[] args)
    {
       //call our matching game 
       Matching game = new Matching();
       //call our game function iside matching
       game.play();
    }
}
