/**
 * 
 */
package prokofiev.pager;

/**
 * @author prokofiev
 * interface for text processor
 */
public interface ProcessLine {
	/**
	 * Process text
	 * @param source_str source text
	 * @return processing text
	 */
	public String processLine(String source_str);
}
