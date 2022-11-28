package video.rental.demo;

import video.rental.demo.application.Interactor;
import video.rental.demo.infrastructure.RepositoryCustomerImpl;
import video.rental.demo.infrastructure.RepositoryVideoImpl;
import video.rental.demo.presentation.GraphicUI;
import video.rental.demo.util.SampleGenerator;

public class Main {

	public static void main(String[] args) {
		RepositoryCustomerImpl repoCustomer = new RepositoryCustomerImpl();
		RepositoryVideoImpl repoVideo = new RepositoryVideoImpl();
		new SampleGenerator(repoCustomer, repoVideo).generateSamples();
		Interactor interactor = new Interactor(repoCustomer, repoVideo);

		GraphicUI gui = new GraphicUI(interactor);
		gui.start();
	}
}
