class HTMLlist {
	String		str;
	HTMLlist	next;
	URL[]		urls = new URL[300];
	int			numberOfURLs = 0;

	HTMLlist(String s, HTMLlist n) {
		str = s;
		next = n;
	}
	
	public void addURL(String url) {
		boolean urlExist = false;
		for(int i = 0; i < urls.length; i++) {
			if(urls[i] != null) {
				if(urls[i].str.equals(url)) {
					urlExist = true;
				}
			}
		}
		if(!urlExist) {
			URL theURL = new URL();
			theURL.str = url;
			theURL.next = urls[numberOfURLs + 1];
			urls[numberOfURLs] = theURL;
			numberOfURLs++;	
		}
	}
}

class URL {
	String str;
	URL next;	
}