package Advanced;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.Clock;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 11/17/13
 * Time: 6:54 PM
 */
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "benchmark-lists")

public class SearcherAdvTest extends AbstractBenchmark {
   private static Object singleton = new Object();
   private static int COUNT = 50000;
   private static int [] rnd;
   private static HashTable[] small, medium, big;
   private static ArrayList<HashTable> testSmall = new ArrayList<>(),
                                       testMedium = new ArrayList<>(),
                                       testBig = new ArrayList<>();
   private static int randomIndex = Math.abs(new Random().nextInt());



   /** Prepare random numbers for tests. */
   @BeforeClass
   public static void prepare() throws Exception
   {
      rnd = new int [COUNT];

      final Random random = new Random();
      for (int i = 0; i < COUNT; i++)
      {
         rnd[i] = Math.abs(random.nextInt());
      }
      small = SearcherAdv.readHashTable("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-small.txt");
      medium = SearcherAdv.readHashTable("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-medium.txt");
      big = SearcherAdv.readHashTable("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-big.txt") ;
      int i;
      for(i = 0; i < small.length; i++){
         testSmall.add(small[i]);
      }
      for(i = 0; i < medium.length; i++){
         testMedium.add(medium[i]);
      }
      for(i = 0; i < big.length; i++){
         testBig.add(big[i]);
      }
   }

   /*@BenchmarkOptions()
   @org.junit.Test
   public void testReadHashTableSmall() throws Exception {
      HashTable[] small = SearcherAdv.readHashTable("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-small.txt") ;
   }
   @BenchmarkOptions()
   @org.junit.Test
   public void testReadHashTableMedium() throws Exception {
      HashTable[] medium = SearcherAdv.readHashTable("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-medium.txt") ;
}
   @BenchmarkOptions()
   @org.junit.Test
   public void testReadHashTableBig() throws Exception {
      HashTable[] big = SearcherAdv.readHashTable("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-big.txt") ;
   }*/

   @BenchmarkOptions(clock = Clock.NANO_TIME, benchmarkRounds = 1000, warmupRounds = 5)
   @org.junit.Test
   public void testSearchSmall()throws Exception{
      //go to search in a random place in the list.
      boolean found = SearcherAdv.exists(small, "bubu");
   }

  /* @BenchmarkOptions(clock = Clock.NANO_TIME, benchmarkRounds = 1000, warmupRounds = 5)
   @org.junit.Test
   public void testSearchMedium()throws Exception{
      testSearch(medium, testMedium);
   }
   @BenchmarkOptions(clock = Clock.NANO_TIME, benchmarkRounds = 1000, warmupRounds = 5)
   @org.junit.Test
   public void testSearchBig()throws Exception{
      testSearch(big, testBig);
   }*/


}
