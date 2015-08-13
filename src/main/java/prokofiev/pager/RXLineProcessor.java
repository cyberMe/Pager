/*
 * author prokofiev
 */
package prokofiev.pager;

import java.util.Scanner;
import java.util.Set;

/**
 * Process string, using regular expression
 */
public class RXLineProcessor implements LineProcessor {
	
	private final Set<String> dict;
	
	/**
	 * @param dictionary - dictionary
	 */
	public RXLineProcessor(Set<String> dictionary){
		dict = dictionary;
	}
	
	/**
	 * process source string
     * @param sourceString
	 */
    @Override
	public String processLine(String sourceString) {
		String result = sourceString;
        try (Scanner scanner = new Scanner(sourceString)) {
            scanner.useDelimiter("[ .,]+");
            while (scanner.hasNext()) {
                String tag = scanner.next();
                if (dict.contains(tag)) {
                    result = result.replaceAll("(\\b" + tag + "\\b)",
                            "<b><i>" + tag + "</i></b>");
                }
            }
        }
		result += "<br>\r\n";
		return result;
	}
}
