package Search.VanilaAlgorithms;

import Search.Exceptions.UnsolvableProblemException;
//import Search.CommonPrQueueSearcher;
import Search.GameMapState;
import Search.Searchable;
import Search.Searcher;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class BestFS<R>
		implements Searcher<R>
{

	protected PriorityQueue<GenericState<R>> openQueue;
	protected void initOpenList() {
		this.openQueue = new PriorityQueue<>(100, GenericState.getComperator());
	}

	@Override
	public List<GenericState<R>> search(Searchable problem) throws UnsolvableProblemException
	{
		//System.out.println("starting BFS searcher:");	// DEBUG/LOG

//		PriorityQueue<GenericState<R>> openQueue = new PriorityQueue<>();
		this.initOpenList();
		HashSet<GenericState<R>> finished = new HashSet<>();

		GenericState source = problem.getInitState();
		openQueue.add(source);

		while(!openQueue.isEmpty())
		{
			GenericState<R> curState = openQueue.poll();
			finished.add(curState);

			if (problem.isGoalState(curState)) {    // should happen assuming problem is solvable!
				return CommonTooling.backTrace(curState, source);
			}

			List<GenericState<R>> allSuccessors = problem.getAllPossibleStates(curState);
			for (GenericState<R> succs : allSuccessors)
			{
				if (finished.contains(succs))
					continue;

				else if (!openQueue.contains(succs))
					openQueue.add(succs);

				else if ( openQueue.removeIf((iState) ->
						iState.equals(succs) && succs.betterThen(iState)) )
					openQueue.add(succs);
			}
		}

		throw new UnsolvableProblemException();
	}

}

/*
public class BestFS<R>
		extends CommonPrQueueSearcher<R>
		implements Searcher<R>
{
	@Override
	public List<GenericState<R>> search(Searchable problem) throws UnsolvableProblemException {

		//System.out.println("starting BFS searcher:");	// DEBUG/LOG

//		PriorityQueue<GenericState<R>> openQueue = new PriorityQueue<>();
		this.initOpenList();
		HashSet<GenericState<R>> finished = new HashSet<>();

		GenericState source = problem.getInitState();
		openQueue.add(source);

		while(!openQueue.isEmpty())
		{
			GenericState<R> curState = openQueue.poll();
			finished.add(curState);

			if (problem.isGoalState(curState)) {    // should happen assuming problem is solvable!
				return CommonTooling.backTrace(curState, source);
			}

			List<GenericState<R>> allSuccessors = problem.getAllPossibleStates(curState);
			for (GenericState<R> succs : allSuccessors)
			{
				if (finished.contains(succs))
					continue;

				else if (!openQueue.contains(succs))
					openQueue.add(succs);

				else if ( openQueue.removeIf((iState) ->
						iState.equals(succs) && succs.betterThen(iState)) )
					openQueue.add(succs);
			}
		}

		throw new UnsolvableProblemException();
	}

}
 */

