package Step2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchCmd {

	public static void main(String[] args) throws IOException {
		String name;

		// Check that a filename has been given as argument


		// Read the file and create the linked list
		HTMLlist l = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-small.txt");

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
         if(Searcher.exists(l,name)) {
            System.out.println("The word \"" + name + "\" has been found on the following pages: ");
            HTMLlist currentPage = Searcher.getNode(l);
            while(currentPage != null){
               if(Searcher.existsOnPage(currentPage,name)){
                  System.out.println("   " + currentPage.str);
               }
               currentPage = Searcher.getNode(currentPage.next);
            }
         } else {
            System.out.println("The word \"" + name + "\" has NOT been found.");
         }
      }
	}
}