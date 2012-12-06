package prokofiev.pager;

/**
 * 
 * @author cool
 * this exception throwing when dict file have
 * bad tokens like empty string and multi word string
 */
public class BadDictException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4179681057377813778L;
	
	public int error_line;
	public String bad_text;
	
	public BadDictException(int line, String text) {
		error_line = line;
		bad_text = text;
	}

}
