/**
 * Created by Maria on 01.08.18.
 */
public class Chance {

    private long probability;
    private long nOfWinners;

    public Chance(long probability) {
        if (probability < 0)
            throw new IllegalArgumentException("Probability must be a positive number: " + probability);
        this.probability = probability;
        this.nOfWinners = 0;
    }

    public long getProbability() { return probability; }

    public long getnOfWinners() { return nOfWinners; }

    public void setnOfWinners(long nOfWinners) {
        if (nOfWinners < 0)
            throw new IllegalArgumentException("Number of winners must be a positive number: " + nOfWinners);
        this.nOfWinners = nOfWinners;
    }
}
