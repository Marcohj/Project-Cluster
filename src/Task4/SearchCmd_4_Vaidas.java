package Task4;

import java.io.*;

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



class Url
{
   String url;
   Url next;

   public Url(String url, Url next)
   {
      this.url = url;
      this.next = next;
   }

}

class Searcher_4_vaidas
{
   private static final int MAX_ARRAY_SIZE = 100000;
   public static boolean exists(HashTable[] l, String word)
   {
      String url = null;
      boolean found = false;

      int hash = getHash(word);

      if (l[hash]!= null)
      {
         if (l[hash].word != null && l[hash].word.compareTo(word) == 0)
         {
            found = true;
            Url u = l[hash].urls;
            while (u != null)
            {
               System.out.println("     -> " + u.url.substring(6));
               u = u.next;
            }
            return found;
         }
      }

      int nextPos = hash;
      HashTable obj = l[nextPos];
      while (obj != null)
      {
         if (obj.word != null && obj.word.compareTo(word) == 0)
         {
            found = true;
            Url u = obj.urls;
            while (u != null)
            {
               System.out.println("     -> " + u.url.substring(6));
               u = u.next;
            }
            return found;
         }
         obj = l[++nextPos];
      }
/*
		while (l != null)
		{
			if (l.str.compareTo(word) == 0)
			{
				found = true;
				Url u = l.urls;
				while (u != null)
				{
					System.out.println("     -> " + u.url.substring(6));
					u = u.next;
				}
			}

			l = l.next;
		}
		*/
      return found;
   }

   public static HashTable[] readHtmlList(String filename) throws IOException
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

   private static int getHash(String str)
   {
      int hashCode = str.hashCode();
      if (hashCode < 0) hashCode *= -1;
      hashCode %= MAX_ARRAY_SIZE;
      return hashCode;
   }
}

public class SearchCmd_4_Vaidas
{

   public static void main(String[] args) throws IOException
   {
      String name;

      // Read the file and create the linked list
      long time1 = System.nanoTime();
      HashTable[] l = Searcher_4_vaidas.readHtmlList("D:\\Workspace\\Project-Cluster\\src\\itcwww-big.txt");
      long time2 = System.nanoTime();

      for (int i = 0; i < l.length; i+=1000)
      {

         if (l[i] != null && l[i].word != null)
         {
            System.out.println(i+ " --> " + l[i].word);
         }
      }
      System.out.println("Indexing took:" + ((time2 - time1)* 1e-6) + " milliseconds to complete");

      BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));

      System.out.println("Hit return to exit.");
      boolean quit = false;
      while (!quit)
      {
         System.out.print("Search for: ");
         name = inuser.readLine(); // Read a line from the terminal
         long searchtime1 = System.nanoTime();
         if (name == null || name.length() == 0)
         {
            quit = true;
         }
         else
         if (Searcher_4_vaidas.exists(l, name))
         {
            System.out.println("The word \"" + name + "\" has been found.");
            long searchtime2 = System.nanoTime();
            System.out.println("searching took "  + ((searchtime2-searchtime1)*1e-6) + "milliseconds to complete");
         }
         else
         {
            System.out.println("The word \"" + name + "\" has NOT been found.");
         }
      }
   }
}
