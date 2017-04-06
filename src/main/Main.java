package main;
import javax.swing.SwingUtilities;

/**
* @author Nicholas Contreras
*/

public class Main {
	
	public static void main(String[] args) {	
		SwingUtilities.invokeLater(() -> new GamePanel());
	}
}
