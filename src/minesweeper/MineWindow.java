/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

/**
 *
 * @author Yasaman Sabbagh Ziarani
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class MineWindow extends JFrame implements ActionListener {

    protected JButton start;
    protected static JLabel won ;
    protected static JLabel lost;
    protected static JLabel display ;

    /**
     * the constructor creates a JFrame,with a  start button , lost and won
     * games label and display label and adds an action listener for the start
     * button.
     */
    public MineWindow() {

        setSize(700, 700);
        setLocationRelativeTo(null);
        setLocation(234, 234);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridLayout(2, 4));

        start = new JButton("PRESS HERE TO START");
        lost = new JLabel("lost:0", SwingConstants.CENTER);
        won = new JLabel("won:0", SwingConstants.CENTER);
        display = new JLabel("you have not started any game",
                SwingConstants.CENTER);

        lost.setOpaque(true);
        lost.setBackground(Color.RED);
        lost.setFont(new Font(lost.getFont().getName(), Font.PLAIN, 30));

        won.setOpaque(true);
        won.setBackground(Color.GREEN);
        won.setFont(new Font(won.getFont().getName(), Font.PLAIN, 30));


        start.setFont(new Font(start.getFont().getName(), Font.PLAIN, 25));
        start.addActionListener(this);

        display.setFont(new Font(start.getFont().getName(), Font.PLAIN, 20));
        display.setOpaque(true);
        display.setBackground(Color.BLUE);


        add(start);
        add(display);
        add(lost);
        add(won);
        revalidate();

    }

    @Override
    // craetes a mineSweeper object when the satrt button is pressed passes this
    // MineWindow to setMW() in MineSweeper
    public void actionPerformed(ActionEvent ae) {
        MineSweeper2 mine = new MineSweeper2();

    }


}
