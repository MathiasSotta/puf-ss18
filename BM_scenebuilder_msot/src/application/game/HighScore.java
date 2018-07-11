package application.game;

public class HighScore {
    private String playerOne;
    private String playerTwo;

    private Integer playerOneScore;
    private Integer playerTwoScore;

    private String date;

    public HighScore() {
        playerOneScore = 0;
        playerTwoScore = 0;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(String playerTwo) {
        this.playerTwo = playerTwo;
    }

    public void setPlayerOneScore(Integer playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public void setPlayerTwoScore(Integer playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public Integer getPlayerOneScore() {
        return playerOneScore;
    }

    public Integer getPlayerTwoScore() {
        return playerTwoScore;
    }

    public String getDate() {
        return date;
    }
}
