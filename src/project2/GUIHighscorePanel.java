package project2;

import javax.swing.*;
import java.awt.*;

public class GUIHighscorePanel extends JPanel {
    GUI1024Panel panel;
    private JLabel score, scoreText;
    private JPanel scorePanel, highscoresPanel;

    private Font myTextFont = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    private Font myTextFont3Char = new Font(Font.SANS_SERIF, Font.BOLD, 32);
    private Font myTextFont4Char = new Font(Font.SANS_SERIF, Font.BOLD, 24);

    public GUIHighscorePanel(GUI1024Panel panel) {
        this.panel = panel;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        scorePanel = new JPanel();
        highscoresPanel = new JPanel();

        scorePanel.setLayout(new GridLayout(2,1));
        scorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scorePanel.setBackground(Color.DARK_GRAY);

        add(scorePanel);
        add(highscoresPanel);

        scoreText =  new JLabel(" Score: ");
        scoreText.setFont(myTextFont);
        scoreText.setForeground(Color.WHITE);
        scorePanel.add(scoreText);

        score = new JLabel("  " + String.valueOf(panel.gameLogic.getScore()), SwingConstants.LEFT);
        score.setFont(myTextFont);
        score.setForeground(Color.WHITE);
        scorePanel.add(score);



    }

    public void updateScore() {
        this.score.setText("  " + this.panel.gameLogic.getScore());
    }

}
