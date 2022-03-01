package caesar;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

class FirstExercise extends Caesar {

	protected static String cipher(String source, int offset) {
		StringBuilder result = new StringBuilder();

		for (char character : source.toCharArray()) {
			if (character != ' ') {
				int originalAlphabetPosition = character - 'A';
				int newAlphabetPosition = (originalAlphabetPosition + offset) % 26;
				char newCharacter = (char) ('A' + newAlphabetPosition);
				result.append(newCharacter);
			} else {
				result.append(character);
			}

		}
		return result.toString();

	}

}

public class Caesar {
	protected static ArrayList<String> sourceWords = new ArrayList<>();

	private static void loadData() {

		Path path = Path.of("./resources", "dane_6_1.txt");

		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				sourceWords.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		loadData();
		

		for(int i = 0; i < sourceWords.size(); i++) {
			System.out.println(FirstExercise.cipher(sourceWords.get(i), 107));
		}
		
	}

}
