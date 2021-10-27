package project2;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class GUIinfoPanel extends JPanel {
    GUI1024Board panel;
    private JLabel score, scoreText, numSlidesText, numSlides, numWins, numWinsText, numPlays, numPlaysText;
    private JLabel highscore, highscoreText, spliter;

    private Font scoreFont = new Font(Font.SANS_SERIF, Font.BOLD, 44);
    private Font myTextFont3Char = new Font(Font.SANS_SERIF, Font.BOLD, 32);
    private Font myTextFont4Char = new Font(Font.SANS_SERIF, Font.BOLD, 24);

    public GUIinfoPanel(GUI1024Board panel) {
        this.panel = panel;

        setPreferredSize(new Dimension(180, 450));

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        setBackground(Color.DARK_GRAY);

        scoreText =  new JLabel(" Score: ");
        scoreText.setFont(myTextFont3Char);
        scoreText.setForeground(Color.WHITE);
        add(scoreText);

        score = new JLabel(" " + String.valueOf(panel.gameLogic.getScore()), SwingConstants.LEFT);
        score.setFont(scoreFont);
        score.setForeground(Color.WHITE);
        add(score);

        spliter = new JLabel(" ------------------- ");
        spliter.setFont(myTextFont4Char);
        spliter.setForeground(Color.WHITE);
        add(spliter);

        numSlidesText = new JLabel(" Slides: ");
        numSlidesText.setFont(myTextFont4Char);
        numSlidesText.setForeground(Color.WHITE);
        add(numSlidesText);

        numSlides = new JLabel("  " + this.panel.gameLogic.getNumSlides());
        numSlides.setFont(myTextFont4Char);
        numSlides.setForeground(Color.WHITE);
        add(numSlides);

        highscoreText = new JLabel(" High Score: ");
        highscoreText.setFont(myTextFont4Char);
        highscoreText.setForeground(Color.WHITE);
        add(highscoreText);

        highscore = new JLabel("  " + this.panel.gameLogic.getHighestScore());
        highscore.setFont(myTextFont4Char);
        highscore.setForeground(Color.WHITE);
        add(highscore);

        numPlaysText = new JLabel(" Plays: ");
        numPlaysText.setFont(myTextFont4Char);
        numPlaysText.setForeground(Color.WHITE);
        add(numPlaysText);

        numPlays = new JLabel("  " + this.panel.gameLogic.getNumPlays());
        numPlays.setFont(myTextFont4Char);
        numPlays.setForeground(Color.WHITE);
        add(numPlays);

        numWinsText = new JLabel(" Wins: ");
        numWinsText.setFont(myTextFont4Char);
        numWinsText.setForeground(Color.WHITE);
        add(numWinsText);

        numWins = new JLabel("  " + this.panel.gameLogic.getNumWins());
        numWins.setFont(myTextFont4Char);
        numWins.setForeground(Color.WHITE);
        add(numWins);
    }

    public void updateAfterSlide() {
        this.score.setText(" " + formatScore(this.panel.gameLogic.getScore()));
        this.numSlides.setText("  " + this.panel.gameLogic.getNumSlides());
    }

    public void updateNumWins() {
        this.numWins.setText("  " + this.panel.gameLogic.getNumWins());
    }

    public void updateHighestScore() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###");
        this.highscore.setText("  " + decimalFormat.format( this.panel.gameLogic.getHighestScore()));
    }

    public void updateNumPlays() {
        this.panel.gameLogic.incNumPlays();
        this.numPlays.setText("  " + this.panel.gameLogic.getNumPlays());
    }

    public String formatScore(float score) {
        String arr[] = {"", "K", "M", "B"};
        int i = 0;
        while ((score / 1000) >= 1) {
            score = score / 1000;
            if (i < 4)
                i++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s%s", decimalFormat.format(score), arr[i]);
    }

}
