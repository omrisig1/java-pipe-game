package Search;

import Search.VanilaAlgorithms.GenericState;

import java.util.HashSet;
import java.util.List;

public interface Searchable<Rep> {
    public GenericState<Rep> getInitState();
    public <concState extends GenericState<Rep>> boolean isGoalState(concState state);

    public <concState extends GenericState<Rep>> List<GameMapState> getAllPossibleStates(concState s);
//    public List<GameMapState> getAllPossibleStates(GameMapState s, HashSet<String> pipesAlreadySeen); // relative to this
    // TODOsuggest - maybe not an ArrayList<>?
}
