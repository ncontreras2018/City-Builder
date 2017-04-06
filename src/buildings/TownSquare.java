package buildings;

/**
 * @author Nicholas Contreras
 */

public class TownSquare extends Building implements Public {

	public TownSquare() {
		super(-1, -1);
	}
	
	public TownSquare(Integer row, Integer col) {
		super(row, col);
	}

	@Override
	public String getDiscription() {
		return "A center for people from all around the town to gather at and admire. Significantly boosts happyness.";
	}

	@Override
	public Building getUpgradedForm() {
		return null;
	}

	@Override
	public int getLevel() {
		return 2;
	}

	@Override
	public double getHappynessModifier() {
		return 1.2;
	}

	@Override
	public int getEffectRadius() {
		return 2;
	}

	@Override
	public int getBuildingCost() {
		return 1000000;
	}

	@Override
	public void dispose() {
	}
}
