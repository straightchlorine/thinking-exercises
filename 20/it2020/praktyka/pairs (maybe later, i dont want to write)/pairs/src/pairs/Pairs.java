package pairs;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Exercise3 extends 

class Exercise2 extends Pairs {
	protected static ArrayList<Integer> fulfillingNumbers = new ArrayList<>();
	protected static ArrayList<String> fulfillingStrings = new ArrayList<>();

	protected static void answer() {

		System.out.println("--- Exercise 2 ---");

		for (String currentString : strings) {

			char[] arr = currentString.toCharArray();
			int maxlength = 1;
			int maxstart = 0;
			int maxend = 0;

			int templength = 1;
			int start = 0;
			int end = 0;
			for (int i = 0; i < arr.length - 1; i++) {
				if ((char) arr[i] == (char) arr[i + 1]) {
					if (templength == 1)
						start = i;
					templength++;
				} else {
					end = i + 1;
					if (templength > 1 && templength > maxlength && start != end) {
						maxlength = templength;
						maxstart = start;
						maxend = end;
					}
					templength = 1;
				}
			}

			if (maxlength > 1) {

				System.out.println(currentString.substring(maxstart, maxend) + " " + maxlength);
			}

		}

	}
}

class Exercise1 extends Pairs {
	protected static ArrayList<Integer> primes = new ArrayList<>();

	private static void getPrimes(int n) {
		primes = new ArrayList<Integer>();
		boolean A[] = new boolean[n];

		A[0] = false;
		A[1] = false;

		for (int i = 2; i < n; i++) {
			A[i] = true;
		}

		for (int i = 2; i < Math.sqrt(n); i++) {
			if (A[i] == true) {
				for (int j = (int) Math.pow(i, 2); j < n; j = j + i) {
					A[j] = false;
				}
			}
		}

		for (int i = 2; i < n; i++) {
			if (A[i] == true)
				primes.add(i);
		}
	}

	protected static void answer() {

		System.out.println("--- Exercise 1 ---");
		for (Integer current : numbers) {
			if (current > 4 && current % 2 == 0)
				getPrimes(current);
			else
				continue;

			ArrayList<Integer> firsts = new ArrayList<>();
			ArrayList<Integer> seconds = new ArrayList<>();

			for (int i = 0; i < primes.size(); i++) {
				for (int j = primes.size() - 1; j >= 0; j--) {
					if (primes.get(i) + primes.get(j) == current) {
						firsts.add(primes.get(i));
						seconds.add(primes.get(j));
					}
				}
			}

			int maxDiff = -1;
			int first = 0;
			int second = 0;
			for (int i = 0; i < firsts.size(); i++) {
				if (firsts.get(i) <= seconds.get(i)) {
					int diff = seconds.get(i) - firsts.get(i);
					if (diff > maxDiff) {
						maxDiff = diff;
						first = firsts.get(i);
						second = seconds.get(i);
					}
				}
			}
			System.out.println(current + " " + first + " " + second);
		}
	}
}

public class Pairs {

	protected static ArrayList<Integer> numbers = new ArrayList<>();
	protected static ArrayList<String> strings = new ArrayList<>();

	private static void loadData() {
		Path path = Path.of("resources", "przyklad.txt");
		// Path path = Path.of("resources", "pary.txt");

		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {

				Pattern pattern = Pattern.compile("(\\d++)\\s(\\w++)");
				Matcher matcher = pattern.matcher(line);

				if (matcher.find()) {
					numbers.add(Integer.parseInt(matcher.group(1)));
					strings.add(matcher.group(2));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		loadData();
		Exercise1.answer();
		Exercise2.answer();
	}
}
