package Advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static Advanced.SearcherAdv.*;


public class SearchCmd_Advanced {

   public static void main(String[] args) throws IOException {

      // Read the file and create the linked list
      HashTable[] l = SearcherAdv.readHashTable("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-big.txt");

      for (int i = 0; i < l.length; i += 300) {

         if (l[i] != null && l[i].word != null) {
            System.out.println(i + " --> " + l[i].word);
         }
      }

      BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));

      System.out.println("Hit return to exit.");
      boolean quit = false;

      Search(l, inuser, quit);
   }

   private static void Search(HashTable[] l, BufferedReader inuser, boolean quit) throws IOException {
      while (!quit) {
         String name, firstWord = null, secondWord = null;
         Url firstWordUrls = null, secondWordUrls = null;

         System.out.print("Search for: ");
         name = inuser.readLine(); // Read a line from the terminal


         if (name == null || name.length() == 0) {
            quit = true;
         }
         //checking for condition and getting the variables in that case
         if(name.contains(OR) || name.contains(AND)){
            firstWord = getFirstWord(name);
            secondWord = getSecondWord(name);
            firstWordUrls = l[getHash(firstWord)].urls;
            secondWordUrls = l[getHash(secondWord)].urls;

         }

         if (exists(l, name)) {

            System.out.println("The word \"" + name + "\" has been found.");

            printUrls(l[getHash(name)].urls);

         } else
         if (name.contains(OR) &&
             exists(l, firstWord) &&
             exists(l, secondWord)) {

            printUrls(joinUrls(firstWordUrls, secondWordUrls, OR));
         } else
         if (name.contains(AND) &&
             exists(l, firstWord) &&
             exists(l, secondWord)) {

            printUrls(joinUrls(firstWordUrls, secondWordUrls, AND));
         } else {
            System.out.println("The word \"" + name + "\" has NOT been found, or one of the words does not exist");
         }
      }
   }

   private static String getSecondWord(String name) {
      return name.substring(name.lastIndexOf(" ") + 1, name.length());
   }

   private static String getFirstWord(String name) {
      return name.substring(0, name.indexOf(" "));
   }
}
