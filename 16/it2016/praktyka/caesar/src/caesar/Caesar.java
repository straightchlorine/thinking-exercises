package caesar;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SecondExercise extends Caesar {
	
	protected static String decipher(String cipher, int offset) {	

		return FirstExercise.cipher(cipher, 26 - (offset % 26));
	}
	
}

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
	protected static ArrayList<String> cipheredWords = new ArrayList<>();
	protected static ArrayList<Integer> cipher = new ArrayList<>();
	
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

	private static void loadDataCipher() {
		Path path = Path.of("./resources", "dane_6_2.txt");
		
		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

			String line = null;
			Pattern pattern = Pattern.compile("\\S+");
			
			while ((line = bufferedReader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				ArrayList<String> matches = new ArrayList<>();
				
				while(matcher.find()) 
					matches.add(matcher.group(0));
					
				cipheredWords.add(matches.get(0));
					
				if(matches.size() == 1)
					cipher.add(0);
				else 
					cipher.add(Integer.parseInt(matches.get(1)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		loadData();
		
		for(int i = 0; i < sourceWords.size(); i++) {
			//System.out.println(FirstExercise.cipher(sourceWords.get(i), 107));
		}
			
		loadDataCipher();
		
		for(int i = 0; i < cipheredWords.size(); i++) {
			System.out.println(SecondExercise.decipher(cipheredWords.get(i), cipher.get(i)));
		}
		
	}

}
