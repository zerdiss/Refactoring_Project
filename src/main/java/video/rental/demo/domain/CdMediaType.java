package video.rental.demo.domain;

public class CdMediaType extends MediaType {

    public CdMediaType() {
        super(MediaType.CD);
    }

    public int getDaysRentedLimit() {
        return 3;
    }

    public int getLateReturnPointPenalty() {
        return 2;
    }
}
