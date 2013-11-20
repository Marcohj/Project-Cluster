package com.vsshs.advanced;

import java.io.*;
import java.util.ArrayList;

class HashTable
{
	String word = null;
	Url urls;

	/**
	 * Adds a new unique url to a word.
	 * 
	 * @param url
	 *            to add
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

		// traverse list of urls and try to find a mach
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

	public static ArrayList<String> exists(HashTable[] l, String word)
	{
		ArrayList<String> result = new ArrayList<String>();
		String url = null;
		boolean found = false;
		if (word.contains("AND"))
		{
			System.out.println("contains AND");
			String[] res = word.split("AND");

			String firstWord = res[0].trim();
			String secondWord = res[1].trim();

			ArrayList<String> firstList = findWord(l, firstWord);
			ArrayList<String> secondList = findWord(l, secondWord);

			for (int i = 0; i < firstList.size(); i++)
			{
				if (secondList.contains(firstList.get(i))) result.add(firstList.get(i));
			}
			
			return result;

		}

		if (word.contains("OR"))
		{
			System.out.println("Contains OR");
			String[] res = word.split("AND");

			String firstWord = res[0].trim();
			String secondWord = res[1].trim();
			result = findWord(l, firstWord);
			ArrayList<String> secondList = findWord(l, secondWord);

			for (int i = 0; i < secondList.size(); i++)
			{
				if (!result.contains(secondList.get(i))) result.add(secondList.get(i));
			}
			
			return result;
		}

		return findWord(l, word);
	}

	private static ArrayList<String> findWord(HashTable[] l, String word)
	{
		ArrayList<String> result = new ArrayList<String>();
		int hash = getHash(word);

		if (l[hash] != null)
		{
			// check in the location of the hash
			if (l[hash].word != null && l[hash].word.compareTo(word) == 0)
			{

				Url u = l[hash].urls;
				while (u != null)
				{
					result.add(u.url.substring(6));
					//System.out.println("     -> " + u.url.substring(6));
					u = u.next;
				}
				return result; // this returns the result if it was found in the
								// location
			}
		}

		int nextPos = hash;
		HashTable obj = l[nextPos];
		while (obj != null)
		{
			if (obj.word != null && obj.word.compareTo(word) == 0)
			{
				// found = true;
				Url u = obj.urls;
				while (u != null)
				{
					//System.out.println("     -> " + u.url.substring(6));
					u = u.next;
				}
				return result;
			}
			obj = l[++nextPos];
		}

		return result;
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

			// no object there means that cell is empty and the new obj can be
			// created.
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
				if (hashTable[nextPos] == null) hashTable[nextPos] = new HashTable();
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

		// HashTable[] l = Searcher.readHtmlList("C:\\itcwww-small-v.txt");
		HashTable[] l = Searcher.readHtmlList("C:\\itcwww-medium.txt");

		for (int i = 0; i < l.length; i++)
		{

			if (l[i] != null && l[i].word != null)
			{
				System.out.println(i + " --> " + l[i].word);
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
			{
				ArrayList<String> result = Searcher.exists(l, name);
				
				if (result != null && result.size() > 0)
				{
					System.out.println("The word \"" + name + "\" has been found.");
					
					for (String string : result)
					{
						System.out.println("   --> " + string);
					}
				}
				else
				{
					System.out.println("The word \"" + name + "\" has NOT been found.");
				}
			}
		}
	}
}
