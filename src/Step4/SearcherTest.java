package Step4;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 11/25/13
 * Time: 4:06 PM
 */
public class SearcherTest extends AbstractBenchmark {
   @org.junit.Before
   public void setUp() throws Exception {

   }

   /*@org.junit.Test
   public void testRead_SmallFile() throws Exception {
      HashTable testList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-small.txt");
   }*/
   @org.junit.Test
   public void testRead_MediumFile() throws Exception {
      HashTable testList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-medium.txt");

   }
   /*@org.junit.Test
   public void testRead_BigFile() throws Exception {
      HashTable testList = Searcher.readHtmlList("D:\\Workspace\\Project-Cluster-master\\src\\common\\itcwww-big.txt");

   }*/
   /*@org.junit.Test
   public void testExists() throws Exception {

   }*/
}
