import java.util.LinkedHashMap;

/**
 * Created by Maria on 31.07.18.
 */
public class Payouts {
    private long jackPot;
    private LinkedHashMap<Integer, Long> prizePayouts;

    public Payouts()
    {
        jackPot = 40000000;
        prizePayouts = payouts();
    }

    public Payouts(LinkedHashMap<Integer, Long> prizePayouts, long jackPot)
    {
        if (jackPot <= 0)
            throw new IllegalArgumentException("jackPot must be more than zero: " + jackPot);
        if (prizePayouts == null)
                throw new IllegalArgumentException("prizePayouts can not be null");

        this.jackPot = jackPot;
        this.prizePayouts = prizePayouts;
    }

    private LinkedHashMap<Integer, Long> payouts() {

        LinkedHashMap<Integer, Long> newPayouts = new LinkedHashMap<Integer, Long>();

        /* key = (numberOfMatchingWhiteNumbers* 10) + matchPowerBall; */
        newPayouts.put(51, jackPot);
        newPayouts.put(50, (long)1000000);
        newPayouts.put(41, (long)50000);
        newPayouts.put(40, (long)100);
        newPayouts.put(31, (long)100);
        newPayouts.put(30, (long)7);
        newPayouts.put(21, (long)7);
        newPayouts.put(11, (long)4);
        newPayouts.put(01, (long)4);

        return newPayouts;
    }

    public long getJackPot() { return jackPot; }

    public LinkedHashMap<Integer, Long> getPrizePayouts() { return prizePayouts; }
}
