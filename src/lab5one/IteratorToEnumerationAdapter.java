package lab5one;


public class IteratorToEnumerationAdapter<E> implements Enumeration<E> {
	private Iterator<E> itr;
	
	public IteratorToEnumerationAdapter(Iterator<E> itr) {
		this.itr = itr;
	}

	public boolean hasMoreElements() {
		return itr.hasNext();
	}

	public E nextElement() {
		return itr.next();
	}
}
