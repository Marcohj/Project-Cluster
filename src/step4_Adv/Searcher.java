package step4_Adv;

import java.io.*;
import java.util.ArrayList;

class Searcher {

	private static String	URLMarker	= "*PAGE";

	public static ArrayList<String> exists(HashTable l, String word) {

		ArrayList<String> resultList = new ArrayList<String>();
		URL wordURLS;
		
		if (word.contains("AND")) {
			// word cotains AND... so lets make it a condition and only take
			// URLs when they are in both objects
			String[] res = word.split("AND");
			if (res.length < 1) {
				return resultList;
			} else if (res.length < 2) {
				return exists(l, res[0].trim());
			} else if (res[0].isEmpty()) {
				return exists(l, res[1].trim());
			}
			String firstWord = res[0].trim();
			String secondWord = res[1].trim();

			// get wordLists from HashTable
			HTMLlist firstWordList = l.get(firstWord);
			HTMLlist secondWordList = l.get(secondWord);

			// make sure the URL is both in firstWordList and secondWordList
			while (firstWordList != null && secondWordList != null && firstWordList.urls != null) {
				wordURLS = firstWordList.urls;
				URL urlSearcher = secondWordList.urls;
				while (urlSearcher != null) {
					if (wordURLS.url.compareTo(urlSearcher.url) == 0) {
						resultList.add(wordURLS.url);
					}
					urlSearcher = urlSearcher.next;
				}
				wordURLS = wordURLS.next;
			}
		} else if (word.contains("OR")) {
			// word contains OR, so lets take URLs from both results
			String[] res = word.split("OR");
			if (res.length < 1) {
				return resultList;
			} else if (res.length < 2) {
				return exists(l, res[0].trim());
			} else if (res[0].isEmpty()) {
				return exists(l, res[1].trim());
			}
			String firstWord = res[0].trim();
			String secondWord = res[1].trim();

			HTMLlist firstWordList = l.get(firstWord);
			HTMLlist secondWordList = l.get(secondWord);
			
			if(firstWordList != null) {
				wordURLS = firstWordList.urls;
				while (wordURLS != null) {
					// TODO: Need to remove double URLs
					resultList.add(wordURLS.url);
					wordURLS = wordURLS.next;
				}
			}
			
			if(secondWordList != null) {
				wordURLS = secondWordList.urls;
				while (wordURLS != null) {
					// TODO: Need to remove double URLs
					resultList.add(wordURLS.url);
					wordURLS = wordURLS.next;
				}
			}
		} else {
			// word contains neither AND or OR, so lets treat it as an single
			// word
			word = word.trim();
			HTMLlist wordList = l.get(word);
			if (wordList != null) {
				wordURLS = wordList.urls;
				while (wordURLS != null) {
					resultList.add(wordURLS.url);
					wordURLS = wordURLS.next;
				}
			}
		}

		return resultList;
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
		start.addUrl(currentUrl);

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
			tmp.addUrl(currentUrl);

			dataTable.put(tmp);
		}

		infile.close(); // Close the file

		return dataTable;
	}
}
