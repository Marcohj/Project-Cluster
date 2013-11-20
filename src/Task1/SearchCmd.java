package Task1;

import common.HTMLlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchCmd {

	public static void main(String[] args) throws IOException {
		String name;
		// Check that a filename has been given as argument
		if (args.length != 1) {
			System.out.println("Usage: java SearchCmd <datafile>");
		}

		// Read the file and create the linked list
		HTMLlist l = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-big.txt");

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
         if(Searcher.exists(l,name)) {  // check if the word is in the file
            System.out.println("The word \"" + name + "\" has been found on the following pages: ");
            HTMLlist currentPage = Searcher.getPage(l);// get the first page as a starting point
            while(currentPage != null){
               if(Searcher.exists(currentPage,name)){ //check on each page if the word is there
                  System.out.println("   " + currentPage.str);
                  currentPage = Searcher.getPage(currentPage.next);// move to the next page, jumps from link to link
               }
            }
         } else {
            System.out.println("The word \"" + name + "\" has NOT been found.");
         }
      }
	}
}