package Task3;

import common.HTMLlist;
import common.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchCmd_task3 {


   public static void main(String[] args) throws IOException {
      String name;
      // Check that a filename has been given as argument
      if (args.length != 1) {
         System.out.println("Usage: java SearchCmd <datafile>");
      }

      // Read the file and create the linked list, the original linked list
      long time1 = System.nanoTime();
      HTMLlist l = Searcher_task3.readHtmlList(args[0]),
              start = l;
      long time2 = System.nanoTime();
      System.out.println("Indexing took:" + ((time2 - time1)* 1e-6) + " miliseconds to complete");
      // Ask for a word to search
      BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));
      boolean quit = false;

      while (!quit) {
         System.out.print("Search for: ");
         name = inuser.readLine(); // Read a line from the terminal

         long searchtime1 = System.nanoTime();
         if (name == null || name.length() == 0) {
            quit = true;
         }else
         if(Searcher_task3.exists(l, name)) {  // check if the word is in the file
            System.out.println(name + " exists on the following pages:");
           URL temp = Searcher_task3.getNode(l, name).urls;
           while(temp != null){
              System.out.println(temp.str);
              temp = temp.next;
           }
         long searchtime2 = System.nanoTime();
         System.out.println("searching " + name + " took " + ((searchtime2 - searchtime1)* 1e-6) + " miliseconds to complete");
         } else {
            System.out.println("The word \"" + name + "\" has NOT been found.");
         }
      }

      //then go and check every page for existence of every word
      //then build the final list only with nodes that have unique words and that
      //order the list, and use binary search to get the pages for each word

   }

}