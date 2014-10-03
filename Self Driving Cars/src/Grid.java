import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
 * I am 99% sure that the map generation team will be writing this exact same class only better probably, but I'll write it anyway!
 * This process could probably be done in a way easier for map creators through picture files
 * @author mwhitma2
 *
 */
public class Grid {
	
	private int width,height;
	//Tile[][] data;
	
	public Grid(int w, int h, String path) {
		this.width=w;
		this.height=h;
		try {
			load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}

	private void load(String path) throws IOException{
		File file = new File(path);
		Scanner scanner = new Scanner(file);
		for(int y=0; y<height && scanner.hasNextLine(); y++) {
			String line = scanner.nextLine();
			for(int x=0; x<width && x<line.length(); x++) {
				char t = line.charAt(x);
				//Process the characters in the text file, put them into the array
				switch(t) {
				case '0': //Nonroad
					break;
				case '1': //Road
					break;
				case '2': //Traffic light
					break;
				default:
					//data[x][y] = null;
					break;
				}
			}
		}
		scanner.close();
	}
		
		
		
		
	/*public Tile getTile(int x, int y) {
		return data[x][y];
	}*/
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	/*public static void main(String[] args) {
		new Grid(5,5,"maps/testmap1.txt");
	}*/
}
