package buildings;

/**
* @author Nicholas Contreras
*/

public class GroceryStore extends Building implements Commercial {

	public GroceryStore(Integer row, Integer col) {
		super(row, col);
	}

	@Override
	public String getDiscription() {
		return "A store to provide a large assortment of foods and prepared products to a large area. Increases nearby property values considerably.";
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
	public double getValueMultiplier() {
		return 1.2;
	}

	@Override
	public double getHappynessModifier() {
		return 0.925;
	}

	@Override
	public int getEffectRadius() {
		return 2;
	}

	@Override
	public int getBuildingCost() {
		return 500000;
	}

	@Override
	public void dispose() {
	}
}
