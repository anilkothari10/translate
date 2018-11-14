<!-- US#5678 : Result Parser XSL -->
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sb1="urn:crmondemand/ws/account/10/2004" xmlns:ns3="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/">
	<xsl:output method="xml"/>
	<xsl:template match="/">
		<data_xml>
			<document document_var_name="transaction">
				<opportunityName_t>
					<xsl:value-of select="//ns3:Name"/>
				</opportunityName_t>
			</document>
		</data_xml>
	</xsl:template>
</xsl:stylesheet>