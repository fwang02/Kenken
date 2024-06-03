package Domain;

/**
 * @author Feiyang Wang
 */
public class PlayerScore {
    private final String name;
    private final int maxScore;

    public PlayerScore(String name, int maxScore) {
        this.name = name;
        this.maxScore = maxScore;
    }

    public String getName() {
        return name;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
