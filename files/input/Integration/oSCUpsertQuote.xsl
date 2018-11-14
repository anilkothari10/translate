<!-- US#8341 : Result Parser XSL -->
<?xml version="1.0" encoding="UTF-8"?>
<!-- ################# Upsert Quote - Oracle Sales Cloud - Result Parser XSL ################# -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="/oracle/apps/sales/custExtn/views/common/">
	<xsl:output method="xml"/>
	<xsl:variable name="main_doc" select="/transaction/data_xml/document[@data_type=0]"/>
	<xsl:template match="/">
		<data_xml>
			<document document_var_name="transaction">
			    <!-- ############## Set oracleCustomObject2QuoteInstanceID_quote Value ########### -->
				<oSCQuoteId><xsl:value-of select="//*[local-name()='OrderHeaderId']"/></oSCQuoteId>
			</document>
		</data_xml>
	</xsl:template>
</xsl:stylesheet>