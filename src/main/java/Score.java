import java.io.Serializable;
import java.util.Objects;

public class Score implements Serializable, Comparable<Score> {

    public final int score;
    public final String name;

    @Override
    public String toString() {
        return "scoreLabel :" + score +
                ", name '" + name + '\'' +
                '}';
    }

    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score)) return false;
        Score score1 = (Score) o;
        return score == score1.score &&
                Objects.equals(name, score1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, name);
    }

    @Override
    public int compareTo(Score score) {
        return Integer.compare(score.score, this.score);
    }
}
