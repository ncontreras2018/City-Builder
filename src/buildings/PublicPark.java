package buildings;

/**
 * @author Nicholas Contreras
 */

public class PublicPark extends Building implements Public {

	public PublicPark() {
		super(-1, -1);
	}
	
	public PublicPark(Integer row, Integer col) {
		super(row, col);
	}

	@Override
	public String getDiscription() {
		return "A community space for people to gather and enjoy themselves. Increases community appeal quite well.";
	}

	@Override
	public Building getUpgradedForm() {
		return new TownSquare(getRow(), getCol());
	}

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public double getHappynessModifier() {
		return 1.1;
	}

	@Override
	public int getEffectRadius() {
		return 1;
	}

	@Override
	public int getBuildingCost() {
		return 400000;
	}

	@Override
	public void dispose() {
	}
}
