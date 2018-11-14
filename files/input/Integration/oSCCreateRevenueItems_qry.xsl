<!-- US#7661 : SOAP Generator XSL -->
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
    <!--   Begin SOAP Envelope For Oracle Sales Cloud   -->
    <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
      <soap:Body>
        <ns1:mergeOpportunity xmlns:ns1="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/types/">
          <ns1:opportunity>
            <ns21:OptyId xmlns:ns21="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/">
              <xsl:value-of select="$main_doc/opportunityID_t"/>
            </ns21:OptyId>
            <xsl:for-each select="/transaction/data_xml/document[@data_type=2 or @data_type=3]">
              <xsl:sort select="normalize-space(_sequence_number)" data-type="number"/>
              <xsl:variable name="sub_doc" select="."/>
              	<xsl:if test="normalize-space($sub_doc/parentLineNumber_l) = ''">
                <ns100:ChildRevenue xmlns:ns100="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/">
                  <ns106:UnitPrice xmlns:ns106="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/">
                    <xsl:call-template name="sanitizeCurrency">
					<xsl:with-param name="val" select="$sub_doc/modelEstimatedPrice_l"/>
					</xsl:call-template>
                  </ns106:UnitPrice>
                  <ns107:Quantity xmlns:ns107="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/">
                    <xsl:value-of select="$sub_doc/requestedQuantity_l"/>
                  </ns107:Quantity>
                  <ns108:RevnAmount xmlns:ns108="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/">
                    <xsl:choose>
  										<xsl:when test="($main_doc/userType_t) ='BuyAccess'">
											<xsl:call-template name="sanitizeCurrency">
											<xsl:with-param name="val" select="$sub_doc/modelTotalBuyPrice_l"/>
											</xsl:call-template>
  										</xsl:when>
  										<xsl:otherwise>
											<xsl:call-template name="sanitizeCurrency">
											<xsl:with-param name="val" select="$sub_doc/modelTotalContractValue_l"/>
											</xsl:call-template>
  										</xsl:otherwise>
  									</xsl:choose>
                  </ns108:RevnAmount>
                  <ns109:RevnAmountCurcyCode xmlns:ns109="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/">
                    <xsl:value-of select="$main_doc/currency_t"/>
                  </ns109:RevnAmountCurcyCode>
                  <ns111:InventoryOrgId xmlns:ns111="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/">
                    <xsl:value-of select="$sub_doc/orgId_l"/>
                  </ns111:InventoryOrgId>
                  <ns110:InventoryItemId xmlns:ns110="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/">
                    <xsl:value-of select="$sub_doc/entityId_l"/>
                  </ns110:InventoryItemId>
                  <!-- <ns112:UOMCode xmlns:ns112="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/">
                  <xsl:value-of select="$sub_doc/requestedUnitOfMeasure_l"/>
                </ns112:UOMCode> -->
                <ns113:ResourcePartyId xmlns:ns113="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/">
                  <xsl:value-of select="$main_doc/oSCOwnerResourcePartyId_t"/>
                </ns113:ResourcePartyId>
              </ns100:ChildRevenue>
          </xsl:if>
          </xsl:for-each>
        </ns1:opportunity>
      </ns1:mergeOpportunity>
    </soap:Body>
  </soap:Envelope>
  <!--   End SOAP XML   -->
</xsl:template>
</xsl:stylesheet>