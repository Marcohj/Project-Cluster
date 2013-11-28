package Step4_Advanced;

class HTMLlist {
	String		str;
	HTMLlist	next;
	URL			urls;

	HTMLlist(String s, HTMLlist n) {
		str = s;
		next = n;
	}

	public URL AddUrl(String url) {
		if (urls == null) {
			urls = new URL(url, null);
			return urls;
		}
		URL current = urls;
		while (current != null) {
			// check if not added already
			if (current.url.compareTo(url) == 0)
				return current;

			// not found while traversing
			if (current.next == null) {
				current.next = new URL(url, null);
				return current.next;
			}
			current = current.next;
		}

		return null;
	}
}