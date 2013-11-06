import java.io.*;

public class Step4_3 {

	public static URL[] exists(HashTable wordList, String searchWord, String[] andWords, String[] orWords) {

		HTMLlist[] andResults = new HTMLlist[andWords.length];
		HTMLlist[] orResults = new HTMLlist[orWords.length];

		boolean urlExist = false;
		int urlCounter = 0;
		int urlAdded = 0;

		// Create a HTMLlist array with AND search words
		for (int i = 0; i < andResults.length; i++) {
			andResults[i] = wordList.get(andWords[i]);
			urlCounter += andResults[i].URLS.length;
		}

		// Create a HTMLlist array with OR search words
		for (int i = 0; i < orWords.length; i++) {
			orResults[i] = wordList.get(orWords[i]);
			urlCounter += orResults[i].URLS.length;
		}

		URL[] result = new URL[urlCounter];

		// Add AND word URL's
		for (int i = 0; (i + 1) < andResults.length; i++) {
			HTMLlist word = andResults[i];
			HTMLlist secondWord = andResults[i + 1];
			if (word != null) {
				for (URL url : word.URLS) {
					for (URL urlTwo : secondWord.URLS) {
						if (url == urlTwo) {
							urlExist = false;
							for (URL urlInArray : result) {
								if (url == urlInArray) {
									urlExist = true;
								}
							}
							if (!urlExist) {
								result[urlAdded] = url;
								urlAdded++;
							}
						}
					}
				}
			}
		}

		// Add OR word URL's
		for (int i = 0; i < orResults.length; i++) {
			if (orResults[i] != null) {
				for (int z = 0; z < orResults[i].URLS.length; z++) {
					for (URL url : orResults[i].URLS) {
						urlExist = false;
						for (URL urlInArray : result) {
							if (url == urlInArray) {
								urlExist = true;
							}
						}
						if (!urlExist) {
							result[urlAdded] = url;
							urlAdded++;
						}
					}
				}
			}
		}

		if (andWords.length == 0 && orWords.length == 0) {
			HTMLlist wordResult = wordList.get(searchWord);
			if (wordResult != null) {
				result = wordResult.URLS;
			} else {
				result = new URL[0];
			}
		}

		return result;
	}

	public static HashTable buildHtmlList(String filename) throws IOException {
		// Reading the line of the file
		String linetext;
		int lines = 0;

		// Variables for creating the URL linked list
		URL urlList = null;
		URL urlTemp = null;
		URL urlCurrent = null;
		URL urlSearcher = null;
		URL urlToAdd = null;

		// Count lines in file
		BufferedReader lineCounterFile = new BufferedReader(new FileReader(filename));
		linetext = lineCounterFile.readLine();
		while (linetext != null) {
			linetext = lineCounterFile.readLine();
			lines++;
		}
		lineCounterFile.close();

		// Open FileReader and read first line
		BufferedReader infile = new BufferedReader(new FileReader(filename));

		// HashTable
		HashTable dataTable = new HashTable(lines);

		linetext = infile.readLine();
		// Stop if the file is empty
		while (linetext != null) {

			// If the line is a URL, add it to urlList
			if (linetext.startsWith("*PAGE")) {
				// Remove "*PAGE:" from the line
				linetext = linetext.replace("*PAGE:", "");
				// Make sure urlToAdd is reset
				urlToAdd = null;

				// If urlList is null, we add a start pointer and set current
				if (urlList == null) {
					urlList = new URL(linetext, null);
					urlCurrent = urlList;
				} else {
					// So let's search all URL gathered...
					urlSearcher = urlList;
					while (urlSearcher != null) {
						if (urlSearcher.str.equals(linetext)) {
							;
							urlToAdd = urlSearcher;
							break;
						}
						urlSearcher = urlSearcher.next;
					}

					// If not found, lets add it to the URL linked list
					if (urlToAdd == null) {
						urlTemp = new URL(linetext, null);
						urlCurrent.next = urlTemp;
						urlCurrent = urlTemp;
						urlToAdd = urlTemp;
					}

				}
				// If wordList is null, we ran into the first word so lets
				// create the list and add the URL
			} else {
				HTMLlist word = new HTMLlist(linetext, null);
				word.addURL(urlToAdd);
				dataTable.put(word);
			}
			linetext = infile.readLine(); // Read the next line
		}
		infile.close(); // Close the file
		System.out.println("Lines: " + lines + " in file " + filename);
		return dataTable;
	}

	public static void main(String[] args) throws IOException {
		String name;

		// Read the file and create the linked list
		HashTable wordList = Step4_3.buildHtmlList("files/itcwww-small.txt");

		// Ask for a word to search
		BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Hit return to exit.");
		boolean quit = false;
		while (!quit) {
			System.out.print("Search for: ");
			name = inuser.readLine(); // Read a line from the terminal

			if (name == null || name.length() == 0) {
				quit = true;
			} else {
				String[] andWords = name.split(" AND ");
				if (andWords.length <= 1) {
					andWords = new String[0];
				}
				String[] orWords = name.split(" OR ");
				if (orWords.length <= 1) {
					orWords = new String[0];
				}

				URL[] results = Step4_3.exists(wordList, name, andWords, orWords);
				if (results != null) {
					for (int i = 0; i < results.length; i++) {
						if (results[i] != null) {
							System.out.println("The word \"" + name + "\" was found on: " + results[i].str);
						}
					}
				}
			}
		}
	}
}