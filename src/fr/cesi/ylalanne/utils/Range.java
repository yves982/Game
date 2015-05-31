package fr.cesi.ylalanne.utils;

/**
 * A simple numeric Range class with start, end and size properties.
 *
 * @param <T> the numeric type for this range
 */
public class Range<T extends Number & Comparable<T>> {
	private T start;
	private T end;
	
	/**
	 * Initializes a new range.
	 *
	 * @param start the lowest {@link Number} in this Range
	 * @param end the highest {@link Number} in this Range
	 */
	public Range(T start, T end) {
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Checks the given {@link Number} is in this {@link Range}.
	 *
	 * @param number the tested number
	 * @return {@link true} if the given number is in this {@link Range} (bounds included), {@link false} otherwise
	 */
	public boolean isIn(T number) {
		return number.compareTo(start) >= 0 && number.compareTo(end) <= 0;
	}
	
	/**
	 * Size.
	 *
	 * @return the size of this range
	 */
	public double size() {
		return end.doubleValue() - start.doubleValue();
	}
	
	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public T getStart() {
		return start;
	}
	
	/**
	 * Sets the start.
	 *
	 * @param start the start to set
	 */
	public void setStart(T start) {
		this.start = start;
	}
	
	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public T getEnd() {
		return end;
	}
	
	/**
	 * Sets the end.
	 *
	 * @param end the end to set
	 */
	public void setEnd(T end) {
		this.end = end;
	}
	
	
}
