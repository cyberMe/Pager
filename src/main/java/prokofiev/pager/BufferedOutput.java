/*
 * author prokofiev
 */
package prokofiev.pager;

import prokofiev.pager.exception.WrongSourceFileException;
import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * text writer with sentences splitter
 */
public class BufferedOutput implements Output {

	private final LinkedList<String> buffer = new LinkedList<>();
	private final Pattern pattern = Pattern.compile("[!?.][^!?.]*$");
	private final String baseName;
	private int linesWrote;
	private final int maxLines;
	private BufferedWriter writer;
	private int page;
	
	/**
	 * create new buffered output
	 * @param fileBaseName base part for output filename
	 * @param maxLines max lines per file
	 */
	public BufferedOutput(String fileBaseName, int maxLines) {
        this.page = 0;
        this.linesWrote = 0;
		this.baseName = fileBaseName;
		this.maxLines = maxLines;
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
     * @param line
	 * @throws IOException
	 * @throws WrongSourceFileException
	 */
	@Override
	public void appendLine(String line) throws IOException, WrongSourceFileException {
		Matcher match = pattern.matcher(line);
		
		if (match.find()) {
			int dotPos = match.end();
			buffer.add(line.substring(0, dotPos));   //left part of string
			flushBuffer();
			buffer.add(line.substring(dotPos));     //right part of string
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
		if ((linesWrote + buffer.size()) >= maxLines) {
			if (buffer.size() >= maxLines) {
                throw new WrongSourceFileException();
            }
			closeOutput();
			openOutput();
		}
		linesWrote += buffer.size();
		while (buffer.size() > 0) {
			writer.write(buffer.removeFirst());
		}
	}
	
	/**
	 * open new file, and write standart html header
	 * @throws IOException
	 */
	private void openOutput() throws IOException {
		writer = new BufferedWriter(new FileWriter(baseName + page++ + ".html"));
		
		writer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 FINAL//EN\">\r\n");
		writer.write("<html>\r\n");
		writer.write("<head></head>\r\n<body>\r\n");
		linesWrote = 0;
	}
	
	/**
	 * write footer and close file
	 * @throws IOException
	 */
	private void closeOutput() throws IOException {
		writer.write("</body>\r\n</html>");
		writer.close();
	}

}
