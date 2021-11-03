package Solution;

import Game.GameMap;
import Search.GameMapState;

import java.util.LinkedList;
import java.util.List;

public class GameMapSolution {

	private List<String> moves;
	private GameMap solvedState;

	public GameMapSolution(List<GameMapState> solStates) {

		this.solvedState = solStates.get(solStates.size()-1).getGameMap();

		this.moves = new LinkedList<>();
		for(GameMapState s : solStates) {
			String move = s.getMove();
			if (move != null)
				moves.add(move);
		}

	}

	public GameMapSolution(GameMap source, GameMap target) {

		this.solvedState = target;
		this.moves = source.deltaMoves(target);
	}

	public List<String> getMoves() {
		return moves;
	}

	public GameMap getSolvedMap() {
		return solvedState;
	}

	// reminder: for cache manager: saving the solvedState (as GameMap!!)
	// reminder: for Solver or ClientHandler:	getting list of moves (getter)
}
