package video.rental.demo.infrastructure;

import video.rental.demo.domain.Repository;
import video.rental.demo.domain.Video;

import javax.persistence.TypedQuery;
import java.util.List;

public class RepositoryVideoImpl extends Repository {

    public List<Video> findAllVideos() {
        TypedQuery<Video> query = getEm().createQuery("SELECT c FROM Video c", Video.class);
        return query.getResultList();
    }

    public Video findVideoByTitle(String title) {
        return find(() -> getEm().find(Video.class, title));
    }

    public void saveVideo(Video video) {
        doIt(video, getEm()::persist);
    }
}
