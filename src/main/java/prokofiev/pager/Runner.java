/*
 * author prokofiev
 */
package prokofiev.pager;

import prokofiev.pager.exception.WrongSourceFileException;
import prokofiev.pager.exception.BadDictionaryException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Run pager with set param
 */
public class Runner {
	
	private String dictName = "dict.txt"; 
	private String sourceName = "src.txt";
	private int maxOutLines = 100000;

	public static void main(String[] args) {
		Runner r = new Runner();
		r.initParam(args);
		r.run();
	}
	
	/**
	 * Initialization parameters of the program by argument list
	 * @param args list of parameters
	 */
	private void initParam(String[] args) {
		if(args.length > 1) {
			dictName = args[0];
			sourceName = args[1];
		}
		if(args.length > 2) {
			maxOutLines = Integer.parseInt(args[2]);
		}
	}
	
	/**
	 * Starts processing
	 */
	private void run() {
		Pager p = new FilePager(maxOutLines);
		try {
			p.loadDictionary(dictName);
		} catch (FileNotFoundException e) {
			System.out.println("bad file name: " + dictName);
		} catch (BadDictionaryException e) {
			System.out.println("bad dictionary file: " + dictName);
			System.out.println(e);
		}
		try {
			p.processTextFile(sourceName);
		} catch (IOException e) {
			System.out.println("Error when reading source file.");
			e.printStackTrace();
		} catch (WrongSourceFileException e) {
			System.out.println("bad source file: " + sourceName);
			e.printStackTrace();
		}
	}
}
