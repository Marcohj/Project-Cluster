package step4_Adv_HashSet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class Searcher {

	private static String	URLMarker	= "*PAGE";

	public static ArrayList<String> exists(HashMap<String, HTMLlist> l, String word) {
		
		ArrayList<String> resultList = new ArrayList<String>();

		if (word.contains("AND")) {
			// word cotains AND... so lets make it a condition and only take
			// URLs when they are duplicate
			String[] res = word.split("AND");
			String firstWord = res[0].trim();
			String secondWord = res[1].trim();

			// get wordLists from HashTable
			HTMLlist firstWordList = l.get(firstWord);
			HTMLlist secondWordList = l.get(secondWord);

			// make sure the URL is both in firstWordList and secondWordList
			while (firstWordList.urls != null) {
				URL urlSearcher = secondWordList.urls;
				while (urlSearcher != null) {
					if (firstWordList.urls.url.compareTo(urlSearcher.url) == 0) {
						resultList.add(firstWordList.urls.url);
					}
					urlSearcher = urlSearcher.next;
				}
				firstWordList.urls = firstWordList.urls.next;
			}
		} else if (word.contains("OR")) {
			// word contains OR, so lets take URLs from both results
			String[] res = word.split("OR");
			String firstWord = res[0].trim();
			String secondWord = res[1].trim();

			HTMLlist firstWordList = l.get(firstWord);
			HTMLlist secondWordList = l.get(secondWord);

			while (firstWordList.urls != null) {
				resultList.add(firstWordList.urls.url);
				firstWordList.urls = firstWordList.urls.next;
			}

			while (secondWordList.urls != null) {
				// TODO: Need to remove double URLs
				resultList.add(secondWordList.urls.url);
				secondWordList.urls = secondWordList.urls.next;
			}
		} else {
			// word contains neither AND or OR, so lets treat it as an single
			// word
			HTMLlist wordList = l.get(word);

			while (wordList.urls != null) {
				resultList.add(wordList.urls.url);
				wordList.urls = wordList.urls.next;
			}
		}

		return resultList;
	}

	public static HTMLlist getNode(HashMap<String, HTMLlist> l, String word) {
		return l.get(word);
	}

	public static HashMap<String, HTMLlist> readHtmlList(String filename) throws IOException {
		String name, currentUrl;
		HTMLlist start, tmp;
		HashMap<String, HTMLlist> wordSet = new HashMap<String, HTMLlist>();

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

			wordSet.put(tmp.str, tmp);
		}

		infile.close(); // Close the file

		return wordSet;
	}
}
