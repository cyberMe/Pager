/*
 * author prokofiev
 */
package prokofiev.pager;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * text writer with sentences splitter
 */
public class BufferedOutput implements Output {

	private LinkedList<String> buffer = new LinkedList<String>();
	private BufferedWriter wr;
	private String base_name;
	private int lines_wrote = 0;
	private int page = 0;
	private int maxLines;
	Pattern pattern = Pattern.compile("[!?.][^!?.]*$");
	
	/**
	 * create new buffered output
	 * @param file_base_name base part for output filename
	 * @param max_lines max lines per file
	 */
	public BufferedOutput(String file_base_name, int max_lines) {
		base_name = file_base_name;
		maxLines = max_lines;
	}
	
	/**
	 * begin work with output
	 * @throws IOException 
	 */
	@Override
	public void open() throws IOException {
		openOutput();
	}

	/**
	 * put line to output
	 * @throws IOException
	 * @throws WrongSourceFileException
	 */
	@Override
	public void appendLine(String line) throws IOException, WrongSourceFileException {
		Matcher match = pattern.matcher(line);
		
		if (match.find()) {
			int dot_pos = match.end();
			buffer.add(line.substring(0, dot_pos));   //left part of string
			flushBuffer();
			buffer.add(line.substring(dot_pos));     //right part of string
		} else {
			buffer.add(line);
		}
	}

	/**
	 * flush all buffer and close output file
	 * @throws IOException
	 * @throws WrongSourceFileException
	 */
	@Override
	public void close() throws IOException, WrongSourceFileException {
		flushBuffer();
		closeOutput();
	}
	
	/**
	 * flush buffer into writer
	 * @throws IOException
	 * @throws WrongSourceFileException 
	 */
	private void flushBuffer() throws IOException, WrongSourceFileException {
		if ((lines_wrote + buffer.size()) >= maxLines) {
			if (buffer.size() >= maxLines)
				throw new WrongSourceFileException();
			closeOutput();
			openOutput();
		}
		lines_wrote += buffer.size();
		while (buffer.size() > 0) {
			wr.write(buffer.removeFirst());
		}
	}
	
	/**
	 * open new file, and write standart html header
	 * @throws IOException
	 */
	private void openOutput() throws IOException {
		wr = new BufferedWriter(new FileWriter(base_name + page++ + ".html"));
		
		wr.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 FINAL//EN\">\r\n");
		wr.write("<html>\r\n");
		wr.write("<head></head>\r\n<body>\r\n");
		lines_wrote = 0;
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
