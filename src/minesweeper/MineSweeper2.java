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
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;

public class MineSweeper2 extends JFrame implements KeyListener {

    private JButton[][] b = new JButton[8][8];
    private Set<JButton> mines = new HashSet<>();
    private Color def = (new JButton()).getBackground();
    private static int won;
    private static int lost;
    private boolean ctrlPressed = false;
    private boolean allDisabled = false;

    /**
     * The Constructor creates a JFrame , initializes the buttons , adds an
     * action listener for each button , and add keyListener. In the
     * actionPerformed , it checks if the ctrl is pressed and the button is not
     * marked, marks the button yellow , otherwise puts the button color to the
     * default color. If the ctrl is not pressed , it disables the button and
     * calls the mine counter. after initializing the buttons , it calls the
     * setRandomMine.
     */
    public MineSweeper2() {

        super("Mine Sweeper");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setLocation(234, 234);
        setVisible(true);
        setFocusable(true);
        addKeyListener(this);
        setLayout(new GridLayout(8, 8));

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                final int x = j;
                final int y = i;

                b[j][i] = new JButton();

                b[j][i].addActionListener(new ActionListener() {
                    @Override

                    public void actionPerformed(ActionEvent ae) {

                        if (ctrlPressed) {
                            if (b[x][y].getBackground() != Color.YELLOW) {
                                b[x][y].setBackground(Color.YELLOW);
                            } else {
                                b[x][y].setBackground(def);
                            }

                        } else {
                            b[x][y].setEnabled(false);

                            enable(x, y, mineCounter(x, y));;

                        }
                    }

                });
                b[j][i].addKeyListener(this);
                add(b[j][i]);
                setVisible(true);
            }
        }

        setRandomMine();
    }

    /**
     * chooses ten random cells on the board and assigns them to mine and stores
     * them in the Set mines. HashSet is used to prevent duplicate mines
     */
    public void setRandomMine() {
        for (int i = 1; i <= 10; i++) {
            int row = (int) (Math.random() * 8);
            int col = (int) (Math.random() * 8);
            mines.add(b[row][col]);
            if (mines.size() != i) {
                i--;
            }
        }
    }

    /**
     *
     * @param x takes the number of rows of the array
     * @param y takes the number of columns of the array the integer local
     * variable mine is by default 0 and counts the number of the mines around a
     * cell. If the current cell is not a mine ,and if it is already marked sets
     * it's color to default color.If the adjacent(not diagonals) cell is safe
     * it calls the mineCounter and passes the row and column of the adjacent
     * cell. If the current cell is mine it marks it red , disables all the
     * buttons and increments the number of lost games. If the game hasWinner
     * and the buttons are not disabled previously ,it disables all the buttons
     * and increment the number of won games
     */
    public int mineCounter(int x, int y) {

        int mine = 0;

        if (!isMine(b[x][y])) {

            if (b[x][y].getBackground() == Color.YELLOW) {
                b[x][y].setBackground(def);
            }

            b[x][y].setEnabled(false);
            if (y > 0 && isMine(b[x][y - 1])) {
                mine++;
            } else if (y > 0 && isEnabled(b[x][y - 1]) && isZero(x, y - 1)) {
                mineCounter(x, y - 1);
                enable(x, y - 1, 0);
            }

            if (y < 7 && isMine(b[x][y + 1])) {
                mine++;
            } else if (y < 7 && isEnabled(b[x][y + 1]) && isZero(x, y + 1)) {
                mineCounter(x, y + 1);
                enable(x, y + 1, 0);
            }

            //number of mines horizontaly
            if (x > 0 && isMine(b[x - 1][y])) {
                mine++;
            } else if (x > 0 && isEnabled(b[x - 1][y]) && isZero(x - 1, y)) {
                mineCounter(x - 1, y);
                enable(x - 1, y, 0);
            }
            if (x < 7 && isMine(b[x + 1][y])) {
                mine++;
            } else if (x < 7 && isEnabled(b[x + 1][y]) && isZero(x + 1, y)) {
                mineCounter(x + 1, y);
                enable(x + 1, y, 0);
            }
            //daigonals
            if (x > 0 && y > 0 && isMine(b[x - 1][y - 1])) {
                mine++;
            }

            if (x < 7 && y < 7 && isMine(b[x + 1][y + 1])) {
                mine++;
            }

            if (x < 7 && y > 0 && isMine(b[x + 1][y - 1])) {
                mine++;
            }
            if (x > 0 && y < 7 && isMine(b[x - 1][y + 1])) {
                mine++;
            }

        } else if (isMine(b[x][y])) {

            b[x][y].setBackground(Color.RED);
            lost++;
            MineWindow.lost.setText("lost:" + lost);
            MineWindow.display.setText("you lost");
            DisableAll();
            allDisabled = true;
            return -1;
        }

        if (hasWinner() && !allDisabled) {
            DisableAll();
            allDisabled = true;
            won++;
            MineWindow.display.setText("you won");
            MineWindow.won.setText("won:" + won);

        }

        revalidate();
        return mine;

    }

    /**
     * checks whether all the safe cells are disabled
     *
     * @return true if all the safe cells are disabled , otherwise returns false
     */
    public boolean isZero(int x, int y) {
        int mine = 0;

        if (y > 0 && isMine(b[x][y - 1])) {
            mine++;
        }

        if (y < 7 && isMine(b[x][y + 1])) {
            mine++;
        }

        //number of mines horizontaly
        if (x > 0 && isMine(b[x - 1][y])) {
            mine++;
        }

        if (x < 7 && isMine(b[x + 1][y])) {
            mine++;
        }

        //daigonals
        if (x > 0 && y > 0 && isMine(b[x - 1][y - 1])) {
            mine++;
        }

        if (x < 7 && y < 7 && isMine(b[x + 1][y + 1])) {
            mine++;
        }

        if (x < 7 && y > 0 && isMine(b[x + 1][y - 1])) {
            mine++;
        }

        if (x > 0 && y < 7 && isMine(b[x - 1][y + 1])) {
            mine++;
        }

        if (mine == 0) {
            return true;
        } else {
            return false;
        }

    }

    public void enable(int x, int y, int mine) {
        System.out.println(x + " " + y);

        b[x][y].setText(String.valueOf(mine));
    }

    public boolean hasWinner() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!isMine(b[i][j]) && isEnabled(b[i][j])) {

                    return false;
                }
            }
        }

        return true;
    }

    /**
     * checks whether the button b is equal to any of the buttons in mines.
     *
     * @param b the JButton object passed to this method.
     * @return true if b is a mine , otherwise return false.
     */
    public boolean isMine(JButton b) {

        for (JButton jb : mines) {
            if (jb == b) {
                return true;
            }
        }
        return false;

    }

    /**
     * overloads the isEnabled ()
     *
     * @param b takes the JButton object
     * @return true if b is enabled , otherwise return false
     */
    public boolean isEnabled(JButton b) {
        return b.isEnabled();
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        ctrlPressed = ke.isControlDown();

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        ctrlPressed = ke.isControlDown();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    /**
     * Disables all the buttons
     */
    public void DisableAll() {

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                b[i][j].setEnabled(false);
            }
        }

    }

}
