

public class PerlinNoise {
	
	public final int octaves = 8;
	public final float persistence = .5f;
	
	private int[][] primes;
	private PrimeGenerator primeGen;
	
	public PerlinNoise(long seed) {
		
		primes = new int[octaves][4];
		primeGen = new PrimeGenerator(seed);
		
		for (int i = 0; i < primes.length; i++) {
			primes[i][0] = primeGen.nextPrimeInRange(45, 65);
			primes[i][1] = primeGen.nextPrimeInRange(12000,  18000);
			primes[i][2] = primeGen.nextPrimeInRange(750000, 800000);
			primes[i][3] = primeGen.nextPrimeInRange(1350000000, 1360000000);
		}
	}
	
	public float noise1D(int x, int octave) {
		
		x = (x<<13) ^ x;
		float val = (1f - ((x * (x * x * primes[octave][1] + primes[octave][2]) + primes[octave][3]) & 0x7FFFFFFF) / 1073741824f);
		return val;
	}
	
	public float noise2D(int x, int y, int octave) {
		
		int n = x + y * primes[octave][0];
		float val = (1f - ((n * (n * n * primes[octave][1] + primes[octave][2]) + primes[octave][3]) & 0x7FFFFFFF) / 1073741824f);
		return val;
	}
	
	public float smoothedNoise1D(int x, int octave) {
		
		return noise1D(x, octave) / 2 + noise1D(x - 1, octave) / 4 + noise1D(x + 1, octave) / 4;
	}
	
	public float smoothedNoise2D(int x, int y, int octave) {
		
		float corners = (noise2D(x - 1, y - 1, octave) + noise2D(x + 1, y - 1, octave) + noise2D(x - 1, y + 1, octave) + noise2D(x + 1, y + 1, octave)) / 16;
		float sides = (noise2D(x - 1, y, octave) + noise2D(x + 1, y, octave) + noise2D(x, y - 1, octave) + noise2D(x, y + 1, octave)) / 8;
		float center = noise2D(x, y, octave) / 4;
		
		return corners + sides + center;
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
	
	public float interpolatedNoise2D(float x, float y, int octave) {
		
		int intX = (int) x;
		float fracX = x - intX;
		
		int intY = (int) y;
		float fracY = y - intY;
		
		float v1, v2, v3, v4, i1, i2;
		
		v1 = smoothedNoise2D(intX, intY, octave);
		v2 = smoothedNoise2D(intX + 1, intY, octave);
		v3 = smoothedNoise2D(intX, intY + 1, octave);
		v4 = smoothedNoise2D(intX + 1, intY + 1, octave);
		
		i1 = cosInterpolate(v1, v2, fracX);
		i2 = cosInterpolate(v3, v4, fracX);
		
		return cosInterpolate(i1, i2, fracY);
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
	
	public float perlinNoise2D(float x, float y) {
		
		float total = 0;
		
		for (int i = 0; i < octaves; i++) {
			
			int frequency = (int) Math.pow(2, i);
			float amplitude = (float) Math.pow(persistence, i);
			
			total += interpolatedNoise2D(x * frequency, y * frequency, i) * amplitude;
		}
		
		return total;
	}

}
