package Game.Tile;

public class BlankBasicTile
		extends AbstractBasicTile
{
	private static final int maxPermsL = 1;
	protected permutation curPerm;

	public static enum permutation
	{	blank;
//		public permutation next() {
//			return values()[ (ordinal()+1) % values().length];
//		}
		public static permutation defaultPerm() {
			return blank;
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

	public BlankBasicTile()
	{
		//super(permutation.defaultPerm());
		curPerm = permutation.defaultPerm();
	}

	public BlankBasicTile(permutation permIndex) //throws Exception
	{
		//super(permIndex);
		curPerm = permIndex;
	}


	@Override
	public AbstractBasicTile cloneTile() {
		return new BlankBasicTile();
	}

	@Override
	public AbstractBasicTile cloneNeutralTile() {
		return new BlankBasicTile();
	}

	@Override
	public NeutralizeableTile getNeutralTile() {
		return new BlankBasicTile();
	}


	@Override
	public boolean equals(Object tile) {
		if (this == tile) return true;
		if (tile == null || getClass() != tile.getClass()) return false;

		BlankBasicTile that = (BlankBasicTile) tile;

		return curPerm == that.curPerm;
	}

	@Override
	public String toString() {
		return " ";
	}

	//	@Override
//	public void neutralize() {
//		curType = permutation.defaultPerm();
//	}

}


//public class B2DTile
//extends AbstractBasicTile
//{
//
//	private static final int maxPermsL = 1;
//
//
//	public B2DTile() throws Exception	 // should never throw!
//	{
//		super(0, maxPermsL);
//	}
//
//	public B2DTile(int permIndex) throws Exception
//	{
//		super(permIndex, maxPermsL);
//	}
//
//
//	@Override
//	public GenericTile getNeutralTile() {
//		// return a tile with permIndex at 0 (always.)
//		try {
//			return new B2DTile();
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//}
