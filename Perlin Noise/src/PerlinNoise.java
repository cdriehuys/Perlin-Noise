import java.util.Arrays;


public class PerlinNoise {
	
	public final int octaves = 8;
	public final float persistence = .5f;
	
	private int[][] primes;
	private PrimeGenerator primeGen;
	
	public PerlinNoise(long seed) {
		
		primes = new int[octaves][3];
		primeGen = new PrimeGenerator(seed);
		
		for (int i = 0; i < primes.length; i++) {
			primes[i][0] = primeGen.nextPrimeInRange(12000,  18000);
			primes[i][1] = primeGen.nextPrimeInRange(750000, 800000);
			primes[i][2] = primeGen.nextPrimeInRange(1350000000, 1400000000);
		}
	}
	
	public float noise1D(int x, int octave) {
		
		x = (x<<13) ^ x;
		float val = (1f - ((x * (x * x * primes[octave][0] + primes[octave][1]) + primes[octave][2]) & 0x7FFFFFFF) / 1073741824f);
		return val;
	}
	
	public float smoothedNoise1D(int x, int octave) {
		
		//online method
		return noise1D(x, octave) / 2 + noise1D(x - 1, octave) / 4 + noise1D(x + 1, octave) / 4;
	}
	
	public float cosInterpolate(float a, float b, float x) {
		
		float ft = x * 3.1415927f;
		float f = (float) ((1 - Math.cos(ft)) * .5f);
		
		return a * (1 - f) + b * f;
	}
	
	public float interpolatedNoise1D(float x, int octave) {
		
		int intX = (int) x;
		float fracX = x - intX;
		
		float v1 = smoothedNoise1D(intX, octave);
		float v2 = smoothedNoise1D(intX + 1, octave);
		
		return cosInterpolate(v1, v2, fracX);
	}

	public static void main(String[] args) {
		
		PerlinNoise noise = new PerlinNoise(0);
		float max = 0;
		float avg = 0;
		for (int i = 0; i < 10; i++) {
			float val = noise.interpolatedNoise1D((float)i / 10, 1);
			System.out.println("Noise at " + i + ": " + val);
			if (Math.abs(val) > Math.abs(max))
				max = val;
			avg += val;
		}
		avg = avg / 10;
		System.out.println("Max val is: " + max);
		System.out.println("Avg val is: " + avg);
	}

}
