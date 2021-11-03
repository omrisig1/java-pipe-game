package Solution;

import Game.GameMap;
import Search.Exceptions.UnsolvableProblemException;
import Search.GameMapState;
import Search.Searcher;

import java.util.LinkedList;
import java.util.List;
//import Search.Solution.ComputableSolution;

public class SolverAdapter
        implements Solver<GameMap, GameMapSolution>
{
    private Searcher toAdapt;
    public SolverAdapter(Searcher toAdapt) {
        this.toAdapt = toAdapt;
    }

    @Override
    public GameMapSolution solveMap(GameMap unsolved)
    {
        GameMapSolution solution = null;
        // solution contains the moves list, and the solved state map

        try {
            //System.out.println("starting searcher.");	// DEBUG/LOG
            solution = new GameMapSolution( toAdapt.search(unsolved) );

        } catch (UnsolvableProblemException e) {

            //System.out.println("ran into an UnsolvableProblemException.");	// DEBUG/LOG
            solution = new GameMapSolution( null );

            //e.printStackTrace();
            // TODOsuggestion - return only string "done",
            // TODOsuggestion - in future explain in GUI: "current map unsolvable"
            // Eli promised, is fine ^ ^ ^
        }

        //System.out.println("searcher DONE!");	// DEBUG/LOG

        return solution;
    }
}
