package video.rental.demo.domain;

public class VhsVideoType extends VideoType {

    public VhsVideoType() {
        super(Video.VHS);
    }

    public int getDaysRentedLimit() {
        return 2;
    }

    public int getLateReturnPointPenalty() {
        return 1;
    }
}
