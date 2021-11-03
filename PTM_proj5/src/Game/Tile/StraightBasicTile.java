package Game.Tile;

import Game.Constants.Direction;

import java.util.HashMap;
import java.util.Map;

public class StraightBasicTile extends AbstractBasicTile
implements PermutableTile
{
	private static final int maxPermsS = 2;
	protected permutation curPerm;

	private static Map<permutation, String> strRepresentor;

	public static enum permutation
	{	UpDown, LeftRight;
		public permutation next() {
			return values()[ (ordinal()+1) % values().length];
		}
		public static permutation defaultPerm() {
			return UpDown;
		}
	};

	private void init_strRepresentor() {
		if (strRepresentor == null) {
			strRepresentor = new HashMap<>();
			strRepresentor.put(permutation.LeftRight, "-");
			strRepresentor.put(permutation.UpDown	, "|");
		}
	}

	// SERIALIZEABLE
	public static int getMaxPermsS() {
		return maxPermsS;
	}
	public permutation getCurPerm() {
		return curPerm;
	}
	public void setCurPerm(permutation curPerm) {
		this.curPerm = curPerm;
	}


	public StraightBasicTile()
	{
		curPerm = permutation.defaultPerm();
	}

	public StraightBasicTile(permutation permIndex) //throws Exception
	{
		curPerm = permIndex;
	}

	@Override
	public AbstractBasicTile cloneTile() {
		return new StraightBasicTile(this.curPerm);
	}

	@Override
	public AbstractBasicTile cloneNeutralTile() {
		return new StraightBasicTile();
	}

	@Override
	public NeutralizeableTile getNeutralTile() {
		return new StraightBasicTile();
	}

	@Override
	public void nextPermute() {
		curPerm = curPerm.next();
	}


	public Direction getCompliment(Direction dir) {
		switch(curPerm) {
		case LeftRight:
			if(dir ==Direction.left) return Direction.right;
			if(dir ==Direction.right) return Direction.left;
			break;
		case UpDown:
			if(dir ==Direction.up) return Direction.down;
			if(dir ==Direction.down) return Direction.up;
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

		StraightBasicTile that = (StraightBasicTile) tile;

		return curPerm == that.curPerm;
	}

	public int getDelta(AbstractBasicTile thatTile) {

		if (this.getClass() != thatTile.getClass())
			// TODOsuggest - throw something
			return 0;
//		StraightBasicTile copyThat= (StraightBasicTile) thatTile.cloneTile();
		StraightBasicTile copyThis = (StraightBasicTile) this.cloneTile();


		int permCount;
		for (permCount = 0; permCount < maxPermsS; permCount++) {
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
