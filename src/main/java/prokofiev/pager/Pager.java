/*
 * author prokofiev 
 */
package prokofiev.pager;

import java.io.*;
import java.util.*;

/**
 * implementation interface
 */
public class Pager implements IPager {

	/** max dictionary size = 100000 words */
	private HashSet<String> dict = new HashSet<String>(100000);
	
	/** max num rows in output file */
	private int maxOutLines;
	
	/** string processor */
	private ProcessLine line_processor;
	
	/** input file reader */
	private BufferedReader buf;
	
	
	public Pager(int maxLines) {
		setMaxOutLines(maxLines);
	}

	private void setMaxOutLines(int maxLines) {
		if (maxLines < 10)
			maxOutLines = 10;
		else if (maxLines > 100000)
			maxOutLines = 100000;
		else
			maxOutLines = maxLines;
	}
	
	/**
	 * load dictionary in memory
	 * @param filename dictionary file
	 * @throws FileNotFoundException on missing file
	 * @throws BadDictException on bad token in dict file
	 */
	public void loadDict(String filename) 
			throws FileNotFoundException, BadDictException {
		
		BufferedReader dict_buf = null;
		try {
			dict_buf = new BufferedReader(new FileReader(filename));
			String txt;
			while ((txt = dict_buf.readLine()) != null) {
				txt = txt.trim();
				if (txt.isEmpty() || (txt.indexOf(' ') >= 0))
					throw new BadDictException(dict.size(), txt);
				dict.add(txt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dict_buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		
	}

	/**
	 * Processing input file by dictionary
	 * @param filename name of input file
	 * @throws IOException 
	 * @throws WrongSourceFileException 
	 */
	public void processTextFile(String filename) 
			throws IOException, WrongSourceFileException {
		buf = new BufferedReader(new FileReader(filename));
		line_processor = new RXProcessLine(dict);
		processOutput(filename);
		buf.close();
	}

	/**
	 * read input buffer and process lines into out file
	 * @param buf
	 * @param fn_out
	 * @throws IOException
	 * @throws WrongSourceFileException 
	 */
	private boolean processOutput(String fn_out) 
			throws IOException, WrongSourceFileException {
		Output output = new BufferedOutput(fn_out, maxOutLines);
		output.open();
		String curline;
		while ((curline = buf.readLine()) != null) {
			String s = line_processor.processLine(curline);
			output.appendLine(s);
		}
		output.close();
		return true;
	}
}
