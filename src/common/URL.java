package common;

/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 10/14/13
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class URL {
   public String str;
   public URL next;

   public URL(String s, URL u) {
      str = s;
      next = u;
   }
}
