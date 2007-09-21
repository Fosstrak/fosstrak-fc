package org.accada.ale.server;

import org.apache.log4j.Logger;

/**
 * represents a tag that has been read on one of the readers in the Logical Reader API.
 * @author sawielan
 *
 */
public class Tag {
	
	/** name of the (composite) reader where the tag has been read. */
	private String reader = null;
	
	/** name of the reader where the tag was read from a physicalReader. */
	private String origin  = null;
	
	/** id of this tag. */
	private String tagID = null;
	
	/** trace where the tag passed through the ALE.  */
	private String trace = null;
	
	/** timestamp when the tag occured. */
	private long timestamp = -1;
	
	/**
	 * constructor for a tag. (default constructor).
	 */
	public Tag() {		
	}
	
	/**
	 * assignment constructor for Tag.
	 * @param origin reader where the tag was read originally 
	 * @param tagId the tagID
	 * @param timestamp the timestamp
	 */
	public Tag(String origin, String tagId, long timestamp) {
		setOrigin(origin);
		setReader(origin);
		setTagID(tagId);
		setTimestamp(timestamp);		
	}
	
	/**
	 * constructor for a tag.(assignment constructor).
	 * @param origin the baseReader where the tag has been read
	 */
	public Tag(String origin) {
		setOrigin(origin);
		setReader(origin);
	}
	
	/**
	 * constructs a tag from another existing tag. (copy constructor).
	 * @param tag the tag to be copied into a new tag
	 */
	public Tag(Tag tag) {
		setOrigin(tag.getOrigin());
		setTimestamp(tag.getTimestamp());
		setReader(tag.getReader());
		this.trace = tag.getTrace();
		setTagID(tag.getTagID());
	}

	/**
	 * gets the name of the reader that read this tag.
	 * @return name of a logicalReader
	 */
	public String getReader() {
		return reader;
	}

	/**
	 * sets the name of the reader that read this tag. when a
	 * reader is part of a composite reader then the reader will set
	 * to the name of the compositeReader. if you want to get the 
	 * original reader refer to origin.
	 * @param reader a name of a logicalReader
	 */
	public void setReader(String reader) {
		this.reader = reader;
	}

	/**
	 * returns the id of this tag.
	 * @return string containing the tag id
	 */
	public String getTagID() {
		return tagID;
	}

	/**
	 * sets the tag id.
	 * @param tagID a string holding the tag id.
	 */
	public void setTagID(String tagID) {
		this.tagID = tagID;
	}

	/**
	 * returns the timestamp when the tag occured.
	 * @return timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * sets the timestamp when a tag has been read.
	 * @param timestamp time when tag has been read.
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * prepends a trace to the given trace.
	 * @param suffix  a trace path item
	 */
	public void addTrace(String suffix) {
		if (trace == null) {
			trace = suffix;
		} else {
			trace = trace + "-" + suffix;
		}
	}
	
	/** 
	 * returns the trace path of the tag.
	 * @return a string containing the tracepath
	 */
	public String getTrace() {
		return trace;
	}
	
	/**
	 * prints a pretty print to the provided logger.
	 * @param log log facility to write the pretty print to
	 */
	public void prettyPrint(Logger log) {
		log.debug(String.format("--------------------------------\n"));
		log.debug(String.format("ReaderName: %s\n", getReader()));
		log.debug(String.format("OriginName: %s\n", getOrigin()));
		log.debug(String.format("Timestamp: %d\n", getTimestamp()));
		log.debug(String.format("Tag id: %s\n", getTagID()));
		log.debug(String.format("Trace: %s\n:", getTrace()));
	}

	/**
	 * returns the name of the baseReader where the tag has been read.
	 * @return returns the name of the baseReader where the tag has been read.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * sets the origin  (baseReader) where the tag has been read.
	 * @param origin the name of the baseReader where the tag has been read.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * comparator to check whether two tags are the same.
	 * @param tag the other tag to be checked.
	 * @return boolean value flaging whether equal or not
	 */
	public boolean equalsTag(Tag tag) {
		if (!tag.getOrigin().equalsIgnoreCase(getOrigin())) {
			return false;
		}
		
		if (!tag.getReader().equalsIgnoreCase(getReader()))  {
			return false;
		}
		
		return true;
	}
}
