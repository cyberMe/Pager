/*
 * author prokofiev
 */
package prokofiev.pager;

/**
 * interface for text processor
 */
public interface LineProcessor {
	/**
	 * Process text
	 * @param sourceStr source text
	 * @return processing text
	 */
	public String processLine(String sourceStr);
}
