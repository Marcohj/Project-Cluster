class HTMLlist {
	int			hash;
	String		str;
	HTMLlist	next;
	URL			urls;
	URL[]		URLS = new URL[ARRAYSIZE];
	int			urlCounter = 0;
	static final int	ARRAYSIZE = 50;

	HTMLlist(String str, HTMLlist next) {
		this.str = str;
		this.next = next;
		hash = str.toLowerCase().hashCode();
	}
	
	public void addURL(URL addURL) {
		if(addURL != null) {
			for(int i = 0; i < URLS.length; i++) {
				if(URLS[i] == addURL) {
					return;
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
		
		
		
//		if(addURL == null) {
//			return;
//		}
//		boolean urlExist = false;
//		
//		URL urlSearcher = urls;
//		while (urlSearcher != null) {
//			if(urlSearcher.str.equals(addURL.str)) {
//				urlExist = true;
//				break;
//			}
//			urlSearcher = urlSearcher.next;
//		}
//		
//		if(!urlExist) {
//			if(urlFirst) {
//				urls = addURL;
//				urlsCurrent = urls;
//				urlFirst = false;
//			} else {
//				urlsCurrent.next = addURL;
//				urlsCurrent = addURL;
//			}
//		}
	}
}

class URL {
	String str;
	URL next;
	
	public URL() {}
	
	public URL(String str, URL next) {
		this.str = str;
		this.next = next;
	}
}

class HashTable {
	HTMLlist[] hashTable = new HTMLlist[10];
	
	public void add(HTMLlist list) {
	}
	
	public HTMLlist[] getList() {
		return hashTable;
	}
}