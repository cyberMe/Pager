/**
 * 
 */
package prokofiev.pager;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author prokofiev
 *
 */
public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPager p = new Pager();
		String dict_name = "dict.txt"; 
		try {
			p.loadDict(dict_name);
		} catch (FileNotFoundException e) {
			System.out.println("bad file name" + dict_name);
			e.printStackTrace();
		}
		try {
			p.processTextFile("src.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
