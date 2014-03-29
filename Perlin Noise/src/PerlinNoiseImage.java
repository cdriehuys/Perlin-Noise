import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class PerlinNoiseImage extends JFrame {
	
	private final long seed = 2;
	
	public PerlinNoiseImage() {
		
		ImagePanel myImg = new ImagePanel();
		add(myImg);
		myImg.repaint();
		
		setSize(1080, 720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setFocusable(true);
	}
	
	private class ImagePanel extends JPanel {
		
		private PerlinNoise noise = new PerlinNoise(seed);
		
		public void draw1D(Graphics g) {
			
			for (int i = 0; i < 1080; i++) {
				int cVal = Math.abs(Math.round(255 * noise.perlinNoise1D((float)i / 10)));
				g.setColor(new Color(cVal, cVal, cVal));
				g.fillRect(i, 0, 1, 710);
			}
		}
		
		public void draw2D(Graphics g) {
			
			Random random = new Random();
			
			for (int i = 0; i < 1080; i++) {
				for (int j = 0; j < 720; j++) {
					float val = Math.abs(noise.perlinNoise2D((float) i / 100, (float) j / 100));
					if (val < .1) {
						g.setColor(new Color(0, 0, 175));
					} else if (.1 <= val && val < .15) {
						g.setColor(new Color(255, 255, 153));
					} else if (.15 <= val && val < .35) {
						g.setColor(new Color(0, 175, 0));
					} else {
						g.setColor(new Color(51, 153, 0));
					}
					g.fillRect(i, j, 1, 1);
				}
			}
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			
			draw2D(g);
		}
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PerlinNoiseImage img = new PerlinNoiseImage();
				img.setVisible(true);
			}
		});
	}

}
