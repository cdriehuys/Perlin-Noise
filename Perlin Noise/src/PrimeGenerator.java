import java.util.Random;


public class PrimeGenerator {
	
	Random random;
	
	public PrimeGenerator(long seed) {
		
		random = new Random(seed);
	}
	
	public boolean isPrime(int x) {
		
		if (x == 1 || x == 2) {
			return true;
		} else {
			for (int i = 3; i <= Math.floor(Math.sqrt(x)); i++) {
				if (x % i == 0) {
					return false;
				} 
			}
			return true;
		}
	}
	
	public int nextPrime() {
		
		while (true) {
			int num = random.nextInt();
			if (isPrime(num))
				return num;
		}
	}

}
