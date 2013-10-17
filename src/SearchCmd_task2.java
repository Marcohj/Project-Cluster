import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchCmd_task2 {

	public static void main(String[] args) throws IOException {
		String name;
		// Check that a filename has been given as argument
		if (args.length != 1) {
			System.out.println("Usage: java SearchCmd <datafile>");
		}

		// Read the file and create the linked list
		HTMLlist l = Searcher_task2.readHtmlList(args[0]);
		// Ask for a word to search
		BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Hit return to exit.");
		boolean quit = false;

      while (!quit) {

         System.out.print("Search for: ");
         name = inuser.readLine(); // Read a line from the terminal
         if (name == null || name.length() == 0) {
            quit = true;
         }else
         if(Searcher_task2.exists(l,name)) {
            System.out.println("The word \"" + name + "\" has been found on the following pages: ");
            HTMLlist currentPage = Searcher_task2.getPage(l);
            while(currentPage != null){
               if(Searcher_task2.exists(currentPage,name)){
                  System.out.println("   " + currentPage.str);
                  currentPage = Searcher_task2.getPage(currentPage.next);
               }
            }
         } else {
            System.out.println("The word \"" + name + "\" has NOT been found.");
         }
      }
	}
}