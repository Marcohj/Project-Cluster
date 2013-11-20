package Advanced;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 11/6/13
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Url
{
   String url;
   Url next;

   public Url(String url, Url next)
   {
      this.url = url;
      this.next = next;
   }

}
