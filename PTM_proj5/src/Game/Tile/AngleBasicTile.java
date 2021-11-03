package Game.Tile;

import Game.Constants.Direction;

import java.util.HashMap;
import java.util.Map;

public class AngleBasicTile extends AbstractBasicTile
implements PermutableTile
{
	private static final int maxPermsL = 4;
	protected permutation curPerm;

	private static Map<permutation, String> strRepresentor;

	public static enum permutation
		{	UpRight, DownRight, DownLeft, UpLeft;
			public permutation next() {
				return values()[ (ordinal()+1) % values().length];
			}
			public static permutation defaultPerm() { 
				return UpRight;
			}
		};


	// SERIALIZEABLE
	public void setCurPerm(permutation curPerm) {
		this.curPerm = curPerm;
	}
	public static int getMaxPermsL() {

		return maxPermsL;
	}
	public permutation getCurPerm() {
		return curPerm;
	}


	public AngleBasicTile()
	{
		//super(permutation.defaultPerm());
		curPerm = permutation.defaultPerm();
	}

	public AngleBasicTile(permutation permIndex) // throws Exception
	{
		//super(permIndex);
		curPerm = permIndex;
	}

	private void init_strRepresentor() {
		if (strRepresentor == null) {
			strRepresentor = new HashMap<>();
			strRepresentor.put(permutation.UpRight 	, "L");		// upright
			strRepresentor.put(permutation.UpLeft 	, "J"); 	// upleft
			strRepresentor.put(permutation.DownRight, "F"); 	// downright
			strRepresentor.put(permutation.DownLeft , "7"); 	// downleft
		}
	}

	@Override
	public AbstractBasicTile cloneTile() {
		return new AngleBasicTile(this.curPerm);
	}

	@Override
	public AbstractBasicTile cloneNeutralTile() {
		return new AngleBasicTile();
	}

	@Override
	public NeutralizeableTile getNeutralTile() {
		return new BlankBasicTile();
	}


	@Override
	public void nextPermute() {
		curPerm = curPerm.next();
	}
	
	public Direction getCompliment(Direction dir) {
		switch(curPerm) {
		case DownLeft:
			if(dir ==Direction.left) return Direction.down;
			if(dir ==Direction.down) return Direction.left;
			break;
		case DownRight:
			if(dir ==Direction.right) return Direction.down;
			if(dir ==Direction.down) return Direction.right;
			break;
		case UpLeft:
			if(dir ==Direction.left) return Direction.up;
			if(dir ==Direction.up) return Direction.left;
			break;
		case UpRight:
			if(dir ==Direction.right) return Direction.up;
			if(dir ==Direction.up) return Direction.right;
			break;
		default:
			return null;		
		}
		return null;
	}

	@Override
	public String getCurPermuteStr() {
		return this.curPerm.name();
	}

	@Override
	public boolean equals(Object tile) {
		if (this == tile) return true;
		if (tile == null || getClass() != tile.getClass()) return false;

		AngleBasicTile that = (AngleBasicTile) tile;

		return curPerm == that.curPerm;
	}


	public int getDelta(AbstractBasicTile thatTile) {

		if (this.getClass() != thatTile.getClass())
			// TODO_throw - throw something
			return 0;
//		AngleBasicTile copyThat= (AngleBasicTile) thatTile.cloneTile();
		AngleBasicTile copyThis = (AngleBasicTile) this.cloneTile();

		int permCount;
		for (permCount = 0; permCount < maxPermsL; permCount++) {
			if (thatTile.equals(copyThis))
				break;
			else
				copyThis.nextPermute();
		}

		return permCount;
	}

	public int numOfPermutaion() { return permutation.values().length; }


	@Override
	public String toString() {
		init_strRepresentor();
		return strRepresentor.get(this.curPerm);
	}
}
//	@Override
//	public void neutralize() {
//		curType = permutation.defaultPerm();
//	}





//public class L2DTile
//		extends AbstractBasicTile
//{
//	private static final int maxPermsL = 4;
//	protected permutation curType;
//
//	public static enum permutation //implements concretePermutation
//	{	UpRight, DownRight, DownLeft, UpLeft;
//		public permutation next() {
//			return values()[ (ordinal()+1) % values().length];
//		}
//		public static permutation defaultPerm() {
//			return UpRight;
//		}
//	};
//
//
//	public L2DTile()
//	{
//		//super(permutation.defaultPerm());
//		curType = permutation.defaultPerm();
//	}
//
//	public L2DTile(permutation permIndex) throws Exception
//	{
//		//super(permIndex);
//		curType = permIndex;
//	}
//
////	@Override
////	protected ArrayList<Integer> setAllPermutations() {
////		return null;
////	}
//
//
//	@Override
//	public GenericTile getNeutralTile() {
//		return new L2DTile();
//	}
//
//	@Override
//	public void nextPermute() {
//		curType = curType.next();
//	}
//
//	@Override
//	public void neutralize() {
//		curType = permutation.defaultPerm();
//	}
//
//}
