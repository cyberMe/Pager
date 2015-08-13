/*
 * author prokofiev 
 */
package prokofiev.pager.exception;

/**
 * this exception throwing when dict file have
 * bad tokens like empty string and multi word string
 */
public class BadDictionaryException extends Exception {

	/** eclipse magic */
	private static final long serialVersionUID = -4179681057377813778L;
	
	private final int errorLine;
	private final String badText;
	
	/**
	 * @param line - line number in source file
	 * @param text - invalid text
	 */
	public BadDictionaryException(int line, String text) {
		errorLine = line;
		badText = text;
	}

    @Override
    public String toString() {
        return "BadDictionaryException{" + "errorLine=" + errorLine + ", badText=" + badText + '}';
    }   
}
