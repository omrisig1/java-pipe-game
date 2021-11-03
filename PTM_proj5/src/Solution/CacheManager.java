package Solution;

import Game.GameMap;

import java.io.IOException;

public interface CacheManager<Data> {

	public Data fetchSolution(Data problemMap);
	public void cacheSolution(Data solution); //throws IOException;

//	public void cacheSolution(GameMap solution) throws IOException;
//	 TODOsuggest - the IO handling should be contained in methods (not with throws)

}
