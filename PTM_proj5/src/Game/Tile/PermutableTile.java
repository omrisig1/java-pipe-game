package Game.Tile;
import Game.Constants.*;

public interface PermutableTile extends NeutralizeableTile {
	public void nextPermute();
	public Direction getCompliment(Direction d);
	public String getCurPermuteStr();
}
