package Search;

import Game.GameMap;
import Search.VanilaAlgorithms.GenericState;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GameMapState extends GenericState<GameMap> {

	private String LastMove;

	public GameMapState(GameMap newRep, double cost) {
		super(newRep, cost);
	}

	public GameMapState(GameMap newRep, double cost, String LastMove, GameMapState parentState) {
		super(newRep, cost, parentState);
		this.LastMove = LastMove;
	}

	public String getMove() {
		return this.LastMove;
	}

	public GameMap getGameMap() {
		return this.getStateRepresentor();
	}

};

//public class GameMapState //extends State
////implements Comparable<GameMapState>
//{
//	private final GameMap curState;
//	double cost;
//	private GameMapState parentState;
//	private String LastMove;	// can be refactored as object(3 ints)
//
//
//	public GameMapState(GameMap newState, double cost) {
//		this.cost = cost;
//		curState = newState;
//		parentState = null;
//		LastMove = null;
//		System.out.println("Created init state (parent&last = null;)");
//	}
//
//	public GameMapState(GameMap newState, double cost,
//						String LastMove, GameMapState parentState) {
//		this.cost = cost;
//		this.curState = newState;
//		this.LastMove = LastMove;
//		this.parentState = parentState;
//	}
//
//	public GameMapState getParentState() {
//		return parentState;
//	}
//
//	public String getMove() {
//		return LastMove;
//	}
//
//	public GameMap getGameMap() {
//		return curState;
//	}
//
//
//	public boolean betterThen(GameMapState anotherState) {
//		return this.cost <= this.cost;
//	}
//
//
//////	public List<GameMapState> getAllPossibleStates(HashSet<String> pipesAlreadySeen) {
//////		return this.curState.getAllPossibleStates(this, pipesAlreadySeen);
//////	}
//////	public boolean isGoalState() {
//////		return curState.isGoalState();
//////	}
//
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o) return true;
//		if (o == null || getClass() != o.getClass()) return false;
//
//		GameMapState state = (GameMapState) o;
//
//		if (curState != null ? !curState.equals(state.curState) : state.curState != null) return false;
//		return true;
//	}
//
//	public int hashCode() {
//		return this.curState.hashCode();
//	}
//
//	public static Comparator<GameMapState> getComperator(){
//		return new Comparator<GameMapState>() {
//			@Override
//			public int compare(GameMapState o1, GameMapState o2) {
//				if (o1.cost < o2.cost)          return -1;
//				else if (o1.cost == o2.cost)    return 0;
//				else                            return 1;
//			}
//		};
//	}
//}
