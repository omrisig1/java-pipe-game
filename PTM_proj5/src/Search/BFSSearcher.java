package Search;

import Game.GameMap;
import Search.Exceptions.UnsolvableProblemException;
import Search.VanilaAlgorithms.BestFS;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class BFSSearcher extends BestFS<GameMap> {}

/*
public class BFSSearcher	//<T extends Searchable>
extends CommonPrQueueSearcher {

	@Override
	public List<GameMapState> search(Searchable problem)
    throws UnsolvableProblemException {

		System.out.println("starting BFS searcher:");	// DEBUG/LOG

		this.initOpenList();
		HashSet<GameMapState> finished = new HashSet<>();

		GameMapState source = problem.getInitState();
		this.addToOpenList(source);

		while(!openQueue.isEmpty())
		{
			GameMapState curState = openQueue.poll();
			finished.add(curState);

			if (problem.isGoalState(curState)) {    // should happen assuming problem is solvable!
				return backTrace(curState, source);
			}

			List<GameMapState> allSuccessors = problem.getAllPossibleStates(curState);
			for (GameMapState succs : allSuccessors)
			{
				if (finished.contains(succs))
					continue;

				else if (!openQueue.contains(succs))
					addToOpenList(succs);

				else if ( openQueue.removeIf((iState) ->
                            iState.equals(succs) && succs.betterThen(iState)) )
						addToOpenList(succs);
			}
		}

		throw new UnsolvableProblemException();
	}

	public List<GameMapState> backTrace(GameMapState goalState, GameMapState startState)
	{
		LinkedList<GameMapState> trace = new LinkedList<>();
		GameMapState curState = goalState;

		// fixed this crap after realizing the OpenQueue was never reset after use.
		// see: CommonPrQ.initOpenList()
		;
//		/////////////////////////
//		if (startState == null) {
//			System.out.println("WHY startState, WHYYYYY");
//			return null;
//		}
//
//		GameMapState tmpState = goalState;
//		do {
//			if (tmpState == null)
//				System.out.println("WHYYYYYYYYYYYYYY");
//			tmpState = tmpState.getParentState();
//		} while (tmpState != null && !tmpState.equals(startState));
//
//		if (tmpState == null)
//			System.out.println("WHYYYYYYYYYYYYYY_FIN");
//		//////////////////////////

		while (!curState.equals(startState)) {
			trace.addFirst(curState);
			curState = curState.getParentState();
		}
		trace.addFirst(startState);

		return trace;
	}

}
*/


;
//
//
//public class BFSSearcher	//<T extends Searchable>
//extends CommonPrQueueSearcher {
//
//	@Override
//	public List<GameMapState> search(Searchable problem)
//    throws UnsolvableProblemException {
//
//		System.out.println("starting BFS searcher:");	// DEBUG/LOG
//
//		this.initOpenList();
//		HashSet<GameMapState> finished = new HashSet<>();
//		HashSet<String> pipesAlreadySeen = new HashSet<>();
//
//		GameMapState source = problem.getInitState();
//		this.addToOpenList(source);
//
//		while(!openQueue.isEmpty())
//		{
//			GameMapState curState = openQueue.poll();
////			System.out.println(curState.getGameMap().toString());
//			finished.add(curState);
//
//			if (curState.isGoalState()) {    // should happen assuming problem is solvable!
//				return backTrace(curState, source);
//			}
//
//			List<GameMapState> allSuccessors = curState.getAllPossibleStates(pipesAlreadySeen);
////			List<GameMapState> allSuccessors = curState.getAllPossibleStates();
//		for (GameMapState succs : allSuccessors)
//		{
//		if (finished.contains(succs))
//		continue;
//
//		else if (!openQueue.contains(succs))
//		addToOpenList(succs);
//
//		else if ( openQueue.removeIf((iState) ->
//		iState.equals(succs) && succs.betterThen(iState)) )
//		addToOpenList(succs);
//		}
//
//		}
//
//		throw new UnsolvableProblemException();
//		}
//
//public List<GameMapState> backTrace(GameMapState goalState, GameMapState startState)
//		{
//		LinkedList<GameMapState> trace = new LinkedList<>();
//		GameMapState curState = goalState;
//
//		// fixed this crap after realizing the OpenQueue was never reset after use.
//		// see: CommonPrQ.initOpenList()
//		;
////		/////////////////////////
////		if (startState == null) {
////			System.out.println("WHY startState, WHYYYYY");
////			return null;
////		}
////
////		GameMapState tmpState = goalState;
////		do {
////			if (tmpState == null)
////				System.out.println("WHYYYYYYYYYYYYYY");
////			tmpState = tmpState.getParentState();
////		} while (tmpState != null && !tmpState.equals(startState));
////
////		if (tmpState == null)
////			System.out.println("WHYYYYYYYYYYYYYY_FIN");
////		//////////////////////////
//
//		while (!curState.equals(startState)) {
//		trace.addFirst(curState);
//		curState = curState.getParentState();
//		}
//		trace.addFirst(startState);
//
//		return trace;
//		}
//
//		}
//
//
//

;

////////////////////////////////////////

;
/*
public class BFSSearcher<T extends Searchable>
extends CommonPrQueueSearcher<T> {

	@Override
	public List<State_old> search(Searchable problem)
    throws UnsolvableProblemException {

		State_old<T> source = problem.getInitState();
		this.addToOpenList(source);
		HashSet<State_old> finished = new HashSet<>();

		while(!openQueue.isEmpty())
		{
			State_old<T> curState = openQueue.poll();
			finished.add(curState);

			if (problem.isGoalState(curState)) {    // should happen assuming problem is solvable!
				return backTrace(curState, source);
			}

			List<State_old> allSuccessors = curState.getStateT().getAllPossibleStates();
			for (State_old succs : allSuccessors)
			{
				if (finished.contains(succs))
					continue;

				else if (!openQueue.contains(succs))
					addToOpenList(succs);

				else if ( openQueue.removeIf((iState) ->
                            iState.equals(succs) && succs.betterThen(iState)) )
						addToOpenList(succs);
			}
		}

		throw new UnsolvableProblemException();
	}

	public List<State_old> backTrace(State_old<T> goalState, State_old<T> startState)
	{
		LinkedList<State_old> trace = new LinkedList<>();
		State_old curState = goalState;

		while (!curState.equals(startState)) {
			trace.addFirst(curState);
			curState = curState.getParentState();
		}
		trace.addFirst(curState);

		return trace;
	}


}

 */
