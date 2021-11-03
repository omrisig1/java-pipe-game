package Search.VanilaAlgorithms;

import Search.Exceptions.UnsolvableProblemException;
import Search.GameMapState;
import Search.Searchable;
import Search.Searcher;

import java.util.*;

public class DFS<R> implements Searcher<R>{
	@Override
	public List<GenericState<R>> search(Searchable problem) throws UnsolvableProblemException {

	//	System.out.println("starting DFS searcher:");	// DEBUG/LOG

		Stack<GenericState<R>> openStack = new Stack<>();
		HashSet<GenericState<R>> finished = new HashSet<>();

		GenericState<R> source = problem.getInitState();
		openStack.push(source);

		while(!openStack.isEmpty())
		{
			GenericState<R> curState = openStack.pop();
			finished.add(curState);

			if (problem.isGoalState(curState)) {    // should happen assuming problem is solvable!
				return CommonTooling.backTrace(curState, source);
			}

			List<GenericState<R>> allSuccessors = problem.getAllPossibleStates(curState);
			for (GenericState<R> succs : allSuccessors)
			{
				if (finished.contains(succs))
					continue;

				else if (!openStack.contains(succs))
					openStack.push(succs);
			}
		}

		throw new UnsolvableProblemException();
	}

}

