package Game;

import Game.Tile.*;

import java.util.HashMap;
import java.util.List;

public class BasicGameMapGenerator {

	public static HashMap<Character, AbstractBasicTile> parserMap;

	public static BasicGameMap parseMap(List<String> lines) {

		//System.out.println("parsing...");	// DEBUG/LOG

		BasicGameMapGenerator.init();

		int rows, cols;
		rows = lines.size();
		cols = lines.get(0).length();

		AbstractBasicTile tilesArr[][] = new AbstractBasicTile[rows][cols];
		AbstractBasicTile parsed;

		for (int i=0; i<rows; i++) {
			String line = lines.get(i);

			if (line.length() != cols);
				// throw new Exception("bad input");

			for (int j=0; j<cols; j++) {
				Character c = line.charAt(j);
				parsed = parserMap.get(c).cloneTile();	// TODO_throw - throw if not parsable
				tilesArr[i][j] = parsed;				// Eli promised, should be fiiiiiiiiiiiinne
			}
		}

		//System.out.println("parsed OK!");	// DEBUG/LOG

		return new BasicGameMap(tilesArr);

	}

	// TODOsuggest - tile factory
	private static void init() {
		if (parserMap == null) {

			parserMap = new HashMap<>();

			parserMap.put(' ', new BlankBasicTile());
			parserMap.put('s', new PointBasicTile(PointBasicTile.pointType.start));
			parserMap.put('g', new PointBasicTile(PointBasicTile.pointType.goal));

			parserMap.put('-', new StraightBasicTile(StraightBasicTile.permutation.LeftRight));
			parserMap.put('|', new StraightBasicTile(StraightBasicTile.permutation.UpDown));

			parserMap.put('L', new AngleBasicTile(AngleBasicTile.permutation.UpRight));		// upright
			parserMap.put('J', new AngleBasicTile(AngleBasicTile.permutation.UpLeft)); 		// upleft
			parserMap.put('F', new AngleBasicTile(AngleBasicTile.permutation.DownRight)); 	// downright
			parserMap.put('7', new AngleBasicTile(AngleBasicTile.permutation.DownLeft)); 	// downleft
		}
	}
}
