package video.rental.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

public abstract class VideoType implements Serializable {
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