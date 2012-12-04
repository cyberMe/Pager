package prokofiev.pager;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author cool
 *
 */
public class RXProcessLine implements ProcessLine {
	
	private Set<String> dict;
	/**
	 * 
	 * @param d
	 */
	public RXProcessLine(Set<String> d){
		dict = d;
	}
	
	/**
	 * 
	 */
	public String processLine(String source_str) {
		Scanner scan  = new Scanner(source_str);
		String sss = source_str;
		scan.useDelimiter("[ .,]+");
		Set<String> ignore_set = new HashSet<String>(); 
		while(scan.hasNext()) {
			String n = scan.next();
			if(!ignore_set.contains(n) && dict.contains(n)) {
				//FIXME "st sta status"  would be error FIX \< begin word, \> end word 
				sss = sss.replaceAll("(\\<"+n+"\\>)", "<b><i>+n+</i></b>");
				ignore_set.add(n);
			}
		}
		scan.close();
		sss += "<br>\r\n";
		return sss;
	}		
}
