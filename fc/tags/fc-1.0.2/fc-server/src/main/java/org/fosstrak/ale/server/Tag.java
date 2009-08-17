package org.fosstrak.ale.server;

import java.math.BigInteger;
import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.epcglobalinc.tdt.LevelTypeList;
import org.fosstrak.tdt.TDTEngine;
import org.fosstrak.tdt.TDTException;

/**
 * represents a tag that has been read on one of the readers in the Logical Reader API.
 * @author sawielan
 * @author alessio orlando
 * @author roberto vergallo
 *
 */
public class Tag {
	
	/** logger. */
	private static final Logger log = Logger.getLogger(Tag.class);
	
	/** name of the (composite) reader where the tag has been read. */
	private String reader = null;
	
	/** name of the reader where the tag was read from a physicalReader. */
	private String origin  = null;
	
	/** id of this tag. */
	private byte[] tagID = null;
	
	/** id as pure uri*/
	private String tagIDAsPureURI = null;
	
	/** id as binary string. */
	private String binary = null;
	
	/** trace where the tag passed through the ALE.  */
	private String trace = null;
	
	/** timestamp when the tag occured. */
	private long timestamp = -1;
	
	/** the tag length. */
	private String tagLength = null;
	
	/** the filter value. */
	private String filter = null;
	
	/** the company prefix length. */
	private String companyPrefixLength = null;
	
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
	public Tag(String origin, byte[] tagId, String tagIDAsPureURI, long timestamp) {
		setOrigin(origin);
		setReader(origin);
		setTagID(tagId);
		setTagIDAsPureURI(tagIDAsPureURI);
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
	 * @return byte[] containing the tag id
	 */
	public byte[] getTagID() {
		return tagID;
	}

	/**
	 * sets the tag id.
	 * @param tagID a byte[] holding the tag id.
	 */
	public void setTagID(byte[] tagID) {
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
	public void prettyPrint(Logger log, Level level) {
		log.log(level, String.format("--------------------------------\n"));
		log.log(level, String.format("ReaderName: %s\n", getReader()));
		log.log(level, String.format("OriginName: %s\n", getOrigin()));
		log.log(level, String.format("Timestamp: %d\n", getTimestamp()));
		log.log(level, String.format("Tag id: %s\n", getTagID()));
		log.log(level, String.format("Trace: %s\n:", getTrace()));
		log.log(level, String.format("Binary: %s\n:", getTagAsBinary()));
		log.log(level, String.format("PureID: %s\n:", getTagIDAsPureURI()));
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
	 * @return boolean value flagging whether equal or not
	 */
	public boolean equalsTag(Tag tag) {
		// if the origin in both tags is null, then do not take the 
		// origin into account when comparing
		if ((getOrigin() != null) && (tag.getOrigin() != null)) { 
			// compare the origin
			if (!tag.getOrigin().equalsIgnoreCase(getOrigin())) {
				return false;
			}
		} else if ((getOrigin() == null) || (tag.getOrigin() == null)) {
			return false;
		}
		
		// if the reader in both tags is null, then do not take the
		// reader into account when comparing
		if ((getReader() != null) && (tag.getReader() != null)) {
			// compare the reader
			if (!tag.getReader().equalsIgnoreCase(getReader()))  {
				return false;
			}
		} else if ((getReader() == null) || (tag.getReader() == null)) {
			return false;
		}
		
		// compare the tag id
		if ((null != tag.getTagIDAsPureURI()) && (null != getTagIDAsPureURI())) {
			// both not null, so compare
			if (!tag.getTagIDAsPureURI().equalsIgnoreCase(getTagIDAsPureURI())) {
				return false;
			}				
		} else if ((null != tag.getTagIDAsPureURI()) || (null != getTagIDAsPureURI())) {
			// only one null, therefore not equals
			return false;
		}
		
		// try to compare the binary value
		if ((null != tag.getTagAsBinary()) && (null != getTagAsBinary())) {
			// both not null, so compare
			if (!tag.getTagAsBinary().equalsIgnoreCase(getTagAsBinary())) {
				return false;
			}				
		} else if ((null != tag.getTagAsBinary()) || (null != getTagAsBinary())) {
			// only one null, therefore not equals
			return false;
		}
		
		return true;
	}
	
	public boolean equals(Tag tag) {
		return equalsTag(tag);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			return equalsTag((Tag)obj);
		}
		return false;
	}
	
	public int hashCode() {
		String val = getTagAsBinary();
		if (null != val) {
			BigInteger dec = new BigInteger(val, 2);
			return dec.getLowestSetBit();
		}
		return super.hashCode();
	}

	/**
	 * returns the id of this tag as pure uri.
	 * @return String containing the tag id
	 */
	public String getTagIDAsPureURI() {
		return tagIDAsPureURI;
	}

	/**
	 * sets the tag id as pure uri.
	 * @param tagIDAsPureURI a string holding the tag id.
	 */
	public void setTagIDAsPureURI(String tagIDAsPureURI) {
		this.tagIDAsPureURI = tagIDAsPureURI;
	}
	
	/**
	 * sets the tag in binary format.
	 * @param binary the tag in binary format.
	 */
	public void setTagAsBinary(String binary) {
		this.binary = binary;
	}
	
	/**
	 * @return the tag in binary format.
	 */
	public String getTagAsBinary() {
		return binary;
	}
		
	/**
	 * @param tagLength the tagLength to set
	 */
	public void setTagLength(String tagLength) {
		this.tagLength = tagLength;
	}

	/**
	 * @return the tagLength
	 */
	public String getTagLength() {
		return tagLength;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param companyPrefixLength the companyPrefixLength to set
	 */
	public void setCompanyPrefixLength(String companyPrefixLength) {
		this.companyPrefixLength = companyPrefixLength;
	}

	/**
	 * @return the companyPrefixLength
	 */
	public String getCompanyPrefixLength() {
		return companyPrefixLength;
	}

	/** instance of the TDT engine used for tag conversion. */
	private static TDTEngine engine = null;
	
	/**
	 * start the TDT engine.
	 */
	private static synchronized void startTDTEngine() {
		try {
			if (null == engine) {
				engine = new TDTEngine();
			}
		} catch (Exception e) {
			log.error(
					"exception when creating the tdt engine: " + e.getMessage()
					);
		}
	}
	
	public static TDTEngine getTDTEngine() {
		if (null != engine) {
			return engine;
		}
		
		startTDTEngine();
		return engine;
	}
	
	/**
	 * run the actual tdt conversion.
	 * @param input the tag to convert in binary format or in TAG_ENCODING.
	 * @param extraparms conversion parameters.
	 * @param outputLevel the destination format.
	 * @return the converted tag.
	 * @throws TDTException whenever a tag conversion error occurs.
	 * @throws NullPointerException when there is some other error...
	 */
	private static synchronized String convert(
			String input,
			HashMap<String, String> extraparms,
			LevelTypeList outputLevel) 
	
		throws TDTException, NullPointerException {
		
		if (null == engine) {
			startTDTEngine();
		}
		return engine.convert(input, extraparms, outputLevel);		
	}
	
	/**
	 * converts a given tag through tdt into LEGACY format.
	 * @param tagLength the inbound taglength must be specified as "64" or "96".
	 * @param filter the inbound filter value must be specified - range 
	 * depends on coding scheme.
	 * @param companyPrefixLength length of the EAN.UCC Company Prefix must be 
	 * specified for GS1 coding schemes. if set to null paramter is ignored.
	 * @param tag the tag to convert in binary format or in TAG_ENCODING.
	 * @return a converted tag or null if exception during conversion.
	 */
	public static synchronized String convert_to_LEGACY(
			String tagLength,
			String filter,
			String companyPrefixLength,
			String tag) {
		
		LevelTypeList outputLevel = LevelTypeList.LEGACY;
		HashMap<String, String> extraparms = new HashMap<String, String> ();
		if (null != tagLength) extraparms.put("taglength", tagLength);
		if (null != filter) extraparms.put("filter", filter);
		if (null != companyPrefixLength)
			extraparms.put("companyprefixlength", companyPrefixLength);
		
		return convert(tag, extraparms, outputLevel);
	}
	
	/**
	 * converts a given tag through tdt into PURE_IDENTITY format.
	 * @param tagLength the inbound taglength must be specified as "64" or "96".
	 * @param filter the inbound filter value must be specified - range 
	 * depends on coding scheme.
	 * @param companyPrefixLength length of the EAN.UCC Company Prefix must be 
	 * specified for GS1 coding schemes. if set to null paramter is ignored.
	 * @param tag the tag to convert in binary format or in TAG_ENCODING.
	 * @return a converted tag or null if exception during conversion.
	 */
	public static synchronized String convert_to_PURE_IDENTITY(
			String tagLength,
			String filter,
			String companyPrefixLength,
			String tag) {
		
		LevelTypeList outputLevel = LevelTypeList.PURE_IDENTITY;
		HashMap<String, String> extraparms = new HashMap<String, String> ();
		if (null != tagLength) extraparms.put("taglength", tagLength);
		if (null != filter) extraparms.put("filter", filter);
		if (null != companyPrefixLength)
			extraparms.put("companyprefixlength", companyPrefixLength);
		
		return convert(tag, extraparms, outputLevel);		
	}
	
	public static synchronized String convert_to_TAG_ENCODING(
			String tagLength,
			String filter,
			String companyPrefixLength,
			String tag) {
		
		LevelTypeList outputLevel = LevelTypeList.TAG_ENCODING;
		HashMap<String, String> extraparms = new HashMap<String, String> ();
		if (null != tagLength) extraparms.put("taglength", tagLength);
		if (null != filter) extraparms.put("filter", filter);
		if (null != companyPrefixLength)
			extraparms.put("companyprefixlength", companyPrefixLength);
		
		return convert(tag, extraparms, outputLevel);
	}
}
