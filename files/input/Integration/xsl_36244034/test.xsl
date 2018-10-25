<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:str="http://exslt.org/strings">
	<!--<xsl:output method="xml"/>-->
	<xsl:decimal-format name="american" decimal-separator="." grouping-separator=","/>
	<xsl:decimal-format name="european" decimal-separator="," grouping-separator="."/>
	<xsl:strip-space elements="*"/>
	<xsl:template match="/">
		<xsl:variable name = "main_doc" select = "/transaction/data_xml/document[@document_var_name = 'transaction']" />
	
		<xsl:variable name = "allLineItemloop" select = "/transaction/data_xml/document[normalize-space(./@data_type)='2' or normalize-space(./@data_type)='3']"  />
		
		<xsl:processing-instruction name="mso-application">
			<xsl:text>progid="Excel.Sheet"</xsl:text>
		</xsl:processing-instruction>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Author> </Author>
  <LastAuthor> </LastAuthor>
  <Created> </Created>
  <LastSaved> </LastSaved>
  <Version> </Version>
 </DocumentProperties>
 
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>7995</WindowHeight>
  <WindowWidth>14040</WindowWidth>
  <WindowTopX>120</WindowTopX>
  <WindowTopY>75</WindowTopY>
  <ActiveSheet>3</ActiveSheet>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="m46412672">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="m46412976">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="m53908352">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="m53908208">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="m45936352">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Roman" ss:Size="12" ss:Color="#000000"/>
   <Interior ss:Color="#FFFF00" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m46412528">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#FFFFFF" ss:Bold="1"/>
   <Interior ss:Color="#4F81BD" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m52042144">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m52042224">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m52042264">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m52042304">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m52041328">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#FFFFFF" ss:Bold="1"/>
   <Interior ss:Color="#4F81BD" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m46412224">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m46412264">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m46412304">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="m52041024">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s62">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s63">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Font ss:FontName="GE Inspira" x:Family="Roman" ss:Size="12" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s64">
   <Alignment ss:Vertical="Center"/>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s65">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Size="14" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s66">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="GE Inspira" x:Family="Roman" ss:Size="12" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s67">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Roman" ss:Size="12" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s68">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Roman" ss:Size="12" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s69">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s70">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#FFFF00" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s71">
   <Alignment ss:Vertical="Center"/>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000" ss:Bold="1" ss:Underline="Single"/>
  </Style>
  <Style ss:ID="s72">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000" ss:Bold="1"/>
  </Style>
  <Style ss:ID="s73">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2" ss:Color="#000000"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000" ss:Bold="1"/>
  </Style>
  <Style ss:ID="s81">
   <Alignment ss:Vertical="Center"/>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Size="14" ss:Color="#000000" ss:Bold="1"/>
  </Style>
  <Style ss:ID="s82">
   <Alignment ss:Vertical="Center"/>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#FFFF00" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s84">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
  </Style>
  <Style ss:ID="s86">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="Arial Unicode MS" x:Family="Swiss" ss:Color="#000000" ss:Bold = "1"/>
  </Style>
  <Style ss:ID="s88">
   <Borders>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
  </Style>
  <Style ss:ID="s92">
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
  </Style>
  <Style ss:ID="s93">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#FFFFFF"/>
   <Interior ss:Color="#0000ff" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s94">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#FFFFFF"/>
   <Interior ss:Color="#4F81BD" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s95">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#FFFFFF" ss:Bold="1"/>
   <Interior ss:Color="#4F81BD" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s103">
   <Alignment ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
  </Style>
  <Style ss:ID="s105">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="Arial Unicode MS" x:Family="Swiss" ss:Color="#000000" ss:Bold = "1"/>
   <Interior ss:Color="#00FF00" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s106">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s112">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s113">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
   <Interior ss:Color="#95B3D7" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s115">
   <Borders>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
  </Style>
  <Style ss:ID="s116">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s123">
   <Alignment ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="2"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="2"/>
   </Borders>
   <Font ss:FontName="GE Inspira" x:Family="Swiss" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s126">
   <Alignment ss:Vertical="Center"/>
   <Font ss:FontName="GE Inspira" x:Family="Roman" ss:Size="12" ss:Color="#000000"/>
  </Style>
 </Styles>
 
 <Worksheet ss:Name="Supplier">
  <Table ss:ExpandedColumnCount="8" ss:ExpandedRowCount="30000" x:FullColumns="1" x:FullRows="1" ss:DefaultRowHeight="15">
   <Column ss:AutoFitWidth="0" ss:Width="120"/>
   <Column ss:AutoFitWidth="0" ss:Width="120"/>
   <Column ss:AutoFitWidth="0" ss:Width="120"/>
   <Column ss:AutoFitWidth="0" ss:Width="96.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="96.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="96.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="120"/>
   <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s64"/>
   </Row>
   <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s64"/>
   </Row>
  
   <Row ss:AutoFitHeight="0">
 <!--    <Cell ss:StyleID="s63"/>
   <Cell ss:StyleID="s63"/> -->
    <Cell ss:StyleID="s86"><Data ss:Type="String">Opportunity and Quote Info</Data></Cell>
  
   </Row>
   
   <!-- Setting Two Empty Rows before Table Start -->
   <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s64"/>
   </Row>
   <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s64"/>
   </Row>
   <!-- End of Inserting -->
   <Row ss:AutoFitHeight="0"> 
		<Cell ss:StyleID="s105"><Data ss:Type="String">Transcation Name</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Transcation ID </Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Prepared By</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Revision Number</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Version Number</Data></Cell>
	</Row>
	<Row ss:AutoFitHeight="0">
		<Cell ss:StyleID="s93"><Data ss:Type="String"> <xsl:value-of select = "$main_doc/transactionName_t"/></Data></Cell>
		<Cell ss:StyleID="s93"><Data ss:Type="String"><xsl:value-of select = "$main_doc/transactionID_t"/> </Data></Cell>
		<Cell ss:StyleID="s93"><Data ss:Type="String"><xsl:value-of select = "$main_doc/owner_t"/> </Data></Cell>
		<Cell ss:StyleID="s93"><Data ss:Type="String"><xsl:value-of select = "$main_doc/version_t"/></Data></Cell>
		<Cell ss:StyleID="s93"><Data ss:Type="String"><xsl:value-of select = "$main_doc/version_attributes_versionTransaction_t/version_number_versionTransaction_t"/></Data></Cell>
	</Row>
  </Table>
    
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Data="&amp;R&amp;&quot;Arial,Bold&quot;&amp;10GE MSAT Internal"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>13</ActiveRow>
     <ActiveCol>3</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
 
 <Worksheet ss:Name="lineItem">
 <Table ss:ExpandedColumnCount="10" ss:ExpandedRowCount="30000" x:FullColumns="1" x:FullRows="1" ss:DefaultRowHeight="15">
   <Column ss:AutoFitWidth="0" ss:Width="120"/>
   <Column ss:AutoFitWidth="0" ss:Width="80"/>
   <Column ss:AutoFitWidth="0" ss:Width="80"/>
   <Column ss:AutoFitWidth="0" ss:Width="40"/>
   <Column ss:AutoFitWidth="0" ss:Width="30"/>
   <Column ss:AutoFitWidth="0" ss:Width="80"/>
   <Column ss:AutoFitWidth="0" ss:Width="80"/>
   <Column ss:AutoFitWidth="0" ss:Width="80"/>
   <Column ss:AutoFitWidth="0" ss:Width="80"/>
   <Column ss:AutoFitWidth="0" ss:Width="80"/>
   <Column ss:AutoFitWidth="160" ss:Width="160"/>
   
  	<!-- Setting Two Empty Rows before Line Item Info and two after the heading-->
	<Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s64"/>
    </Row>
    <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s64"/>
    </Row>
	
	<Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s86"><Data ss:Type="String">Line Item Info</Data></Cell>
    </Row>
   
    <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s64"/>
    </Row>
    <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s64"/>
    </Row>
	<!-- End of Inserting -->
	
	<Row ss:AutoFitHeight="0"> 
		<Cell ss:StyleID="s105"><Data ss:Type="String">Item</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">List Price </Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Fee Type</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Family</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Qty</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Net Price</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Net Amount</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Buy Price</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Ext Buy Price</Data></Cell>
		<Cell ss:StyleID="s105"><Data ss:Type="String">Total Contract Value</Data></Cell>
	</Row>

	<xsl:for-each select = "$allLineItemloop">
	    <xsl:variable name = "position" select = "position()"/>
		<Row ss:AutoFitHeight="0"> 
			<Cell ss:StyleID="s93"><Data ss:Type="String">
									<xsl:if test = "(./model_l/_model_name) != ''">
										<xsl:value-of select = "./model_l/_model_name"/>
									</xsl:if>
									<xsl:if test = "(./item_l/_part_desc) != ''">
										<xsl:value-of select = "./item_l/_part_desc"/>
									</xsl:if>
			</Data></Cell>
			<Cell ss:StyleID="s93"><Data ss:Type="String">$<xsl:value-of select = "./listPrice_l"/> </Data></Cell>
			<Cell ss:StyleID="s93"><Data ss:Type="String"><xsl:value-of select = "./priceType_l"/> </Data></Cell>
			<Cell ss:StyleID="s93"><Data ss:Type="String"><xsl:value-of select = "./family_l"/></Data></Cell>
			<Cell ss:StyleID="s93"><Data ss:Type="String">$<xsl:value-of select = "./price/_price_quantity"/></Data></Cell>
			<Cell ss:StyleID="s93"><Data ss:Type="String">$<xsl:value-of select = "./netPrice_l"/></Data></Cell>
			<Cell ss:StyleID="s93"><Data ss:Type="String">$<xsl:value-of select = "./netAmount_l"/></Data></Cell>
			<Cell ss:StyleID="s93"><Data ss:Type="String">$<xsl:value-of select = "./buyPrice_l"/> </Data></Cell>
			<Cell ss:StyleID="s93"><Data ss:Type="String">$<xsl:value-of select = "./buyExtPrice_l"/></Data></Cell>
			<Cell ss:StyleID="s93"><Data ss:Type="String">$<xsl:value-of select = "./contractValue_l"/></Data></Cell>
			
		</Row>	
	</xsl:for-each>
 </Table>
 </Worksheet>
 </Workbook>
</xsl:template>
</xsl:stylesheet>