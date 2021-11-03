package Solution;

import Game.GameMap;
//import Search.Solution.ComputableSolution;

import java.util.List;

public interface Solver<Problem, Solution> {

	public Solution solveMap(Problem unsolved);
//	public GameMapSolution solveMap(GameMap unsolved);

	//public GameMapSolution solveMapAndCache(GameMap unsolved, CacheManager cache);
	//public GameMapSolution CacheSolution(GameMap unsolved, CacheManager cache);

	// TODO_DONE - can be refactored
	// TODO_DONE - return object Solution, less dependent!
}
