package Task3;/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
*/

import common.HTMLlist;
import common.URL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Searcher_task3 {

   private static String URLMarker = "*PAGE:";
   private static long SizeOfList = 0;
   private static long numberOfTraversedItems = 2;
   private static long numberOfItems = 3;
   private static long numberOfURLs = 1;

   // I could check the existence of a word when creating the list so I don't have to
   // go through it again later to remove the duplicates
   public static HTMLlist readHtmlList(String filename) throws IOException {
      String name;
      HTMLlist start, current, tmp;

      // Open the file given as argument
      BufferedReader infile = new BufferedReader(new FileReader(filename));

      name = infile.readLine(); //Read the first line
      String currentURL = name.substring(6);//getting the first URL
      name = infile.readLine(); // Read the next line
      start = new HTMLlist(name, null);//beginning of list
      SizeOfList++;//measuring the size of the list, to be used for Hashing
      current = start;
      current.addURL(currentURL);
      name = infile.readLine();

      while (name != null) {
         //checking for URLs
         if (name.startsWith(URLMarker)){
            currentURL = name.substring(6);
            numberOfURLs++;
         }
         else //check if it already exists
         if(alreadyExists(start, name)){  //if so check the link
            getNode(start, name).addURL(currentURL);
         }
         else{//create new element in the list in case it wasn't found
            tmp = new HTMLlist(name, null, new URL(currentURL,null));//careful for the object in the constructor call with new
            current.next = tmp;
            current = tmp;
            SizeOfList++;
         }
         numberOfItems++;
         name = infile.readLine();
      }
      infile.close(); // Close the file
      /*System.out.println("the number of unique items is: " + SizeOfList);
      System.out.println("the total number of items in the file is: " + numberOfItems);
      System.out.println("number of nodes accessed is: " + numberOfTraversedItems);
      System.out.println("number of URLs is: " + numberOfURLs);*/
      return start;
   }
   public static boolean exists(HTMLlist l, String word) {
      while (l != null ) {
         if (l.str.equals(word)) {
            return true;
         }
         l = l.next;
      }
      return false;
   }
   public static boolean alreadyExists(HTMLlist l, String word) {
      int occurences = 0;
      while (l != null ) {
         if (l.str.equals(word)) {
               occurences++;
         }
         if (occurences == 1)
            return true;
         numberOfTraversedItems++;
         l = l.next;
      }
      return false;
   }
   public static HTMLlist getNode(HTMLlist l, String word) {
      while (l != null) {
         if (l.str.equals(word))
            return l;
         numberOfTraversedItems++;
         l = l.next;
      }
      return null;
   }




}
