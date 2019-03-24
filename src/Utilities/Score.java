package Utilities;

public class Score implements Comparable {

    private final int score;
    private final String date;

    public Score(int score, String date) {
        this.score = score;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compareTo(Object o) {
        int objectScore = ((Score) o).getScore();
        return objectScore - this.getScore();
    }

}
