import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FirstExercise extends PI {

}

public class PI {
	
	private static void loadPoints(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) {
		
		Path path = Path.of("./resources", "punkty.txt");
		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				// regex
				Pattern pattern = Pattern.compile("[0-9]*\\S");
				Matcher matcher = pattern.matcher(line);

				// ArrayList containing all the matches
				ArrayList<String> temp = new ArrayList<String>();
				while (matcher.find())
					temp.add(matcher.group());
				
				xCoords.add(Integer.parseInt(temp.get(0)));
				yCoords.add(Integer.parseInt(temp.get(1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		ArrayList<Integer> xCoords = new ArrayList<Integer>();
		ArrayList<Integer> yCoords = new ArrayList<Integer>();

		loadPoints(xCoords, yCoords);
		
		System.out.println("--- First Exercise ---");

	}

}
