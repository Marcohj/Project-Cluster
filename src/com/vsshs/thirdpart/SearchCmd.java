package com.vsshs.thirdpart;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

class HTMLlist
{
	String str;
	HTMLlist next;
	Url urls;

	HTMLlist(String s, HTMLlist n)
	{
		str = s;
		next = n;
	}

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
			if (current.url.compareTo(url) == 0)
				return current;
			
			
			
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

class Searcher
{

	public static boolean exists(HTMLlist l, String word)
	{
		String url = null;
		boolean found = false;
		
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
		return found;
	}

	public static HTMLlist readHtmlList(String filename) throws IOException
	{
		String name, currentUrl;
		HTMLlist start, current, tmp;

		// Open the file given as argument
		BufferedReader infile = new BufferedReader(new FileReader(filename));

		
		
		
		currentUrl = infile.readLine(); // Read the first line
		name = infile.readLine(); // Read the second line

		start = new HTMLlist(name, null);
		start.AddUrl(currentUrl);
		
		

		while (1==1)// Exit if there is none
		{ 

			
			// get next line
			name = infile.readLine();
			if (name == null)
				break;
			
			
			// check if name is url
			if (name.startsWith("*"))
			{
				currentUrl = name;
				continue;
			}

			
			tmp = start;
			
			while (tmp != null)
			{
				if (tmp.str.compareTo(name) == 0)
				{
					tmp.AddUrl(currentUrl);
					break;
				}
				
				
				// if not found (last element) add new
				if (tmp.next == null)
				{
					tmp.next = new HTMLlist(name, null);
					tmp.next.AddUrl(currentUrl);
					break;
				}
				
				tmp = tmp.next;
			}
		}
		
		
		infile.close(); // Close the file

		return start;
	}
}

public class SearchCmd
{

	public static void main(String[] args) throws IOException
	{
		String name;

		// Read the file and create the linked list
		HTMLlist l = Searcher.readHtmlList("C:\\itcwww-medium.txt");
//		HTMLlist tmp = l;
//		while (tmp != null)
//		{
//			System.out.println(tmp.str);
//			Url u = tmp.urls;
//			while (u != null)
//			{
//				System.out.println("     -> " + u.url);
//				u = u.next;
//			}
//			tmp = tmp.next;
//		}
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
