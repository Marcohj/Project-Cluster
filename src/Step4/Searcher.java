package Step4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Searcher {

	private static String	URLMarker	= "*PAGE";

	public static boolean exists(HashTable l, String word) {
		if (l.get(word) != null) {
			return true;
		}
		return false;
	}

	public static HTMLlist getNode(HashTable l, String word) {
		return l.get(word);
	}

	public static HashTable readHtmlList(String filename) throws IOException {
		String name, currentUrl;
		HTMLlist start, tmp;
		HashTable dataTable = new HashTable();

		// Open the file given as argument
		BufferedReader infile = new BufferedReader(new FileReader(filename));

		currentUrl = infile.readLine(); // Read the first line
		name = infile.readLine(); // Read the second line

		start = new HTMLlist(name, null);
		start.AddUrl(currentUrl);

		while (name != null)// Exit if there is none
		{

			// get next line
			name = infile.readLine();
			if (name == null)
				break;

			// check if name is url
			if (name.startsWith(URLMarker)) {
				currentUrl = name;
				continue;
			}
			
			tmp = new HTMLlist(name, null);
			tmp.AddUrl(currentUrl);
			
			dataTable.put(tmp);
		}

		infile.close(); // Close the file

		return dataTable;
	}
}
