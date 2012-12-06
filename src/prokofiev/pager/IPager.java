package prokofiev.pager;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IPager {
	public void loadDict(String filename) throws FileNotFoundException, BadDictException;
	public void processTextFile(String filename) throws IOException, WrongSourceFileException;
}
