package fr.cesi.ylalanne.utils;

/**
 * A simple numeric Range class with start and end properties
 */
public class Range<T extends Number & Comparable<T>> {
	private T start;
	private T end;
	
	/**
	 * @param start the lowest {@link Number} in this Range
	 * @param end the highest {@link Number} in this Range
	 */
	public Range(T start, T end) {
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Checks the given {@code Number} is in this {@code Range}
	 * @param number the tested number
	 * @return {@code true} if the given number is in this {@code Range} (bounds included), {@code false} otherwise
	 */
	public boolean isIn(T number) {
		return number.compareTo(start) >= 0 && number.compareTo(end) <= 0;
	}
	
	/**
	 * @return the size of this range
	 */
	public double size() {
		return end.doubleValue() - start.doubleValue();
	}
	
	/**
	 * @return the start
	 */
	public T getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(T start) {
		this.start = start;
	}
	
	/**
	 * @return the end
	 */
	public T getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(T end) {
		this.end = end;
	}
	
	
}
