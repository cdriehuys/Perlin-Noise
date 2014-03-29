import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class PerlinNoiseImage extends JFrame {
	
	private final long seed = 1343;
	
	public PerlinNoiseImage() {
		
		ImagePanel myImg = new ImagePanel();
		add(myImg);
		myImg.repaint();
		
		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setFocusable(true);
	}
	
	private class ImagePanel extends JPanel {
		
		private PerlinNoise noise = new PerlinNoise(seed);
		
		public void draw(Graphics g) {
			
			for (int i = 0; i < 640; i++) {
				int cVal = Math.abs(Math.round(255 * noise.perlinNoise1D((float)i / 10)));
				System.out.println("Noise value: " + noise.perlinNoise1D((float) i / 10) + ", cVal: " + cVal);
				g.setColor(new Color(cVal, cVal, cVal));
				g.fillRect(i, 0, 1, 470);
			}
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			
			draw(g);
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
