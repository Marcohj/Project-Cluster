package Step4_Advanced;

class HashTable {
	private HTMLlist[]	data;
	private int			capacity	= 200;
	private int			wordsAdded = 0;

	public HashTable() {
		data = new HTMLlist[capacity];
	}

	public int hashThis(String key) {
		return Math.abs(key.hashCode()) % capacity;
	}

	public HTMLlist get(String key) {
		// hash the key
		int hash = hashThis(key);
		// find the array with the key
		HTMLlist HTMLlistInArray = data[hash];
		// go through the chained list until we find the word
		while (HTMLlistInArray != null) {
			if (HTMLlistInArray.str.equals(key)) {
				return HTMLlistInArray;
			} else {
				HTMLlistInArray = HTMLlistInArray.next;
			}
		}
		// if no word found, return null
		return null;
	}

	public void put(HTMLlist element) {
		// make sure we dont get an empty element
		if (element != null) {
			biggerHashTable();
			
			// hash the key
			int hash = hashThis(element.str);

			// find the array with the key
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
					// first url, so add it and break
					if (HTMLlistInArray.urls == null) {
						HTMLlistInArray.urls = element.urls;
						break;
					}

					// else we need to add it to the chained list
					URL current = HTMLlistInArray.urls;
					while (current != null) {
						// check if not added already
						if (current.url.compareTo(element.urls.url) == 0)
							break;

						// not found while traversing
						if (current.next == null) {
							current.next = element.urls;
							current = element.urls;
						}
						current = current.next;
					}
				}

				if (HTMLlistInArray.next == null) {
					HTMLlistInArray.next = element;
					HTMLlistInArray = element;
					wordsAdded++;
					break;
				}

				HTMLlistInArray = HTMLlistInArray.next;
			}
		}
	}
	
	public void biggerHashTable() {
		if ((wordsAdded / 10) >= capacity) {
			HTMLlist[] tempArray = data;
			// Double size plus one
			capacity = (capacity * 2) + 1;
			data = new HTMLlist[capacity];
			for (int i = 0; i < tempArray.length; i++) {
				data[i] = tempArray[i];
			}
		}
	}
}