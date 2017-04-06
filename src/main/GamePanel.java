package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buildings.Building;
import buildings.EmptyLot;
import buildings.Residential;

/**
 * @author Nicholas Contreras
 */

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements MouseListener, KeyListener {

	private JFrame frame;

	private Building[][] city;

	public static GamePanel inst;
	
	private int cash;
	
	private JLabel cashDisplay;

	public GamePanel() {

		inst = this;
		
		cash = 1000000;

		frame = new JFrame("City Builder");
		
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);

		city = new Building[10][10];

		for (int row = 0; row < city.length; row++) {
			for (int col = 0; col < city[0].length; col++) {
				city[row][col] = new EmptyLot(row, col);
			}
		}

		this.setPreferredSize(new Dimension(city[0].length * Building.SIZE, city.length * Building.SIZE));

		this.addMouseListener(this);
		
		frame.addKeyListener(this);
		
		JPanel statsPanel = new JPanel(new FlowLayout());	
		cashDisplay = new JLabel("Money: $" + cash);
		statsPanel.add(cashDisplay);
		
		frame.add(statsPanel, BorderLayout.NORTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setResizable(false);

		frame.pack();

		frame.setVisible(true);
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				repaint();
			}
		}, 0, 50);
	}
	
	public void modifyCash(int diff) {
		cash += diff;
		cashDisplay.setText("Money: $" + cash);
	}
	
	public int getCash() {
		return cash;
	}

	public Building getBuildingAt(int row, int col) {
		return city[row][col];
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		for (int row = 0; row < city.length; row++) {
			for (int col = 0; col < city[0].length; col++) {
				g2d.drawImage(city[row][col].getImg(), col * Building.SIZE, row * Building.SIZE, null);
			}
		}

		g2d.setColor(Color.BLACK);

		for (int i = 1; i < city.length; i++) {
			g2d.drawLine(0, i * Building.SIZE, getWidth(), i * Building.SIZE);
		}

		for (int i = 1; i < city[0].length; i++) {
			g2d.drawLine(i * Building.SIZE, 0, i * Building.SIZE, getHeight());
		}
	}

	private void displayPopupMenu(int row, int col, int x, int y) {
		new BuildingPopupMenu(city[row][col]).displayPopup(x, y);
	}
	
	public void setBuilding(Building b) {
		city[b.getRow()][b.getCol()].dispose();
		city[b.getRow()][b.getCol()] = b;
		
		for (Building[] row : city) {
			for (Building cur : row) {
				if (cur instanceof Residential) {
					((Residential) cur).recalculateStats();
				}
			}
		}
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public int getNumRows() {
		return city.length;
	}
	
	public int getNumCols() {
		return city[0].length;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

		int x = e.getX(), y = e.getY();

		if (e.getButton() == MouseEvent.BUTTON1) {
			displayPopupMenu(y / Building.SIZE, x / Building.SIZE, x + Building.SIZE, y);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.isControlDown()) {
			if (e.getKeyCode() == KeyEvent.VK_S) {
				JFileChooser saveWindow = new JFileChooser();
				int result = saveWindow.showSaveDialog(frame);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					File f = saveWindow.getSelectedFile();
					
					try {
						BufferedWriter writer = new BufferedWriter(new FileWriter(f));
						
						writer.write(cash + "");
						writer.newLine();
						
						for (int row = 0; row < city.length; row++) {
							for (int col = 0; col < city[0].length; col++) {
								writer.write(city[row][col].getClass().getName() + ",");
							}
						}
						
						writer.close();
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} else if (e.getKeyCode() == KeyEvent.VK_O) {
				JFileChooser openWindow = new JFileChooser();
				int result = openWindow.showOpenDialog(frame);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					File f = openWindow.getSelectedFile();
					
					try {
						BufferedReader reader = new BufferedReader(new FileReader(f));
						
						cash = Integer.parseInt(reader.readLine());
						
						String data = reader.readLine();
						
						reader.close();
						
						int row = 0, col = 0;
						
						for (String cur : data.split(",")) {
							Building instance = (Building) Class.forName(cur).getConstructor(Integer.class, Integer.class).newInstance(row, col);
							
							city[row][col] = instance;
							
							col++;
							
							if (col == city[0].length) {
								col = 0;
								row++;
							}
						}
						
						cashDisplay.setText("Money: $" + cash);
						
						for (Building[] row1 : city) {
							for (Building cur : row1) {
								if (cur instanceof Residential) {
									((Residential) cur).recalculateStats();
								}
							}
						}
						
					} catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
