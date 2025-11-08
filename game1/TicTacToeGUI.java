package game1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI extends JFrame {
    private static final int SIZE = 3;
    private final JButton[][] buttons;
    private char currentPlayer;
    private int player1Score, player2Score;
    private final JLabel scoreLabel;

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe Game");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buttons = new JButton[SIZE][SIZE];
        currentPlayer = 'X';
        player1Score = 0;
        player2Score = 0;

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.CYAN, 0, getHeight(), Color.PINK);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        scoreLabel = new JLabel("Player 1 (X): 0 | Player 2 (O): 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.DARK_GRAY);
        topPanel.add(scoreLabel, BorderLayout.CENTER);


        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        JButton newGameBtn = new JButton("New Game");
        JButton resetBtn = new JButton("Reset Score");

        newGameBtn.addActionListener(e -> resetBoard());
        resetBtn.addActionListener(e -> {
            player1Score = 0;
            player2Score = 0;
            updateScoreLabel();
            resetBoard();
        });

        controlPanel.add(newGameBtn);
        controlPanel.add(resetBtn);
        topPanel.add(controlPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);


        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE, 5, 5));
        gridPanel.setOpaque(false);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                gridPanel.add(buttons[i][j]);
            }
        }
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }


    private class ButtonClickListener implements ActionListener {
        private final int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                buttons[row][col].setForeground(currentPlayer == 'X' ? Color.BLUE : Color.RED);

                if (checkWin(currentPlayer)) {
                    highlightWinningCombo(currentPlayer);
                    JOptionPane.showMessageDialog(TicTacToeGUI.this, "Player " + currentPlayer + " wins!");
                    updateScore();
                    resetBoard();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(TicTacToeGUI.this, "It's a draw!");
                    resetBoard();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
        }
    }

    private void resetBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.WHITE);
            }
        }
        currentPlayer = 'X';
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < SIZE; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(player)) &&
                buttons[i][1].getText().equals(String.valueOf(player)) &&
                buttons[i][2].getText().equals(String.valueOf(player))) return true;

            if (buttons[0][i].getText().equals(String.valueOf(player)) &&
                buttons[1][i].getText().equals(String.valueOf(player)) &&
                buttons[2][i].getText().equals(String.valueOf(player))) return true;
        }

        if (buttons[0][0].getText().equals(String.valueOf(player)) &&
            buttons[1][1].getText().equals(String.valueOf(player)) &&
            buttons[2][2].getText().equals(String.valueOf(player))) return true;

        return buttons[0][2].getText().equals(String.valueOf(player)) &&
               buttons[1][1].getText().equals(String.valueOf(player)) &&
               buttons[2][0].getText().equals(String.valueOf(player));
    }

    private void highlightWinningCombo(char player) {
        Color winColor = new Color(144, 238, 144); 
        for (int i = 0; i < SIZE; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(player)) &&
                buttons[i][1].getText().equals(String.valueOf(player)) &&
                buttons[i][2].getText().equals(String.valueOf(player))) {
                buttons[i][0].setBackground(winColor);
                buttons[i][1].setBackground(winColor);
                buttons[i][2].setBackground(winColor);
            }
            if (buttons[0][i].getText().equals(String.valueOf(player)) &&
                buttons[1][i].getText().equals(String.valueOf(player)) &&
                buttons[2][i].getText().equals(String.valueOf(player))) {
                buttons[0][i].setBackground(winColor);
                buttons[1][i].setBackground(winColor);
                buttons[2][i].setBackground(winColor);
            }
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (buttons[i][j].getText().equals("")) return false;
        return true;
    }

    private void updateScore() {
        if (currentPlayer == 'X') player1Score++;
        else player2Score++;
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Player 1 (X): " + player1Score + " | Player 2 (O): " + player2Score);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGUI::new);
    }
}
