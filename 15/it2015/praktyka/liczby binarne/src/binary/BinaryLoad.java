package binary;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class FirstExcercise extends BinaryLoad {
	protected static int moreZeroes(ArrayList<String> binaries) {

		int numbersWithMoreZeroes = 0;

		for (String binary : binaries) {

			int zeroes = 0;
			int ones = 0;

			for (Character singleDigit : binary.toCharArray()) {
				if (singleDigit == '1') {
					ones++;
					continue;
				}
				if (singleDigit == '0') {
					zeroes++;
					continue;
				}
			}

			if (zeroes > ones)
				numbersWithMoreZeroes++;

		}

		return numbersWithMoreZeroes;
	}
}

class SecondExercise extends BinaryLoad {
	/*
	 * Binary number is divisible by 2 iff the last digit is zero.
	 */
	protected static int divisibleBy2(ArrayList<String> binaries) {

		int divisibleBy2 = 0;

		for (String binary : binaries) {
			if (binary.length() > 2 && binary.toCharArray()[binary.length() - 1] == '0')
				divisibleBy2++;
		}

		return divisibleBy2;
	}

	/*
	 * Binary number is divisible by three iff the difference of non-zero even and odd bits is divisible by three.
	 */
	protected static int divisibleBy3(ArrayList<String> binaries) {
		int divisibleBy3 = 0;

		for (String binary : binaries) {
			char[] arrayBinary = binary.toCharArray();
			int evenBits = 0;
			int oddBits = 0;

			for (int i = 0; i < arrayBinary.length; i++) {
				if (i % 2 == 0 && arrayBinary[i] == 1) {
					evenBits++;
				} else if (i % 2 != 0 && arrayBinary[i] == 1) {
					oddBits++;
				}

			}

			int difference = evenBits - oddBits;
			if (difference % 3 == 0)
				divisibleBy3++;
		}

		return divisibleBy3;

	}

	/*
	 * Binary number is divisible by 8 iff the last three digits are zeroes.
	 */
	protected static int divisibleBy8(ArrayList<String> binaries) {

		int divisibleBy8 = 0;

		for (String binary : binaries) {

			if (binary.length() > 4 && binary.toCharArray()[binary.length() - 1] == '0'
					&& binary.toCharArray()[binary.length() - 2] == '0'
					&& binary.toCharArray()[binary.length() - 3] == '0')
				divisibleBy8++;
		}
		return divisibleBy8;
	}
}

class ThirdExercise extends BinaryLoad {
	
	/*
	 * Function copies the original array, then sorts it based on length. 
	 * After that it extracts every record with the greatest length and adds it to separate array
	 * Verifies if comparing is necessary, if so sorts one more time based on genuine value.
	 */
	protected static int findGreatest(ArrayList<String> binaries) {

		// copied binaries array - in order to sort it later
		ArrayList<String> copyBinaries = new ArrayList<String>(binaries);
		// array full of the binaries with the largest amount of digits - less to check
		ArrayList<String> greatestCandidates = new ArrayList<String>();

		// sorting each binary based on length (from the longest to the shortest)
		Collections.sort(copyBinaries, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1.length() > o2.length())
					return -1;
				else if (o1.length() < o2.length())
					return 1;
				else
					return 0;
			}
		});

		// length of the longest binary
		int greatestLength = copyBinaries.get(0).length();

		// extracting every single binary that matches the greatest length
		for (String binary : copyBinaries) {
			if (binary.length() != greatestLength)
				break;
			else
				greatestCandidates.add(binary);
		}

		// comparing, if deemed necessary
		if (greatestCandidates.size() == 1) {
			int copyID = copyBinaries.indexOf(greatestCandidates.get(0));
			return binaries.indexOf(copyBinaries.get(copyID)) + 1;
		} else {
			Collections.sort(greatestCandidates, new Comparator<String>() {	// comparator, based on length
				@Override
				public int compare(String o1, String o2) {

					char[] o1Array = o1.toCharArray();
					char[] o2Array = o2.toCharArray();

					for (int i = 0; i < o1.length(); i++) {
						if (o1Array[i] == o2Array[i])
							continue;
						else if (o1Array[i] == '0' && o2Array[i] == '1')
							return 1;
						else
							return -1;
					}

					return 0;
				}
			});

			int copyIndex = copyBinaries.indexOf(greatestCandidates.get(0));
			int originalIndex = binaries.indexOf(copyBinaries.get(copyIndex));

			return originalIndex + 1;
		}
	}
	
	/*
	 * Very similar to the previous one - with sorting reversed.
	 */
	protected static int findLowest(ArrayList<String> binaries) {
		// copied binaries array - in order to sort it later
		ArrayList<String> copyBinaries = new ArrayList<String>(binaries);
		// array full of the binaries with the largest amount of digits - less to check
		ArrayList<String> lowestCandidates = new ArrayList<String>();

		// sorting each binary based on length (from the longest to the shortest)
		Collections.sort(copyBinaries, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1.length() < o2.length())
					return -1;
				else if (o1.length() > o2.length())
					return 1;
				else
					return 0;
			}
		});

		// length of the longest binary
		int greatestLength = copyBinaries.get(0).length();

		// extracting every single binary that matches the greatest length
		for (String binary : copyBinaries) {
			if (binary.length() != greatestLength)
				break;
			else
				lowestCandidates.add(binary);
		}

		// comparing, if deemed necessary
		if (lowestCandidates.size() == 1) {
			int copyID = copyBinaries.indexOf(lowestCandidates.get(0));
			return binaries.indexOf(copyBinaries.get(copyID));
		} else {
			// using another comparator to compare every single entry and determine which is
			// greater
			Collections.sort(lowestCandidates, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {

					char[] o1Array = o1.toCharArray();
					char[] o2Array = o2.toCharArray();

					for (int i = 0; i < o1.length(); i++) {
						if (o1Array[i] == o2Array[i])
							continue;
						else if (o1Array[i] == '0' && o2Array[i] == '1')
							return -1;
						else
							return 1;
					}

					return 0;
				}
			});

			int copyIndex = copyBinaries.indexOf(lowestCandidates.get(0));
			int originalIndex = binaries.indexOf(copyBinaries.get(copyIndex));

			return originalIndex + 1;
		}
	}
}

public class BinaryLoad {

	/*
	 * Function loads data line by line and creates and array of those for further
	 * processing
	 */
	public static ArrayList<String> loadBinaries() {
		ArrayList<String> binaries = new ArrayList<String>();

		Path path = Path.of("./resources", "liczby.txt");
		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			String line = null;
			
			while ((line = bufferedReader.readLine()) != null) {
				binaries.add(line);
			}
			
		} catch (IOException e) {
			System.err.println("Exception caught: ");
			e.printStackTrace();
		}

		return binaries;
	}

	/*
	 * Helper method for quick testing
	 */
	@SuppressWarnings("unused")
	private static void display(ArrayList<String> boi) {
		for (String binary : boi) {
			System.out.println(boi.indexOf(binary) + ": " + binary);
		}
	}

	

	public static void main(String args[]) {
		// load the numbers into the string array
		ArrayList<String> binaries = loadBinaries();


		System.out.println("A(0) > A(1): " + FirstExcercise.moreZeroes(binaries));
		System.out.println("\t\t-- Divisibility ---");
		System.out.println("\tNumbers divisible by 2: " + SecondExercise.divisibleBy2(binaries));
		System.out.println("\tNumbers divisible by 3: " + SecondExercise.divisibleBy3(binaries));
		System.out.println("\tNumbers divisible by 8: " + SecondExercise.divisibleBy8(binaries));
		System.out.println("\t\t-- Minimum and maximum ---");
		System.out.println("\tIndex of the greatest number: " + ThirdExercise.findGreatest(binaries));
		System.out.println("\tIndex of the lowest number: " + ThirdExercise.findLowest(binaries));

	}
}
