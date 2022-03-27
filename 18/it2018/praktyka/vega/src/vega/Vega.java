package vega;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class ThirdExercise extends Vega {

	private static int getDistance(char firstLetter, char secondLetter) {
		return Math.abs((int) firstLetter - secondLetter);
	}

	private static boolean isFulfilling(String toVerify) {

		char[] word = toVerify.toCharArray();

		for(int i = 0; i < word.length; i++) {
			for(int j = i + 1; j < word.length; j++) {
				if(i == j) continue;
				
				if(getDistance(word[i], word[j]) > 10)
					return false;
			}
		}

		return true;
	}

	protected static void letterDistance() {
		ArrayList<String> fulfilling = new ArrayList<>();
		for (String current : Vega.signals) {
			if(isFulfilling(current))
				fulfilling.add(current);
		}
		
		
		System.out.println("3\n");
		for(String fulfilled : fulfilling) {
			System.out.println(fulfilled);
		}
		
	}
}

class SecondExercise extends Vega {

	protected static String countCharacters() {

		int currentBest = 0;
		int currentBestIndex = 0;
		int index = 0;

		for (int i = Vega.signals.size() - 1; i >= 0; i--) {
			ArrayList<Character> characters = new ArrayList<>();
			index = i;

			for (char character : Vega.signals.get(i).toCharArray()) {
				if (!characters.contains(character)) {
					characters.add(character);
				}
			}

			if (characters.size() >= currentBest) {
				currentBest = characters.size();
				currentBestIndex = index;
			}
		}

		System.out.println("2\n");
		System.out.println(Vega.signals.get(currentBestIndex) + " " + currentBest);
		System.out.println();
		
		return Vega.signals.get(currentBestIndex);
	}

}

class FirstExercise extends Vega {

	protected static String hiddenMessage() {
		StringBuilder builder = new StringBuilder();

		int index = 1;
		for (String current : Vega.signals) {
			if (index % 40 == 0) {
				char[] currentLine = current.toCharArray();
				builder.append(currentLine[9]);
			}
			index++;
		}

		System.out.println("1\n");
		System.out.println(builder.toString());
		System.out.println();
		
		return builder.toString();
	}
}

public class Vega {

	protected static ArrayList<String> signals = new ArrayList<>();

	private static void getData() {
		Path path = Path.of("./resources", "sygnaly.txt");

		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				signals.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getTestData() {
		Path path = Path.of("./resources", "przyklad.txt");

		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				signals.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getData();
		//getTestData();

		FirstExercise.hiddenMessage();
		SecondExercise.countCharacters();
		ThirdExercise.letterDistance();

	}

}
