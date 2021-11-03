package Solution;

import Game.GameMap;

import java.io.*;
import java.nio.file.Files;

public class SimpleCacheManager
implements CacheManager<GameMap> {

	private static String folder = "./solutions/";

	@Override
	public GameMap fetchSolution(GameMap problemMap) {

		//problemMap.hashCode();	// TODO_Done fix hashcode!!!! should represent any (permutation) map state!

		String path = getFolderPath() +
				problemMap.NeutralHashCode() + ".sol";	// NeutralHashCode as fix ^ ^ ^

		try {
			FileInputStream file = new FileInputStream(path);
			ObjectInputStream mapReader = new ObjectInputStream(file);

			GameMap solution = (GameMap) mapReader.readObject();

			mapReader.close();
			file.close();

			return solution;	// found and returned cached solution!
		}
		catch (FileNotFoundException e) {	// there is no cached solution!
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();		// oh no!!!!!!
		}

		return null; 	// should never happen anyway...
	}

	@Override
	public void cacheSolution(GameMap solution){

		String path = getFolderPath() +
				solution.NeutralHashCode() + ".sol";

//		File fileObj = null;
		FileOutputStream fileStream = null;
		ObjectOutputStream mapWriter = null;
		try {
//			 = getNewFileObj()
//			fileObj = new File(path);
//			fileObj
			fileStream = new FileOutputStream(path);
			mapWriter = new ObjectOutputStream(fileStream);

			mapWriter.writeObject(solution);	// saving to file!

		}
		catch (IOException IO) {
			IO.printStackTrace();
		}
		finally {
			try {
				if (mapWriter != null) mapWriter.close();	// TODOsuggest - bad bad thing, find how to fix dis
				if (fileStream != null) fileStream.close();
			} catch (IOException IO) {}
		}
	}

	public static String getFolderPath() {

		File fold = new File(folder);
		if (!fold.exists())
			fold.mkdir();

//		fold.isDirectory();
//		fold.canRead();
//		fold.canWrite();

		return (fold.exists()) ? folder : "";

	}

	
//	public void cacheSolution1(GameMap solution) {
//
//		String path = folder +
//				solution.hashCode() + ".sol";
//		FileOutputStream file = null;
//		try {
//			file = new FileOutputStream(path);
//			ObjectOutputStream mapWriter = new ObjectOutputStream(file);
//
//			mapWriter.writeObject(solution);
//
//			mapWriter.close();
//			file.close();
//
//		}
//		catch(IOException IO) {
//			if(file!=null) {try {
//				file.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}}
//			IO.getStackTrace();
//		}
//
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}







