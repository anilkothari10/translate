<!-- US#1003 : SOAP Generator XSL -->
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
	<xsl:output method="xml"/>
	<xsl:template match="/">
		<xsl:variable name="main_doc" select="/transaction/data_xml/document[@document_number=1]"/>
		<!-- Begin SOAP Envelope For Sales Cloud-->
		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:typ="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/types/" xmlns:opp="http://xmlns.oracle.com/apps/sales/opptyMgmt/opportunities/opportunityService/" xmlns:rev="http://xmlns.oracle.com/apps/sales/opptyMgmt/revenues/revenueService/" xmlns:not="http://xmlns.oracle.com/apps/crmCommon/notes/noteService" xmlns:not1="http://xmlns.oracle.com/apps/crmCommon/notes/flex/noteDff/" xmlns:rev1="http://xmlns.oracle.com/oracle/apps/sales/opptyMgmt/revenues/revenueService/" xmlns:act="http://xmlns.oracle.com/apps/crmCommon/activities/activitiesService/">
			<soapenv:Header/>
			<soapenv:Body>
				<typ:updateOpportunity>
				<xsl:choose>
					<xsl:when test="string-length($main_doc/opportunityID_t) != 0">
					<typ:opportunity>
						<opp:OptyId><xsl:value-of select="$main_doc/opportunityID_t"/></opp:OptyId>
						<opp:SalesStageId>300000000127579</opp:SalesStageId>
						<opp:ReasonWonLostCode>PRODUCT</opp:ReasonWonLostCode>
						<opp:StatusCode>WON</opp:StatusCode>
					</typ:opportunity>
					</xsl:when>
					<xsl:otherwise>
						<typ:opportunity>
							<opp:OptyId>300000066841395</opp:OptyId>
						</typ:opportunity>
					</xsl:otherwise>
					</xsl:choose>
				</typ:updateOpportunity>
			</soapenv:Body>
		</soapenv:Envelope>
		<!-- End SOAP XML -->
	</xsl:template>
</xsl:stylesheet>
