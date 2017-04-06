package buildings;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * @author Nicholas Contreras
 */

public abstract class Building {
	
	static {
		images = new HashMap<String, BufferedImage[]>();
	}

	public static final int SIZE = 100;

	private static final HashMap<String, BufferedImage[]> images;
	
	private BufferedImage image;
	
	private int row, col;
	
	public Building(Integer row, Integer col) {
		this.row = row;
		this.col = col;
		setImg();
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public BufferedImage getImg() {
		return image;
	}

	private void setImg() {

		String key = this.getClass().getSimpleName();

		if (!images.containsKey(key)) {
			loadImagesForBuilding(key);
		}

		BufferedImage[] imgs = images.get(key);

		image = imgs[(int) (Math.random() * imgs.length)];
	}

	private void loadImagesForBuilding(String building) {

		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();

		int index = 1;

		while (true) {

			URL url = getClass().getResource("/images/" + building + index + ".png");

			if (url == null) {
				break;
			}

			try {
				BufferedImage cur = ImageIO.read(url);

				if (cur.getWidth() == cur.getHeight()) {
					imgs.add(resize(cur, SIZE, SIZE));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			index++;
		}
		
		System.out.println("Loaded " + imgs.size() + " images for " + building);
		
		images.put(building, imgs.toArray(new BufferedImage[imgs.size()]));
	}

	private static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}
	
	public String getDisplayName() {
		String displayName = this.getClass().getSimpleName();
		
		for (int i = 1; i < displayName.length(); i++) {
			if (Character.isUpperCase(displayName.charAt(i))) {
				displayName = displayName.substring(0, i) + " " + displayName.substring(i);
				i++;
			}
		}
		return displayName;
	}
	
	public abstract String getDiscription();
	
	public abstract Building getUpgradedForm();
	
	public abstract int getLevel();
	
	public abstract int getBuildingCost();
	
	public abstract void dispose();
}
