package FileIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Map.Map;
import Map.RandomMapGenerator;

public class MapIO {
	
	public static void saveMap(Map map, String fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static Map loadMap(String fileName) {
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			return (Map)objectIn.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
