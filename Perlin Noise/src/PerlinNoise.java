import java.util.Arrays;


public class PerlinNoise {
	
	public final int octaves = 8;
	public final float persistence = .25f;
	
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
	
	public float perlinNoise1D(float x) {
		
		float total = 0;
		
		for (int i = 0; i < octaves; i++) {
			
			int frequency = (int) Math.pow(2, i);
			float amplitude = (float) Math.pow(persistence, i);
			
			total += interpolatedNoise1D(x * frequency, i) * amplitude;
		}
		
		return total;
	}

}
