import java.util.TreeSet;

/**
 * Created by Maria on 30.07.18.
 */
public class Ticket {
    private static long ticketCounter;
    private final long id = assignID();
    private TreeSet<Integer> whiteNumbers;
    private int powerBall;
    private PowerPlayOptions powerPlay;

    private static long assignID(){ return ticketCounter++; }

    private Ticket() { }

    public Ticket(TreeSet<Integer> whiteNumbers, int powerBall, PowerPlayOptions powerPlay) {
        if (!TicketValidator.validateWhiteNumbers(whiteNumbers))
             throw new IllegalArgumentException("Invalid white numbers set:" + whiteNumbers);
        if (!TicketValidator.validatePowerBall(powerBall))
            throw new IllegalArgumentException("Invalid powerball: " + powerBall);
        this.whiteNumbers = whiteNumbers;
        this.powerBall = powerBall;
        this.powerPlay = powerPlay;
    }

    public TreeSet<Integer> getWhiteNumbers() {
        return whiteNumbers;
    }

    public long getId() { return id; }

    public int getPowerBall() {
        return powerBall;
    }

    public PowerPlayOptions getPowerPlay() { return powerPlay; }

    public static Ticket newRandomTicket(){
        TreeSet<Integer> newWhiteNumbers = new TreeSet<Integer>();
        int newPowerBall;
        PowerPlayOptions newPowerPlay;

        while (newWhiteNumbers.size() != Game.WHITEBALLNUMBER)
            newWhiteNumbers.add((int) ((Math.random() * (Game.WHITEBALLRANGE - 1)) + 1));

        newPowerBall = (int) ((Math.random() * (Game.POWERBALLRANGE - 1)) + 1);
        newPowerPlay = PowerPlayOptions.values()[(int) ((Math.random() * Game.WHITEBALLNUMBER))];
        return (new Ticket(newWhiteNumbers, newPowerBall, newPowerPlay));
    }

    @Override
    public String toString() {
        return "Ticket {" +
                "id=" + id +
                ", whiteNumbers=" + whiteNumbers +
                ", powerBall=" + powerBall +
                ", powerPlay=" + powerPlay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (powerBall != ticket.powerBall) return false;
        if (!whiteNumbers.equals(ticket.whiteNumbers)) return false;
        return powerPlay == ticket.powerPlay;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + whiteNumbers.hashCode();
        result = 31 * result + powerBall;
        result = 31 * result + powerPlay.hashCode();
        return result;
    }
}
