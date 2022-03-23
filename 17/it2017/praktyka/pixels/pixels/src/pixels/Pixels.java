package pixels;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class GetInput extends Pixels {
	protected static int[][] getInput() {
		int[][] pixels = new int[200][320];
		Path data = Path.of("resources", "dane.txt");

		try (BufferedReader bufferedReader = Files.newBufferedReader(data, Charset.forName("UTF-8"))) {

			String line;
			int row = 0;
			while ((line = bufferedReader.readLine()) != null) {

				Pattern spaces = Pattern.compile("\\S+");
				Matcher match = spaces.matcher(line);

				ArrayList<Integer> matches = new ArrayList<>();
				while (match.find())
					matches.add(Integer.parseInt(match.group(0)));

				for (int i = 0; i < 320; i++) {
					if (row == 200)
						break;
					pixels[row][i] = matches.get(i);

				}

				row++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * for (int i = 0; i < 200; i++) { for (int j = 0; j < 320; j++) {
		 * System.out.print(pixels[i][j] + " "); } System.out.println(); }
		 */

		return pixels;
	}
	
	protected static int[][] getTestInput() {
		int[][] pixels = new int[200][320];
		Path data = Path.of("resources", "przyklad.txt");

		try (BufferedReader bufferedReader = Files.newBufferedReader(data, Charset.forName("UTF-8"))) {

			String line;
			int row = 0;
			while ((line = bufferedReader.readLine()) != null) {

				Pattern spaces = Pattern.compile("\\S+");
				Matcher match = spaces.matcher(line);

				ArrayList<Integer> matches = new ArrayList<>();
				while (match.find())
					matches.add(Integer.parseInt(match.group(0)));

				for (int i = 0; i < 320; i++) {
					if (row == 200)
						break;
					pixels[row][i] = matches.get(i);

				}

				row++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * for (int i = 0; i < 200; i++) { for (int j = 0; j < 320; j++) {
		 * System.out.print(pixels[i][j] + " "); } System.out.println(); }
		 */

		return pixels;
	}
}

public class Pixels {
	public static void main(String[] args) {
		int testPixels[][] = GetInput.getTestInput();
	}
}
