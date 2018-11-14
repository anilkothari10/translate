<!-- US#1003 : Result Parser XSL -->
<?xml version="1.0" encoding="UTF-8"?>
<!-- ################# Upsert Quote - Oracle CRMOD Integration - Result Parser XSL ################# -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
	<xsl:output method="xml"/>
	<xsl:variable name="main_doc" select="/transaction/data_xml/document[@data_type=0]"/>
	<xsl:template match="/">
		<data_xml>
			<document document_var_name="quote_process">
			    <!-- ############## Set oracleCustomObject2QuoteInstanceID_quote Value ########### -->
				<crmQuoteId_quote><xsl:value-of select="//*[local-name()='OrderHeaderId']"/></crmQuoteId_quote>
			</document>
		</data_xml>
	</xsl:template>
</xsl:stylesheet>
