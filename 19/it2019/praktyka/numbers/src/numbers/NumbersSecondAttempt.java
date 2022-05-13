package numbers;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class Exercise3second extends NumbersSecondAttempt {

	private static int gcd(int n, int m) {
		while (m != 0) {
			int t = m;
			m = n % m;
			n = t;
		}

		return n;
	}

	protected static void answer() {
		int greatestFirst = 0;
		int greatestLength = 0;
		int greatestGCD = 0;

		int currentGCD = gcd(gcd(numbers.get(0), numbers.get(1)), numbers.get(2));
		int first = 0;
		int firstValue = numbers.get(0);
		int length = 3;
		int previous = 0;

		for (int i = 3; i < numbers.size(); i++) {
			previous = currentGCD;
			currentGCD = gcd(currentGCD, numbers.get(i));

			if (currentGCD == 1) {
				if (length > greatestLength) {
					greatestLength = length;
					greatestFirst = firstValue;
					greatestGCD = previous;
				}

				first += 1;
				i = first + 1;
				firstValue = numbers.get(first - 1);
				currentGCD = gcd(gcd(numbers.get(first - 1), numbers.get(first)), numbers.get(i));
				length = 3;

			} else {
				length++;
			}

		}

		System.out.println("--- Third Exercise ---");
		System.out.println(greatestFirst);
		System.out.println(greatestLength);
		System.out.println(greatestGCD);
	}

}

class Exercise2second extends NumbersSecondAttempt {
	private static int factorial(int n) {
		int factorial = 1;

		if (n == 0)
			return 1;
		if (n == 1)
			return 1;

		for (int i = 2; i <= n; i++) {
			factorial *= i;
		}

		return factorial;

	}

	protected static void answer() {
		int count = 0;
		ArrayList<Integer> theNumbers = new ArrayList<>();

		for (Integer current : numbers) {
			int n = current;
			int factorialOfDigits = 0;

			while (n > 0) {
				int digit = n % 10;
				factorialOfDigits += factorial(digit);

				if (factorialOfDigits > current)
					break;

				n = n / 10;
			}

			if (factorialOfDigits == current) {
				count++;
				theNumbers.add(current);
			}

		}

		System.out.println("--- Second Exercise ---");
		System.out.println(count + ", " + theNumbers);
	}
}

class Exercise1second extends NumbersSecondAttempt {
	private static boolean isPower(Integer num) {

		for (int i = 0;; i++) {
			if (Math.pow(3, i) > num)
				return false;

			if (Math.pow(3, i) == num)
				return true;
		}

	}

	protected static void answer() {
		int count = 0;

		for (Integer current : numbers) {
			if (isPower(current))
				count++;

		}

		System.out.println("--- First Exercise ---");
		System.out.println(count);
	}
}

public class NumbersSecondAttempt {
	protected static ArrayList<Integer> numbers = new ArrayList<>();

	private static void getData() {
		Path path = Path.of("resources", "liczby.txt");

		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

			String line;
			while ((line = bufferedReader.readLine()) != null)
				numbers.add(Integer.parseInt(line));

		} catch (Exception e) {

		}

	}

	public static void main(String[] args) {
		getData();
		Exercise1second.answer();
		Exercise2second.answer();
		Exercise3second.answer();

	}

}
