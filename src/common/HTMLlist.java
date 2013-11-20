package common;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 10/14/13
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */

public class HTMLlist{
   public String str;
   public HTMLlist next;
   public URL urls;

   public HTMLlist(String s, HTMLlist n){
      str = s;
      next = n;
   }

   public HTMLlist(String s, HTMLlist n, URL u) {
      str = s;
      next = n;
      urls = u;
   }
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
