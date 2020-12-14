package alien;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Base64;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Starter extends JFrame implements ActionListener {

	Container container = getContentPane();
	JLabel userLabel = new JLabel("USERNAME");
	JLabel passwordLabel = new JLabel("PASSWORD");
	JTextField userTextField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton("LOGIN");
	JButton resetButton = new JButton("RESET");
	JButton uploadButton = new JButton("UPLOAD");
	JButton viewButton = new JButton("VIEW");
	JButton infoButton = new JButton("INFO");
	JCheckBox showPassword = new JCheckBox("Show Password");
	JTextArea field = new JTextArea();
	static String token;
	static String role;
	ObjectOutputStream os;
	ObjectInputStream is;
	Socket socket;

	// roles : researcher , astronaut

	public Starter() {

		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		showPassword.setBackground(Color.GRAY);
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		Starter frame = new Starter();
		frame.setTitle("WARZONE 3 | Login Form");
		frame.setVisible(true);
		frame.setBounds(10, 10, 370, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		Image image = new ImageIcon("aliens.png").getImage();
		frame.setIconImage(image);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Coding Part of LOGIN button
		if (e.getSource() == loginButton) {
			String userText;
			String pwdText;
			userText = userTextField.getText();
			pwdText = passwordField.getText();

			try {
				socket = new Socket("localhost", 4444);

				System.out.println("Client connected");
				os = new ObjectOutputStream(socket.getOutputStream());

				User login = new User(userText, pwdText, "researcher");
				os.writeObject(login);

				is = new ObjectInputStream(socket.getInputStream());
				Response response = (Response) is.readObject();
				Tokenyzer tokenyzer = response.getTokenyzer();
				os.close();
				socket.close();

				if (response.getMessage().equals("true")) {

					token = new String(Base64.getDecoder().decode(tokenyzer.getToken()));
					role = tokenyzer.getRole();
					JOptionPane.showMessageDialog(this, "Login Successful");
					dashboard();

				} else {
					JOptionPane.showMessageDialog(this, "Invalid Username or Password");
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		// Coding Part of RESET button
		if (e.getSource() == resetButton) {
			userTextField.setText("");
			passwordField.setText("");
		}
		// Coding Part of showPassword JCheckBox
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}

		}

		if (e.getSource() == viewButton) {

			if (role.equals("researcher")) {

				JOptionPane.showMessageDialog(this, "Permission Denied");
			} else if (role.equals("astronaut")) {

				JFrame view = new JFrame("View Reports");

			}

		}

		if (e.getSource() == uploadButton) {

			try {
				socket = new Socket("localhost", 4444);

				System.out.println("Client connected");

				os = new ObjectOutputStream(socket.getOutputStream());

				Request ftp = new Request("ftp_prepare");
				os.writeObject(ftp);

				os.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// FileChooser f = new FileChooser();

	

		}

	}

	public void setLayoutManager() {
		container.setLayout(null);
		container.setBackground(Color.GRAY);
	}

	public void setLocationAndSize() {
		// Setting location and Size of each components using setBounds() method.
		userLabel.setBounds(50, 150, 100, 30);
		passwordLabel.setBounds(50, 220, 100, 30);
		userTextField.setBounds(150, 150, 150, 30);
		passwordField.setBounds(150, 220, 150, 30);
		showPassword.setBounds(150, 250, 150, 30);
		loginButton.setBounds(50, 300, 100, 30);
		resetButton.setBounds(200, 300, 100, 30);
		uploadButton.setBounds(50, 300, 100, 30);
		viewButton.setBounds(200, 300, 100, 30);
		field.setBounds(20, 20, 320, 50);

	}

	public void addComponentsToContainer() {
		// Adding each components to the Container
		container.add(userLabel);
		container.add(passwordLabel);
		container.add(userTextField);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(resetButton);
	}

	public void addActionEvent() {
		loginButton.addActionListener(this);
		resetButton.addActionListener(this);
		showPassword.addActionListener(this);
		uploadButton.addActionListener(this);
		viewButton.addActionListener(this);
	}

	public void dashboard() {

		field.setText("This is a secret researching system.");
		field.append("\nHere you can submit or view reports about aliens behavior");
		field.setEditable(false);
		container.removeAll();
		setLayoutManager();
		container.add(uploadButton);
		container.add(viewButton);
		container.add(field);
		container.setLayout(new FlowLayout());
		container.repaint();

	}

}
