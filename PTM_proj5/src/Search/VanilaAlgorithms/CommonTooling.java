package Search.VanilaAlgorithms;

import Search.GameMapState;

import java.util.LinkedList;
import java.util.List;

public class CommonTooling {

	public static <R> List<GenericState<R>> backTrace(
			GenericState<R> goalState, GenericState<R> startState)
	{
		LinkedList<GenericState<R>> trace = new LinkedList<>();
		GenericState<R> curState = goalState;

		while (!curState.equals(startState)) {
			trace.addFirst(curState);
			curState = curState.getParentState();
		}
		trace.addFirst(startState);

		return trace;
	}

}
