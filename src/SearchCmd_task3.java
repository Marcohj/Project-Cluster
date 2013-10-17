import java.io.IOException;

public class SearchCmd_task3 {

   private static String URLMarker = "*PAGE";

   public static void main(String[] args) throws IOException {
      String name;
      // Check that a filename has been given as argument
      if (args.length != 1) {
         System.out.println("Usage: java SearchCmd <datafile>");
      }

      // Read the file and create the linked list, the original linked list
      HTMLlist l = Searcher_task3.readHtmlList(args[0]);
      // Ask for a word to search
      // BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));
      HTMLlist start = l;

      URLlist startURL = new URLlist(l.str, null),
              URLs = startURL;
      l = l.next;
      // boolean quit = false;
      // go through the list
      // build URLs list;
      while(l!= null){
         URLlist tmp;
         while(l != null){
            if(l.str.contains(URLMarker)){
               tmp = new URLlist(l.str, null);
               URLs.next = tmp;
               URLs = tmp;
            }
            l = l.next;
         }
      }
      l = start;
      while (l != null) {
         //get the first page
         HTMLlist currentPage = Searcher_task3.getPage(l);
         //mark on which Page the word is on.
         while (currentPage != null) {
            if (Searcher_task3.exists(currentPage, l.str) && !l.str.contains(URLMarker)) { //check on each page if the word is there
               URLlist tmp  = new URLlist(currentPage.str,null);// get the first page as a starting point
               if(l.urls == null)
                  l.urls = tmp;
               else
                  l.urls.next = tmp;
            }
            if(l.str.contains(URLMarker))
               currentPage = Searcher_task3.getPage(currentPage.next);// move to the next page, jumps from link to link
            l = l.next;
         }
      }
      l = start;
      while (l != null){
         System.out.println(l.str + "  " +  l.urls.str);
         l = l.next;
      }
   }
}