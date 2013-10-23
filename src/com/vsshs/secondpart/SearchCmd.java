package com.vsshs.secondpart;

import java.io.*;
import java.util.ArrayList;

class HTMLlist
{
	String str;
	HTMLlist next;

	HTMLlist(String s, HTMLlist n)
	{
		str = s;
		next = n;
	}
}

class Searcher
{

	public static boolean exists(HTMLlist l, String word)
	{
		String url = null;
		boolean found = false;
		ArrayList<String> printed = new ArrayList<String>();
		while (l != null)
		{
			if (l.str.startsWith("*"))
			{
				url = l.str.replace("*PAGE:", "");
				
			}
			if (l.str.equals(word))
			{
				if (!printed.contains(url))
				{
					System.out.println("URL: " + url);
					printed.add(url);
				}
				found = true;
			}

			l = l.next;
		}
		return found;
	}

	public static HTMLlist readHtmlList(String filename) throws IOException
	{
		String name;
		HTMLlist start, current, tmp;

		// Open the file given as argument
		BufferedReader infile = new BufferedReader(new FileReader(filename));

		name = infile.readLine(); // Read the first line
		start = new HTMLlist(name, null);
		current = start;
		name = infile.readLine(); // Read the next line
		while (name != null)
		{ // Exit if there is none
			tmp = new HTMLlist(name, null);
			current.next = tmp;
			current = tmp; // Update the linked list
			name = infile.readLine(); // Read the next line
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
