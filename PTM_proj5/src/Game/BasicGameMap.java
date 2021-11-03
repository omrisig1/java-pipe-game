package Game;

import Game.Constants.*;
import Game.Tile.AbstractBasicTile;
import Game.Tile.BlankBasicTile;
import Game.Tile.PermutableTile;
import Search.GameMapState;
import Search.VanilaAlgorithms.GenericState;
//import Search.Solution.DeltaComputable;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;


public class BasicGameMap
		implements GameMap, Serializable {

	private int rows, cols;
	private AbstractBasicTile[][] tiles;
	private MapCoord start, goal;
	//private final PointBasicTile start,goal;
	private HashSet<String> pipesAlreadySeen = new HashSet<>();		// reminder - SERIALIZEABLE!! (still works...)


	public class GameMapDescription {
		public MapCoord mapSourceCo;
		public MapCoord mapStart, mapGoal;
		public ArrayList<MapCoord> pipesFromSource[] = new ArrayList[Direction.size()];
		public ArrayList<MapCoord> longestPipe;
		public MapCoord[] pipeStoppedAt = new MapCoord[Direction.values().length];
//		private HashSet<MapCoord> allCoordsOfPipes = new HashSet<>(rows * cols);

		public void cutLastOfPipe(Direction dir)
		{
			ArrayList<MapCoord> pipeToCut = this.pipesFromSource[dir.ordinal()];
			this.pipeStoppedAt[dir.ordinal()] = (
				(pipeToCut.size() == 0) ? null :
					pipeToCut.remove( pipeToCut.size() - 1 )
			);
		}
	}

	// SERIALIZEABLE
	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}
	public AbstractBasicTile[][] getTiles() {
		return tiles;
	}
	public MapCoord getStart() {
		if (start == null)
			start = getStartCoord();
		return start;
	}
	public MapCoord getGoal() {
		if (goal == null)
			goal = getGoalCoord();
		return goal;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public void setStart(MapCoord start) {
		this.start = new MapCoord(start);
	}
	public void setGoal(MapCoord goal) {
		this.goal = new MapCoord(goal);
	}

	public BasicGameMap() {}
	public BasicGameMap(AbstractBasicTile[][] tiles)
	{
		setTiles(tiles);
		setSize(tiles.length, tiles[0].length);
	}

	public void setTiles(AbstractBasicTile[][] tiles) {
		this.tiles = tiles;
		// TODOsuggest - find java copier later
	}
	public void setSize(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	private MapCoord getGoalCoord()	{

		if(this.goal == null)		//loop search for goal
			for (int i=0; i<tiles.length; i++)
				for (int j=0; j<tiles[i].length; j++)
					if(tiles[i][j].isGoal()) {
						this.goal=new MapCoord(i,j);
						break;
					}

		return this.goal;
	}
	private MapCoord getStartCoord() {

		if(this.start == null)		//loop search for goal
			for (int i=0; i<tiles.length; i++)
				for (int j=0; j<tiles[i].length; j++)
					if(tiles[i][j].isStart()) {
						this.start=new MapCoord(i,j);
						break;
					}
		return this.start;
	}

	public BasicGameMap cloneMap() {
		return cloneMapFunc(
				(aTile) -> aTile.cloneTile()
		);
	}
	public BasicGameMap cloneNeutralMap() {
		return cloneMapFunc((aTile) -> aTile.cloneNeutralTile());
	}

	private BasicGameMap cloneMapFunc(Function<AbstractBasicTile, AbstractBasicTile> f)
	{
		BasicGameMap map = new BasicGameMap();
		map.setSize(this.tiles.length, this.tiles[0].length);

		AbstractBasicTile copyTiles[][] =
				new AbstractBasicTile[map.rows][map.cols];

		for (int i=0; i<tiles.length; i++)
			for (int j=0; j<tiles[i].length; j++)
				copyTiles[i][j] = f.apply(this.tiles[i][j]);

		map.setTiles(copyTiles);
		return map;
	}


	@Override
	public boolean equals(Object map) {
		if (this == map) return true;
		if (map == null || getClass() != map.getClass()) return false;

		BasicGameMap that = (BasicGameMap) map;

		if (this.rows != that.rows ||
				this.cols != that.cols) return false;
		return Arrays.deepEquals(tiles, that.tiles);
	}

	@Override
	public String toString() {
		StringBuilder retStr = new StringBuilder();
		for (AbstractBasicTile[] row : tiles) {
			for (AbstractBasicTile tile : row)
				retStr.append(tile.toString());
			retStr.append('\n');
		}

		return retStr.toString();
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	public int NeutralHashCode() {
		GameMap nutralized = this.cloneNeutralMap();
		String strMap = nutralized.toString();
		return strMap.hashCode();
	}


	@Override
	public GameMapState getInitState() {
		init_pipesAlreadySeen();
		return new GameMapState(this.cloneMap(), 0);
	}
	private void init_pipesAlreadySeen() {
		this.pipesAlreadySeen = new HashSet<>();
	}

	@Override
	//	public boolean isGoalState(GameMapState aState)		// -- old signature
	public <C extends GenericState<GameMap>> boolean isGoalState(C concState)
	{
		GameMapState state = (GameMapState) concState;
		BasicGameMap curMap = (BasicGameMap) state.getGameMap();
		// this will be ok once: State<BasicGameMap> === GameMapState	// TODO_checkup - DOWN CASTING

		MapCoord source = curMap.getStart();
		GameMapDescription curMapDesc = curMap.getMapDescription(source);
		return isMapSolved(curMapDesc);
	}

	public GameMapDescription getMapDescription(MapCoord s)
	{
		GameMapDescription desc = new GameMapDescription();

		desc.mapSourceCo = s;
		desc.mapStart = this.getStartCoord();
		desc.mapGoal = this.getGoalCoord();

		for (Direction initDir : Direction.values())
		{
			MapCoord cur = s;
			MapCoord next = cur.neighborToMy(initDir);
			AbstractBasicTile tile;
			Direction curDirection = initDir;

			ArrayList<MapCoord> curPipeList = new ArrayList<>();
			while (true)
			{
				try { tile = this.getTile(next.r, next.c); }
				catch(ArrayIndexOutOfBoundsException e) { break; }
				if (next.equals(s))	break;

				Direction inputLeg = curDirection.flipDir();
				Direction outputLeg = tile.getCompliment(inputLeg);

				if (outputLeg == null) {
					desc.pipeStoppedAt[initDir.ordinal()] = next;
					break;
				}
				else {
					curPipeList.add(next);		// add chain of pipe in a direction
					cur = next;
					next = cur.neighborToMy(outputLeg);
					curDirection = outputLeg;
				}
			}

			desc.pipesFromSource[initDir.ordinal()] = curPipeList;

			if (desc.longestPipe == null || desc.longestPipe.size() < curPipeList.size())
				desc.longestPipe = curPipeList;
		}

		return desc;
	}
	public GameMapDescription getMapDescription() {
		return this.getMapDescription(this.getStart());
	}


	// TODOsuggest - method can also belong to the interface ~
	public boolean isMapSolved(GameMapDescription mapDesc)
	{
		for (Direction dir : Direction.getAllValues()) {
			MapCoord stopper = mapDesc.pipeStoppedAt[dir.ordinal()];
			if (mapDesc.mapGoal.equals(stopper))
				return true;
		}
		return false;
	}

//
//
//	@Override
//	public boolean isGoalState(GameMapState aState) {		// TODO_Done ^ ^ ^ - fix to work with aState!!!
////		curMap = aState.getGameMap();
//		MapCoord start = this.getStartCoord();
//		MapCoord goal  = this.getGoalCoord();
//		for (Direction initDir : Direction.values()) {
//
//			MapCoord cur = start;
//			MapCoord next = start.add(MapCoord.calcDelta(initDir));
//			AbstractBasicTile tile;
//			Direction curDirection = initDir;
//
//			ArrayList<MapCoord> pipeList = new ArrayList<>();
//
//			while (true)
//			{
//				try { tile = this.getTile(next.r, next.c); }
//				catch(ArrayIndexOutOfBoundsException e) { break; }
//				if (next.equals(start))	break;
//
//				if (next.equals(goal)) {
//					System.out.println("current is GOAL! huraaaaay!");	// DEBUG/LOG
//					return true;
//				}
//
//				Direction inputPipeNext = curDirection.flipDir();
//				Direction outputPipeNext = tile.getCompliment(inputPipeNext);
//
//				if (outputPipeNext == null) {
//					this.tileAfterPipeLast[initDir.ordinal()] = next;
//					break;
//				}
//				else {
//					pipeList.add(next);		// add chain of pipe in a direction
//					cur = next;
//					next = cur.add(MapCoord.calcDelta(outputPipeNext));
//					curDirection = outputPipeNext;
//				}
//			}
//
//			this.pipesFromStart[initDir.ordinal()] = pipeList;
//			allCoordsOfPipes.addAll(pipeList);
//
//			if (this.longestPipeFromStart.size() < pipeList.size())
//				this.longestPipeFromStart = pipeList;
//
//		}
//
////		System.out.println("current is NOT! the GOAL");	// DEBUG/LOG
//
////		System.out.println("pipes list:");
////		System.out.println(pipesFromStart.toString());
//		return false;
//	}
//
//


	@Override
//	public List<GameMapState> getAllPossibleStates(GameMapState state)		// -- old signature
	public <C extends GenericState<GameMap>> List<GameMapState> getAllPossibleStates(C concState)
	{
		GameMapState state = (GameMapState) concState;
		List<GameMapState> possibleStates = new LinkedList<>();

		BasicGameMap mapOfCurState = (BasicGameMap) state.getGameMap();		// TODO_checkup - DOWN CASTING
		GameMapDescription description = mapOfCurState.getMapDescription();

		MapCoord lastMoveCo = getLastMoveOf(state);


		for (Direction dir : Direction.getAllValues())
		{
			ArrayList<MapCoord> curPipe = description.pipesFromSource[dir.ordinal()];
			int iter = calcLoopbackIters(curPipe, lastMoveCo);

			for( int i=0; i < iter; i++, description.cutLastOfPipe(dir) )
			{
				String pipeSig = mapOfCurState.getPipeSignature(curPipe, dir);
				if (this.pipesAlreadySeen.contains( pipeSig ))
					continue;

//				pull last & afterLast from description, check them regularly with getMovesWithLongerPipe(...)

				if (curPipe != null && curPipe.size() != 0) {
					MapCoord last = curPipe.get(curPipe.size() - 1);
					possibleStates.addAll(
							mapOfCurState.getMovesWithLongerPipe(last, state, dir, description)
					);
				}

				MapCoord stopperCo = description.pipeStoppedAt[dir.ordinal()];
				if (stopperCo != null) {
					possibleStates.addAll(
							mapOfCurState.getMovesWithLongerPipe(stopperCo, state, dir, description)
					);
				}

				this.pipesAlreadySeen.add(pipeSig);
				// manipulate description:	(see for-loop)
				// description.pipeStoppedAt[dir.ordinal()] =
				// 		curPipe.remove( curPipe.size() - 1 );
			}

		}

		return possibleStates;
	}

	public String getPipeSignature(ArrayList<MapCoord> pipe, Direction dir) {

		StringBuilder signature = new StringBuilder();        // reminder: must include perm of the last in pipe!~!~
		signature.append(dir.name()).append(';');

		if (pipe.size() >= 1) {
			pipe.forEach(
					(co) -> signature.append(co.toString())
			);
			signature.append(';');

			MapCoord lastCoord = pipe.get(pipe.size() - 1);
			PermutableTile lastTile = (PermutableTile) this.getTile(lastCoord.r, lastCoord.c);
			signature.append(lastTile.getCurPermuteStr());
		}

		return signature.toString();
	}
	public MapCoord getLastMoveOf(GameMapState state) {

		MapCoord lastMove = null;
		if (state.getMove() != null) {
			String moveData[] = state.getMove().split(",");
			int r = Integer.parseInt(moveData[0]);
			int c = Integer.parseInt(moveData[1]);
			lastMove = new MapCoord(r, c);
		}

		return lastMove;
	}
	public int calcLoopbackIters(ArrayList<MapCoord> pipe, MapCoord lastMoveCo) {

		int pipeSize = pipe.size();
		int lastMoveIndex = pipe.indexOf(lastMoveCo);
		int iter;

		if (pipeSize == 0)
			iter = 1;
		else {
			if (lastMoveCo == null)
				iter = pipeSize;
			else {
				if (!pipe.contains(lastMoveCo))
					iter = 1;
				else
					iter = (pipeSize - lastMoveIndex);
			}
		}

		// alternative ?
//		int iter = 1;
//
//		if (pipeSize != 0) {
//
//			if (lastMove == null)
//				iter = pipeSize;
//
//			else if (curPipe.contains(lastMove))
//				iter = (pipeSize - lastMoveIndex)
//
//		}

		return iter;
	}

	public List<GameMapState> getMovesWithLongerPipe(
			MapCoord tileCo, GameMapState thisMapsState, Direction pipeDir, GameMapDescription desc)
	{
		List<GameMapState> wantedStates = new LinkedList<>();
		double sourceMapCost = this.getMapCost(desc);	// cost refers to the sourceMap's description (make sure...)

		double punishment = 1;

		int i = tileCo.r, j = tileCo.c;
		AbstractBasicTile curTile = this.getTile(i, j);

		if (curTile instanceof PermutableTile)
		{
			int p = curTile.numOfPermutaion() - 1;
			BasicGameMap lastMap = this;
			for (int x = 1; x <= p; x++)
			{
				BasicGameMap copyMap = lastMap.cloneMap();
				PermutableTile moveTile = (PermutableTile)copyMap.getTile(i,j);
				moveTile.nextPermute();

				GameMapDescription copyDesc = copyMap.getMapDescription();
				double mapCost = copyMap.getMapCost(copyDesc);
				if (mapCost != 0)
				{
					ArrayList<MapCoord> thisPipe, newPipe;
					thisPipe = desc.pipesFromSource[pipeDir.ordinal()];		// current sourceMap description!!!!
					newPipe = copyDesc.pipesFromSource[pipeDir.ordinal()];

					if (newPipe.size() < thisPipe.size()) {		// reminder; comment if you get "Unsolvable" thrown
						lastMap = copyMap;
						continue;
					}

					mapCost += (mapCost == sourceMapCost) ? punishment : 0;		// might not work now...
					mapCost += (copyMap.isTileADeadEnd(i, j)) ? punishment : 0;
				}

				wantedStates.add(
						new GameMapState(copyMap, mapCost, getMoveString(i, j, x), thisMapsState)
				);

				lastMap = copyMap;
			}
		}

		return wantedStates;
	}

	private boolean isTileADeadEnd(int r, int c) {
		AbstractBasicTile tile = this.getTile(r, c);
		Direction ftLeg=null, scLeg=null;

		for (Direction test : Direction.values()) {
			Direction ans = tile.getCompliment(test);
			if (ans != null) {
				ftLeg = test;
				scLeg = ans;
				break;
			}
		}

		MapCoord tileCo = new MapCoord(r,c);
		MapCoord nbFst = tileCo.add(MapCoord.calcDelta(ftLeg));
		MapCoord nbScd = tileCo.add(MapCoord.calcDelta(scLeg));

		AbstractBasicTile tileFst, tileSec;
		try {
			tileFst = getTile(nbFst.r, nbFst.c);
			tileSec = getTile(nbScd.r, nbScd.c);
		}
		catch (Exception e) {	// dead end due to border
			return true;
		}

		if ( tileFst instanceof BlankBasicTile ||	// dead end due to blank tile
			 tileSec instanceof BlankBasicTile )
			return true;

		else
			return false;
	}

;
	////////////// 2984379237498327498327498327498327493284732984732948273948732948498327498327498327498324732
	////////////// 2984379237498327498327498327498327493284732984732948273948732948498327498327498327498324732
	////////////// 2984379237498327498327498327498327493284732984732948273948732948498327498327498327498324732
	////////////// 2984379237498327498327498327498327493284732984732948273948732948498327498327498327498324732
	////////////// 2984379237498327498327498327498327493284732984732948273948732948498327498327498327498324732
;
//	@Override
//	public List<GameMapState> getAllPossibleStates(GameMapState thisMapsState)
//	{
//		List<GameMapState> possibleStates = new LinkedList<>();
//		double costGrid[][] = getAllCost();
//
//		for (int i=0; i<rows; i++)
//			for (int j=0; j<cols; j++)
//				if (getTile(i,j) instanceof PermutableTile)
//				{
//					BasicGameMap copyMap = this.cloneMap();
//					((PermutableTile)copyMap.getTile(i,j)).nextPermute();
//
//					possibleStates.add(
//						new GameMapState(copyMap, costGrid[i][j], getMoveString(i,j), thisMapsState)
//					);
//				}
//
//		return possibleStates;
//	}
;
//	public List<GameMapState> getMovesWithLongerPipe(
//			MapCoord tileCo, Direction pipeDir, GameMapState thisMapsState)
//	{
//		List<GameMapState> wantedStates = new LinkedList<>();
//		double sourceMapCost = this.getMapCost();
//		double punishment = 1;
////		boolean loto = (Math.random() < 0.30);
////		Direction lotoTicket = loto ? this.randomPrefferedLotoTicket() : null;
////		if (lotoTicket == null)	 loto = false;
//
//		int i = tileCo.r, j = tileCo.c;
//		AbstractBasicTile curTile = getTile(i, j);
//
//		if (curTile instanceof PermutableTile)
//		{
//			int p = curTile.numOfPermutaion() - 1;
//			BasicGameMap lastMap = this;
//			for (int x = 1; x <= p; x++)
//			{
//				BasicGameMap copyMap = lastMap.cloneMap();
//				PermutableTile moveTile = (PermutableTile)copyMap.getTile(i,j);
//				moveTile.nextPermute();
//
//				double mapCost = copyMap.getMapCost();
//				if (mapCost != 0)
//				{
//					ArrayList<MapCoord> thisPipe, newPipe;
//					thisPipe = this.pipesFromStart[pipeDir.ordinal()];
//					newPipe = copyMap.pipesFromStart[pipeDir.ordinal()];
//
//					if (newPipe.size() < thisPipe.size()) {
//						lastMap = copyMap;
//						continue;
//					}
//
//					mapCost += (mapCost == sourceMapCost) ? punishment : 0;
//					mapCost += (copyMap.isTileADeadEnd(i, j)) ? punishment : 0;
//				}
////				if (loto && copyMap.hasDirFromStart(lotoTicket))	//	mapCost *= randBonus;
////					lotoWinners.add (
////							new SimpleEntry(copyMap, getMoveString(i,j,x))
////					);
////				else
//				//iff this pipe does not exists in closed list/set
//				wantedStates.add(
//						new GameMapState(copyMap, mapCost, getMoveString(i, j, x), thisMapsState)
//				);
//
//
////				if (winnerCost > mapCost)	winnerCost = mapCost;
//				lastMap = copyMap;
//			}
//		}
//
//		return wantedStates;
//	}

//	@Override
//	public List<GameMapState> getAllPossibleStates(GameMapState thisMapsState)
//	{
//		List<GameMapState> possibleStates = new LinkedList<>();
//		List<SimpleEntry<GameMap, String>> lotoWinners = new LinkedList<>();
//		//double costGrid[][] = getAllCost();
//		double sourceMapCost = this.getMapCost();
//		double punishment = 99;
//
//		boolean loto = (Math.random() < 0.30);
//		Direction lotoTicket = loto ? this.randomPrefferedLotoTicket() : null;
//		if (lotoTicket == null)	 loto = false;
//
//		double randBonus = 1.0 - (0.5 + Math.random() * 0.30);	// checkup
//		double winnerCost = sourceMapCost;
//
//		for (int i=0; i<rows; i++)
//			for (int j=0; j<cols; j++) {
//				AbstractBasicTile curTile = getTile(i,j);
//				if (curTile instanceof PermutableTile)
//				{
//					int p = curTile.numOfPermutaion() - 1;
//					BasicGameMap lastMap = this;
//					for (int x = 1; x <= p; x++)
//					{
//						BasicGameMap copyMap = lastMap.cloneMap();
//						((PermutableTile)copyMap.getTile(i,j)).nextPermute();
//
//						double mapCost = copyMap.getMapCost();
//						mapCost += (mapCost == sourceMapCost) ? punishment : 0;
//						mapCost += (copyMap.isTileADeadEnd(i, j)) ? punishment : 0;
//
//						if (loto && copyMap.hasDirFromStart(lotoTicket))	//	mapCost *= randBonus;
//							lotoWinners.add (
//									new SimpleEntry(copyMap, getMoveString(i,j,x))
//							);
//
//						else	possibleStates.add(
//									new GameMapState(copyMap, mapCost, getMoveString(i, j, x), thisMapsState)
//								);
//
//						if (winnerCost > mapCost)	winnerCost = mapCost;
//						lastMap = copyMap;
//					}
//				}
//			}
//
//		winnerCost *= randBonus;
//		for (SimpleEntry<GameMap, String> e : lotoWinners)
//		{
//			GameMap winnerMap = e.getKey();
//			String winnerMove = e.getValue();
//			possibleStates.add(
//					new GameMapState(winnerMap, winnerCost, winnerMove, thisMapsState)
//			);
//		}
//
//		return possibleStates;
//	}

	private double getMapCost(GameMapDescription description)
	{
//		BasicGameMap forCost = this.cloneMap();
		if (this.isMapSolved(description))		// TODO_DONE - causes problems with above code with "this"
			return 0;							// fixed with GameMapDescription

		int mapSize = this.cols * this.rows + 1;
		double epsilon = 1.0 / (Math.pow(mapSize, 2));
//		Double costArr[] = new Double[Direction.values().length];
		Double minCost = null;

		for (Direction dir : Direction.values())
		{
			double curCost;
			double costByLen, costByDistance;
			ArrayList<MapCoord> curPipe = description.pipesFromSource[dir.ordinal()];

			double curPipeLen = curPipe.size();
			if (curPipeLen == 0 && description.pipeStoppedAt[dir.ordinal()] == null)
				continue;
			else
				costByLen = inverse(curPipeLen + epsilon);

			MapCoord lastInPipe = (curPipeLen == 0) ? description.mapStart : curPipe.get(curPipe.size() - 1);
			double distanceToGoal = ManhattenDist(lastInPipe, description.mapGoal) + epsilon;
			costByDistance = logBaseN(mapSize, distanceToGoal);	// log base mapSize of distance

			curCost = costByLen * costByDistance;
			if (minCost == null || minCost > curCost)
				minCost = curCost;
		}

		return minCost;
	}
	private double ManhattenDist(MapCoord s, MapCoord t) {
		double distR = Math.abs(s.r - t.r);
		double distC = Math.abs(s.c - t.c);
		return (distR + distC);
	}
	private double logBaseN(double base, double operand) {
		return Math.log(operand) / Math.log(base);
	}
	private double inverse(double num) {
		return 1.0 / num;
	}

	private boolean hasDirFromStart(Direction dir) {
		MapCoord s = getStart();
		MapCoord t = s;
		t = t.add(MapCoord.calcDelta(dir));

		AbstractBasicTile dirTile;
		try { dirTile = getTile(t.r, t.c); }
		catch (Exception e) { return false; }

		Direction neededLeg = dir.flipDir();
		return (dirTile.getCompliment(neededLeg) != null);

		// true if tile exists and supports direction "dir".
	}

//	private Direction randomPrefferedLotoTicket()
//	{
//		final int highChance = 5;
//		final int lowChance = 0;
//
//		boolean dirPossibleFromStart[] = new boolean[Direction.values().length];
//		int chanceOfEachDirection[] = new int[Direction.values().length];
//
//		MapCoord s = this.getStart();
//		for (Direction d : Direction.values())
//		{
//			MapCoord nb = s.add(MapCoord.calcDelta(d));
//
//			AbstractBasicTile nbTile = null;
//			try { nbTile = getTile(nb.r, nb.c); }
//			catch (Exception e) { continue; }	// no tile there
//
//			if (nbTile != null &&
//				!(nbTile instanceof PermutableTile)) { continue; }	// no tile or not permumetable
//
//			dirPossibleFromStart[d.ordinal()] = true;	// dir exists and possible
//
//			Direction neededLeg = d.flipDir();
//			chanceOfEachDirection[d.ordinal()] =
//					(nbTile.getCompliment(neededLeg) == null) ?
//							highChance :		// if permutable is not connected -  preferred!
//							lowChance;			// if permutable already connected - not preferred!
//
//		}
//
//		int totalTickets = IntStream.of(chanceOfEachDirection).sum();
//		int winnerDir = (int) (Math.random() * totalTickets + 1);
//
//		for (Direction dir : Direction.values()) {
//			if (winnerDir <= chanceOfEachDirection[dir.ordinal()])
//				return dir;
//			winnerDir -= chanceOfEachDirection[dir.ordinal()];
//		}
//
//		return null; // only when all directions are connected!~
//	}


	private AbstractBasicTile getTile(int i, int j) { return this.tiles[i][j]; }

//	private double[][] getAllCost() {
//		double costGrid[][] = new double[rows][cols];
//
//		//for (ArrayList<MapCoord> pipe : pipesFromStart) {
//		do {
//
//			ArrayList<MapCoord> pipe = this.longestPipeFromStart;
//
//			double pipeLength = pipe.size();
//			if (pipeLength == 0)  continue;		// skip if pipe is empty
//
//			// skip if pipe already priced (via another pipe)
//			MapCoord fstTile = pipe.get(0);
//			if (costGrid[fstTile.r][fstTile.c] != 0) continue;
//
//			double tileCost = pipeLength;
//			for (MapCoord co : pipe)
//			{
////				double totalSize = this.rows * this.cols;
////				costGrid[co.r][co.c] = tileCost / totalSize;
//				costGrid[co.r][co.c] = tileCost / pipeLength;
//				tileCost--;
//			}
//
//
//			double neighborCost = 0.5 / pipeLength;
//			Direction startDir = MapCoord.calcDirection(this.start, pipe.get(0)).flipDir();		// fix calcDir
//
//			MapCoord afterPipeCoord = this.tileAfterPipeLast[startDir.ordinal()];
//			if (afterPipeCoord != null && !pipe.contains(afterPipeCoord))
//			{
//				AbstractBasicTile afterPipeTile =
//						getTile(afterPipeCoord.r, afterPipeCoord.c);
//				if (afterPipeTile instanceof PermutableTile)
//					costGrid[afterPipeCoord.r][afterPipeCoord.c] = neighborCost;
//			}
//
////			for (Direction d : Direction.values())
////			{
////				MapCoord nb = lastTileCoord.add(MapCoord.calcDelta(d));	// neighbor coordinate
////
////				try { this.getTile(nb.r, nb.c); }
////				catch (ArrayIndexOutOfBoundsException e) { continue; }
////
////				// cost for any neighbor, including non permutables!
////				if (!allCoordsOfPipes.contains(nb))
////				{
////					if (costGrid[nb.r][nb.c] == 0)
////						costGrid[nb.r][nb.c] = neighborCost;
////					else if (costGrid[nb.r][nb.c] > neighborCost)
////						costGrid[nb.r][nb.c] = neighborCost;
////				}
////			}
//
//		} while (false);
//
//		for (int i=0; i<rows; i++) {
//			for (int j = 0; j < cols; j++) {
//				if (costGrid[i][j] == 0) costGrid[i][j] = 1;
//			}
//		}
//		System.out.println("{\n"+this+"}");
//
////		System.out.println("{");
////		for (int i=0; i<rows; i++) {
////			System.out.print("[");
////			for (int j = 0; j < cols; j++) {
////				if (costGrid[i][j] == 0) costGrid[i][j] = 1;
////				System.out.print(String.format( "%.3f", costGrid[i][j] ) + ", ");
////
////			}
////			System.out.println("]");
////		}
////		System.out.println(this);
////		System.out.println("}");
//
//		// TODO_DONE - FIX COST WITH HEURISTICS!!!!!!!!!!!!!!!
//
//		return costGrid;
//	}

	private String getMoveString(int i, int j) {
		return getMoveString(i, j, 1);
	}

	private String getMoveString(int i, int j, int p) {
		StringBuilder ret = new StringBuilder();
		ret.append(i).append(',');
		ret.append(j).append(',');
		ret.append(p);
//		ret.append('\n');
		return ret.toString();
	}



	public List<String> deltaMoves(GameMap targetMap) {
	// throws!
		// should make sure that both maps are of the same concrete type (in GameMapSolution c'tor)

		// sanity check
		if (this.getClass() != targetMap.getClass())
			return null;
		if (this.NeutralHashCode() !=
				targetMap.NeutralHashCode())
			//throw new Exception("cannot calculate delta of different map (not isomorphic)");
			return null;

		// code
		BasicGameMap target = (BasicGameMap) targetMap;
		int permCount;
		List<String> moves = new LinkedList<>();

		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++) {
				permCount = this.tiles[i][j].getDelta(target.tiles[i][j]);
				if (permCount > 0)
					moves.add(getMoveString(i,j,permCount));
			}

		return moves;
	}

//	private double[][] getAllCost() {
//		double costGrid[][] = new double[rows][cols];
//
//		for (ArrayList<MapCoord> pipe : pipesFromStart) {
//
//			double pipeLength = pipe.size();
//			if (pipeLength == 0)  continue;		// skip if pipe is empty
//
//			// skip if pipe already priced (via another pipe)
//			MapCoord fstTile = pipe.get(0);
//			if (costGrid[fstTile.r][fstTile.c] != 0) continue;
//
//			double tileCost = pipeLength;
//			for (MapCoord co : pipe)
//			{
////				costGrid[co.r][co.c] = tileCost / (pipeLength+1);
//				costGrid[co.r][co.c] = tileCost / pipeLength;
//				tileCost--;
//			}
//
//			MapCoord lastTileCoord = pipe.get(pipe.size()-1);
//			double neighborCost = 0.5 / pipeLength;
//
//			for (Direction d : Direction.values())
//			{
//				MapCoord nb = lastTileCoord.add(MapCoord.calcDelta(d));	// neighbor coordinate
//
//				try { this.getTile(nb.r, nb.c); }
//				catch (ArrayIndexOutOfBoundsException e) { continue; }
//
//				// cost for any neighbor, including non permutables!
//				if (!allCoordsOfPipes.contains(nb))
//				{
//					if (costGrid[nb.r][nb.c] == 0)
//						costGrid[nb.r][nb.c] = neighborCost;
//					else if (costGrid[nb.r][nb.c] > neighborCost)
//						costGrid[nb.r][nb.c] = neighborCost;
//				}
//			}
//		}
//
//		for (int i=0; i<rows; i++) {
//			for (int j = 0; j < cols; j++) {
//				if (costGrid[i][j] == 0) costGrid[i][j] = 1;
//			}
//		}
//		System.out.println("{\n"+this+"}");
//
////		System.out.println("{");
////		for (int i=0; i<rows; i++) {
////			System.out.print("[");
////			for (int j = 0; j < cols; j++) {
////				if (costGrid[i][j] == 0) costGrid[i][j] = 1;
////				System.out.print(String.format( "%.3f", costGrid[i][j] ) + ", ");
////
////			}
////			System.out.println("]");
////		}
////		System.out.println(this);
////		System.out.println("}");
//
//		// TODO_DONE - FIX COST WITH HEURISTICS!!!!!!!!!!!!!!!
//
//		return costGrid;
//	}
//

}

