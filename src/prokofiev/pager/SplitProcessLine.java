package prokofiev.pager;

import java.util.*;

public class SplitProcessLine implements ProcessLine {

	private Set<String> dict;
	
	/**
	 * 
	 * @param d
	 */
	public SplitProcessLine(Set<String> d) {
		dict = d;
	}
	
	/**
	 * 
	 */
	public String processLine(String source_str) {
		StringBuilder sb = new StringBuilder();
		for (String t : source_str.split(" ")) {
			if (dict.contains(t)) {
				sb.append("<b><i>").append(t).append("</i></b> ");
			}
			else {
				sb.append(t).append(" ");
			}
		}
		return sb.append("<br>\r\n").toString();
	}

}
