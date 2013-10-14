import java.io.*;

public class SearchCmd {

	public static void main(String[] args) throws IOException {
		String name;

		// Check that a filename has been given as argument
		// if (args.length != 1) {
		// System.out.println("Usage: java SearchCmd <datafile>");
		// System.exit(1);
		// }

		// Read the file and create the linked list
		// Read the file and create the linked list
		HTMLlist l = Searcher.readHtmlList("files/itcwww-small.txt");
		// HTMLlist l = Searcher.readHtmlList(args[0]);

		// Ask for a word to search
		BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Hit return to exit.");
		boolean quit = false;
		while (!quit) {
			System.out.print("Search for: ");
			name = inuser.readLine(); // Read a line from the terminal
			// if (name == null || name.length() == 0) {
			// quit = true;
			// } else if (Searcher.exists(l, name)) {
			// System.out.println("The word \"" + name + "\" has been found.");
			// } else {
			// System.out.println("The word \"" + name +
			// "\" has NOT beenfor found.");
			// }

			if (name == null || name.length() == 0) {
				quit = true;
			} else {
				String[] results = Searcher.exists(l, name);
				for (String foundIn : results) {
					if (foundIn != null) {
						System.out.println("The word \"" + name + "\" was found on: " + foundIn);
					}
				}
			}

		}
	}
}