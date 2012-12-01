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
public class Pager {

	private HashSet<String> dict = new HashSet<String>(1000000);

	/**
	 * 
	 * @param filename
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		BufferedWriter wr = new BufferedWriter(
				new FileWriter(filename + ".out"));
		wr.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 FINAL//EN\">\r\n");
		wr.write("<html>\r\n");
		wr.write("<head></head>\r\n<body>\r\n");
		String curline;
		while ((curline = buf.readLine()) != null) {
			String out_str = "";
			for (String t : curline.split(" ")) {
				if (dict.contains(t)) {
					out_str += "<b><i>" + t + "</i></b> ";
				}
				else {
					out_str += t + " ";
				}
			}
			wr.write(out_str + "<br>\r\n");
		}
		wr.write("</body>\r\n</html>");
		wr.close();
		buf.close();
	}


}
