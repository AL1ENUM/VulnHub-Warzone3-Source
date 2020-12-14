package alien;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StarterServer {

	public static final int port = 4444;
	public static ServerSocket ss = null;
	public static Socket socket = null;
	ObjectInputStream is;
	ObjectOutputStream os;
	Token token;

	public static void main(String[] args) throws ClassNotFoundException, IOException, NoSuchAlgorithmException, InterruptedException {

		
		
		new StarterServer().runServer();
		
		

	}

	public void runServer() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InterruptedException {

		try {
		
		ss = new ServerSocket(port, 200);

		while (true) {

		    socket = ss.accept();
			is = new ObjectInputStream(socket.getInputStream());
			os = new ObjectOutputStream(socket.getOutputStream());

			RE request = (RE) is.readObject();
			token = (Token) request.getToken();

			if (request.getOption().equals("LOGIN")) {

				auth(request.getValue());

			} else if (request.getOption().equals("VIEW") && request.getCmd().equals("LIST")) {

				if (token.getValue().equals(sha256("alienum"))
						&& token.getRole().equals("astronaut")) {

					Process p = Runtime.getRuntime().exec("ls");
					BufferedReader pRead = new BufferedReader(new InputStreamReader(p.getInputStream()));

					String line;
					StringBuilder sb = new StringBuilder();
					while ((line = pRead.readLine()) != null) {

						sb.append(line + "@");

					}

					RE rl = new RE();
					rl.setValue(sb.toString());
					os.writeObject(rl);
					os.flush();

				}

			} else if (request.getOption().equals("VIEW") && request.getValue().equals("VALUE")) {

				if (token.getValue().equals(sha256("alienum"))
						&& token.getRole().equals("astronaut")) {

					Process p = Runtime.getRuntime().exec(request.getCmd());
					BufferedReader pRead = new BufferedReader(new InputStreamReader(p.getInputStream()));

					String line;
					StringBuilder sb = new StringBuilder();
					while ((line = pRead.readLine()) != null) {

						sb.append(line + "@");

					}

					RE rl = new RE();
					rl.setValue(sb.toString());
					os.writeObject(rl);
					os.flush();

				}

			}
		}
		
		}
		catch(SocketException s) {
			ss.close();
			socket.close();
			ss = null;
			socket = null;
			Thread.sleep(3000);
			new StarterServer().runServer();
				
			}
			catch(IOException i) {
				ss.close();
				socket.close();
				ss = null;
				socket = null;
				Thread.sleep(3000);
				new StarterServer().runServer();
					
			}
		
		

	}

	private void auth(String value) throws IOException, NoSuchAlgorithmException {

		String[] credentials = value.split("@");

		if (credentials[0].equals("alienum") && credentials[1].equals("exogenesis")) {

			String tokenValue = sha256(credentials[0]);
			String role = "researcher";
			Token token = new Token(tokenValue, role);
			RE response = new RE();
			response.setToken(token);
			response.setValue("TRUE");
			os.writeObject(response);
			os.close();

		} else {

			RE response = new RE();
			response.setToken(null);
			response.setValue("FALSE");
			os.writeObject(response);
			os.close();
		}

	}

	private String sha256(String username) throws NoSuchAlgorithmException {

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(username.getBytes(StandardCharsets.UTF_8));

		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();

	}

}
