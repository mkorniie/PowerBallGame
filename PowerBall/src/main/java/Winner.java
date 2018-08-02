/**
 * Created by Maria on 31.07.18.
 */
public class Winner {

    private Ticket ticket;
    private long sum;

    public Winner(Ticket ticket, long sum) {
        if (ticket == null)
            throw new IllegalArgumentException("Ticket can not be null");
        if (sum <= 0)
            throw new IllegalArgumentException("Sum must be more than zero: " + sum);
        this.ticket = ticket;
        this.sum = sum;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        if (ticket == null)
            throw new IllegalArgumentException("Ticket can not be null");
        this.ticket = ticket;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        if (sum <= 0)
            throw new IllegalArgumentException("Sum must be more than zero: " + sum);
        this.sum = sum;
    }
}
