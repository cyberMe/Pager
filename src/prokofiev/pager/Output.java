/**
 * 
 */
package prokofiev.pager;

import java.io.IOException;

/**
 * @author prokofiev
 * interface for output text
 */
public interface Output {
	public void open() throws IOException;
	public void close() throws IOException, WrongSourceFileException;
	public void appendLine(String line) throws IOException, WrongSourceFileException;
}
