//package Test;
//
//import Game.BasicGameMap;
//import Game.BasicGameMapGenerator;
//import Game.GameMap;
//import Search.GameMapState;
//
//import java.util.*;
//
//public class MainCheck {
//	public static void main(String[] args)
//	{
////        StateAndMapCommapring();
//        DataStructContains();
//    }
//
//    public static void StateAndMapCommapring()
//    {
//        GameMapState testState1, testState2;
//        List<String> mapString = new LinkedList<>();
//        mapString.add("s-g");
//        GameMap testGameMap = BasicGameMapGenerator.parseMap(mapString);
//        GameMap testGameMap2 = BasicGameMapGenerator.parseMap(mapString);
//
//
//        System.out.println("State Checks:");
//
//        testState1 = new GameMapState(testGameMap,0);
//        testState2 = new GameMapState(testGameMap,0);
//
//        if (testState1 == testState2)
//            System.out.println("== OK");
//
//        if (testState1.equals(testState2))
//            System.out.println(".equals OK");
//
////        if (testState1.compareTo(testState2) == 0)
////            System.out.println(".compare OK");
//        if (GameMapState.getComperator().compare(testState1, testState2) == 0)
//            System.out.println("comparator OK");
//
//        if (testState1.hashCode() == testState2.hashCode())
//            System.out.println(".hashCode OK");
//
//        System.out.println(testState1.hashCode());
//        System.out.println(testState2.hashCode());
//
//
//        System.out.println("Map Checks:");
//
//        if (testGameMap == testGameMap2)
//            System.out.println("== OK");
//
//        if (testGameMap.equals(testGameMap2))
//            System.out.println(".equals OK");
//
//        if (testGameMap.NeutralHashCode() == testGameMap2.NeutralHashCode())
//            System.out.println(".NeutralHashCode OK");
//
//        if (testGameMap.hashCode() == testGameMap2.hashCode())
//            System.out.println(".hashCode OK");
//
//        System.out.println(testGameMap.hashCode());
//        System.out.println(testGameMap2.hashCode());
//        System.out.println(testGameMap.NeutralHashCode());
//        System.out.println(testGameMap2.NeutralHashCode());
//    }
//
//    public static void DataStructContains()
//    {
//        List<String> mapString = new LinkedList<>();
//        mapString.add("s-g");
//        GameMap testGameMap = BasicGameMapGenerator.parseMap(mapString);
//        GameMap testGameMap1 = BasicGameMapGenerator.parseMap(mapString);
//        GameMap testGameMap2 = BasicGameMapGenerator.parseMap(mapString);
//
//        GameMapState testState = new GameMapState(testGameMap,0);
//        GameMapState testState1 = new GameMapState(testGameMap1,0);
//        GameMapState testState2 = new GameMapState(testGameMap2,0);
//
//        HashSet<GameMapState> finished = new HashSet<>();
//        PriorityQueue<GameMapState> openQueue = new PriorityQueue<>();
//
//        // works for same pointer of State
//        if (finished.contains(testState))   System.out.println("HashSet contains OK");
//        if (openQueue.contains(testState))   System.out.println("Queue contains OK");
//
//        finished.add(testState);
//        finished.add(testState);
//        finished.add(testState);
//
//        openQueue.add(testState);
//
//        if (finished.contains(testState))   System.out.println("HashSet contains OK");
//        if (openQueue.contains(testState))   System.out.println("Queue contains OK");
//
//        // works after fixing State.equals(Object o)
//        finished.add(testState);
//        finished.add(testState1);
//        finished.add(testState2);
//        if (finished.contains(testState))   System.out.println("HashSet contains OK");
//        if (finished.contains(testState1))   System.out.println("HashSet contains OK");
//        if (finished.contains(testState2))   System.out.println("HashSet contains OK");
//
//        // also works - hooray!!!
//        openQueue.add(testState);
//
//        if (openQueue.contains(testState))   System.out.println("Queue contains OK");
//        if (openQueue.contains(testState1))   System.out.println("Queue contains OK");
//        if (openQueue.contains(testState2))   System.out.println("Queue contains OK");
//
//    }
//
//}
