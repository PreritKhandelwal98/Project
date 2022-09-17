
package com.MyGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Prerit
 */
public class MyGame extends JFrame implements ActionListener{
    
    JLabel heading,clockLabel;
    Font font = new Font("",Font.BOLD,40);
    JPanel mainPanel;
    
    JButton []btns = new JButton[9];
    
    //game instance variable
    int gameChances[] = {2,2,2,2,2,2,2,2,2};
    int activePlayer=0;
    
    
    int wp[][] = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
        };
    int winner = 2;
    
    boolean gameOver = false;
    
    MyGame(){
        System.out.println("Creating game instance");
        setTitle("My Tic Tac Toe Game..");
        setSize(450,450);
        ImageIcon icon = new ImageIcon("src/img/icon.jpg");
        setIconImage(icon.getImage());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }
    
    private void createGUI(){
        this.getContentPane().setBackground(Color.decode("#2196f3"));
        
        //This layout devide the frame into NORTH,SOUTH,EAST,WEST portion
        this.setLayout(new BorderLayout());
        
        //NORTH portion
        
        //Asigning heading in the north
        heading = new JLabel("Tic Tac Toe");
        //heading.setIcon(new ImageIcon("src/img/icon.jpg"));
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.black);
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        
        this.add(heading,BorderLayout.NORTH);
        
        
        //SOUTH portion
        
        //creating object of JLabel for clock
        clockLabel = new JLabel("CLOCK");
        clockLabel.setForeground(Color.white);
        
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        this.add(clockLabel,BorderLayout.SOUTH);
        
        Thread t = new Thread(){
            @Override
            public void run(){
                try{
                    while(true){
                        String dateTime = new Date().toLocaleString();
                        clockLabel.setText(dateTime);
                        Thread.sleep(1000);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
            
        //panel section
        
        mainPanel = new JPanel();
        //grid layout sets the main panel in 3x3 frame set
        mainPanel.setLayout(new GridLayout(3,3));
        
        for(int i=1;i<=9;i++){
           JButton btn = new JButton(); 
           //btn.setIcon(new ImageIcon("src/img/zero.png"));
           btn.setBackground(Color.decode("#000"));
           btn.setFont(font);
           
           //adding button to main pannel
           mainPanel.add(btn);
           
           btns[i-1] = btn;
           btn.addActionListener(this);
           btn.setName(String.valueOf(i-1));
        }
        
        this.add(mainPanel,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentButton = (JButton)e.getSource();
        
        String nameStr = currentButton.getName();
        
        
        int name = Integer.parseInt(nameStr.trim());
        
        if(gameOver == true){
            JOptionPane.showMessageDialog(this, "Game Already Over....");
            return;
        }
        
        if(gameChances[name] == 2){
            if(activePlayer == 1){
                currentButton.setIcon(new ImageIcon("src/img/one.png"));
                gameChances[name] = activePlayer;
                activePlayer = 0;
            }else{
                currentButton.setIcon(new ImageIcon("src/img/zero_2.jpg"));
                gameChances[name] = activePlayer;
                activePlayer = 1;
            }
            
            for(int []temp:wp){
                if((gameChances[temp[0]] == gameChances[temp[1]]) && (gameChances[temp[1]] == gameChances[temp[2]]) && gameChances[temp[2]]!=2){
                    winner = gameChances[temp[0]];
                    gameOver = true;
                    JOptionPane.showMessageDialog(null,"Player "+winner+" has won the game...");
                    int i = JOptionPane.showConfirmDialog(this, "Do you want to play more??");
                    if(i==0){
                        this.setVisible(false);
                        new MyGame();
                    }else if(i==1){
                        System.exit(34234);
                    }else{
                        
                    }
                    System.out.print(i);
                    break;
                }
            }
            
            //draw logic
            
            int c = 0 ;
            
            for(int x:gameChances){
                if(x==2){
                    c++;
                    break;
                }
            }
            
            if(c==0 && gameOver==false){
                JOptionPane.showMessageDialog(null, "It's a Draw...");
                
                int i = JOptionPane.showConfirmDialog(this,"Play More...");
                
                if(i==0){
                    this.setVisible(false);
                    new MyGame();
                }else if(i==1){
                    System.exit(2323);
                }else{
                    
                }
                
                gameOver = true;
            }
            
        }else{
            JOptionPane.showMessageDialog(this, "Position already occupied...");
        }
    }
}
