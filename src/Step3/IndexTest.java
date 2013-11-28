package Step3;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 11/25/13
 * Time: 4:06 PM
 */
public class IndexTest extends AbstractBenchmark {
   @org.junit.Before
   public void setUp() throws Exception {
   //TODO - setup the test lists with words that should be tested on each file

   }
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
   /*@org.junit.Test
   public void testExists() throws Exception {
   }*/
}
