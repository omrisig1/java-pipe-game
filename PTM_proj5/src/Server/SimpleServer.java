package Server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SimpleServer implements Server {

//	private static int TIMEOUT = 60000; //3000;
	private static int TIMEOUT = 1000;

	private int port;
	private boolean running;

	public SimpleServer(int port) {
		this.port = port;
	}

	@Override
	public void startServer(ClientHandler ch) {		//throws Exception {

		running = true;
		new Thread( ()->serverLogic(ch) ).start();

	}

	@Override
	public void stopServer() {
		running = false;
	}
//	public void stopServer() {
//		running = false;
//	}

	private void serverLogic(ClientHandler ch)
	{
		try {
			ServerSocket vSock = new ServerSocket(port);
			vSock.setSoTimeout(TIMEOUT);
			// System.out.print("server is up.");

			while (this.running)
			{
				Socket aClient;
				try { aClient = vSock.accept(); }
				catch (SocketTimeoutException TO) { continue; }		//System.out.print(".");
				//System.out.println("new connection: "+aClient.toString());

				InputStream clInput = aClient.getInputStream();
				OutputStream clOutput = aClient.getOutputStream();

				ch.handleClient(clInput, clOutput);
				//System.out.println("handle OK, closing all.");

				clInput.close();
				clOutput.close();

				aClient.close();
			}
			
			vSock.close();
			//System.out.println("client closed, all closed OK");
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private void serverLogic(ClientHandler ch)
//	{
//		try {
//			ServerSocket vSock = new ServerSocket(port);
//			vSock.setSoTimeout(TIMEOUT);
//			// should throw specific
//
//			while (this.running) {
//				Socket aClient = vSock.accept();
//				System.out.println("new connection: "+aClient.toString());		// FIX 60sec T.O to 1 sec with continue
//
//				InputStream clInput = aClient.getInputStream();
//				OutputStream clOutput = aClient.getOutputStream();
//
//				ch.handleClient(clInput, clOutput);
//				System.out.println("handle OK, closing all.");
//
//				clInput.close();
//				clOutput.close();
//
//				aClient.close();
//			}
//
//			vSock.close();
//			System.out.println("client closed, all closed OK");
//		}
//
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
