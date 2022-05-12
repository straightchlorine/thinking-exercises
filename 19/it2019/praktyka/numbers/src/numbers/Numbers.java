package numbers;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class Exercise3 extends Numbers {
	private static int euclidean(int n, int m) {
		while (m != 0) {
			int temp = m;
			m = n % m;
			n = temp;
		}

		return n;
	}


	protected static void answer() {
		int greatestFirstElement = 0;
		int greatestNodeCount = 0;
		int greatestGCD = 0;
		
		int firstTwo = euclidean(numbers.get(0), numbers.get(1));
		int firstThree = euclidean(firstTwo, numbers.get(2));
		
		int previous = firstThree;
		int first = 0;
		int nodes = 0;
		for(int i = 3;  i < numbers.size() - 2; i++) {
			int current = euclidean(firstThree, numbers.get(i));
			
			if(current > 1) {
				if(nodes == 0) {
					nodes = 2;
					first = numbers.get(i - 1);
				}
				else nodes++;
					
			} else {
				if(greatestNodeCount < nodes) {
					greatestNodeCount = nodes;
					greatestFirstElement = first;
					greatestGCD = previous;
				}
				
				firstTwo = euclidean(numbers.get(i), numbers.get(i + 1));
				firstThree = euclidean(firstTwo, numbers.get(i + 2));
				nodes = 0;
				
			}
			
			previous = current;
		}
		
		System.out.println("--- Third Exercise ---");
		System.out.println("First number: " + greatestFirstElement);
		System.out.println("Length: " + greatestNodeCount);
		System.out.println("GCD: " + greatestGCD);
	}

}

class Exercise2 extends Numbers {
	// will only count factorials up to 9
	private static int factorial(int n) {
		if (n == 0)
			return 1;

		int fac = 1;
		for (int i = 1; i <= n; i++) {
			fac *= i;
		}

		return fac;
	}

	private static ArrayList<Integer> fulfilling = new ArrayList<>();

	protected static void answer() {
		int count = 0;

		for (Integer currentNumber : numbers) {
			int n = currentNumber;
			int sumOfFactorials = 0;

			while (n > 0) {
				int digit = n % 10;
				n = n / 10;
				sumOfFactorials += factorial(digit);
				if (sumOfFactorials > currentNumber)
					break;
			}

			if (sumOfFactorials == currentNumber) {
				count++;
				fulfilling.add(currentNumber);
			}
		}

		System.out.println("--- Second Exercise ---");
		System.out.println("Amount of such numbers: " + count);
		System.out.println(fulfilling);
	}

}

class Exercise1 extends Numbers {
	private static int power(int n, int x) {
		if (x == 0)
			return 1;

		if (x % 2 == 0) {
			return power(n, x / 2) * power(n, x / 2);
		} else {
			return n * power(n, (x - 1) / 2) * power(n, (x - 1) / 2);
		}
	}

	private static boolean isPowerOfThree(int n) {
		if (n % 3 != 0)
			return false;
		else {
			int exponent = 0;
			while (true) {
				if (power(3, exponent) > n)
					return false;
				if (power(3, exponent) == n)
					return true;
				exponent++;
			}
		}
	}

	protected static void answer() {
		int count = 1;
		for (Integer currentNumber : Numbers.numbers) {
			if (isPowerOfThree(currentNumber))
				count++;
		}
		System.out.println("--- First Exercise ---");
		System.out.println(count);
	}
}

public class Numbers {

	static ArrayList<Integer> numbers = new ArrayList<Integer>();

	private static ArrayList<Integer> getData() {
		ArrayList<Integer> n = new ArrayList<>();
		Path path = Path.of("resources", "liczby.txt");

		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {

				n.add(Integer.parseInt(line));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return n;

	}

	public static void main(String args[]) {
		Numbers.numbers = getData();
		Exercise1.answer();
		Exercise2.answer();
		Exercise3.answer();
	}

}
