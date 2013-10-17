/**
 * Created with IntelliJ IDEA.
 * User: Julian
 * Date: 10/14/13
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
class URLlist {
   String str;
   URLlist next;

   URLlist (String s, URLlist u) {
      str = s;
      next = u;
   }
}
