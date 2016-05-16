<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
	<xsl:template match="report">
		<report>
			<xsl:for-each select="row">
			<xsl:sort select="column2"/>
			<row>
			   <column1><xsl:value-of select="column1"/></column1>
			  <column2><xsl:value-of select="column2"/></column2>
			</row>
				
			</xsl:for-each>
		</report>
	</xsl:template>
</xsl:stylesheet>
