package Game.Constants;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class MapCoord implements Serializable {
	public final int r, c;	// row, col
	public MapCoord(int r, int c) {
		this.r = r;
		this.c = c;
	}
	public MapCoord(MapCoord coord) {
		this.r = coord.r;
		this.c = coord.c;
	}

	private static Map<Direction,MapCoord> dir2Delta;
	private static Map<MapCoord,Direction> delta2Dir;

	public MapCoord add(MapCoord p) {
		return new MapCoord(this.r+p.r, this.c+p.c);
	}
	public MapCoord sub(MapCoord p) {
		return new MapCoord(this.r-p.r, this.c-p.c);
	}
	public static void init_dir2Delta () {
		if (dir2Delta ==null) {
			dir2Delta = new EnumMap<>(Direction.class);
			dir2Delta.put(Direction.left, 	new MapCoord(0,-1));
			dir2Delta.put(Direction.right, 	new MapCoord(0,1));
			dir2Delta.put(Direction.up,   	new MapCoord(-1,0));
			dir2Delta.put(Direction.down, 	new MapCoord(1,0));
		}
	}
	public static void init_delta2Dir () {
		if (delta2Dir ==null) {
			delta2Dir = new HashMap<>(4);
			delta2Dir.put(new MapCoord(0,-1), 	Direction.left);
			delta2Dir.put(new MapCoord(0,1), 	Direction.right);
			delta2Dir.put(new MapCoord(-1,0), 	Direction.up);
			delta2Dir.put(new MapCoord(1,0),	Direction.down);
		}
	}


	public static Direction calcDirection(MapCoord s, MapCoord t) {
		init_delta2Dir();
		MapCoord diff = s.sub(t);
		return MapCoord.delta2Dir.get(diff);
	}
	public static MapCoord calcDelta(Direction dir) {
		init_dir2Delta();
		return MapCoord.dir2Delta.get(dir);
	}

	public MapCoord neighborToMy(Direction dir) {
		MapCoord nb = new MapCoord(this).add(calcDelta(dir));
		return nb;
	}


//	public boolean equals(MapCoord p) {
//		return (this.c == p.c) && (this.r == p.r);
//	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MapCoord that = (MapCoord) o;
		return (this.c == that.c) && (this.r == that.r);
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public String toString() {
		return "(" + r + ", " + c +	")";
	}
};



//import java.util.EnumMap;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Point2D{
//	public final int x, r;
//	public Point2D(int x, int r) {
//		this.x = x;
//		this.r = r;
//	}
//	private static Map<Direction,Point2D> dir2Delta;
//	private static Map<Point2D,Direction> delta2Dir;
//
//
//	public Point2D add(Point2D p) {
//		return new Point2D(this.x+p.x, this.r+p.r);
//	}
//	public Point2D sub(Point2D p) {
//		return new Point2D(this.x-p.x, this.r-p.r);
//	}
//	public static void init_dir2Delta () {
//		if (dir2Delta ==null) {
//			dir2Delta = new EnumMap<>(Direction.class);
//			dir2Delta.put(Direction.left, new Point2D(0,-1));
//			dir2Delta.put(Direction.right, new Point2D(0,1));
//			dir2Delta.put(Direction.up, new Point2D(-1,0));
//			dir2Delta.put(Direction.down, new Point2D(1,0));
//		}
//	}
//	public static void init_delta2Dir () {
//		if (delta2Dir ==null) {
//			delta2Dir = new HashMap<>(4);
//			delta2Dir.put(new Point2D(0,-1), Direction.left);
//			delta2Dir.put(new Point2D(0,1), Direction.right);
//			delta2Dir.put(new Point2D(-1,0), Direction.up);
//			delta2Dir.put(new Point2D(1,0), Direction.down);
//		}
//	}
//	public boolean equals(Point2D p) {
//		return (this.x == p.x) && (this.r == p.r);
//	}
//
//	public static Direction calcDirection(Point2D s, Point2D t) {
//		init_delta2Dir();
//		Point2D diff = s.sub(t);
//		return Point2D.delta2Dir.get(diff);
//	}
//	public static Point2D calcDelta(Direction dir) {
//		init_dir2Delta();
//		return Point2D.dir2Delta.get(dir);
//	}
//
//};
