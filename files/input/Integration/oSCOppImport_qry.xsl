<!-- US#1234 : SOAP Generator XSL -->
<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:typ="http://www.w3.org/1999/XSL/Format" version="1.0">
	<xsl:output method="xml"/>
	<xsl:template match="/">
		<xsl:variable name="main_doc" select="/transaction/data_xml/document[@document_number=1]"/>
		<!-- Begin SOAP Envelope For Oracle Sales Cloud -->
		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:typ="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/types/" xmlns:typ1="http://xmlns.oracle.com/adf/svc/types/">
			<soapenv:Body>
				<ns1:getOpportunity xmlns:ns1="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/types/">
					<ns1:optyId><xsl:value-of select="$main_doc/opportunityID_t"/></ns1:optyId>
				</ns1:getOpportunity>
			</soapenv:Body>
		</soapenv:Envelope>
		<!-- End SOAP XML -->
	</xsl:template>
</xsl:stylesheet>
