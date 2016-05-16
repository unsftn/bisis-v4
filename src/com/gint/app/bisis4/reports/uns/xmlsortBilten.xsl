<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
	<xsl:template match="report">
		<report>
			<xsl:for-each select="item">
			<xsl:sort select="opis"/>
		    <item>
			  <opis><xsl:value-of select="opis"/></opis>
			  <signatura><xsl:value-of select="signatura"/></signatura>
			</item>
				
			</xsl:for-each>
		</report>
	</xsl:template>
</xsl:stylesheet>


