package Game.Constants;

public enum Direction{
	up,down,left,right;
	public Direction flipDir(){
		switch(this) {
		case up: 	return down;
		case down: 	return up;
		case left: 	return right;
		case right: return left;
		default: 	return null;
		}
	}
	private static Direction[] allValues;
	public static Direction[] getAllValues() {
		if (allValues == null) allValues = Direction.values();
		return allValues;
	}
	public static int size(){
		return getAllValues().length;
	}
};