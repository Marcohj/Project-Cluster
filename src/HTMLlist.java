/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 10/14/13
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */

public class HTMLlist {
   String str;
   HTMLlist next;
   URLlist urls;

   HTMLlist(String s, HTMLlist n){
      str = s;
      next = n;
   }

   HTMLlist(String s, HTMLlist n, URLlist u) {
      str = s;
      next = n;
      urls = u;
   }


  /* class HTMLlistIterator {
      private HTMLlist current;

      HTMLlistIterator() {
         current = HTMLlist.this.next;
      }

      public  boolean hasNext(){
         return current != null;
      }

      public HTMLlist next(){
         HTMLlist item = current;// get the next item
         current = current.next; // move forwards
         return item;
      }*/


  }
