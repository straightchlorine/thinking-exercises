package pixels;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FourthExercise extends Pixels {
	private static int longestVerticalLine = 0;

	protected static void horizontalLines(int[][] pixels) {
		findVerticalLines(pixels);
		System.out.println("--- fourth exercise ---");
		System.out.println("The longest vertical line is " + FourthExercise.longestVerticalLine + " pixels long.");
	}
	
	private static void findVerticalLines(int[][] pixels) {
		ArrayList<Integer> lines = new ArrayList<>();

		
		for (int i = 0; i < 320; i++) {
			int currentLineBrightness = 0;	// first pixel of each line as a potential beginning
			int currentLineComponents = 0;				// how many pixels the line consists of
			for (int j = 0; j < 200; j++) {
				if(j == 0) {
					currentLineBrightness = pixels[j][i];
					currentLineComponents++;
					continue;
				}
				
				if(pixels[j][i] != currentLineBrightness) {
					if(currentLineComponents > 1) 
						lines.add(currentLineComponents);
					currentLineComponents = 1;
					currentLineBrightness = pixels[j][i];
				} else currentLineComponents++;
	
			}
		}
		
		lines.sort(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o2, o1);
			}
			
			
		});
		
		FourthExercise.longestVerticalLine = lines.get(0);
		
	}
}

class ThirdExercise extends Pixels {
	private static int contrastNeighbour = 0;

	protected static void getContrastNeighbours(int[][] pixels) {

		checkNeighbours(pixels);
		System.out.println("--- third exercise ---");
		System.out.println("Contrasting neighbours: " + ThirdExercise.contrastNeighbour);
	}

	private static void checkNeighbours(int[][] pixels) {
		for (int i = 0; i < 200; i++)
			for (int j = 0; j < 320; j++) {
				ArrayList<Integer> neighbours = getNeighbours(pixels, i, j);
				isContrast(pixels[i][j], neighbours);
			}
	}

	private static void isContrast(int original, ArrayList<Integer> neighbours) {
		for (Integer current : neighbours) {
			if (Math.abs(original - current) > 128) {
				ThirdExercise.contrastNeighbour++;
				break;
			}
		}
	}

	private static ArrayList<Integer> getNeighbours(int[][] pixels, int row, int column) {
		ArrayList<Integer> neighbours = new ArrayList<>();

		if (row == 0) {
			if (column == 0) {
				neighbours.add(pixels[row + 1][column]); // top left
				neighbours.add(pixels[row][column + 1]);
			} else if (column == 319) {
				neighbours.add(pixels[row + 1][column]); // top right
				neighbours.add(pixels[row][column - 1]);
			} else {
				neighbours.add(pixels[row][column - 1]); // top
				neighbours.add(pixels[row][column + 1]);
				neighbours.add(pixels[row + 1][column]);
			}
		} else if (row == 199) {
			if (column == 0) {
				neighbours.add(pixels[row][column + 1]); // bottom left
				neighbours.add(pixels[row - 1][column]);
			} else if (column == 319) {
				neighbours.add(pixels[row][column - 1]); // bottom right
				neighbours.add(pixels[row - 1][column]);
			} else {
				neighbours.add(pixels[row][column + 1]); // bottom
				neighbours.add(pixels[row][column - 1]);
				neighbours.add(pixels[row - 1][column]);
			}
		} else if (column == 0) {
			neighbours.add(pixels[row - 1][column]); // on the left side
			neighbours.add(pixels[row + 1][column]);
			neighbours.add(pixels[row][column + 1]);
		} else if (column == 319) {
			neighbours.add(pixels[row - 1][column]); // on the right side
			neighbours.add(pixels[row + 1][column]);
			neighbours.add(pixels[row][column - 1]);
		} else {
			neighbours.add(pixels[row][column - 1]); // any other position
			neighbours.add(pixels[row][column + 1]);
			neighbours.add(pixels[row + 1][column]);
			neighbours.add(pixels[row - 1][column]);
		}

		return neighbours;
	}
}

class SecondExercise extends Pixels {
	private static int asymetricalRows = 0;

	private static void symetryCheck(int[][] pixels) {
		for (int i = 0; i < 200; i++)
			for (int j = 0, k = 319; j < 320 && k > 0; j++, k--) {
				if (pixels[i][j] != pixels[i][k]) {
					SecondExercise.asymetricalRows++;
					break;
				}
			}
	}

	protected static void asymetry(int[][] pixels) {
		symetryCheck(pixels);
		System.out.println("--- second exercise ---");
		System.out.println(
				"Minimal number of rows in order to achieve vertical symmetry: " + SecondExercise.asymetricalRows);
	}
}

class FirstExercise extends Pixels {
	private static int theBrightest = 0;
	private static int theDarkest = 0;

	private static void brigtest(int[][] pixels) {

		int buffer = 0;

		for (int i = 0; i < 200; i++)
			for (int j = 0; j < 320; j++) {
				if (pixels[i][j] > buffer)
					buffer = pixels[i][j];
			}

		FirstExercise.theBrightest = buffer;
	}

	private static void darkest(int[][] pixels) {

		int buffer = 255;

		for (int i = 0; i < 200; i++)
			for (int j = 0; j < 320; j++) {
				if (pixels[i][j] < buffer)
					buffer = pixels[i][j];
			}

		FirstExercise.theDarkest = buffer;
	}

	protected static void minimumAndMaximum(int[][] pixels) {
		brigtest(pixels);
		darkest(pixels);
		System.out.println("--- first exercise ---");
		System.out.println("The brightest pixel: " + FirstExercise.theBrightest);
		System.out.println("The darkest pixel: " + FirstExercise.theDarkest);

	}

}

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

		return pixels;
	}
}

public class Pixels {
	public static void main(String[] args) {
		int pixels[][] = GetInput.getInput(); // TEST INPUT
		FirstExercise.minimumAndMaximum(pixels);
		SecondExercise.asymetry(pixels);
		ThirdExercise.getContrastNeighbours(pixels);
		FourthExercise.horizontalLines(pixels);
	}
}
