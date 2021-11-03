package test;

// edit these imports according to your project

import SearchStuff.BFSSearcher;
import SearchStuff.Searcher;
import ServerStuff.ClientHandler;
import ServerStuff.GameClientHandler;
import ServerStuff.Server;
import ServerStuff.SimpleServer;
import SolutionStuff.CacheManager;
import SolutionStuff.SimpleCacheManager;
import SolutionStuff.Solver;
import SolutionStuff.SolverAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


public class TestSetter {
	
	public static void setClasses(DesignTest dt){
		
		// set the server's Interface, e.g., "Server.class"
		// don't forget to import the correct package e.g., "import server.Server"
		dt.setServerInteface(Server.class);
		// now fill in the other types according to their names
		dt.setServerClass(SimpleServer.class);
		dt.setClientHandlerInterface(ClientHandler.class);
		dt.setClientHandlerClass(GameClientHandler.class);
		dt.setCacheManagerInterface(CacheManager.class);
		dt.setCacheManagerClass(SimpleCacheManager.class);
		dt.setSolverInterface(Solver.class);
		dt.setSolverClass(SolverAdapter.class);
	}
	
	// run your server here
	//	static Server s;
	//	public static void runServer(int port){
	//	s=new MyServer(port);
	//	s.start(new MyClientHandler());

	static final PrintStream SysOutBckup = System.out;

	static Server s;

	public static void runServer(int port){

//		disableSysOut();

		SimpleServer server = new SimpleServer(port);
		s = server;

		SimpleCacheManager cache = new SimpleCacheManager();
		Searcher searcher = new BFSSearcher();
		SolverAdapter solver = new SolverAdapter(searcher);
		ClientHandler ch = new GameClientHandler(cache, solver);

		server.startServer(ch);
}
	// stop your server here
	public static void stopServer(){
		s.stopServer();

//		enableSysOut();
	}


//	public static void disableSysOut() {
//		System.setOut(new PrintStream(new OutputStream() {
//			@Override
//			public void write(int arg0) throws IOException {}
//		}));
//	}
//	public static void enableSysOut() {
//		System.setOut(SysOutBckup);
//	}

}
