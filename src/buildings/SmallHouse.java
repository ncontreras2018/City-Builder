package buildings;

import java.util.Timer;
import java.util.TimerTask;

import main.GamePanel;

/**
 * @author Nicholas Contreras
 */

public class SmallHouse extends Building implements Residential {

	private int value, happyness;
	
	private Timer payoutTimer;
	
	public SmallHouse() {
		super(-1, -1);
	}

	public SmallHouse(Integer row, Integer col) {
		super(row, col);

		payoutTimer = new Timer(true);
		payoutTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				if (Math.random() * 100 < happyness) {
					int rent = value / 30 * 12;
					
					GamePanel.inst.modifyCash(rent);
				}
			}
		}, (long) (Math.random() * 30000), 30000);
	}

	@Override
	public String getDiscription() {
		return "A basic residence. Houses 1 person.";
	}

	@Override
	public Building getUpgradedForm() {
		return new MediumHouse(getRow(), getCol());
	}

	@Override
	public int getBaseValue() {
		return 100000;
	}

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public void recalculateStats() {
		value = getBaseValue();

		int avgAreaValue = 0;
		int residentialsCount = 0;

		for (int row = Math.max(getRow() - 1, 0); row < Math.min(getRow() + 1, GamePanel.inst.getNumRows()); row++) {
			for (int col = Math.max(getCol() - 1, 0); col < Math.min(getCol() + 1,
					GamePanel.inst.getNumCols()); col++) {
				if (row != getRow() || col != getCol()) {

					Building b = GamePanel.inst.getBuildingAt(row, col);

					if (b instanceof Residential) {
						avgAreaValue += ((Residential) b).getBaseValue();
						residentialsCount++;
					}
				}
			}
		}

		if (residentialsCount > 0) {
			avgAreaValue /= residentialsCount;
		}

		value += (avgAreaValue - value) / 20;

		double commericalMultiplier = 1;

		for (int row = 0; row < GamePanel.inst.getNumRows(); row++) {
			for (int col = 0; col < GamePanel.inst.getNumCols(); col++) {

				Building b = GamePanel.inst.getBuildingAt(row, col);

				if (b instanceof Commercial) {
					if (b.getLevel() <= getLevel()) {

						Commercial c = (Commercial) b;

						if (Math.hypot(row - getRow(), col - getCol()) <= c.getEffectRadius()) {
							commericalMultiplier *= c.getValueMultiplier();
						}
					}
				}
			}
		}

		value *= commericalMultiplier;

		value = Math.max(value, 10000);

		happyness = getBaseHappyness();

		int avgAreaHappyness = 0;

		for (int row = Math.max(getRow() - 1, 0); row < Math.min(getRow() + 1, GamePanel.inst.getNumRows()); row++) {
			for (int col = Math.max(getCol() - 1, 0); col < Math.min(getCol() + 1,
					GamePanel.inst.getNumCols()); col++) {
				if (row != getRow() || col != getCol()) {

					Building b = GamePanel.inst.getBuildingAt(row, col);

					if (b instanceof Residential) {
						avgAreaHappyness += ((Residential) b).getBaseHappyness();
					}
				}
			}
		}

		if (residentialsCount > 0) {
			avgAreaHappyness /= residentialsCount;
		}

		happyness += (happyness - avgAreaHappyness);

		double publicMultiplier = 1;

		for (int row = 0; row < GamePanel.inst.getNumRows(); row++) {
			for (int col = 0; col < GamePanel.inst.getNumCols(); col++) {

				Building b = GamePanel.inst.getBuildingAt(row, col);

				if (b instanceof Public) {
					if (b.getLevel() <= getLevel()) {

						Public p = (Public) b;

						if (Math.hypot(row - getRow(), col - getCol()) <= p.getEffectRadius()) {
							publicMultiplier *= p.getHappynessModifier();
						}
					}
				} else if (b instanceof Commercial) {
					Commercial c = (Commercial) b;

					if (Math.hypot(row - getRow(), col - getCol()) <= c.getEffectRadius()) {
						publicMultiplier *= c.getHappynessModifier();
					}
				}
			}
		}
		happyness *= publicMultiplier;

		happyness = Math.max(happyness, 0);
		happyness = Math.min(happyness, 100);
	}

	@Override
	public int getTotalValue() {
		return value;
	}

	@Override
	public int getBaseHappyness() {
		return 60;
	}

	@Override
	public int getTotalHappyness() {
		return happyness;
	}

	@Override
	public int getBuildingCost() {
		return 75000;
	}

	@Override
	public void dispose() {
		payoutTimer.cancel();
	}
}
