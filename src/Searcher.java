/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
 */

import java.io.*;

class Searcher {

	public static String[] exists(HTMLlist l, String word) {
		// Array size
		final int ARRAYSIZE = 5;

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

	public static String[] exists2(HTMLlist wordlist, String searchWord) {
		String[] URLS = new String[5000];
		int URLcounter = 0;

		while(wordlist != null) {
			if(wordlist.str.equals(searchWord)) {
				while(wordlist.urls != null) {
					URLS[URLcounter] = wordlist.urls.str;
					URLcounter++;
					wordlist.urls = wordlist.urls.next;
				}
				wordlist = null;
			}
			wordlist = wordlist.next;
		}

		return URLS;

	}

	public static HTMLlist buildHtmlList(String filename) throws IOException {
		String linetxt;
		String lastURL = "";
		HTMLlist temp, current, start = null;
		boolean alreadyExists = false;
		boolean isStart = true;

		BufferedReader infile = new BufferedReader(new FileReader(filename));
		linetxt = infile.readLine(); // Read the first line

		while (linetxt != null) { // Exit if there is none
			if (linetxt.substring(0, 1).equals("*")) {
				lastURL = linetxt;
			} else {
				// Check if word already exists
				alreadyExists = false;
				while (start != null) {
					if (start.str.equals(linetxt)) {
						start.addURL(lastURL);
						alreadyExists = true;
					}
					start = start.next;
				}

				// Add to LinkedList if it dosen't already exist
				if (isStart && !alreadyExists) {
					start = new HTMLlist(linetxt, null);
					current = start;
				} else if (!alreadyExists) {
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
