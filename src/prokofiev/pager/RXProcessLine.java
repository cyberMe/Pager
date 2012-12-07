package prokofiev.pager;

import java.util.Scanner;
import java.util.Set;

/**
 * @author prokofiev
 * Process string, using regular expression
 */
public class RXProcessLine implements ProcessLine {
	
	private Set<String> dict;
	/**
	 * @param d - dictionary
	 */
	public RXProcessLine(Set<String> d){
		dict = d;
	}
	
	/**
	 * process source string
	 */
	public String processLine(String source_str) {
		String result = source_str.toString();
		Scanner scan  = new Scanner(source_str.toString());
		scan.useDelimiter("[ .,]+"); 
		while(scan.hasNext()) {
			String tag = scan.next();
			if(dict.contains(tag)) { 
				result = result.replaceAll("(\\b" + tag + "\\b)", 
						"<b><i>" + tag + "</i></b>");
			}
		}
		scan.close();
		result += "<br>\r\n";
		return result;
	}		
}
