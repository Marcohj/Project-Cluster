import java.io.*;

public class Step4 {

	public static URL[] exists(HashTable wordList, String searchWord) {
		// List of URL's that contains the word
		HTMLlist result = wordList.get(searchWord);
		if(result != null) {
			return result.URLS;
		} else {
			return null;
		} 
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
		
		// HashTable
		HashTable dataTable = new HashTable(255);

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
			} else {
				HTMLlist word = new HTMLlist(linetext, null);
				word.addURL(urlToAdd);
				dataTable.put(word);
			}
			linetext = infile.readLine(); // Read the next line
			lines++;
		}
		infile.close(); // Close the file
		System.out.println("Lines: " + lines + " in file " + filename);
		return dataTable;
	}
	
	public static void main(String[] args) throws IOException {
		String name;

		// Read the file and create the linked list
		HashTable wordList = Step4.buildHtmlList("files/itcwww-small.txt");

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
				URL[] results = Step4.exists(wordList, name);
				if(results != null) {
					for(int i = 0; i < results.length; i++) {
						if(results[i] != null) {
							System.out.println("The word \"" + name + "\" was found on: " + results[i].str);
						}
					}	
				}
			}
		}
	}
	
}
