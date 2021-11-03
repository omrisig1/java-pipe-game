package Search.VanilaAlgorithms;

import Search.Exceptions.UnsolvableProblemException;
import Search.GameMapState;
import Search.Searchable;
import Search.Searcher;

import java.util.Comparator;
import java.util.List;

public class HillClimbing<R> implements Searcher {

	public double tryClimbingPrec = 0.7;
	private long timeCap = 10000;
	private StateGrader grader;

	public HillClimbing(StateGrader grader) {
		this.grader = grader;
	}

	public HillClimbing(StateGrader grader, long timeCap) {
		this.grader = grader;
		this.timeCap = timeCap;
	}

	public void setTimeCap(long timeCap) {
		this.timeCap = timeCap;
	}
	public void setClimbingPrec(double tryClimbingPrec) {
		this.tryClimbingPrec = tryClimbingPrec;
	}

	@Override
	public List<GameMapState> search(Searchable problem) throws UnsolvableProblemException {

		/// REMINDER !!!!!!
		/// hill climbing does NOT (always) return a solution, SHOULD NOT BE CACHED !!

		GenericState source = problem.getInitState();
		GenericState curState = source;

		long startTime = System.nanoTime();
		long curTime = startTime;

		while (!problem.isGoalState(curState) &&
				curTime - startTime <= timeCap)
		{
			List<? extends GenericState<R>> possibleS = problem.getAllPossibleStates(curState);
			grader.sortAllStates(possibleS);

			if (possibleS.isEmpty())
				throw new UnsolvableProblemException();

			int stepTo;
			if (Math.random() < tryClimbingPrec)
				stepTo = 0;
			else {
				stepTo = (int) Math.ceil(Math.random()*possibleS.size());
			}

			curState = possibleS.get(stepTo);
			curTime = System.nanoTime();
		}

		return CommonTooling.backTrace(curState, source);
	}

	public static abstract class StateGrader<R> {
		public abstract double calcStateGrade(GenericState<R> state);
		public void sortAllStates(List<? extends GenericState<R>> allStates) {
			StateGrader grader = this;
			Comparator<GenericState<R>> c = new Comparator<GenericState<R>>() {
				@Override
				public int compare(GenericState<R> o1, GenericState<R> o2) {
					double grade1 = grader.calcStateGrade(o1);
					double grade2 = grader.calcStateGrade(o2);

					if (grade1 < grade2) return +1;
					else if (grade1 == grade2) return 0;
					else return -1;
				}
			};
			allStates.sort(c);
		}
	}
}

/*


class HillClimbingSearcher implements Searcher<char[][]> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HillClimbingSearcher.class);

    private long timeToRun;
    private StateGrader<char[][]> grader;


    HillClimbingSearcher(StateGrader<char[][]> grader, long timeToRun) {
        this.grader = grader;
        this.timeToRun = timeToRun;
    }


    @Override
    public Solution search(Searchable<char[][]> searchable) {
        //Define the current state as an initial state
        State<char[][]> next = searchable.getInitialState();
        Solution result = new Solution();

        long time0 = System.currentTimeMillis();


        //Loop until the goal state is achieved or no more operators can be applied on the current state:
        while (System.currentTimeMillis() - time0 < timeToRun) {
            List<State<char[][]>> neighbors = searchable.getPossibleStates(next);

            if (Math.random() < 0.7) { // with a high probability
                // find the best one
                int grade = 0;
                for (State<char[][]> step : neighbors) {
                    int g = grader.grade(step);
                    if (g > grade) {
                        grade = g;
                        next = step;
                        //add this step to the solution
                        //result.add
                        //result.add(step[0]);

                    }
                }
            } else {
                next = neighbors.get(new Random().nextInt(neighbors.size()));
            }
        }

        return result;

    }
}



* */