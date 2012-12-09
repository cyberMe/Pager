/**
 * 
 */
package prokofiev.pager;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author prokofiev
 * Run pager with set param
 */
public class Runner {
	
	private String dict_name = "dict.txt"; 
	private String source_name = "src.txt";
	private int maxOutLines = 100000;

	public static void main(String[] args) {
		Runner r = new Runner();
		r.initParam(args);
		r.Run();
	}
	
	/**
	 * Initialization parameters of the program by argument list
	 * @param args list of parameters
	 */
	private void initParam(String[] args) {
		if(args.length > 1) {
			dict_name = args[0];
			source_name = args[1];
		}
		if(args.length > 2) {
			maxOutLines = Integer.parseInt(args[2]);
		}
	}
	
	/**
	 * Starts processing
	 */
	private void Run() {
		IPager p = new Pager(maxOutLines);
		try {
			p.loadDict(dict_name);
		} catch (FileNotFoundException e) {
			System.out.println("bad file name: " + dict_name);
		} catch (BadDictException e) {
			System.out.println("bad dictionary file: " + dict_name);
			System.out.println("at line:" + e.error_line + " : " + e.error_line);
		}
		try {
			p.processTextFile(source_name);
		} catch (IOException e) {
			System.out.println("Error when reading source file.");
			e.printStackTrace();
		} catch (WrongSourceFileException e) {
			System.out.println("bad source file: " + source_name);
			e.printStackTrace();
		}
	}
}
