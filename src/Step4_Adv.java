import java.io.*;

public class Step4_Adv {

	public static URL[] exists(HashTable wordList, String searchWords) {

		int urlAdded = 0;
		URL[] urlResult = new URL[150000];
		
		// Split into expressions by OR
		String[] expressions = searchWords.split(" OR ");
		for(String expression : expressions) {
			// Split into sub expressions by each AND word
			String[] andWords = expression.split(" AND ");
			// Do if there is at least one AND
			if(andWords.length > 1) {
				// For each AND word we find the current and the next to compare URL to
				for (int i = 0; (i + 1) < andWords.length; i++) {
					HTMLlist word = wordList.get(andWords[i]);
					HTMLlist secondWord = wordList.get(andWords[i + 1]);
					// If both words return a result we go ahead
					if (word != null && secondWord != null) {
						for (URL url : word.URLS) {
							// We make sure that the URL exist in both words
							if(!urlDosentExist(secondWord.URLS, url)) {
								// We make sure that it dosen't already exist in the urlResult
								if(urlDosentExist(urlResult, url)) {
									urlResult[urlAdded] = url;
									urlAdded++;
								}
							}
						}
					}
				}
			} else {
			// Else we just go for a single word
				HTMLlist wordResult = wordList.get(expression);
				if (wordResult != null) {
					for(URL url : wordResult.URLS) {
						if(urlDosentExist(urlResult, url)) {
							urlResult[urlAdded] = url;
							urlAdded++;
						}
					}
				}
			}
		}

		return urlResult;
	}
	
	public static boolean urlDosentExist(URL[] urlList, URL urlToAdd) {
		boolean addURL = true;
		
		for (URL url : urlList) {
			if (url == urlToAdd) {
				addURL = false;
			}
		}
		
		return addURL;
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
		String word;

		// Read the file and create the linked list
		HashTable wordList = Step4_Adv.buildHtmlList("files/itcwww-small.txt");

		// Ask for a word to search
		BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Hit return to exit.");
		boolean quit = false;
		while (!quit) {
			System.out.print("Search for: ");
			word = inuser.readLine(); // Read a line from the terminal

			if (word == null || word.length() == 0) {
				quit = true;
			} else {
				URL[] results = Step4_Adv.exists(wordList, word);
				if (results != null) {
					for (int i = 0; i < results.length; i++) {
						if (results[i] != null) {
							System.out.println("The word \"" + word + "\" was found on: " + results[i].str);
						}
					}
				}
			}
		}
	}
}