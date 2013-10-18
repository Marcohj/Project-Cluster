class HTMLlist {
	int			hash;
	String		str;
	HTMLlist	next;
	URL			urls, current;

	HTMLlist(String s, HTMLlist n) {
		str = s;
		next = n;
		hash = s.toLowerCase().hashCode();
	}
	
	public void addURL(String url) {
		boolean urlExist = false;
		
		URL urlSearcher = urls;
		while (urlSearcher != null) {
			if(urlSearcher.str.equals(url)) {
				urlExist = true;
			}
			urlSearcher = urlSearcher.next;
		}
		
		if(!urlExist) {			
			if(current == null) {
				urls = new URL();
				urls.str = url;
				current = urls;
			} else {
				URL theURL = new URL();
				theURL.str = url;
				current.next = theURL;
				current = theURL;	
			}
		}
	}
}

class URL {
	String str;
	URL next;	
}