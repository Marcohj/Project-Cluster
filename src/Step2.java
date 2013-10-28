import java.io.*;

public class Step2 {
	
	// Array size
	final static int ARRAYSIZE = 500;
	
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
	
	public static void main(String[] args) throws IOException {
		String name;

		long startTimer = System.currentTimeMillis();
		
		// Read the file and create the linked list
		HTMLlist l = Step2.readHtmlList("files/itcwww-small.txt");

		long stopTimer = System.currentTimeMillis();
		System.out.println("Timer: " + (stopTimer - startTimer));
		
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
				String[] results = Step2.exists(l, name);
				for (String foundIn : results) {
					if (foundIn != null) {
						System.out.println("The word \"" + name + "\" was found on: " + foundIn);
					}
				}
			}
		}
	}
}
