package video.rental.demo.domain;

public class VhsMediaType extends MediaType {

    public VhsMediaType() {
        super(MediaType.VHS);
    }

    public int getDaysRentedLimit() {
        return 2;
    }

    public int getLateReturnPointPenalty() {
        return 1;
    }
}
