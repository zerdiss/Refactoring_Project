package video.rental.demo.domain;

import java.io.Serializable;


public abstract class MediaType implements Serializable {
    public static final int VHS = 1;
    public static final int CD = 2;
    public static final int DVD = 3;
    private final int type;

    public MediaType(int type) {
        this.type = type;
    }

    public int getVideoType() {
        return type;
    }

    abstract int getDaysRentedLimit();

    abstract public int getLateReturnPointPenalty();

    public static MediaType of(int type) {
        switch (type) {
            case VHS:
                return new VhsMediaType();
            case CD:
                return new CdMediaType();
            case DVD:
                return new DvdMediaType();
        }
        throw new IllegalArgumentException("Video " + type + " is not defined");
    }
}