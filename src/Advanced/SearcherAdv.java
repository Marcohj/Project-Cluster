package Advanced;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearcherAdv
{

   public static final String AND = " and ";
   public static final String OR = " or ";

   public static void printUrls(Url u) {
      while (u != null)
      {
         if(u.url != null)
            System.out.println("     -> " + u.url.substring(6));
         u = u.next;
      }
   }

   private static final int MAX_ARRAY_SIZE = 100000;

   public static boolean exists(HashTable[] l, String word)
   {
      boolean found = false;

      int hash = getHash(word);

      if (l[hash]!= null)
      {
         if (l[hash].word != null && l[hash].word.compareTo(word) == 0)
         {
            return true;
         }
      }

      int nextPos = hash;
      HashTable obj = l[nextPos];
      while (obj != null)
      {
         if (obj.word != null && obj.word.compareTo(word) == 0)
         {
            return true;
         }
         obj = l[++nextPos];
      }

      return found;
   }

   public static HashTable[] readHashTable(String filename) throws IOException
   {
      HashTable[] hashTable = new HashTable[MAX_ARRAY_SIZE];
      String name, currentUrl = "";


      // Open the file given as argument
      BufferedReader infile = new BufferedReader(new FileReader(filename));

      // currentUrl = infile.readLine(); // Read the first line

      while (true)// Exit if there is none
      {

         // get next line
         name = infile.readLine();

         if (name == null) break;

         // check if name is url
         if (name.startsWith("*"))
         {
            currentUrl = name;
            continue;
         }

         int hash = getHash(name);

         HashTable obj = hashTable[hash];
         if (obj == null)
         {
            obj = hashTable[hash] = new HashTable();
         }
         if (obj.word == null)
         {
            obj.word = name;
            obj.AddUrl(currentUrl);
            continue;
         }
         if (obj.word.compareTo(name) == 0)
         {
            obj.AddUrl(currentUrl);
            continue;
         }

         int nextPos = hash;
         while (true)
         {
            nextPos++;
            if (nextPos < 0) nextPos = 0;

            // try to find the word
            if (hashTable[nextPos] != null && hashTable[nextPos].word.compareTo(name) == 0)
            {
               hashTable[nextPos].AddUrl(currentUrl);
               break;
            }

            // check for empty spot and add word
            if (hashTable[nextPos] == null)
               hashTable[nextPos] = new HashTable();
            if (hashTable[nextPos].word == null)
            {
               hashTable[nextPos].word = name;
               hashTable[nextPos].AddUrl(currentUrl);
               break;
            }
         }
      }

      infile.close(); // Close the file

      return hashTable;
   }

   public static int getHash(String str)
   {
      int hashCode = str.hashCode();
      if (hashCode < 0) hashCode *= -1;
      hashCode %= MAX_ARRAY_SIZE;
      return hashCode;
   }
   public static Url joinUrls(Url u1, Url u2, String keyword){
    HashTable urls = new HashTable();
      if(keyword.equalsIgnoreCase(AND))
         while(u1 != null){
            Url tmp = u2;
            while(tmp != null){
               if(u1.url.compareTo(tmp.url) == 1){
                  urls.AddUrl(tmp.url);
                  break;
               }
               else
                  tmp = tmp.next;
            }
            u1 = u1.next;
         }
      else if(keyword.equalsIgnoreCase(OR)){
         Url tmp = u1;
         while(u1 != null){
            urls.AddUrl(u1.url);
            u1 = u1.next;
         }
         while(u2 != null){
            while(tmp != null){
               if(tmp.url.compareTo(u2.url) == 1){
                  tmp = tmp.next;
                  continue;
               }
               urls.AddUrl(u2.url);
               tmp = tmp.next;
            }
            u2 = u2.next;
         }

      }
      return urls.urls;

   }
}

