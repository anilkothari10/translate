<!-- US#7661 : Result Parser XSL -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns1="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/">
	<xsl:output method="xml"/>
	<xsl:variable name="ids" select="//ns1:RevnId"/>
	<xsl:template match="/">
		<response>
			<xsl:for-each select="$ids">
			<xsl:sort select="." data-type="number"/>
				<id_field_value>
					<xsl:value-of select="."/>
				</id_field_value>
			</xsl:for-each>
		</response>
	</xsl:template>
</xsl:stylesheet>