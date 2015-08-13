package prokofiev.pager;

import prokofiev.pager.exception.WrongSourceFileException;
import prokofiev.pager.exception.BadDictionaryException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author prokofiev
 * interface for Pager class
 */
public interface Pager {
	/**
	 * load dictionary
	 * @param filename - dictionary file
	 * @throws FileNotFoundException - when file not exist
	 * @throws BadDictionaryException - file have bad symbols
	 */
	public void loadDictionary(String filename) throws FileNotFoundException, BadDictionaryException;
	
	/**
	 * process input file by dictionary
	 * @param filename input file
	 * @throws IOException I/O errors.
	 * @throws WrongSourceFileException input file have bad text
	 */
	public void processTextFile(String filename) throws IOException, WrongSourceFileException;
}
