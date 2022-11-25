package video.rental.demo.domain;

public class CdVideoType extends VideoType {

    public CdVideoType() {
        super(Video.CD);
    }

    public int getDaysRentedLimit() {
        return 3;
    }

    public int getLateReturnPointPenalty() {
        return 2;
    }
}
