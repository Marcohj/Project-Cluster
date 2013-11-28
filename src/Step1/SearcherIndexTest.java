package Step1;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import org.junit.Rule;
import org.junit.rules.TestRule;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 11/25/13
 * Time: 4:06 PM
 */
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "benchmark-lists-task1")
public class SearcherIndexTest extends AbstractBenchmark {
   ArrayList<String> small, medium, big;
   HTMLlist smallList, mediumList, bigList;
   @Rule
   public TestRule benchmarkRun = new BenchmarkRule();
   /*@org.junit.Before
   public void setUp() throws Exception {
      small = new ArrayList<>();
      HTMLlist smallList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-small.txt");
      int i = 0;
      HTMLlist tmp = smallList;
      while(tmp != null){
         if(i++%10 == 0 && !tmp.str.startsWith("*PAGE:")){
            small.add(tmp.str);
         }
         tmp = tmp.next;
      }
      System.out.println(" Number of items in the small list " + i);

      medium = new ArrayList<>();
      mediumList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-medium.txt");
      i = 0;
      tmp = mediumList;
      while(tmp != null){
         if(i++%100 == 0 && !tmp.str.startsWith("*PAGE:")){
            medium.add(tmp.str);
         }
         tmp = tmp.next;
      }
      System.out.println(" Number of items in the medium list " + i);
      big = new ArrayList<>();
      bigList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-big.txt");
      i = 0;
      tmp = bigList;
      while(tmp != null){
         if(i++%100 == 0 && !tmp.str.startsWith("*PAGE:")){
            big.add(tmp.str);
         }
         tmp = tmp.next;
      }
      System.out.println(" Number of items in the big list " + i);
   }
*/

   @org.junit.Test
   public void testRead_SmallFile() throws Exception {
      HTMLlist testList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-small.txt");
   }
   @org.junit.Test
   public void testRead_MediumFile() throws Exception {
      HTMLlist testList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-medium.txt");

   }
   @org.junit.Test
   public void testRead_BigFile() throws Exception {
      HTMLlist testList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-big.txt");

   }
  /* @org.junit.Test
   public void testExists() throws Exception {
      Searcher.exists(bigList, big.get(big.size()/2));
   }*/


}
