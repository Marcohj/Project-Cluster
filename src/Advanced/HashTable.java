package Advanced;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 11/6/13
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
class HashTable
{
   String word = null;
   Url urls;

   public Url AddUrl(String url)
   {
      if (urls == null)
      {
         urls = new Url(url, null);
         return urls;
      }
      Url current = urls;
      while (current != null)
      {
         // check if not added already
         if (current.url.compareTo(url) == 0) return current;

         // not found while traversing
         if (current.next == null)
         {
            current.next = new Url(url, null);
            return current.next;
         }
         current = current.next;
      }

      return null;
   }
}