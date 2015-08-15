/*
 * author prokofiev 
 */
package prokofiev.pager;

import prokofiev.pager.exception.WrongSourceFileException;
import prokofiev.pager.exception.BadDictionaryException;
import java.io.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * implementation interface
 */
public class FilePager implements Pager {

	/** max dictionary size = 100000 words */
	private final Set<String> dictionary = new HashSet<>(100000);
	
	/** max num rows in output file */
	private int maxOutLines;
	
	/** string processor */
	private LineProcessor lineProcessor;
	
	/** input file reader */
	private BufferedReader reader;

	private final Logger log = LoggerFactory.getLogger(FilePager.class);

	public FilePager(int maxLines) {
		setMaxOutLines(maxLines);
	}

	private void setMaxOutLines(int maxLines) {
		if (maxLines < 10) {
            maxOutLines = 10;
        } else if (maxLines > 100000) {
            maxOutLines = 100000;
        } else {
            maxOutLines = maxLines;
        }
	}
	
	/**
	 * load dictionary in memory
	 * @param filename dictionary file
	 * @throws FileNotFoundException on missing file
	 * @throws BadDictionaryException on bad token in dict file
	 */
    @Override
	public void loadDictionary(String filename) 
			throws FileNotFoundException, BadDictionaryException {
		
		try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = buffer.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty() || line.indexOf(' ') >= 0) {
                    throw new BadDictionaryException(dictionary.size(), line);
                }
				dictionary.add(line);
			}
		} catch (IOException e) {
			log.error("", e);
		} 
	}

	/**
	 * Processing input file by dictionary
	 * @param filename name of input file
	 * @throws IOException 
	 * @throws WrongSourceFileException 
	 */
    @Override
	public void processTextFile(String filename) 
			throws IOException, WrongSourceFileException {
		reader = new BufferedReader(new FileReader(filename));
		lineProcessor = new RXLineProcessor(dictionary);
		processOutput(filename);
		reader.close();
	}

	/**
	 * read input buffer and process lines into out file
	 * @param fileNameOut
	 * @throws IOException
	 * @throws WrongSourceFileException 
	 */
	private boolean processOutput(String fileNameOut) 
			throws IOException, WrongSourceFileException {
		Output output = new BufferedOutput(fileNameOut, maxOutLines);
		output.open();
		String curline;
		while ((curline = reader.readLine()) != null) {
			String s = lineProcessor.processLine(curline);
			output.appendLine(s);
		}
		output.close();
		return true;
	}
}
