package Game.Tile;

import java.util.HashMap;
import java.util.Map;

public class PointBasicTile extends AbstractBasicTile
{
	public static enum pointType
	{	start, goal;
		public static pointType defaultPerm() {
			return start;
		}
	};

	private static final int maxPermsL = 0;
	protected pointType curType;

	private static Map<pointType, String> strRepresentor;


	public static int getMaxPermsL() {
		return maxPermsL;
	}
	public pointType getCurType() {
		return curType;
	}
	public void setCurType(pointType curType) {
		this.curType = curType;
	}


//	public PointBasicTile()		// reminder - SERIALIZEABLE!! (but works...)
//	{
//		curType = pointType.defaultPerm();
//	}

	public PointBasicTile(pointType permIndex)
	{
		curType = permIndex;
	}


	private void init_strRepresentor() {
		if (strRepresentor == null) {
			strRepresentor = new HashMap<>();
			strRepresentor.put(pointType.start, "s");
			strRepresentor.put(pointType.goal , "g");
		}
	}

	@Override
	public AbstractBasicTile cloneTile() {
		return new PointBasicTile(this.curType);
	}

	@Override
	public AbstractBasicTile cloneNeutralTile() {
		return new PointBasicTile(this.curType);
	}

	@Override
	public NeutralizeableTile getNeutralTile() {
		return new PointBasicTile(curType);
	}

	
	public boolean isGoal()  { return curType == pointType.goal; }
	public boolean isStart() { return curType == pointType.start; }


	@Override
	public boolean equals(Object tile) {
		if (this == tile) return true;
		if (tile == null || getClass() != tile.getClass()) return false;

		PointBasicTile that = (PointBasicTile) tile;

		return curType == that.curType;
	}

	@Override
	public String toString() {
		init_strRepresentor();
		return strRepresentor.get(this.curType);
	}

}