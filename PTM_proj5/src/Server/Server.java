package Server;

public interface Server {

	void startServer(ClientHandler ch) throws Exception;
	void stopServer();

//	void start(ClientHandler ch) throws Exception;
//	void stop();

}
