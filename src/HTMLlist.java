class HTMLlist {
	String				str;
	HTMLlist			next;
	URL[]				URLS		= new URL[ARRAYSIZE];
	int					urlCounter	= 0;
	static final int	ARRAYSIZE	= 50;

	HTMLlist(String str, HTMLlist next) {
		this.str = str.toLowerCase();
		this.next = next;
	}

	public void addURL(URL addURL) {
		if (addURL != null) {
			for (int i = 0; i < URLS.length; i++) {
				if (URLS[i] != null) {
					if (URLS[i].str == addURL.str) {
						return;
					}
				}
			}

			if (urlCounter == URLS.length) {
				URL[] tempArray = URLS;
				int tempArraySize = tempArray.length + ARRAYSIZE;
				URLS = new URL[tempArraySize];
				for (int i = 0; i < tempArray.length; i++) {
					URLS[i] = tempArray[i];
				}
			}

			URLS[urlCounter] = addURL;
			urlCounter++;
		}
	}
}

class URL {
	String	str;
	URL		next;

	public URL() {
	}

	public URL(String str, URL next) {
		this.str = str;
		this.next = next;
	}
}

class HashTable {

	private HTMLlist[]	data;
	private int			capacity;

	public HashTable(int capacity) {
		this.capacity = capacity;
		data = new HTMLlist[capacity];
	}

	public int hashThis(String key) {
		return Math.abs(key.toLowerCase().hashCode()) % capacity;
	}

	public HTMLlist get(String key) {
		// hash the key
		int hash = hashThis(key);
		// find the array with the key
		HTMLlist arrayHTMLlist = data[hash];
		// find the result
		while (arrayHTMLlist != null) {
			if (arrayHTMLlist.str.equals(key.toLowerCase())) {
				return arrayHTMLlist;
			} else {
				arrayHTMLlist = arrayHTMLlist.next;
			}
		}

		return null;
	}

	public void put(HTMLlist element) {
		// make sure we dont get an empty element
		if (element != null) {
			// hash the key
			int hash = hashThis(element.str);

			// find the array with the key
			HTMLlist arrayHTMLlist = data[hash];

			if (arrayHTMLlist == null) {
				data[hash] = element;
				return;
			}

			while (arrayHTMLlist != null) {
				if (arrayHTMLlist.str.equals(element.str)) {
					arrayHTMLlist.addURL(element.URLS[0]);
					break;
				}

				if (arrayHTMLlist.next == null) {
					arrayHTMLlist.next = element;
					arrayHTMLlist = element;
					break;
				}

				arrayHTMLlist = arrayHTMLlist.next;
			}
		}

	}

}
