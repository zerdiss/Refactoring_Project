package video.rental.demo.domain;

public abstract class VideoType {
    private final int type;

    public VideoType(int type) {
        this.type = type;
    }

    public int getVideoType() {
        return type;
    }

    abstract int getDaysRentedLimit();

    abstract public int getLateReturnPointPenalty();

    public static VideoType of(int type) {
        switch (type) {
            case Video.VHS:
                return new VhsVideoType();
            case Video.CD:
                return new CdVideoType();
            case Video.DVD:
                return new DvdVideoType();
        }
        throw new IllegalArgumentException("Video " + type + " is not defined");
    }
}