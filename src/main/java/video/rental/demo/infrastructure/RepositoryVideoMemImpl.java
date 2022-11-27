package video.rental.demo.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import video.rental.demo.domain.Video;

public class RepositoryVideoMemImpl extends RepositoryVideoImpl {

    private HashMap<String, Video> videos = new LinkedHashMap<>();

	@Override
	public List<Video> findAllVideos() {
		return new ArrayList<>(videos.values());
	}

	@Override
	public Video findVideoByTitle(String title) {
		return videos.get(title);
	}

	@Override
	public void saveVideo(Video video) {
		videos.put(video.getTitle(), new Video(video));
	}
}
