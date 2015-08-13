/*
 * author prokofiev
 */
package prokofiev.pager;

import prokofiev.pager.exception.WrongSourceFileException;
import java.io.IOException;

/**
 * interface for output text
 */
public interface Output {
	public void open() throws IOException;

	public void close() throws IOException, WrongSourceFileException;

	public void appendLine(String line) throws IOException,
			WrongSourceFileException;
}
