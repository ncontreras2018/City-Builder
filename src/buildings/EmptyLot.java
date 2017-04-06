package buildings;

/**
 * @author Nicholas Contreras
 */

public class EmptyLot extends Building implements Commercial {

	public EmptyLot() {
		super(-1, -1);
	}
	
	public EmptyLot(Integer row, Integer col) {
		super(row, col);
	}

	@Override
	public String getDiscription() {
		return "A completly vacent piece of land. Looks kinda ugly and is just pretty sucky all around.";
	}

	@Override
	public Building getUpgradedForm() {
		return null;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public double getValueMultiplier() {
		return 0.8;
	}

	@Override
	public double getHappynessModifier() {
		return 0.8;
	}

	@Override
	public int getEffectRadius() {
		return 1;
	}

	@Override
	public int getBuildingCost() {
		return 50000;
	}

	@Override
	public void dispose() {
	}
}
