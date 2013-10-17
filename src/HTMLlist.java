class HTMLlist {
	String		str;
	HTMLlist	next;
	URL			urls;

	HTMLlist(String s, HTMLlist n) {
		str = s;
		next = n;
	}
	
	public void addURL(String url) {
		boolean urlExist = false;
		
		while (urls != null) {
			if(urls.str.equals(url)) {
				urlExist = true;
			}
			urls = urls.next;
		}
		
		if(!urlExist) {
			URL theURL = new URL();
			theURL.str = url;
			urls.next = theURL;
			urls = theURL;
		}
	}
}

class URL {
	String str;
	URL next;	
}