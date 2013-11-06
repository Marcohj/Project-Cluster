package com.vsshs.last;

import java.io.*;

class HashTable
{
	String word = null;
	Url urls;

	/**
	 * Adds a new unique url to a word.
	 * @param url to add
	 * @return object that has been added or found during the search
	 */
	public Url AddUrl(String url)
	{
		
		// if no url's are presend - create new one.
		if (urls == null)
		{
			urls = new Url(url, null);
			return urls;
		}
		
		//traverse list of urls and try to find a mach
		Url current = urls;
		while (current != null)
		{
			// check if not added already
			if (current.url.compareTo(url) == 0) return current;

			// not found while traversing - add a new one to the end.
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

class Searcher
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

		return found;
	}

	public static HashTable[] readHtmlList(String filename) throws IOException
	{
		HashTable[] hashTable = new HashTable[MAX_ARRAY_SIZE];
		String name, currentUrl = "";
		

		// Open the file given as argument
		BufferedReader infile = new BufferedReader(new FileReader(filename));

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

			// calculate hash for the string
			int hash = getHash(name);

			
			// find the object at the position of the hash value.
			HashTable obj = hashTable[hash];
			
			// no object there means that cell is empty and the new obj can be created.
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
			
			// if we got up to here it means that the spot was taken. 
			// find a new empty spot in the list and add the word.
			// This means that the word is going to be "kinda" close to
			// where it's hash value is.
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

public class SearchCmd
{

	public static void main(String[] args) throws IOException
	{
		String name;

		// Read the file and create the linked list

//		HashTable[] l = Searcher.readHtmlList("C:\\itcwww-small-v.txt");
		HashTable[] l = Searcher.readHtmlList("C:\\itcwww-medium.txt");
		
		for (int i = 0; i < l.length; i++)
		{
			
			if (l[i] != null && l[i].word != null)
			{
				System.out.println(i+ " --> " + l[i].word);
			}
		}

		// Ask for a word to search
		BufferedReader inuser = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Hit return to exit.");
		boolean quit = false;
		while (!quit)
		{
			System.out.print("Search for: ");
			name = inuser.readLine(); // Read a line from the terminal
			if (name == null || name.length() == 0)
			{
				quit = true;
			}
			else
				if (Searcher.exists(l, name))
				{
					System.out.println("The word \"" + name + "\" has been found.");
				}
				else
				{
					System.out.println("The word \"" + name + "\" has NOT been found.");
				}
		}
	}
}
