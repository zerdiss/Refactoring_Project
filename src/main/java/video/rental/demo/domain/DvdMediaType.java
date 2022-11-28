package video.rental.demo.domain;

public class DvdMediaType extends MediaType {
    public DvdMediaType() {
        super(MediaType.DVD);
    }
    public int getDaysRentedLimit() {
        return 5;
    }

    public int getLateReturnPointPenalty() {
        return 3;
    }
}
