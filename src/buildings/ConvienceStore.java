package buildings;

/**
* @author Nicholas Contreras
*/

public class ConvienceStore extends Building implements Commercial {

	public ConvienceStore() {
		super(-1, -1);
	}
	
	public ConvienceStore(Integer row, Integer col) {
		super(row, col);
	}

	@Override
	public String getDiscription() {
		return "A simple corner store to sell various products. Provides a small boost to nearby property values.";
	}

	@Override
	public Building getUpgradedForm() {
		return new GroceryStore(getRow(), getCol());
	}

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public double getValueMultiplier() {
		return 1.1;
	}

	@Override
	public double getHappynessModifier() {
		return 0.9;
	}

	@Override
	public int getEffectRadius() {
		return 1;
	}

	@Override
	public int getBuildingCost() {
		return 150000;
	}

	@Override
	public void dispose() {
	}
}
