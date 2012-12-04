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
	private final int N = 100000;
	
	private ProcessLine line_processor;
	
	/**
	 * load dictionary in memory
	 * @param filename dictionary file
	 * @throws FileNotFoundException
	 */
	public void loadDict(String filename) throws FileNotFoundException {
		BufferedReader buf = new BufferedReader(new FileReader(filename));
		try {
			String txt;
			while ((txt = buf.readLine()) != null) {
				dict.add(txt);
			}
		} catch (IOException e) {
			//TODO something wrong with filesystem?
			e.printStackTrace();
		}
		try {
			buf.close();
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
		BufferedReader buf = new BufferedReader(new FileReader(filename));
		line_processor = new RXProcessLine(dict);
		int step = 0;
		//TODO replace lines check on state buf check
		boolean flag = false;
		do {
			flag = processOutput(buf, filename+"."+step+".html");
		} while(flag);
		buf.close();
	}

	/**
	 * read input buffer and process lines into out file
	 * @param buf
	 * @param fn_out
	 * @throws IOException
	 */
	private boolean processOutput(BufferedReader buf, String fn_out) throws IOException {
		BufferedWriter wr = new BufferedWriter(new FileWriter(fn_out));
		wr.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 FINAL//EN\">\r\n");
		wr.write("<html>\r\n");
		wr.write("<head></head>\r\n<body>\r\n");
		int lines_wrote = 0;
		String curline;
		while ((curline = buf.readLine()) != null && lines_wrote <= N) {
			//TODO search dots in line, for finding sent border.
			// delay write output. waiting end of sent
			// throw exception when sent more then N lines
			String s = line_processor.processLine(curline);
			wr.write(s);
			++lines_wrote;
		}
		wr.write("</body>\r\n</html>");
		wr.close();
		return curline != null;
	}
	
	
	/**
	 * read whole input file for find all sentences and check numrows
	 * @param filename name of input file
	 * @throws WrongSourceFileException
	 */
	/*private void preprocessInputFile(String filename) throws WrongSourceFileException {
		BufferedReader buf = new BufferedReader(new FileReader(filename));
		
		for(int i = 0; ; ++i){
			String curline = buf.readLine();
			if(curline == null)
				break;
			
		}
		buf.close();
	}*/
	
}
