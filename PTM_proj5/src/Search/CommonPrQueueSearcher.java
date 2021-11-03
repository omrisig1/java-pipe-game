//package Search;
//
//import Search.Exceptions.UnsolvableProblemException;
//import Search.VanilaAlgorithms.GenericState;
//
//import java.util.List;
//import java.util.PriorityQueue;
//
//public abstract class CommonPrQueueSearcher<R>
//implements Searcher<R> {
//
//    protected PriorityQueue<GenericState<R>> openQueue;
//    private int evaluated;
//
//    public CommonPrQueueSearcher() {
//        initOpenList();
//    }
//    public abstract List<GenericState<R>> search(Searchable problem) throws UnsolvableProblemException;
//
//    public void addToOpenList(GenericState<R> newState) {
//        openQueue.add(newState);
//        evaluated++;
//    }
//
//    protected void initOpenList() {
//        this.openQueue = new PriorityQueue<>(100, GenericState.getComperator());
//    }
//
//    public int evaluatedNodesTally() {
//        return evaluated;
//    }
//}

//public abstract class CommonPrQueueSearcher
//implements Searcher {
//
//    protected PriorityQueue<GameMapState> openQueue;
//    private int evaluated;
//
//    public CommonPrQueueSearcher() {
//        initOpenList();
//    }
//
//    public abstract List<GameMapState> search(Searchable problem) throws UnsolvableProblemException;
//
//
//    public void addToOpenList(GameMapState newState) {
//        openQueue.add(newState);
//        evaluated++;
//    }
//
//    protected void initOpenList() {
//        this.openQueue = new PriorityQueue<>(100, GameMapState.getComperator());
//    }
//
//    public int evaluatedNodesTally() {
//        return evaluated;
//    }
//}
//
;
//
// with T
//public abstract class CommonPrQueueSearcher<T>
//        implements Searcher {
//
//    protected PriorityQueue<State<T>> openQueue;
//    private int evaluated;
//
//    public CommonPrQueueSearcher() {
//        this.openQueue = new PriorityQueue<>();
//    }
//
//    public abstract AlgorithmicSolution search(Searchable problem) throws UnsolvableProblemException;
//
//
//    public void addToOpenList(State<T> newState) {
//        openQueue.add(newState);
//        evaluated++;
//    }
//
//    public int evaluatedNodesTally() {
//        return evaluated;
//    }
//}
//