<!-- US#1234 : Result Parser XSL -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sb1="urn:crmondemand/ws/account/10/2004" xmlns:ns3="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/" xmlns:ns2="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/" version="1.0">
	<xsl:output method="xml"/>
	<xsl:template match="/">
		<data_xml>
			<document document_var_name="transaction">
				<opportunityName_t>
					<xsl:value-of select="//ns3:Name"/>
				</opportunityName_t>
				<_customer_t_last_name>
					<xsl:value-of select="//ns2:PrimaryContactLastName"/>
				</_customer_t_last_name>
				<_customer_t_first_name>
					<xsl:value-of select="//ns2:PrimaryContactFirstName"/>
				</_customer_t_first_name>
				<_invoiceTo_t_first_name>
					<xsl:value-of select="//ns2:PrimaryContactFirstName"/>
				</_invoiceTo_t_first_name>
				<_invoiceTo_t_last_name>
					<xsl:value-of select="//ns2:PrimaryContactLastName"/>
				</_invoiceTo_t_last_name>
				<opportunityNumber_t>
					<xsl:value-of select="//ns2:OptyNumber"/>
				</opportunityNumber_t>
				<oSCPrimaryOrgID_t>
					<xsl:value-of select="//ns2:PrimaryOrganizationId"/>
				</oSCPrimaryOrgID_t>
				<oSCOwnerResourcePartyId_t>
					<xsl:value-of select="//ns2:OwnerResourcePartyId"/>
				</oSCOwnerResourcePartyId_t>
				<currency_t>
					<xsl:value-of select="//ns2:CurrencyCode"/>
				</currency_t>
        <partnerLevel_t>
          <xsl:value-of select="//ns3:PriceList_c"/>
        </partnerLevel_t>
			</document>
		</data_xml>
	</xsl:template>
</xsl:stylesheet>
