package step3;

import java.io.*;

class Searcher {

	private static String	URLMarker	= "*PAGE";

	public static boolean exists(HTMLlist l, String word) {
		while (l != null) {
			if (l.str.equals(word)) {
				return true;
			}
			l = l.next;
		}
		return false;
	}

	public static HTMLlist getNode(HTMLlist l, String word) {
		while (l != null) {
			if (l.str.equals(word))
				return l;
			l = l.next;
		}
		return null;
	}

	public static HTMLlist readHtmlList(String filename) throws IOException {
		String name, currentUrl;
		HTMLlist start, tmp;

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

			tmp = start;

			while (tmp != null) {
				if (tmp.str.compareTo(name) == 0) {
					tmp.AddUrl(currentUrl);
					break;
				}

				// if not found (last element) add new
				if (tmp.next == null) {
					tmp.next = new HTMLlist(name, null);
					tmp.next.AddUrl(currentUrl);
					break;
				}

				tmp = tmp.next;
			}
		}

		infile.close(); // Close the file

		return start;
	}
}
