<!-- US#8341 : SOAP Generator XSL -->
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
	<xsl:output method="xml"/>
	
	<xsl:template name="sanitizeCurrency">
		<xsl:param name="val" />
		<xsl:choose>
			<xsl:when test="$val != ''">
				<xsl:choose>
					<xsl:when test="contains($val, ',')">
						<xsl:value-of select="substring-before($val, ',')" />
						<xsl:text>.</xsl:text>
						<xsl:value-of select="substring-after($val, ',')" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$val"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>0</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	
	<xsl:template match="/">
		<xsl:variable name="main_doc" select="/transaction/data_xml/document[@document_number=1]"/>
		<xsl:variable name="documentAvailable">
			<xsl:choose>
				<xsl:when test="string-length($main_doc/oSCFileAttachment) = 0">
					<xsl:value-of select="'false'"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="'true'"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!--  Begin SOAP Envelope For Oracle Sales Cloud - Upsert Quote Export Integration -->
		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:typ="http://xmlns.oracle.com/apps/crmCommon/orders/salesOrdersService/types/" xmlns:sal="http://xmlns.oracle.com/apps/crmCommon/orders/salesOrdersService/">
			<soapenv:Body>
				<xsl:choose>
					<xsl:when test="string-length($main_doc/oSCQuoteId) = 0">
						<typ:createSalesOrderHeader>
							<typ:salesOrderHeader>
								<sal:OptyId>
									<xsl:value-of select="$main_doc/opportunityID_t"/>
								</sal:OptyId>
								<sal:Name>
									<xsl:value-of select="$main_doc/transactionName_t"/>
								</sal:Name>
								<sal:ExternalReferenceNumber>
									<xsl:value-of select="$main_doc/bs_id"/>
								</sal:ExternalReferenceNumber>
								<sal:ExternalSystemReferenceCode>BMQUOTE</sal:ExternalSystemReferenceCode>
								<sal:ProposalExistFlag>
									<xsl:value-of select="$documentAvailable"/>
								</sal:ProposalExistFlag>
								<sal:WinStatusCode>
									<xsl:value-of select="$main_doc/statusOSC_t"/>
								</sal:WinStatusCode>
								<sal:VersionNumber>
									<xsl:value-of select="$main_doc/version_number_versionTransaction_t"/>
								</sal:VersionNumber>
								<sal:OrderTotal>
									<xsl:choose>
  										<xsl:when test="($main_doc/userType_t) ='BuyAccess'">
											<xsl:call-template name="sanitizeCurrency">
												<xsl:with-param name="val" select="$main_doc/totalExtBuyPrice_t"/>
											</xsl:call-template>
										</xsl:when>
  										<xsl:otherwise>
											<xsl:call-template name="sanitizeCurrency">
												<xsl:with-param name="val" select="$main_doc/totalContractValue_t"/>
											</xsl:call-template>
										</xsl:otherwise>
  									</xsl:choose>										
								</sal:OrderTotal>
								<sal:CurrencyCode>
									<xsl:value-of select="$main_doc/currency_t"/>
								</sal:CurrencyCode>
								<sal:SoldCustomerPartyId>
									<xsl:value-of select="$main_doc/_customer_id"/>
								</sal:SoldCustomerPartyId>
								<sal:LastOptySyncDate>
									<xsl:value-of select="$main_doc/oSCSyncTime"/>
								</sal:LastOptySyncDate>
								<sal:Status>
									<xsl:value-of select="$main_doc/status_t"/>
								</sal:Status>
							</typ:salesOrderHeader>
						</typ:createSalesOrderHeader>
					</xsl:when>
					<xsl:otherwise>
						<typ:updateSalesOrderHeader>
							<typ:salesOrderHeader>
								<sal:OptyId>
									<xsl:value-of select="$main_doc/opportunityID_t"/>
								</sal:OptyId>
								<sal:Name>
									<xsl:value-of select="$main_doc/transactionName_t"/>
								</sal:Name>
								<sal:ExternalReferenceNumber>
									<xsl:value-of select="$main_doc/bs_id"/>
								</sal:ExternalReferenceNumber>
								<sal:ExternalSystemReferenceCode>BMQUOTE</sal:ExternalSystemReferenceCode>
								<sal:ProposalExistFlag>
									<xsl:value-of select="$documentAvailable"/>
								</sal:ProposalExistFlag>
								<sal:WinStatusCode>
									<xsl:value-of select="$main_doc/statusOSC_t"/>
								</sal:WinStatusCode>
								<sal:VersionNumber>
									<xsl:value-of select="$main_doc/version_number_versionTransaction_t"/>
								</sal:VersionNumber>
								<sal:OrderTotal>
									<xsl:choose>
  										<xsl:when test="($main_doc/userType_t) ='BuyAccess'">
											<xsl:call-template name="sanitizeCurrency">
												<xsl:with-param name="val" select="$main_doc/totalExtBuyPrice_t"/>
											</xsl:call-template>
										</xsl:when>
  										<xsl:otherwise>
											<xsl:call-template name="sanitizeCurrency">
												<xsl:with-param name="val" select="$main_doc/totalContractValue_t"/>
											</xsl:call-template>
										</xsl:otherwise>
  									</xsl:choose>
								</sal:OrderTotal>
								<sal:CurrencyCode>
									<xsl:value-of select="$main_doc/currency_t"/>
								</sal:CurrencyCode>
								<sal:SoldCustomerPartyId>
									<xsl:value-of select="$main_doc/_customer_id"/>
								</sal:SoldCustomerPartyId>
								<sal:LastOptySyncDate>
									<xsl:value-of select="$main_doc/oSCSyncTime"/>
								</sal:LastOptySyncDate>
								<sal:Status>
									<xsl:value-of select="$main_doc/status_t"/>
								</sal:Status>
							</typ:salesOrderHeader>
						</typ:updateSalesOrderHeader>
					</xsl:otherwise>
				</xsl:choose>
			</soapenv:Body>
		</soapenv:Envelope>
		<!--  End SOAP XML  -->
	</xsl:template>
</xsl:stylesheet>