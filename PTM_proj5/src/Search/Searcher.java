package Search;

import Search.Exceptions.UnsolvableProblemException;
import Search.VanilaAlgorithms.GenericState;

import java.util.List;

public interface Searcher<Rep> {
    public List<GenericState<Rep>> search(Searchable problem) throws UnsolvableProblemException;
    // TODO_DONE - maybe diff. return
}
