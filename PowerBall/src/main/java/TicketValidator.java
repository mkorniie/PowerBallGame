
import java.util.TreeSet;

/**
 * Created by Maria on 31.07.18.
 */
public class TicketValidator {

    public static boolean validateWhiteNumbers(TreeSet<Integer> whiteNumber)
    {
        if (whiteNumber == null || whiteNumber.size() != Game.WHITEBALLNUMBER)
            return false;
        for (Integer num: whiteNumber) {
            if (num < 1 || num > Game.WHITEBALLRANGE)
                return false;
        }
        return true;
    }

    public static boolean validatePowerBall(int powerBall)
    {
        if (powerBall < 1 || powerBall > Game.POWERBALLRANGE)
            return false;
        return true;
    }
}
