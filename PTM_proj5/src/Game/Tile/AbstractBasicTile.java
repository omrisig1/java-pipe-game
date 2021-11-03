package Game.Tile;

import Game.Constants.*;

import java.io.Serializable;
//import Game.Tile.PointBasicTile.pointType;

public abstract class AbstractBasicTile
implements NeutralizeableTile, ClonableTile, Serializable {
	
	public boolean isGoal()  { return false; }
	public boolean isStart() { return false; }
	
	public Direction getCompliment(Direction dir) {
		return null;
	}

	public int getDelta(AbstractBasicTile t) {
		return 0;
	}

	public int numOfPermutaion() { return 0; }

	@Override
	public abstract String toString();
}

	//protected ArrayList<Integer> permutations = setAllPermutations();
	//protected abstract ArrayList<Integer> setAllPermutations();

	//protected boolean isValidIndex(int index) {
	//	return ( index>=0 && index < permutations.size() );
	//}


	//	protected ArrayList<Integer> setAllPermutations() {

	// TODOwhat?? - map char to integer in ArrayList for parsing

