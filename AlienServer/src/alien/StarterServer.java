package alien;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class StarterServer {

	public static final int port = 4444;
	private ServerSocket ss = null;

	public static void main(String[] args) throws ClassNotFoundException, IOException {

		System.out.println("Server started");
		new StarterServer().runServer();

	}

	public void runServer() throws IOException, ClassNotFoundException {
		ss = new ServerSocket(port,200);
		
		while(true) {

		Socket socket = ss.accept();
		ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

		User login = (User) is.readObject();
		boolean auth = authUser(login);

		Tokenyzer tokenyzer = null;
		if (auth) {

		   tokenyzer = new Tokenyzer(Base64.getEncoder().encodeToString(
					"8c6e1bc1bbc0a22f7804676db2c810429ad56bf1622d7b97f573ce736122e775".getBytes()),"researcher");
			Response response = new Response("true",tokenyzer);
			os.writeObject(response);
			
			

		} else {

			 tokenyzer = new Tokenyzer(Base64.getEncoder().encodeToString(
						"fail".getBytes()),"fail");
				Response response = new Response("fail",tokenyzer);
			os.writeObject(response);

		}
		
		Request request = (Request) is.readObject();
		if(request.getOption().equals("ftp_prepare")) {
			
			FTP_Server ftp = new FTP_Server();
			ftp.startFTP();
			
		}
		

		//
		is.close();
		socket.close();
		}
		
		
	}

	private boolean authUser(User login) {

		System.out.println(login);
		if (login.getUsername().equals("alienum") && login.getPassword().equals("exogenesis")) {
			if (login.getRole().equals("researcher")) {

				return true;
			}
		}
		return false;
	}

}
