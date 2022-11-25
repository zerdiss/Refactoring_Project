package video.rental.demo.domain;

public class DvdVideoType extends VideoType {
    public DvdVideoType() {
        super(Video.DVD);
    }
    public int getDaysRentedLimit() {
        return 5;
    }

    public int getLateReturnPointPenalty() {
        return 3;
    }
}
