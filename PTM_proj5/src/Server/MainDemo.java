package Server;

import Game.GameMap;
import Search.BFSSearcher;
import Search.Searcher;
import Search.VanilaAlgorithms.BestFS;
import Search.VanilaAlgorithms.GenericState;
import Search.VanilaAlgorithms.HillClimbing;
import Solution.SimpleCacheManager;
import Solution.SolverAdapter;

public class MainDemo {

	public static void main(String[] args) {

		SimpleServer server;
		server = new SimpleServer(6400);

		SimpleCacheManager cache = new SimpleCacheManager();
//		DemoSolver solver = new DemoSolver();
		Searcher s = new BestFS();
		SolverAdapter solver = new SolverAdapter(s);
		ClientHandler ch = new GameClientHandler(cache, solver);

		server.startServer(ch);

		// server.stopServer();

	}

}

//		Searcher s = new DFS();

//		Searcher s = new HillClimbing<GameMap>( new HillClimbing.StateGrader<GameMap>() {
//			@Override
//			public double calcStateGrade(GenericState<GameMap> state) {
//				return 1.0 / (state.getCost());
//			}
//		} );

