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
	private final int N = 10;
	
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
	
	LinkedList<String> buffer = new LinkedList<String>();
	
	/**
	 * load dictionary in memory
	 * @param filename dictionary file
	 * @throws FileNotFoundException
	 */
	public void loadDict(String filename) throws FileNotFoundException {
		BufferedReader dict_buf = new BufferedReader(new FileReader(filename));
		try {
			String txt;
			while ((txt = dict_buf.readLine()) != null) {
				dict.add(txt);
			}
		} catch (IOException e) {
			//TODO something wrong with filesystem?
			e.printStackTrace();
		}
		try {
			dict_buf.close();
		} catch (IOException e) {
			// TODO replace on error?
			e.printStackTrace();
		}

	}

	/**
	 * Processing input file by dictionary
	 * @param filename name of input file
	 * @throws IOException 
	 */
	public void processTextFile(String filename) throws IOException {
		buf = new BufferedReader(new FileReader(filename));
		line_processor = new RXProcessLine(dict);
		//TODO replace lines check on state buf check
		try {
			processOutput(filename);
		}
		catch (WrongSourceFileException e) {
			//TODO
			e.printStackTrace();
		}
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
				if((lines_wrote + buffer.size()) >= N) {
					if(buffer.size() >= N)
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
		if(buffer.size() >= N)
			throw new WrongSourceFileException();
		if((lines_wrote + buffer.size()) >= N) {
			closeOutput();
			openOutput(fn_out + page++ + "html");
			lines_wrote = 0;
		}
		flushBuffer();
		closeOutput();
		return true;
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	private void flushBuffer() throws IOException {
		while (buffer.size() > 0) {
			wr.write(buffer.removeFirst());
		}
	}
	
	/**
	 * 
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
	 * 
	 * @throws IOException
	 */
	private void closeOutput() throws IOException {
		wr.write("</body>\r\n</html>");
		wr.close();
	}
}
