package Step3;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Julian
 * Date: 11/26/13
 * Time: 3:45 PM
 */
public class SearchTest {
   static HTMLlist smallList,mediumList, bigList;
   static Set<String> small = new HashSet<String>(),medium = new HashSet<String>(), big = new HashSet<String>();
   private static void initializeBigList() throws IOException {
      long searchtime1 = System.nanoTime();
      bigList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-copy\\src\\common\\itcwww-big.txt");
      long searchtime2 = System.nanoTime();
      int i = 0;
      HTMLlist tmp = bigList;
      while(tmp != null){
         if(i++%1000 == 0 && !tmp.str.startsWith("*PAGE:")){
            big.add(tmp.str);
         }
         tmp = tmp.next;
      }
      System.out.println(" Indexing took : " + ((searchtime2 - searchtime1)* 1e-6));

   }

   private static void initializeSmallList() throws IOException {
      long searchtime1 = System.nanoTime();
      smallList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-copy\\src\\common\\itcwww-small.txt");
      long searchtime2 = System.nanoTime();
      int i = 0;
      HTMLlist tmp = smallList;
      while(tmp != null){
         if(i++%10 == 0 && !tmp.str.startsWith("*PAGE:")){
            small.add(tmp.str);
         }
         tmp = tmp.next;
      }
      System.out.println(" Indexing took : " + ((searchtime2 - searchtime1)* 1e-6));
   }
   private static void initializeMediumList() throws IOException {
      long searchtime1 = System.nanoTime();
      mediumList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-copy\\src\\common\\itcwww-medium.txt");
      long searchtime2 = System.nanoTime();
      int i = 0;
      HTMLlist tmp = mediumList;
      while(tmp != null){
         if(i++%10 == 0 && !tmp.str.startsWith("*PAGE:")){
            medium.add(tmp.str);
         }
         tmp = tmp.next;
      }
      System.out.println(" Indexing took : " + ((searchtime2 - searchtime1)* 1e-6) + " milliseconds");
   }
   private static void testBigSearch() {
      double resultTime = 0;
      for(String s : big){
         long searchtime1 = System.nanoTime();
         Searcher.exists(bigList, s);
         HTMLlist wordFound = Searcher.getNode(bigList, s);
         while(wordFound.urls != null){
            wordFound.urls = wordFound.urls.next;
         }
         long searchtime2 = System.nanoTime();

         if (resultTime == 0)
            resultTime = ((searchtime2 - searchtime1)* 1e-6);
         else
            resultTime = (resultTime + (((double)searchtime2 - (double)searchtime1)* 1e-6))/2;
      }
      System.out.println(" the number of sought items is " + medium.size() + " the average search time (milliseconds)  (big list) is: " + resultTime);
   }
   private static void testMediumSearch() {
      double resultTime = 0;
      for(String s : medium){
         long searchtime1 = System.nanoTime();
         Searcher.exists(mediumList, s);
         HTMLlist wordFound = Searcher.getNode(mediumList, s);
         while(wordFound.urls != null){
            wordFound.urls = wordFound.urls.next;
         }
         long searchtime2 = System.nanoTime();

         if (resultTime == 0)
            resultTime = ((searchtime2 - searchtime1)* 1e-6);
         else
            resultTime = (resultTime + (((double)searchtime2 - (double)searchtime1)* 1e-6))/2;
      }
      System.out.println(" the number of sought items is " + big.size() + " the average search time (milliseconds)  (big list) is: " + resultTime);
   }

   private static void testSmallSearch() {
      double resultTime = 0;
      for(String s: small){
         long searchtime1 = System.nanoTime();
         Searcher.exists(smallList, s);
         HTMLlist wordFound = Searcher.getNode(smallList, s);
         while(wordFound.urls != null){
            wordFound.urls = wordFound.urls.next;
         }
         long searchtime2 = System.nanoTime();

         if (resultTime == 0)
            resultTime = ((searchtime2 - searchtime1)* 1e-6);
         else
            resultTime = (resultTime + (((double)searchtime2 - (double)searchtime1)* 1e-6))/2;
      }
      System.out.println(" the number of sought items is " + small.size() + " the average search time (milliseconds)  (small list) is: " + resultTime);
   }

   public static void main (String[] args) throws Exception {
      initializeSmallList();
      testSmallSearch();

      initializeMediumList();
      testMediumSearch();

      /*initializeBigList();
      testBigSearch();*/
   }

}
