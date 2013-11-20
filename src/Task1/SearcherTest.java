package Task1;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import common.HTMLlist;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 11/13/13
 * Time: 5:36 PM
 */
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "benchmark-lists-task2")
public class SearcherTest extends AbstractBenchmark {
   @org.junit.Test
   public void testReadHtmlListSmall() throws Exception {
      HTMLlist l = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-small.txt") ;
   }
   @org.junit.Test
   public void testReadHtmlListMedium() throws Exception {
      HTMLlist l = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-medium.txt") ;
   }
   @org.junit.Test
   public void testReadHtmlListBig() throws Exception {
      HTMLlist l = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-big.txt") ;
   }

  /* @org.junit.Test
   public void testExistsBestCase() throws Exception {
   }
   @org.junit.Test
   public void testExistsAverageCase() throws Exception {

   } @org.junit.Test
     public void testExistsWorstCase() throws Exception {

   }
   @org.junit.Test
   public void testGetPage() throws Exception {

   }*/
}
