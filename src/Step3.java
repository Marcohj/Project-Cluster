import java.io.*;

public class Step3 {

	public static URL[] exists(HTMLlist wordList, String searchWord) {
		// List of URL's that contains the word
		URL[] resultURLs = null;
		
		// Search wordList (ignore casing)
		while(wordList != null) {
			if(wordList.str.equalsIgnoreCase(searchWord)) {
				// If found, set resultURL to the list of URL's that contain that word
				resultURLs = wordList.URLS;
				// And stop the loop!
				break;
			}
			wordList = wordList.next;
		}

		return resultURLs;
	}
	
	public static HTMLlist buildHtmlList(String filename) throws IOException {
		// Reading the line of the file
		String linetext;
		int lines = 0;
		
		// Variables for creating the URL linked list
		URL urlList = null;
		URL urlTemp = null;
		URL urlCurrent = null;
		URL urlSearcher = null;
		URL urlToAdd = null;
		
		// Variables for creating the HTMLlist linked list
		HTMLlist wordList = null;
		HTMLlist wordTemp = null;
		HTMLlist wordCurrent = null;
		HTMLlist wordSearcher = null;
		
		// Variable for detecting if the word/URL already exists
		boolean alreadyExists = false;

		// Open FileReader and read first line
		BufferedReader infile = new BufferedReader(new FileReader(filename));
		linetext = infile.readLine();
		
		// Stop if the file is empty
		while (linetext != null) {
			
			// If the line is a URL, add it to urlList
			if (linetext.startsWith("*PAGE")) {
				// Remove "*PAGE:" from the line
				linetext = linetext.replace("*PAGE:", "");
				
				// If urlList is null, we add a start pointer and set current
				if(urlList == null) {
					urlList = new URL(linetext, null);
					urlCurrent = urlList;
				} else {
					// Else we need to check if the link already exist in the list
					alreadyExists = false;
					urlSearcher = urlList;
					// So let's search all URL gathered...
					while (urlSearcher != null) {
						if (urlSearcher.str.equals(linetext)) {;
							urlToAdd = urlSearcher;
							alreadyExists = true;
							break;
						}
						urlSearcher = urlSearcher.next;
					}
					// If not found, lets add it to the URL linked list
					if (!alreadyExists) {
						urlTemp = new URL(linetext, null);
						urlCurrent.next = urlTemp;
						urlCurrent = urlTemp;
						urlToAdd = urlTemp;
					}
					
				}
			// If wordList is null, we ran into the first word so lets create the list and add the URL
			} else if(wordList == null) {
				wordList = new HTMLlist(linetext, null);
				wordList.addURL(urlToAdd);
				wordCurrent = wordList;
			// Else we know its not an URL and that the wordList is already created, 
			// so lets see if the word already exist else add it to the list
			} else {
				// Check if word already exists
				alreadyExists = false;
				wordSearcher = wordList;
				while (wordSearcher != null) {
					// If it does, add lastURL to its URL list
					if (wordSearcher.str.equals(linetext)) {
						wordSearcher.addURL(urlToAdd);
						alreadyExists = true;
						break;
					}
					wordSearcher = wordSearcher.next;
				}

				// Add to HTMLlist if it dosen't already exist
				if (!alreadyExists) {
					wordTemp = new HTMLlist(linetext, null);
					wordTemp.addURL(urlToAdd);
					wordCurrent.next = wordTemp;
					wordCurrent = wordTemp;
				}
			}
			linetext = infile.readLine(); // Read the next line
			lines++;
		}
		infile.close(); // Close the file
		System.out.println("Lines: " + lines + " in file " + filename);
		return wordList;
	}
	
	public static void main(String[] args) throws IOException {
		String name;

		// Read the file and create the linked list
		HTMLlist wordList = Step3.buildHtmlList("files/itcwww-big.txt");

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
				URL[] results = Step3.exists(wordList, name);
				for(int i = 0; i < results.length; i++) {
					if(results[i] != null) {
						System.out.println("The word \"" + name + "\" was found on: " + results[i].str);
					}
				}
			}
		}
	}
	
}
