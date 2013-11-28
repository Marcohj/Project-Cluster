package Step1;

import java.util.HashSet;

/**
 * User: Julian
 * Date: 11/26/13
 * Time: 3:45 PM
 */
public class SearchTest {
   static HTMLlist smallList,bigList;
   static HashSet<String> small = new HashSet<String>(), big = new HashSet<String>();


   public static void initializeList() throws Exception{
      smallList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-small.txt");
      bigList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-big.txt");

      int i = 0;
      HTMLlist tmp = smallList;
      while(tmp != null){
         if(i++%10 == 0 && !tmp.str.startsWith("*PAGE:")){
            small.add(tmp.str);
         }
         tmp = tmp.next;
      }

      i = 0;
      tmp = bigList;
      while(tmp != null){
         if(i++%1000 == 0 && !tmp.str.startsWith("*PAGE:")){
            big.add(tmp.str);
         }
         tmp = tmp.next;
      }

   }

   public static void testExists() throws Exception {
      double resultTime = 0;

      for(String s: small){
         long searchtime1 = System.nanoTime();
         Searcher.exists(smallList, s);
         long searchtime2 = System.nanoTime();

         if (resultTime == 0)
            resultTime = ((searchtime2 - searchtime1)* 1e-6);
         else
            resultTime = (resultTime + (((double)searchtime2 - (double)searchtime1)* 1e-6))/2;
      }
         System.out.println(" the number of sought items is " + small.size() + " the average search time (small list) is: " + resultTime);
      resultTime = 0;

      for(String s : big){
         long searchtime1 = System.nanoTime();
         Searcher.exists(bigList, s);
         long searchtime2 = System.nanoTime();

         //if(i < big.size()/2)
            if (resultTime == 0)
               resultTime = ((searchtime2 - searchtime1)* 1e-6);
            else
               resultTime = (resultTime + (((double)searchtime2 - (double)searchtime1)* 1e-6))/2;
      }
         System.out.println(" the number of sought items is " + big.size() + " the average search time (big list) is: " + resultTime);
   }

   public static void main (String[] args) throws Exception {

      initializeList();
      testExists();

   }

}
