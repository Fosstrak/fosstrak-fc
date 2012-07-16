package org.fosstrak.ale.server.readers;

import java.util.Collection;
import java.util.List;

import org.fosstrak.ale.wsdl.ale.epcglobal.DuplicateNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationException;
import org.fosstrak.ale.wsdl.ale.epcglobal.ImplementationExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameException;
import org.fosstrak.ale.wsdl.ale.epcglobal.NoSuchNameExceptionResponse;
import org.fosstrak.ale.wsdl.ale.epcglobal.SecurityException;
import org.fosstrak.ale.wsdl.ale.epcglobal.SecurityExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.ImmutableReaderExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.InUseExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.NonCompositeReaderExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.ReaderLoopExceptionResponse;
import org.fosstrak.ale.wsdl.alelr.epcglobal.ValidationExceptionResponse;
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
	String getVendorVersion()  throws ImplementationExceptionResponse;

	/**
	 * returns the standard version of the ale (see 10.3 API).
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @return standard version of the ale
	 */
	String getStandardVersion() throws ImplementationExceptionResponse;

	/**
	 * returns the current value of the specified property for reader name (see 10.3 API).
	 * @param name the reader the property value is requested
	 * @param propertyName the property that for the value is requested
	 * @throws NoSuchNameException whenever the specified name is not defined in the logicalReader API
	 * @throws SecurityException the operation was not permitted due to access restrictions
	 * @throws ImplementationException whenever something goes wrong inside the implementation
	 * @return returns a value for a requested property
	 */
	String getPropertyValue(String name,  String propertyName) throws NoSuchNameExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse;

	/**
	 * changes properties for the reader name (see 10.3 API). Null value for the properties are not allowed.
	 * @param name name of the reader to change
	 * @param properties new properties for the reader
	 * @throws NoSuchNameExceptionResponse whenever the specified name is not defined in the logicalReader API
	 * @throws InUseExceptionResponse Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ImmutableReaderExceptionResponse whenever you want to change a immutable reader
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation
	 * @throws ValidationExceptionResponse the provided LRSpec is invalid
	 */
	void setProperties(String name, List<LRProperty> properties) throws NoSuchNameExceptionResponse, ValidationExceptionResponse, InUseExceptionResponse, ImmutableReaderExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse;

	/**
	 * removes the specified logical readers from the components of the composite reader (see 10.3 API).
	 * @param name name of the composite reader
	 * @param readers list of readers to be removed
	 * @throws NoSuchNameExceptionResponse whenever the specified name is not defined in the logicalReader API
	 * @throws InUseExceptionResponse Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ImmutableReaderExceptionResponse whenever you want to change a immutable reader
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation
	 * @throws NonCompositeReaderExceptionResponse addReader or setReader or removeReader was called on a non compositeReader
	 */
	void removeReaders(String name, java.util.List<String> readers) throws NoSuchNameExceptionResponse, InUseExceptionResponse, ImmutableReaderExceptionResponse, NonCompositeReaderExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse;

	/**
	 * changes the list of readers in a composite reader (see 10.3 API).
	 * @param name name of the composite reader
	 * @param readers list of readers to be changed
	 * @throws NoSuchNameExceptionResponse whenever the specified name is not defined in the logicalReader API
	 * @throws ValidationExceptionResponse the provided LRSpec is invalid
	 * @throws InUseExceptionResponse Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ImmutableReaderExceptionResponse whenever you want to change a immutable reader
	 * @throws ReaderLoopExceptionResponse the reader would include itself which would result in a loop
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation
	 * @throws NonCompositeReaderExceptionResponse addReader or setReader was called on a non compositeReader
	 */
	void setReaders(String name, java.util.List<String> readers)  throws NoSuchNameExceptionResponse, ValidationExceptionResponse, InUseExceptionResponse, ImmutableReaderExceptionResponse, NonCompositeReaderExceptionResponse, ReaderLoopExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse;

	/**
	 * adds the specified logical readers to a composite reader (see 10.3 API).
	 * @param name name of the composite reader
	 * @param readers list of readers to be added to the composite reader
	 * @throws NoSuchNameExceptionResponse whenever the specified name is not defined in the logicalReader API
	 * @throws ValidationExceptionResponse the provided LRSpec is invalid
	 * @throws InUseExceptionResponse Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ImmutableReaderExceptionResponse whenever you want to change a immutable reader
	 * @throws ReaderLoopExceptionResponse the reader would include itself which would result in a loop
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation
	 * @throws NonCompositeReaderExceptionResponse the reader is not composite.
	 */
	void addReaders(String name, java.util.List<String> readers) throws NoSuchNameExceptionResponse, ValidationExceptionResponse, InUseExceptionResponse, ImmutableReaderExceptionResponse, ReaderLoopExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse, NonCompositeReaderExceptionResponse;
	
	/**
	 * returns an LRSpec that describes a logical reader called name (see 10.3 API).
	 * @param name name of the logical reader
	 * @throws NoSuchNameExceptionResponse whenever the specified name is not defined in the logicalReader API
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation
	 * @return LRSpec for the logical reader name
	 */
	LRSpec getLRSpec(String name) throws NoSuchNameExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse;

	/**
	 * returns a list of the logical readers in the reader (see 10.3 API).
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation 
	 * @return list of String containing the logicalReaders
	 */
	java.util.List<String> getLogicalReaderNames() throws SecurityExceptionResponse, ImplementationExceptionResponse;

	/**.
	 * removes the logicalReader name (see 10.3 API).
	 * @param name name for the logical reader to be undefined
	 * @throws NoSuchNameExceptionResponse whenever the specified name is not defined in the logicalReader API
	 * @throws InUseExceptionResponse Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImmutableReaderExceptionResponse whenever you want to change a immutable reader
	 * @throws ImplementationExceptionResponse whenever an internal error occurs
	 */
	void undefine(String name) throws NoSuchNameExceptionResponse, InUseExceptionResponse, SecurityExceptionResponse, ImmutableReaderExceptionResponse, ImplementationExceptionResponse;

	/** 
	 * Changes the definition of the logical reader named name to
	 *  match the specification in the spec parameter. This is
	 *  different than calling undefine followed by define, because
	 *  update may be called even if there are defined ECSpecs, 
	 *  CCSpecs, or other logical readers that refer to this 
	 *  logical reader. 
	 * @param name a valid name for the reader to be changed.
	 * @param spec an LRSpec describing the changes to the reader  
	 * @throws ImmutableReaderExceptionResponse whenever you want to change a immutable reader
	 * @throws ValidationExceptionResponse the provided LRSpec is invalid
	 * @throws InUseExceptionResponse Is thrown when you try to undefine a Reader that is still referenced by EC or CC
	 * @throws ReaderLoopExceptionResponse the reader would include itself which would result in a loop
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation 
	 * @throws NoSuchNameExceptionResponse whenever the specified name is not defined in the logicalReader API
	 */
	void update(String name, LRSpec spec)  throws NoSuchNameExceptionResponse, ValidationExceptionResponse, InUseExceptionResponse,  ImmutableReaderExceptionResponse, ReaderLoopExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse;

	/**
	 * creates a new logical Reader according to spec (see 10.3 API). this variant works on jaxb LRSpec
	 * @param name name of the new logicalReader
	 * @param spec LRSpec how to build the reader
	 * @throws DuplicateNameExceptionResponse when a reader name is already defined
	 * @throws ValidationExceptionResponse the provided LRSpec is invalid
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation 
	 */
	void define(String name, org.fosstrak.ale.server.readers.gen.LRSpec spec) throws DuplicateNameExceptionResponse, ValidationExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse;
	
	/**
	 * creates a new logical Reader according to spec (see 10.3 API). this variant works directly on LRSpec
	 * @param name name of the new logicalReader
	 * @param spec LRSpec how to build the reader
	 * @throws DuplicateNameExceptionResponse when a reader name is already defined
	 * @throws ValidationExceptionResponse the provided LRSpec is invalid
	 * @throws SecurityExceptionResponse the operation was not permitted due to access restrictions
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation 
	 */
	void define(String name, LRSpec spec) throws DuplicateNameExceptionResponse, ValidationExceptionResponse, SecurityExceptionResponse, ImplementationExceptionResponse;
			
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
	 * @throws ImplementationExceptionResponse whenever something goes wrong inside the implementation
	 */
	void setLogicalReader(LogicalReader reader) throws ImplementationExceptionResponse;
	
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
