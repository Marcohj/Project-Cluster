package step3;

class URL {
	String	url;
	URL		next;

	public URL(String url, URL next) {
		this.url = url;
		this.next = next;
	}
}