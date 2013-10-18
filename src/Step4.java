import java.io.*;

public class Step4 {

	public static URL exists(HTMLlist wordlist, String searchWord) {
		URL resultURLs = null;
		int searchHash = searchWord.toLowerCase().hashCode();

		while(wordlist != null) {
			if(wordlist.hash == searchHash) {
				resultURLs = wordlist.urls;
			}
			wordlist = wordlist.next;
		}

		return resultURLs;
	}
	
	public static HTMLlist buildHtmlList(String filename) throws IOException {
		String linetxt;
		String lastURL = "";
		
		HTMLlist start = null;
		HTMLlist temp = null;
		HTMLlist current = null;
		
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
	
	public static void main(String[] args) throws IOException {
		String name;

		// Read the file and create the linked list
		HTMLlist wordList = Step4.buildHtmlList("files/itcwww-small.txt");

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
				URL results = Step4.exists(wordList, name);
				while(results != null) {
					System.out.println("The word \"" + name + "\" was found on: " + results.str);
					results = results.next;
				}
			}
		}
	}
	
}
