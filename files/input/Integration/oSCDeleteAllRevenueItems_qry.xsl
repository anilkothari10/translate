<!-- US#5678 : SOAP Generator XSL -->
<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
	<xsl:output method="xml"/>
	<xsl:template match="/">
		<xsl:variable name="main_doc" select="/transaction/data_xml/document[@document_number=1]"/>
		<!-- Begin SOAP Envelope For Oracle Sales Cloud -->
			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:typ="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/types/">
			   <soapenv:Header/>
			   <soapenv:Body>
						  <typ:deleteAllRevenuesInOpportunity>
							 <typ:optyId><xsl:value-of select="$main_doc/opportunityID_t"/></typ:optyId>
						  </typ:deleteAllRevenuesInOpportunity>
			   </soapenv:Body>
			</soapenv:Envelope>
			<!-- End SOAP XML -->
	</xsl:template>
</xsl:stylesheet>
