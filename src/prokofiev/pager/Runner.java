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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPager p = new Pager();
		String dict_name = "dict.txt"; 
		String source_name = "src.txt";
		try {
			p.loadDict(dict_name);
		} catch (FileNotFoundException e) {
			System.out.println("bad file name: " + dict_name);
		}
		catch (BadDictException e) {
			System.out.println("bad dictionary file: " + dict_name); 
			System.out.println("at line:"+ e.error_line + " : " + e.error_line);
		}
		try {
			p.processTextFile(source_name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (WrongSourceFileException e) {
			System.out.println("bad source file: " + source_name);
			e.printStackTrace();
		}
	}

}
