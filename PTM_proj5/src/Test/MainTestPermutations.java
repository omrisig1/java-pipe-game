//package Test;
//import Game.BasicGameMap;
//import Game.BasicGameMapGenerator;
//import Game.GameMap;
//import Game.Tile.AbstractBasicTile;
//import Game.Tile.PermutableTile;
//import Search.BFSSearcher;
//import Search.Searcher;
//import Server.ClientHandler;
//import Server.GameClientHandler;
//import Server.SimpleServer;
//import Solution.SimpleCacheManager;
//import Solution.SolverAdapter;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//public class MainTestPermutations {
//
//	static Socket sock;
//	static BufferedReader fromServer;
//	static PrintWriter toServer;
//	static final PrintStream SysOutBckup = System.out;
//
//	public static void main(String[] args)
//	{
//		ClientHandler ch = getCH();
//		SimpleServer server = getServer();
//		server.startServer(ch);
//
//		BasicGameMap mapPerm = getInitMap();
//
//		ArrayList<Long> timesList = new ArrayList();
//		int rows = mapPerm.getRows();
//		int cols = mapPerm.getCols();
//		int counter=1;
//
//		long time;
//		for (int repeats = 0; repeats < 1; repeats++)
//		for (int i=0; i<rows; i++)
//		for (int j=0; j<cols; j++) {
//
//			BasicGameMap mapResult = mapPerm.cloneMap();
//			try {
//				int p = (int) Math.floor(Math.random() * 4 + 1);
//				for (; p>=1; p--)
//					((PermutableTile)mapResult.getTiles()[i][j]).nextPermute();
//			}
//			catch (Exception e) { continue; }
//
//			System.out.println("iter #"+counter);	counter++;
//			connectAsClient();
//			disableSysOut();
//			time = System.nanoTime();
//			ClientSim(mapResult);
//
//			time = System.nanoTime() - time;
//			time = TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
//			enableSysOut();
//
//			boolean asd = mapResult.isGoalState();
//			String solOKstr = mapResult.isGoalState() ? "OK" : "BAD";
//			System.out.println("server returned in "+time+
//					"ms, solution is "+solOKstr);
//
//			timesList.add(time);
//
//			closeClient();
//			System.out.println("----------------------------");
////			break;
//		}
//
//		server.stopServer();
//
//		System.out.println("\n\nAll Run Times: (Total "+counter+" iterations)");
//		timesList.sort( Long::compare );
//		timesList.forEach(
//				(t) -> System.out.print(t+", ")
//		); System.out.println();
//
//		return;
//
//	}
//	public static BasicGameMap getInitMap() {
//
//		List<String> mapStrings = new LinkedList<>();
//
//		mapStrings.add( "|F|||" );
//		mapStrings.add( "-FJs-" );
//		mapStrings.add( "-7JF7" );
//		mapStrings.add( "-|FJ-" );
//		mapStrings.add( "gF-|F" );
//
////		mapStrings.add( "|F|||" );	// still problem, about 1min
////		mapStrings.add( "-FJs-" );
////		mapStrings.add( "-7LF7" );
////		mapStrings.add( "-|FJ-" );
////		mapStrings.add( "gF-|F" );
//
////		mapStrings.add( "s--JF7-||-F7JLF|" );
////		mapStrings.add( " F|LF-|L7 FJ7- F" );
////		mapStrings.add( "7||--FJ7F7J7--||" );
////		mapStrings.add( "7LFJ-||-LF7-F|F7" );
////		mapStrings.add( " F7-----|JJJg7--" );
////		mapStrings.add( "F7LLF---|7  |-  " );
//
//
//		BasicGameMap initMap = BasicGameMapGenerator.parseMap(mapStrings);
//		return initMap;
//	}
//
//	public static SimpleServer getServer() {
//		Server.SimpleServer server = new SimpleServer(1234);
//		return server;
//	}
//	public static ClientHandler getCH() {
//		SimpleCacheManager cache = new SimpleCacheManager();
//		Searcher s = new BFSSearcher();
//		SolverAdapter solver = new SolverAdapter(s);
//		ClientHandler ch = new GameClientHandler(cache, solver);
//		return ch;
//	}
//
//	public static boolean connectAsClient() {
//		try {
//			sock = new Socket("127.0.0.1", 1234);
//			fromServer = new BufferedReader(new InputStreamReader( sock.getInputStream() ));
//			toServer = new PrintWriter( sock.getOutputStream() );
//		}
//		catch (IOException e) { System.out.println("error with socket"); return false; }
//		return true;
//	}
//	public static boolean closeClient() {
//		try {
//			if (sock != null)		{ sock.close(); 		sock = null;		}
//			if (fromServer != null)	{ fromServer.close(); 	fromServer = null;	}
//			if (toServer != null)	{ toServer.close(); 	toServer = null;	}
//		}
//		catch (IOException e) { System.out.println("error with close"); return false; }
//		return true;
//	}
//
//	public static void  ClientSim(BasicGameMap mapResult)
//	{
//		toServer.println(mapResult.toString() + "done");
//		toServer.flush();
//		StringBuilder allMoves = new StringBuilder();
//
//		do {
//			String move = "";
//
//			try { move = fromServer.readLine(); }
//			catch (Exception e) {}
//
//			allMoves.append(move).append('\n');
//			if (move.equals("done")) break;
//
//			String moveData[] = move.split(",");
//			int r = Integer.parseInt(moveData[0]);
//			int c = Integer.parseInt(moveData[1]);
//			int p = Integer.parseInt(moveData[2]);
//
//			AbstractBasicTile tiles[][] = mapResult.getTiles();
//			for (; p>=1; p--)
//				if (tiles[r][c] instanceof PermutableTile)
//					((PermutableTile)tiles[r][c]).nextPermute();
//
//		} while(true);
//
//		System.out.println("moves recvd:");
//		System.out.println(allMoves);
//	}
//
//	public static void disableSysOut() {
//		System.setOut(new PrintStream(new OutputStream() {
//			@Override
//			public void write(int arg0) throws IOException {}
//		}));
//	}
//	public static void enableSysOut() {
//		System.setOut(SysOutBckup);
//	}
//}
