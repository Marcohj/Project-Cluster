package step4_Adv;

class HashTable {
	// The Array used for keeping all the word in
	private HTMLlist[]	data;

	// The starting size of “data”.
	private int			capacity	= 201;

	// Counter for the number of words added.
	private int			wordsAdded	= 0;

	// The number of words we would like in each slot in “data”.
	private int			wordsInSlot	= 50;

	// Creates a new object with the “data” array being the size of “capacity”.
	public HashTable() {
		data = new HTMLlist[capacity];
	}

	// Hashcode function
	public int hashThis(String key) {
		return Math.abs(key.hashCode()) % capacity;
	}

	public HTMLlist get(String word) {
		// hash the given word
		int hash = hashThis(word);
		// find the array slot with the key
		HTMLlist HTMLlistInArray = data[hash];
		// go through the chained list until we find the word
		while (HTMLlistInArray != null) {
			if (HTMLlistInArray.str.equals(word)) {
				return HTMLlistInArray;
			} else {
				HTMLlistInArray = HTMLlistInArray.next;
			}
		}
		// if no word was found, return null
		return null;
	}

	public void put(HTMLlist element) {
		// make sure we don't get an empty element
		if (element == null) {
			return;
		}
		
		// make sure our array is big enough
		biggerHashTable();
		element.next = null;

		// hash the key
		int hash = hashThis(element.str);
		
		// find the array slot with the key
		HTMLlist HTMLlistInArray = data[hash];

		// if no word is found, we can safely add it as the first
		if (HTMLlistInArray == null) {
			data[hash] = element;
			wordsAdded++;
			return;
		}

		// else we go to the end, or until we find the same word
		while (HTMLlistInArray != null) {
			if (HTMLlistInArray.str.equals(element.str)) {
				HTMLlistInArray.addUrl(element.urls.url);
				break;
			}
			
			// If it is the last word, we now know it wasn't in the list, so lets add it and break
			if (HTMLlistInArray.next == null) {
				HTMLlistInArray.next = element;
				HTMLlistInArray = element;
				wordsAdded++;
				break;
			}
			
			// go to next linked word in while loop
			HTMLlistInArray = HTMLlistInArray.next;
		}

	}

	public void biggerHashTable() {
		if ((wordsAdded / wordsInSlot) >= capacity) {
			// Cop7 data to tempArray
			HTMLlist[] tempArray = data;
			// Create temp HTMLlist
			HTMLlist temp = null;
			// Double size plus one
			capacity = (capacity * 2) + 1;
			// New data size set
			data = new HTMLlist[capacity];
			for (int i = 0; i < tempArray.length; i++) {
				while(tempArray[i] != null) {
					temp = new HTMLlist(tempArray[i].str, null);
					temp.urls = tempArray[i].urls;
					this.put(temp);
					tempArray[i] = tempArray[i].next;
				}
			}
			tempArray = null;
		}
	}
}