package com.gint.app.bisis4.textsrv.fulltext;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.pdfbox.exceptions.CryptographyException;
import org.pdfbox.exceptions.InvalidPasswordException;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

/**
 * Builds Lucene documents from PDF files.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class PdfDocBuilder implements DocBuilder {

  /**
   * Builds a Lucene document based on the given file.
   * 
   * @param file The file to be processed
   * @return The built Lucene document
   * @throws IOException
   */
  public Document getDoc(File file, int fileId) throws IOException {
    Document doc = new Document();
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file)); 
    PDDocument pdfDoc = PDDocument.load(in);
    if (pdfDoc.isEncrypted())
      try {
        pdfDoc.decrypt(""); // probamo praznu lozinku za svaki slucaj
      } catch (InvalidPasswordException ex) {
        log.warn(ex);
      } catch (CryptographyException ex) {
        log.warn(ex);
      }
    
    StringWriter sw = new StringWriter();
    if (stripper == null)
      stripper = new PDFTextStripper();
    else
      stripper.resetEngine();
    stripper.writeText(pdfDoc, sw);
    
    doc.add(new Field("FT",sw.getBuffer().toString(), 
        Field.Store.NO, Field.Index.TOKENIZED, 
        Field.TermVector.WITH_POSITIONS_OFFSETS));
    doc.add(new Field("FILEID", Integer.toString(fileId), 
        Field.Store.YES, Field.Index.UN_TOKENIZED));
    
    return doc;
  }
  
  private PDFTextStripper stripper;
  private static Log log = LogFactory.getLog(PdfDocBuilder.class);
}
