/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
 */

import java.io.*;

class Searcher {
	
	// Array size
	final static int ARRAYSIZE = 5;
	
	// Step 2
	public static String[] exists(HTMLlist l, String word) {
		
		// temp variables
		String[] tempArray = new String[0];
		int tempArraySize = 0;

		// Page list and counter
		String[] pages = new String[ARRAYSIZE];
		int pageCounter = 0;

		// Pages found list and counter
		String[] existInPage = new String[ARRAYSIZE];
		int pagesFoundCounter = 0;

		while (l != null) {
			// Array size control
			if (pageCounter == pages.length) {
				tempArray = pages;
				tempArraySize = tempArray.length + ARRAYSIZE;
				pages = new String[tempArraySize];
				for (int i = 0; i < tempArray.length; i++) {
					pages[i] = tempArray[i];
				}
			}
			if (pagesFoundCounter == existInPage.length) {
				tempArray = existInPage;
				tempArraySize = tempArray.length + ARRAYSIZE;
				existInPage = new String[tempArraySize];
				for (int i = 0; i < tempArray.length; i++) {
					existInPage[i] = tempArray[i];
				}
			}

			// IF a url, add to page list
			if (l.str.substring(0, 1).equals("*")) {
				pages[pageCounter] = l.str.replace("*PAGE:", "");
				pageCounter++;
			}

			// IF text string is found, copy latest URL (make sure it is not
			// already in found list)
			if (l.str.equals(word)) {
				if (pageCounter > 0) {
					if (pageCounter > 0 && pagesFoundCounter > 0) {
						if (!existInPage[pagesFoundCounter - 1].equals(pages[pageCounter - 1])) {
							existInPage[pagesFoundCounter] = pages[pageCounter - 1];
							pagesFoundCounter++;
						}
					} else {
						existInPage[pagesFoundCounter] = pages[pageCounter - 1];
						pagesFoundCounter++;
					}
				}
			}
			l = l.next;
		}
		return existInPage;
	}
	
	// Step 3
	public static String[] exists2(HTMLlist wordlist, String searchWord) {
		String[] URLS = new String[ARRAYSIZE];
		int URLcounter = 0;
		String[] tempArray = new String[0];
		int tempArraySize = 0;

		while(wordlist != null) {
			
			if (URLcounter == URLS.length) {
				tempArray = URLS;
				tempArraySize = tempArray.length + ARRAYSIZE;
				URLS = new String[tempArraySize];
				for (int i = 0; i < tempArray.length; i++) {
					URLS[i] = tempArray[i];
				}
			}
			
			if(wordlist.str.equalsIgnoreCase(searchWord)) {
				while(wordlist.urls != null) {
					URLS[URLcounter] = wordlist.urls.str;
					URLcounter++;
					wordlist.urls = wordlist.urls.next;
				}
				wordlist.next = null;
			}
			wordlist = wordlist.next;
		}

		return URLS;
	}
	
	// Step 3
	public static HTMLlist buildHtmlList(String filename) throws IOException {
		String linetxt;
		String lastURL = "";
		HTMLlist temp, current, start = new HTMLlist(null, null);
		current = null;
		boolean alreadyExists = false;
		boolean isStart = true;

		BufferedReader infile = new BufferedReader(new FileReader(filename));
		linetxt = infile.readLine(); // Read the first line

		while (linetxt != null) { // Exit if there is none
			if (linetxt.substring(0, 1).equals("*")) {
				lastURL = linetxt;
			} else if(isStart) {
				start = new HTMLlist(linetxt, null);
				current = start;
				isStart = false;
			} else {
				// Check if word already exists
				alreadyExists = false;
				HTMLlist searcher = start;
				while (searcher != null) {
					if (searcher.str.equals(linetxt)) {
						searcher.addURL(lastURL);
						alreadyExists = true;
					}
					searcher = searcher.next;
				}

				// Add to LinkedList if it dosen't already exist
				if (!alreadyExists) {
					temp = new HTMLlist(linetxt, null);
					temp.addURL(lastURL);
					current.next = temp;
					current = temp;
				}
			}
			linetxt = infile.readLine(); // Read the next line
		}
		infile.close(); // Close the file

		return start;
	}
	
	// Step 2
	public static HTMLlist readHtmlList(String filename) throws IOException {
		String name;
		HTMLlist start, current, tmp;

		// Open the file given as argument
		BufferedReader infile = new BufferedReader(new FileReader(filename));

		name = infile.readLine(); // Read the first line
		start = new HTMLlist(name, null);
		current = start;
		name = infile.readLine(); // Read the next line
		while (name != null) { // Exit if there is none
			tmp = new HTMLlist(name, null);
			current.next = tmp;
			current = tmp; // Update the linked list
			name = infile.readLine(); // Read the next line
		}
		infile.close(); // Close the file

		return start;
	}

}
