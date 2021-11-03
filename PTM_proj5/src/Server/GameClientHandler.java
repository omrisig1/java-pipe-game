package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Game.BasicGameMapGenerator;
import Game.GameMap;
import Solution.CacheManager;
import Solution.GameMapSolution;
import Solution.Solver;

public class GameClientHandler //<P,S>
implements ClientHandler {

	private Solution.CacheManager<GameMap> cache;
	private Solution.Solver<GameMap,GameMapSolution> solver;

	public GameClientHandler(CacheManager cache, Solver solver) {
		this.cache = cache;
		this.solver = solver;
	}

	@Override
	public void handleClient(InputStream clientInput, OutputStream clientOutput) {
		
		//System.out.println("new handle!");
		long beginTime = System.nanoTime();

		BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientInput));
		PrintWriter clientOut = new PrintWriter(clientOutput);

		List<String> textPipeMap = readPipeGameMap(clientIn, "done");

		GameMap mapToHandle = BasicGameMapGenerator.parseMap(textPipeMap);
		List<String> movesToReturn;
		GameMap solvedMap;
		GameMapSolution solution;

//		solvedMap = null;
		solvedMap = cache.fetchSolution(mapToHandle);
		if(solvedMap != null) {
			//System.out.println("solved map exists in cache!\nMap:\n"+solvedMap);
			solution = new GameMapSolution(mapToHandle, solvedMap);
		}

		else {
			solution = solver.solveMap(mapToHandle);

			solvedMap = solution.getSolvedMap();
			cache.cacheSolution(solvedMap);
		}

		movesToReturn = solution.getMoves();
		sendPipeGameMoves(movesToReturn, "done", clientOut);

		long endTime = System.nanoTime();
		long secs = TimeUnit.SECONDS.convert(endTime - beginTime, TimeUnit.NANOSECONDS);
		long mili = TimeUnit.MILLISECONDS.convert(endTime - beginTime, TimeUnit.NANOSECONDS);
		//System.out.println("done handle!" + "\t["+secs+"sec, "+mili+"ms]");
	}


	private List<String> readPipeGameMap(BufferedReader anInput, String stopStr) {

		//System.out.println("receiving:");	// DEBUG/LOG

		List<String> output = new LinkedList<>();;
		String line;
		try {
			while(!(line=anInput.readLine()).equals(stopStr)) {
				output.add(line);
				//System.out.println(line);	// DEBUG/LOG
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}

	private void sendPipeGameMoves(List<String> moves, String lastCommand, PrintWriter target) {

		//System.out.println("moves:");	// DEBUG/LOG

		if (moves != null)
		for (String move : moves) {
			target.println(move);
			target.flush();
			//System.out.println(move);	// DEBUG/LOG
		}

		target.println(lastCommand);
		target.flush();
	}


}


//					// ELIYAHU DEMO-LIKE TEST
//	@Override
//	public void handleClient(InputStream clientInput, OutputStream clientOutput) {
//
//		System.out.println("new handle!");
//
//		BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientInput));
//		PrintWriter serverOut = new PrintWriter(System.out);
//
//		List<String> textPipeMap = new LinkedList<>();
//
//		BufferedReader serverAns = new BufferedReader(new InputStreamReader(System.in));
//		PrintWriter clientOut = new PrintWriter(clientOutput);
//
//		System.out.println("recv from client...");
//		readLineAndSend(clientIn, serverOut, "done");
//
//		// LOGIC!!! ~~~~
//
//		System.out.println("send to client...");
//		readLineAndSend(serverAns, clientOut, "done");
//
//		System.out.println("finished handle!");
//
//		/*
//		clientInput >> curMapState;
//		curMap.nutral().hash();	 //(in CM)
//
//		sol = curMap >> CacheMan;
//		if (sol != null)	send steps to client!;
//		else
//
//		curMap >> sol = Solver(curMap);
//		sol.solvedState >> Cache(solved)
//		send steps to client!
//
//		*/
//
//	}
//
//	//private void CLItest()
//	private void readLineAndSend(BufferedReader anInput, PrintWriter anOutput, String stopStr) {
//
//		String line;
//		try {
//			while(!(line=anInput.readLine()).equals(stopStr)) {
//				anOutput.println(line);
//				anOutput.flush();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
