package buildings;

/**
* @author Nicholas Contreras
*/

public interface Residential {
	
	public int getBaseValue();
	
	public int getBaseHappyness();
	
	public void recalculateStats();
	
	public int getTotalValue();
	
	public int getTotalHappyness();
	
}
