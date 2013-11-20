package common;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 11/5/13
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class Hashtable {
   String str;
   URL urls;
   public URL addURL(String url)
   {
      if (urls == null)
      {
         urls = new URL(url, null);
         return urls;
      }
      URL current = urls;
      while (current != null)
      {
         // check if not added already
         if (current.str.equals(url))
            return current;

         // not found while traversing
         if (current.next == null)
         {
            current.next = new URL(url, null);
            return current.next;
         }
         current = current.next;
      }
      return null;
   }

}
