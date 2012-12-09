/**
 * 
 */
package prokofiev.pager;
import java.io.*;
import java.util.*;

/**
 * @author prokofiev
 * 
 */
public class Pager implements IPager {

	/**
	 * max dictionary size = 100000 words
	 */
	private HashSet<String> dict = new HashSet<String>(100000);
	/**
	 * max num rows in output file
	 */
	private int maxOutLines;
	

	/**
	 * string processor
	 */
	private ProcessLine line_processor;
	
	/**
	 * input file reader
	 */
	private BufferedReader buf;
	/**
	 * output file writer
	 */
	private BufferedWriter wr;
	
	private LinkedList<String> buffer = new LinkedList<String>();
	
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
	public void loadDict(String filename) throws FileNotFoundException, BadDictException {
		BufferedReader dict_buf = new BufferedReader(new FileReader(filename));
		try {
			String txt;
			while ((txt = dict_buf.readLine()) != null) {
				txt = txt.trim();
				if(txt.isEmpty() || (txt.indexOf(' ') >= 0))
					throw new BadDictException(dict.size(), txt);
				dict.add(txt);
			}
			dict_buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processing input file by dictionary
	 * @param filename name of input file
	 * @throws IOException 
	 * @throws WrongSourceFileException 
	 */
	public void processTextFile(String filename) throws IOException, WrongSourceFileException {
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
	private boolean processOutput(String fn_out) throws IOException, WrongSourceFileException {
		int lines_wrote = 0;
		int page = 0;
		openOutput(fn_out + page++ + ".html");
		String curline;
		while ((curline = buf.readLine()) != null) {
			String s = line_processor.processLine(curline);
			int dot_pos = s.lastIndexOf('.');
			if(dot_pos >= 0) {
				buffer.add(s.substring(0, dot_pos +1));
				if((lines_wrote + buffer.size()) >= maxOutLines) {
					if(buffer.size() >= maxOutLines)
						throw new WrongSourceFileException();
					closeOutput();
					openOutput(fn_out + page++ + ".html");
					lines_wrote = 0;
				}
				lines_wrote += buffer.size();
				flushBuffer();
				buffer.add(s.substring(dot_pos + 1));
			}
			else {
				buffer.add(s);
			}
		}
		if(buffer.size() >= maxOutLines)
			throw new WrongSourceFileException();
		if((lines_wrote + buffer.size()) >= maxOutLines) {
			closeOutput();
			openOutput(fn_out + page++ + "html");
			lines_wrote = 0;
		}
		flushBuffer();
		closeOutput();
		return true;
	}
	
	/**
	 * flush buffer into writer
	 * @throws IOException
	 */
	private void flushBuffer() throws IOException {
		while (buffer.size() > 0) {
			wr.write(buffer.removeFirst());
		}
	}
	
	/**
	 * open new file, and write standart html header
	 * @param fn_out
	 * @throws IOException
	 */
	private void openOutput(String fn_out) throws IOException {
		wr = new BufferedWriter(new FileWriter(fn_out));
		wr.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 FINAL//EN\">\r\n");
		wr.write("<html>\r\n");
		wr.write("<head></head>\r\n<body>\r\n");
	}
	
	/**
	 * write footer and close file
	 * @throws IOException
	 */
	private void closeOutput() throws IOException {
		wr.write("</body>\r\n</html>");
		wr.close();
	}
}
