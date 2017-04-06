package main;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import buildings.Building;
import buildings.Commercial;
import buildings.ConvienceStore;
import buildings.EmptyLot;
import buildings.Public;
import buildings.PublicPark;
import buildings.Residential;
import buildings.SmallHouse;

/**
 * @author Nicholas Contreras
 */

@SuppressWarnings("serial")
public class BuildingPopupMenu extends JPanel implements ActionListener {

	private JDialog popup;

	private Building building;

	public BuildingPopupMenu(Building b) {
		this.building = b;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setPreferredSize(new Dimension(450, 200));

		Box discBox = new Box(BoxLayout.Y_AXIS);

		JLabel discLabel = new JLabel("Discription");
		discBox.add(discLabel);

		JTextArea discription = new JTextArea(building.getDiscription());
		discription.setLineWrap(true);
		discription.setWrapStyleWord(true);
		discription.setEditable(false);
		discription.setMargin(new Insets(5, 5, 5, 5));

		discBox.add(new JScrollPane(discription));

		this.add(discBox);

		Box buildBox = new Box(BoxLayout.Y_AXIS);

		if (building instanceof EmptyLot) {
			JLabel buildLabel = new JLabel("Build");
			buildBox.add(buildLabel);

			Box buildOptions = new Box(BoxLayout.X_AXIS);

			JButton buildResButton = new JButton("Residential: $" + new SmallHouse().getBuildingCost());
			buildResButton.addActionListener(this);
			buildOptions.add(buildResButton);

			JButton buildComButton = new JButton("Commercial: $" + new ConvienceStore().getBuildingCost());
			buildComButton.addActionListener(this);
			buildOptions.add(buildComButton);

			JButton buildParkButton = new JButton("Public: $" + new PublicPark().getBuildingCost());
			buildParkButton.addActionListener(this);
			buildOptions.add(buildParkButton);

			buildBox.add(buildOptions);
		} else {
			JLabel upgradeDestroyLabel = new JLabel("Upgrade/Destroy");
			buildBox.add(upgradeDestroyLabel);

			Box upgradeDestroyOptions = new Box(BoxLayout.X_AXIS);

			if (building.getUpgradedForm() != null) {
				JButton upgradeButton = new JButton("Upgrade: $" + building.getUpgradedForm().getBuildingCost());
				upgradeButton.addActionListener(this);
				upgradeDestroyOptions.add(upgradeButton);
			}

			JButton destroyButton = new JButton("Destroy: $" + new EmptyLot().getBuildingCost());
			destroyButton.addActionListener(this);
			upgradeDestroyOptions.add(destroyButton);

			buildBox.add(upgradeDestroyOptions);
		}

		this.add(buildBox);

		Box statsBox = new Box(BoxLayout.Y_AXIS);

		JLabel statsLabel = new JLabel("Stats");
		statsBox.add(statsLabel);

		Box stats = new Box(BoxLayout.Y_AXIS);

		Box levelStat = new Box(BoxLayout.X_AXIS);
		levelStat.add(Box.createHorizontalStrut(20));
		JLabel levelStatLabel = new JLabel("Level:");
		levelStat.add(levelStatLabel);
		levelStat.add(Box.createHorizontalGlue());
		JLabel levelStatValue = new JLabel("" + building.getLevel());
		levelStat.add(levelStatValue);
		levelStat.add(Box.createHorizontalStrut(20));
		stats.add(levelStat);

		if (building instanceof Residential) {
			Box valueStat = new Box(BoxLayout.X_AXIS);
			valueStat.add(Box.createHorizontalStrut(20));
			JLabel valueStatLabel = new JLabel("Value:");
			valueStat.add(valueStatLabel);
			valueStat.add(Box.createHorizontalGlue());
			JLabel valueStatValue = new JLabel("$" + ((Residential) b).getTotalValue());
			valueStat.add(valueStatValue);
			valueStat.add(Box.createHorizontalStrut(20));
			stats.add(valueStat);

			Box happynessStat = new Box(BoxLayout.X_AXIS);
			happynessStat.add(Box.createHorizontalStrut(20));
			JLabel happynessStatLabel = new JLabel("Happyness:");
			happynessStat.add(happynessStatLabel);
			happynessStat.add(Box.createHorizontalGlue());
			JLabel happynessStatValue = new JLabel("" + ((Residential) b).getTotalHappyness());
			happynessStat.add(happynessStatValue);
			happynessStat.add(Box.createHorizontalStrut(20));
			stats.add(happynessStat);
		} else if (building instanceof Commercial) {
			Box valueModStat = new Box(BoxLayout.X_AXIS);
			valueModStat.add(Box.createHorizontalStrut(20));
			JLabel valueModStatLabel = new JLabel("Value Multiplier:");
			valueModStat.add(valueModStatLabel);
			valueModStat.add(Box.createHorizontalGlue());
			JLabel valueModStatValue = new JLabel((int) (((Commercial) b).getValueMultiplier() * 100) - 100 + "%");
			valueModStat.add(valueModStatValue);
			valueModStat.add(Box.createHorizontalStrut(20));
			stats.add(valueModStat);

			Box happynessModStat = new Box(BoxLayout.X_AXIS);
			happynessModStat.add(Box.createHorizontalStrut(20));
			JLabel happynessModStatLabel = new JLabel("Happyness Multiplier:");
			happynessModStat.add(happynessModStatLabel);
			happynessModStat.add(Box.createHorizontalGlue());
			JLabel happynessModStatValue = new JLabel(
					(int) (((Commercial) b).getHappynessModifier() * 100) - 100 + "%");
			happynessModStat.add(happynessModStatValue);
			happynessModStat.add(Box.createHorizontalStrut(20));
			stats.add(happynessModStat);
			
			Box effectRadiusStat = new Box(BoxLayout.X_AXIS);
			effectRadiusStat.add(Box.createHorizontalStrut(20));
			JLabel effectRadiusStatLabel = new JLabel("Effect Radius:");
			effectRadiusStat.add(effectRadiusStatLabel);
			effectRadiusStat.add(Box.createHorizontalGlue());
			
			int value = (int) ((Commercial) b).getEffectRadius();
			JLabel effectRadiusStatValue = new JLabel(value + (value == 1 ? " tile" : " tiles"));
			effectRadiusStat.add(effectRadiusStatValue);
			effectRadiusStat.add(Box.createHorizontalStrut(20));
			stats.add(effectRadiusStat);
			
		} else if (building instanceof Public) {
			Box happynessModStat = new Box(BoxLayout.X_AXIS);
			happynessModStat.add(Box.createHorizontalStrut(20));
			JLabel happynessModStatLabel = new JLabel("Happyness Multiplier:");
			happynessModStat.add(happynessModStatLabel);
			happynessModStat.add(Box.createHorizontalGlue());
			JLabel happynessModStatValue = new JLabel(
					(int) (((Public) b).getHappynessModifier() * 100) - 100 + "%");
			happynessModStat.add(happynessModStatValue);
			happynessModStat.add(Box.createHorizontalStrut(20));
			stats.add(happynessModStat);
			
			Box effectRadiusStat = new Box(BoxLayout.X_AXIS);
			effectRadiusStat.add(Box.createHorizontalStrut(20));
			JLabel effectRadiusStatLabel = new JLabel("Effect Radius:");
			effectRadiusStat.add(effectRadiusStatLabel);
			effectRadiusStat.add(Box.createHorizontalGlue());
			
			int value = (int) ((Public) b).getEffectRadius();
			JLabel effectRadiusStatValue = new JLabel(value + (value == 1 ? " tile" : " tiles"));
			effectRadiusStat.add(effectRadiusStatValue);
			effectRadiusStat.add(Box.createHorizontalStrut(20));
			stats.add(effectRadiusStat);
		}

		statsBox.add(stats);

		this.add(statsBox);

		popup = new JDialog(GamePanel.inst.getFrame(), building.getDisplayName(),
				Dialog.ModalityType.APPLICATION_MODAL);
		popup.setResizable(false);
		popup.add(this);
		popup.pack();
	}

	public void displayPopup(int x, int y) {
		popup.setLocation(x, y);
		popup.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = ((JButton) arg0.getSource()).getText();

		int actionCost = Integer.parseInt(command.substring(command.indexOf("$") + 1));

		if (actionCost > GamePanel.inst.getCash()) {
			JOptionPane.showMessageDialog(popup, "You do not have enough money to preform this operation.",
					"Insuficient Funds", JOptionPane.ERROR_MESSAGE, null);
			return;
		}

		GamePanel.inst.modifyCash(-actionCost);

		switch (command.substring(0, command.indexOf(":"))) {
		case "Residential":
			GamePanel.inst.setBuilding(new SmallHouse(building.getRow(), building.getCol()));
			break;
		case "Commercial":
			GamePanel.inst.setBuilding(new ConvienceStore(building.getRow(), building.getCol()));
			break;
		case "Public":
			GamePanel.inst.setBuilding(new PublicPark(building.getRow(), building.getCol()));
			break;
		case "Upgrade":
			Building b = building.getUpgradedForm();
			if (b != null) {
				GamePanel.inst.setBuilding(b);
			} else {
				return;
			}
			break;
		case "Destroy":
			GamePanel.inst.setBuilding(new EmptyLot(building.getRow(), building.getCol()));
			break;
		}

		popup.dispose();
	}
}
