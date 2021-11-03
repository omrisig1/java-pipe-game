package Game;

import java.io.Serializable;
import java.util.List;

import Search.GameMapState;
import Search.Searchable;
import Search.VanilaAlgorithms.GenericState;

// OLD!
//public interface GameMap
//extends Serializable, Searchable, Equateable<GameMap> {
//
//	public GameMap cloneNeutralMap();
//}

public interface GameMap
		extends Serializable, Searchable<GameMap> {

	public GameMap cloneNeutralMap();
	public int NeutralHashCode();

	public boolean equals(Object map);

	public List<String> deltaMoves(GameMap targetMap);
}
