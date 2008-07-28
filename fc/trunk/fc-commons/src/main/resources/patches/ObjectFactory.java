
package org.fosstrak.ale.xsd.ale.epcglobal;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpecExtension.StartTriggerList;
import org.fosstrak.ale.xsd.ale.epcglobal.ECBoundarySpecExtension.StopTriggerList;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterListMember.PatList;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpec.ExcludePatterns;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpec.IncludePatterns;
import org.fosstrak.ale.xsd.ale.epcglobal.ECFilterSpecExtension.FilterList;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReaderStat.Sightings;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMemberExtension.Stats;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReportSpecExtension.StatProfileNames;
import org.fosstrak.ale.xsd.ale.epcglobal.ECReports.Reports;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec.LogicalReaders;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpec.ReportSpecs;
import org.fosstrak.ale.xsd.ale.epcglobal.ECSpecExtension.PrimaryKeyFields;
import org.fosstrak.ale.xsd.ale.epcglobal.ECTagStat.StatBlocks;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec.Properties;
import org.fosstrak.ale.xsd.ale.epcglobal.LRSpec.Readers;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.fosstrak.ale.xsd.ale.epcglobal package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ECSpec_QNAME = new QName("urn:epcglobal:ale:xsd:1", "ECSpec");
    private final static QName _ECReports_QNAME = new QName("urn:epcglobal:ale:xsd:1", "ECReports");

    private final static QName _LRProperty_QNAME = new QName("urn:epcglobal:ale:xsd:1", "LRProperty");
    private final static QName _LRSpec_QNAME = new QName("urn:epcglobal:ale:xsd:1", "LRSpec");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.fosstrak.ale.xsd.ale.epcglobal
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Sightings }
     * 
     */
    public Sightings createECReaderStatSightings() {
        return new Sightings();
    }

    /**
     * Create an instance of {@link ECBoundarySpecExtension }
     * 
     */
    public ECBoundarySpecExtension createECBoundarySpecExtension() {
        return new ECBoundarySpecExtension();
    }

    /**
     * Create an instance of {@link ECBoundarySpec }
     * 
     */
    public ECBoundarySpec createECBoundarySpec() {
        return new ECBoundarySpec();
    }

    /**
     * Create an instance of {@link ECReportGroupListExtension }
     * 
     */
    public ECReportGroupListExtension createECReportGroupListExtension() {
        return new ECReportGroupListExtension();
    }

    /**
     * Create an instance of {@link ECSpecExtension2 }
     * 
     */
    public ECSpecExtension2 createECSpecExtension2() {
        return new ECSpecExtension2();
    }

    /**
     * Create an instance of {@link org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMemberExtension.FieldList }
     * 
     */
    public org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMemberExtension.FieldList createECReportGroupListMemberExtensionFieldList() {
        return new org.fosstrak.ale.xsd.ale.epcglobal.ECReportGroupListMemberExtension.FieldList();
    }

    /**
     * Create an instance of {@link ECReaderStat }
     * 
     */
    public ECReaderStat createECReaderStat() {
        return new ECReaderStat();
    }

    /**
     * Create an instance of {@link PatList }
     * 
     */
    public PatList createECFilterListMemberPatList() {
        return new PatList();
    }

    /**
     * Create an instance of {@link ECFilterListMemberExtension }
     * 
     */
    public ECFilterListMemberExtension createECFilterListMemberExtension() {
        return new ECFilterListMemberExtension();
    }

    /**
     * Create an instance of {@link ECReportGroupCount }
     * 
     */
    public ECReportGroupCount createECReportGroupCount() {
        return new ECReportGroupCount();
    }

    /**
     * Create an instance of {@link ECGroupSpecExtension2 }
     * 
     */
    public ECGroupSpecExtension2 createECGroupSpecExtension2() {
        return new ECGroupSpecExtension2();
    }

    /**
     * Create an instance of {@link ECReport }
     * 
     */
    public ECReport createECReport() {
        return new ECReport();
    }

    /**
     * Create an instance of {@link ECReportOutputSpecExtension }
     * 
     */
    public ECReportOutputSpecExtension createECReportOutputSpecExtension() {
        return new ECReportOutputSpecExtension();
    }

    /**
     * Create an instance of {@link ECReportSpecExtension }
     * 
     */
    public ECReportSpecExtension createECReportSpecExtension() {
        return new ECReportSpecExtension();
    }

    /**
     * Create an instance of {@link ECReportGroupListMember }
     * 
     */
    public ECReportGroupListMember createECReportGroupListMember() {
        return new ECReportGroupListMember();
    }

    /**
     * Create an instance of {@link ECGroupSpec }
     * 
     */
    public ECGroupSpec createECGroupSpec() {
        return new ECGroupSpec();
    }

    /**
     * Create an instance of {@link ECReportExtension }
     * 
     */
    public ECReportExtension createECReportExtension() {
        return new ECReportExtension();
    }

    /**
     * Create an instance of {@link ECReportMemberFieldExtension }
     * 
     */
    public ECReportMemberFieldExtension createECReportMemberFieldExtension() {
        return new ECReportMemberFieldExtension();
    }

    /**
     * Create an instance of {@link ReportSpecs }
     * 
     */
    public ReportSpecs createECSpecReportSpecs() {
        return new ReportSpecs();
    }

    /**
     * Create an instance of {@link Stats }
     * 
     */
    public Stats createECReportGroupListMemberExtensionStats() {
        return new Stats();
    }

    /**
     * Create an instance of {@link ECReportGroup }
     * 
     */
    public ECReportGroup createECReportGroup() {
        return new ECReportGroup();
    }

    /**
     * Create an instance of {@link ECReportSpec }
     * 
     */
    public ECReportSpec createECReportSpec() {
        return new ECReportSpec();
    }

    /**
     * Create an instance of {@link ECSpec }
     * 
     */
    public ECSpec createECSpec() {
        return new ECSpec();
    }

    /**
     * Create an instance of {@link ECSpecExtension }
     * 
     */
    public ECSpecExtension createECSpecExtension() {
        return new ECSpecExtension();
    }

    /**
     * Create an instance of {@link ECTagTimestampStat }
     * 
     */
    public ECTagTimestampStat createECTagTimestampStat() {
        return new ECTagTimestampStat();
    }

    /**
     * Create an instance of {@link StatProfileNames }
     * 
     */
    public StatProfileNames createECReportSpecExtensionStatProfileNames() {
        return new StatProfileNames();
    }

    /**
     * Create an instance of {@link ECSightingStat }
     * 
     */
    public ECSightingStat createECSightingStat() {
        return new ECSightingStat();
    }

    /**
     * Create an instance of {@link ECReportOutputSpecExtension2 }
     * 
     */
    public ECReportOutputSpecExtension2 createECReportOutputSpecExtension2() {
        return new ECReportOutputSpecExtension2();
    }

    /**
     * Create an instance of {@link ECReportOutputFieldSpec }
     * 
     */
    public ECReportOutputFieldSpec createECReportOutputFieldSpec() {
        return new ECReportOutputFieldSpec();
    }

    /**
     * Create an instance of {@link PrimaryKeyFields }
     * 
     */
    public PrimaryKeyFields createECSpecExtensionPrimaryKeyFields() {
        return new PrimaryKeyFields();
    }

    /**
     * Create an instance of {@link LogicalReaders }
     * 
     */
    public LogicalReaders createECSpecLogicalReaders() {
        return new LogicalReaders();
    }

    /**
     * Create an instance of {@link ECFilterSpecExtension }
     * 
     */
    public ECFilterSpecExtension createECFilterSpecExtension() {
        return new ECFilterSpecExtension();
    }

    /**
     * Create an instance of {@link StatBlocks }
     * 
     */
    public StatBlocks createECTagStatStatBlocks() {
        return new StatBlocks();
    }

    /**
     * Create an instance of {@link ECReportGroupListMemberExtension2 }
     * 
     */
    public ECReportGroupListMemberExtension2 createECReportGroupListMemberExtension2() {
        return new ECReportGroupListMemberExtension2();
    }

    /**
     * Create an instance of {@link IncludePatterns }
     * 
     */
    public IncludePatterns createECFilterSpecIncludePatterns() {
        return new IncludePatterns();
    }

    /**
     * Create an instance of {@link ECTime }
     * 
     */
    public ECTime createECTime() {
        return new ECTime();
    }

    /**
     * Create an instance of {@link ECBoundarySpecExtension2 }
     * 
     */
    public ECBoundarySpecExtension2 createECBoundarySpecExtension2() {
        return new ECBoundarySpecExtension2();
    }

    /**
     * Create an instance of {@link ECReportGroupListMemberExtension }
     * 
     */
    public ECReportGroupListMemberExtension createECReportGroupListMemberExtension() {
        return new ECReportGroupListMemberExtension();
    }

    /**
     * Create an instance of {@link StartTriggerList }
     * 
     */
    public StartTriggerList createECBoundarySpecExtensionStartTriggerList() {
        return new StartTriggerList();
    }

    /**
     * Create an instance of {@link ECFilterSpec }
     * 
     */
    public ECFilterSpec createECFilterSpec() {
        return new ECFilterSpec();
    }

    /**
     * Create an instance of {@link ECFieldSpecExtension }
     * 
     */
    public ECFieldSpecExtension createECFieldSpecExtension() {
        return new ECFieldSpecExtension();
    }

    /**
     * Create an instance of {@link ECGroupSpecExtension }
     * 
     */
    public ECGroupSpecExtension createECGroupSpecExtension() {
        return new ECGroupSpecExtension();
    }

    /**
     * Create an instance of {@link ECFilterSpecExtension2 }
     * 
     */
    public ECFilterSpecExtension2 createECFilterSpecExtension2() {
        return new ECFilterSpecExtension2();
    }

    /**
     * Create an instance of {@link ECReportMemberField }
     * 
     */
    public ECReportMemberField createECReportMemberField() {
        return new ECReportMemberField();
    }

    /**
     * Create an instance of {@link ECReports }
     * 
     */
    public ECReports createECReports() {
        return new ECReports();
    }

    /**
     * Create an instance of {@link FilterList }
     * 
     */
    public FilterList createECFilterSpecExtensionFilterList() {
        return new FilterList();
    }

    /**
     * Create an instance of {@link ECTagStat }
     * 
     */
    public ECTagStat createECTagStat() {
        return new ECTagStat();
    }

    /**
     * Create an instance of {@link ECReportSetSpec }
     * 
     */
    public ECReportSetSpec createECReportSetSpec() {
        return new ECReportSetSpec();
    }

    /**
     * Create an instance of {@link ECReportSpecExtension2 }
     * 
     */
    public ECReportSpecExtension2 createECReportSpecExtension2() {
        return new ECReportSpecExtension2();
    }

    /**
     * Create an instance of {@link Reports }
     * 
     */
    public Reports createECReportsReports() {
        return new Reports();
    }

    /**
     * Create an instance of {@link ECFilterListMember }
     * 
     */
    public ECFilterListMember createECFilterListMember() {
        return new ECFilterListMember();
    }

    /**
     * Create an instance of {@link org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpecExtension.FieldList }
     * 
     */
    public org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpecExtension.FieldList createECReportOutputSpecExtensionFieldList() {
        return new org.fosstrak.ale.xsd.ale.epcglobal.ECReportOutputSpecExtension.FieldList();
    }

    /**
     * Create an instance of {@link StopTriggerList }
     * 
     */
    public StopTriggerList createECBoundarySpecExtensionStopTriggerList() {
        return new StopTriggerList();
    }

    /**
     * Create an instance of {@link ECReportOutputFieldSpecExtension }
     * 
     */
    public ECReportOutputFieldSpecExtension createECReportOutputFieldSpecExtension() {
        return new ECReportOutputFieldSpecExtension();
    }

    /**
     * Create an instance of {@link ECReportsExtension }
     * 
     */
    public ECReportsExtension createECReportsExtension() {
        return new ECReportsExtension();
    }

    /**
     * Create an instance of {@link ECReportGroupCountExtension }
     * 
     */
    public ECReportGroupCountExtension createECReportGroupCountExtension() {
        return new ECReportGroupCountExtension();
    }

    /**
     * Create an instance of {@link ECReportOutputSpec }
     * 
     */
    public ECReportOutputSpec createECReportOutputSpec() {
        return new ECReportOutputSpec();
    }

    /**
     * Create an instance of {@link ECFieldSpec }
     * 
     */
    public ECFieldSpec createECFieldSpec() {
        return new ECFieldSpec();
    }

    /**
     * Create an instance of {@link ExcludePatterns }
     * 
     */
    public ExcludePatterns createECFilterSpecExcludePatterns() {
        return new ExcludePatterns();
    }

    /**
     * Create an instance of {@link ECReportGroupExtension }
     * 
     */
    public ECReportGroupExtension createECReportGroupExtension() {
        return new ECReportGroupExtension();
    }

    /**
     * Create an instance of {@link ECReportGroupList }
     * 
     */
    public ECReportGroupList createECReportGroupList() {
        return new ECReportGroupList();
    }
    
        /**
     * Create an instance of {@link LRProperty }
     * 
     */
    public LRProperty createLRProperty() {
        return new LRProperty();
    }

    /**
     * Create an instance of {@link Properties }
     * 
     */
    public Properties createLRSpecProperties() {
        return new Properties();
    }

    /**
     * Create an instance of {@link LRSpec }
     * 
     */
    public LRSpec createLRSpec() {
        return new LRSpec();
    }

    /**
     * Create an instance of {@link LRSpecExtension }
     * 
     */
    public LRSpecExtension createLRSpecExtension() {
        return new LRSpecExtension();
    }

    /**
     * Create an instance of {@link Readers }
     * 
     */
    public Readers createLRSpecReaders() {
        return new Readers();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ECSpec }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:epcglobal:ale:xsd:1", name = "ECSpec")
    public JAXBElement<ECSpec> createECSpec(ECSpec value) {
        return new JAXBElement<ECSpec>(_ECSpec_QNAME, ECSpec.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ECReports }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:epcglobal:ale:xsd:1", name = "ECReports")
    public JAXBElement<ECReports> createECReports(ECReports value) {
        return new JAXBElement<ECReports>(_ECReports_QNAME, ECReports.class, null, value);
    }

        /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LRProperty }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:epcglobal:ale:xsd:1", name = "LRProperty")
    public JAXBElement<LRProperty> createLRProperty(LRProperty value) {
        return new JAXBElement<LRProperty>(_LRProperty_QNAME, LRProperty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LRSpec }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:epcglobal:ale:xsd:1", name = "LRSpec")
    public JAXBElement<LRSpec> createLRSpec(LRSpec value) {
        return new JAXBElement<LRSpec>(_LRSpec_QNAME, LRSpec.class, null, value);
    }
}
