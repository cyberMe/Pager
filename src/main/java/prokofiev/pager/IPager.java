package prokofiev.pager;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author prokofiev
 * interface for Pager class
 */
public interface IPager {
	/**
	 * load dictionary
	 * @param filename - dictionary file
	 * @throws FileNotFoundException - when file not exist
	 * @throws BadDictException - file have bad symbols
	 */
	public void loadDict(String filename) throws FileNotFoundException, BadDictException;
	
	/**
	 * process input file by dictionary
	 * @param filename input file
	 * @throws IOException I/O errors.
	 * @throws WrongSourceFileException input file have bad text
	 */
	public void processTextFile(String filename) throws IOException, WrongSourceFileException;
}
