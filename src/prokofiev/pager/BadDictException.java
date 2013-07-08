/*
 * author prokofiev 
 */
package prokofiev.pager;

/**
 * this exception throwing when dict file have
 * bad tokens like empty string and multi word string
 */
public class BadDictException extends Exception {

	/** eclipse magic */
	private static final long serialVersionUID = -4179681057377813778L;
	
	public final int error_line;
	public final String bad_text;
	
	/**
	 * @param line - line number in source file
	 * @param text - invalid text
	 */
	public BadDictException(int line, String text) {
		error_line = line;
		bad_text = text;
	}
}
