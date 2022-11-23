package video.rental.demo.presentation;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerListModel;

import video.rental.demo.application.Interactor;

@SuppressWarnings("serial")
public class GraphicUI extends JFrame {

	private JTextField userCodeField;
	private JTextField nameField;
	private JTextField birthdayField;

	private JTextField titleField;

	private JSpinner priceCodeSpinner;
	private JSpinner videoTypeSpinner;
	private JSpinner ratingSpinner;

	private SpinnerListModel priceCodeModel;
	private SpinnerListModel videoTypeModel;
	private SpinnerListModel ratingModel;

	private JTextArea textArea;

	private Interactor interactor;

	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GraphicUI(Interactor interactor) {
		initialize();
		this.interactor = interactor;
	}

	/**
	 * Initialize the contents of the
	 */
	private void initialize() {
		setBounds(100, 100, 680, 422);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblWelcomeToSs = new JLabel("Welcome To Premier Video Shop");
		lblWelcomeToSs.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblWelcomeToSs.setBounds(208, 20, 240, 16);
		getContentPane().add(lblWelcomeToSs);

		makeButton("Register User", (e) -> registerUser(), 18, 54, 117, 29);

		makeLabel("User Code:", 147, 59, 70, 16);
		userCodeField = new JTextField();
		makeTextField(userCodeField, 217, 54, 50, 26);

		makeLabel("Name:", 280, 59, 61, 16);
		nameField = new JTextField();
		makeTextField(nameField, 324, 54, 120, 26);

		makeLabel("Birthday:", 462, 59, 60, 16);
		birthdayField = new JTextField();
		makeTextField(birthdayField, 520, 54, 96, 26);

		makeSeparator(18, 86, 583, 1);

		makeButton("Register Video", (e) -> registerVideo(), 18, 95, 117, 29);

		makeLabel("Title:", 147, 100, 61, 16);
		titleField = new JTextField();
		makeTextField(titleField, 182, 95, 100, 26);

		makeLabel("Price Code:", 294, 100, 75, 16);
		priceCodeModel = new SpinnerListModel(new String[] { "Regular", "New", "Children" });
		makeSpinner(priceCodeSpinner, priceCodeModel, 362, 95, 75, 26);

		makeLabel("Type:", 445, 100, 61, 16);
		videoTypeModel = new SpinnerListModel(new String[] { "VHS", "CD", "DVD" });
		makeSpinner(videoTypeSpinner, videoTypeModel, 480, 95, 55, 26);

		makeLabel("Rating:", 544, 100, 61, 16);
		ratingModel = new SpinnerListModel(new String[] { "Twelve", "Fifteen", "Eighteen" });
		makeSpinner(ratingSpinner, ratingModel, 590, 95, 70, 26);

		makeSeparator(18, 136, 583, 16);

		makeButton("Rent", (e) -> rentVideo(), 18, 148, 117, 29);
		makeButton("Return", (e) -> returnVideo(), 136, 148, 117, 29);
		makeButton("Clear", (e) -> clear(), 484, 149, 117, 29);

		makeSeparator(18, 186, 583, 16);

		makeButton("List Customers", (e) -> listCustomers(), 18, 193, 130, 29);
		makeButton("List Videos", (e) -> listVideos(), 146, 193, 117, 29);
		makeButton("Customer Report", (e) -> getCustomerReport(), 297, 193, 138, 29);
		makeButton("Clear Customer Rentals", (e) -> clearRentals(), 427, 193, 174, 29);

		textArea = new JTextArea(6, 80);
		textArea.setEditable(false);
		textArea.setVisible(true);

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(18, 249, 640, 133);
		getContentPane().add(scroll);
	}

	private void makeSpinner(JSpinner spinner, SpinnerListModel model, int x, int y, int width, int height) {
		spinner = new JSpinner(model);
		spinner.setBounds(x, y, width, height);
		getContentPane().add(spinner);
	}

	private void makeTextField(JTextField textField, int x, int y, int width, int height) {
		textField.setBounds(x, y, width, height);
		getContentPane().add(textField);
		textField.setColumns(10);
	}

	private void clear() {
		nameField.setText("");
		userCodeField.setText("");
		titleField.setText("");
		textArea.setText("");
	}

	private void clearRentals() {
		int code = Integer.parseInt(userCodeField.getText().toString());

		String result = interactor.clearRentals(code);

		textArea.append(result);
	}

	private void getCustomerReport() {
		int code = Integer.parseInt(userCodeField.getText().toString());

		String result = interactor.getCustomerReport(code);

		textArea.append(result);
	}

	private void listVideos() {
		textArea.append("List of videos\n");
		textArea.append(interactor.listVideos());
		textArea.append("End of list\n");
	}

	private void listCustomers() {
		textArea.append("List of customers\n");
		textArea.append(interactor.listCustomers());
		textArea.append("End of list\n");
	}

	private void returnVideo() {
		int customerCode = Integer.parseInt(userCodeField.getText().toString());
		String videoTitle = titleField.getText().toString();

		interactor.returnVideo(customerCode, videoTitle);
	}

	private void rentVideo() {
		int customerCode = Integer.parseInt(userCodeField.getText().toString());
		String videoTitle = titleField.getText().toString();

		interactor.rentVideo(customerCode, videoTitle);
	}

	private void registerUser() {
		int code = Integer.parseInt(userCodeField.getText().toString());
		String name = nameField.getText().toString();
		String birthday = birthdayField.getText().toString();

		interactor.registerCustomer(name, code, birthday);
	}

	private void registerVideo() {
		String title = titleField.getText().toString();
		String videoTypeString = videoTypeSpinner.getValue().toString();
		int videoType;
		if (videoTypeString.equals("Regular"))
			videoType = 1;
		else if (videoTypeString.equals("New"))
			videoType = 2;
		else // Children
			videoType = 3;

		String priceCodeString = priceCodeSpinner.getValue().toString();
		int priceCode;
		if (priceCodeString.equals("VHS"))
			priceCode = 1;
		else if (priceCodeString.equals("CD"))
			priceCode = 2;
		else // DVD
			priceCode = 3;

		String ratingString = ratingSpinner.getValue().toString();
		int videoRating;
		if (ratingString.equals("Twelve"))
			videoRating = 1;
		else if (ratingString.equals("Fifteen"))
			videoRating = 2;
		else // Eighteen
			videoRating = 3;

		interactor.registerVideo(title, videoType, priceCode, videoRating);
	}

	private void makeButton(String title, ActionListener listener, int x, int y, int w, int h) {
		JButton button = new JButton(title);
		button.addActionListener(listener);
		button.setBounds(x, y, w, h);
		getContentPane().add(button);
	}

	private void makeLabel(String title, int x, int y, int w, int h) {
		JLabel label = new JLabel(title);
		label.setBounds(x, y, w, h);
		getContentPane().add(label);
	}

	private void makeSeparator(int x, int y, int w, int h) {
		JSeparator separator = new JSeparator();
		separator.setBounds(x, y, w, h);
		getContentPane().add(separator);
	}
}
