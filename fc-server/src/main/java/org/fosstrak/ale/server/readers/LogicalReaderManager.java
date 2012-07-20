package org.fosstrak.ale.server.readers;

import java.util.Collection;
import java.util.List;

import org.fosstrak.ale.exception.DuplicateNameException;
import org.fosstrak.ale.exception.ImmutableReaderException;
import org.fosstrak.ale.exception.ImplementationException;
import org.fosstrak.ale.exception.InUseException;
import org.fosstrak.ale.exception.NoSuchNameException;
import org.fosstrak.ale.exception.NonCompositeReaderException;
import org.fosstrak.ale.exception.ReaderLoopException;
import org.fosstrak.ale.exception.SecurityException;
import org.fosstrak.ale.exception.ValidationException;
import org.fosstrak.ale.xsd.ale.epcglobal.LRProperty;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec;

/**
 * interface for the logical reader manager. In order to use the reader manager, use the LogicalReaderManagerFactory
 * to obtain a handle onto the currently installed singleton of the reader manager in this ALE. 
 * 
 * @author swieland
 *
 */
public interface LogicalReaderManager {	
	
	/**
	 * returns the vendor version of the ale (see 10.3 API).
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @return vendor version of the ale
	 */
	String getVendorVersion()  throws ImplementationException;

	/**
	 * returns the standard version of the ale (see 10.3 API).
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @return standard version of the ale
	 */
	String getStandardVersion() throws ImplementationException;

	/**
	 * returns the current value of the specified property for reader name (see 10.3 API).
	 * @param name the reader the property value is requested
	 * @param propertyName the property that for the value is requested
	 * @throws NoSuchNameException whenever the specified name is not defined in the logicalReader API
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @return returns a value for a requested property
	 */
	String getPropertyValue(String name,  String propertyName) throws NoSuchNameException, SecurityException, ImplementationException;

	/**
	 * changes properties for the reader name (see 10.3 API). Null value for the properties are not allowed.
	 * @param name name of the reader to change
	 * @param properties new properties for the reader
	 * @throws NoSuchNameException whenever the specified name is not defined in the logicalReader API
	 * @throws InUseException Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ImmutableReaderException whenever you want to change a immutable reader
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @throws ValidationException the provided LRSpec is invalid
	 */
	void setProperties(String name, List<LRProperty> properties) throws NoSuchNameException, ValidationException, InUseException, ImmutableReaderException, SecurityException, ImplementationException;

	/**
	 * removes the specified logical readers from the components of the composite reader (see 10.3 API).
	 * @param name name of the composite reader
	 * @param readers list of readers to be removed
	 * @throws NoSuchNameException whenever the specified name is not defined in the logicalReader API
	 * @throws InUseException Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ImmutableReaderException whenever you want to change a immutable reader
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @throws NonCompositeReaderException addReader or setReader or removeReader was called on a non compositeReader
	 */
	void removeReaders(String name, java.util.List<String> readers) throws NoSuchNameException, InUseException, ImmutableReaderException, NonCompositeReaderException, SecurityException, ImplementationException;

	/**
	 * changes the list of readers in a composite reader (see 10.3 API).
	 * @param name name of the composite reader
	 * @param readers list of readers to be changed
	 * @throws NoSuchNameException whenever the specified name is not defined in the logicalReader API
	 * @throws ValidationException the provided LRSpec is invalid
	 * @throws InUseException Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ImmutableReaderException whenever you want to change a immutable reader
	 * @throws ReaderLoopException the reader would include itself which would result in a loop
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @throws NonCompositeReaderException addReader or setReader was called on a non compositeReader
	 */
	void setReaders(String name, java.util.List<String> readers)  throws NoSuchNameException, ValidationException, InUseException, ImmutableReaderException, NonCompositeReaderException, ReaderLoopException, SecurityException, ImplementationException;

	/**
	 * adds the specified logical readers to a composite reader (see 10.3 API).
	 * @param name name of the composite reader
	 * @param readers list of readers to be added to the composite reader
	 * @throws NoSuchNameException whenever the specified name is not defined in the logicalReader API
	 * @throws ValidationException the provided LRSpec is invalid
	 * @throws InUseException Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ImmutableReaderException whenever you want to change a immutable reader
	 * @throws ReaderLoopException the reader would include itself which would result in a loop
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @throws NonCompositeReaderException the reader is not composite.
	 */
	void addReaders(String name, java.util.List<String> readers) throws NoSuchNameException, ValidationException, InUseException, ImmutableReaderException, ReaderLoopException, SecurityException, ImplementationException, NonCompositeReaderException;
	
	/**
	 * returns an LRSpec that describes a logical reader called name (see 10.3 API).
	 * @param name name of the logical reader
	 * @throws NoSuchNameException whenever the specified name is not defined in the logicalReader API
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @return LRSpec for the logical reader name
	 */
	LRSpec getLRSpec(String name) throws NoSuchNameException, SecurityException, ImplementationException;

	/**
	 * returns a list of the logical readers in the reader (see 10.3 API).
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation 
	 * @return list of String containing the logicalReaders
	 */
	java.util.List<String> getLogicalReaderNames() throws SecurityException, ImplementationException;

	/**.
	 * removes the logicalReader name (see 10.3 API).
	 * @param name name for the logical reader to be undefined
	 * @throws NoSuchNameException whenever the specified name is not defined in the logicalReader API
	 * @throws InUseException Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImmutableReaderException whenever you want to change a immutable reader
	 * @throws ImplementationException whenever an internal error occurs
	 */
	void undefine(String name) throws NoSuchNameException, InUseException, SecurityException, ImmutableReaderException, ImplementationException;

	/** 
	 * Changes the definition of the logical reader named name to
	 *  match the specification in the spec parameter. This is
	 *  different than calling undefine followed by define, because
	 *  update may be called even if there are defined ECSpecs, 
	 *  CCSpecs, or other logical readers that refer to this 
	 *  logical reader. 
	 * @param name a valid name for the reader to be changed.
	 * @param spec an LRSpec describing the changes to the reader  
	 * @throws ImmutableReaderException whenever you want to change a immutable reader
	 * @throws ValidationException the provided LRSpec is invalid
	 * @throws InUseException Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ReaderLoopException the reader would include itself which would result in a loop
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation 
	 * @throws NoSuchNameException whenever the specified name is not defined in the logicalReader API
	 */
	void update(String name, LRSpec spec)  throws NoSuchNameException, ValidationException, InUseException,  ImmutableReaderException, ReaderLoopException, SecurityException, ImplementationException;

	/**
	 * creates a new logical Reader according to spec (see 10.3 API). this variant works on jaxb LRSpec
	 * @param name name of the new logicalReader
	 * @param spec LRSpec how to build the reader
	 * @throws DuplicateNameException when a reader name is already defined
	 * @throws ValidationException the provided LRSpec is invalid
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation 
	 */
	void define(String name, org.fosstrak.ale.server.readers.gen.LRSpec spec) throws DuplicateNameException, ValidationException, SecurityException, ImplementationException;
	
	/**
	 * creates a new logical Reader according to spec (see 10.3 API). this variant works directly on LRSpec
	 * @param name name of the new logicalReader
	 * @param spec LRSpec how to build the reader
	 * @throws DuplicateNameException when a reader name is already defined
	 * @throws ValidationException the provided LRSpec is invalid
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation 
	 */
	void define(String name, LRSpec spec) throws DuplicateNameException, ValidationException, SecurityException, ImplementationException;
			
	/**
	 * returns the requested logicalReader.
	 * @param readerName name of the requested reader
	 * @return a logicalReder
	 */
	LogicalReader getLogicalReader(String readerName);
	
	/**
	 * returns all available logicalReaders.
	 * @return Set of LogicalReader
	 */
	Collection<LogicalReader> getLogicalReaders();
	
	/**
	 * 
	 * @param reader a logicalReader to be stored in the manager
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 */
	void setLogicalReader(LogicalReader reader) throws ImplementationException;
	
	/**
	 * This method indicates if the manager contains a logical reader with specified name.
	 * 
	 * @param logicalReaderName to search
	 * @return true if the logical reader exists and false otherwise
	 */
	boolean contains(String logicalReaderName);
	
	/**
	 * indicates whether the reader management is intialized or not.
	 * @return true if initialized, false otherwise.
	 */
	boolean isInitialized();	
}
