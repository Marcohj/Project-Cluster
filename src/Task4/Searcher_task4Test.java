package Task4;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import common.HTMLlist;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 11/17/13
 * Time: 5:04 PM
 */
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart(filePrefix = "benchmark-lists-HashMap")
public class Searcher_task4Test extends AbstractBenchmark {
   @org.junit.Test
   public void testReadHtmlListSmall() throws Exception {
      HTMLlist l = Searcher_task4.readHtmlList("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-small.txt") ;
   }
   @org.junit.Test
   public void testReadHtmlListMedium() throws Exception {
      HTMLlist l = Searcher_task4.readHtmlList("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-medium.txt") ;
   }
   @org.junit.Test
   public void testReadHtmlListBig() throws Exception {
      HTMLlist l = Searcher_task4.readHtmlList("D:\\Workspace\\Project-Cluster\\src\\common\\itcwww-big.txt") ;
   }
}