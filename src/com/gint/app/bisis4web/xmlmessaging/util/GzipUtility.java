/*
 * Created on 2004.11.11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gint.app.bisis4web.xmlmessaging.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GzipUtility {
	public static int sChunk = 8192;
	  
	
	public static byte[] zipText(String text){
		byte []ba=null;
		GZIPOutputStream gzos=null;
		ByteArrayOutputStream baos=null;
		try{
			baos=new ByteArrayOutputStream();
			if(baos!=null){
				gzos=new GZIPOutputStream(baos);
				byte[] stringAsBytes=(text).getBytes("UTF-8");
				gzos.write(stringAsBytes,0,stringAsBytes.length);
				gzos.finish();
				baos.close();
				ba=baos.toByteArray();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(baos!=null){
				try{
					gzos.close();
					//baos.close();
				}catch(Exception e1){
					System.out.println("Exception while closing output stream");
				}
			}
		}
		//System.out.println("Zipped\n"+baos.toString());
		return ba;
	}
	
	public static String unzipText(byte[] text){
		GZIPInputStream gzis=null;
		ByteArrayInputStream bios=null;
		StringBuffer sb=new StringBuffer();
		try{
			bios=new ByteArrayInputStream(text);
			if(bios!=null){
				gzis=new GZIPInputStream(bios);
				byte[] buffer = new byte[sChunk];
			    // Decompress the file.
			      int length;
			      while ((length = gzis.read(buffer, 0, sChunk)) != -1){
			      	System.out.println("length: "+length);
			      	sb.append(new String(buffer,"UTF-8"));
			      }
			        
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bios!=null){
				try{
					gzis.close();
					bios.close();
				}catch(Exception e1){
					System.out.println("Exception while closing input stream");
				}
			}
		}
		return sb.toString();
	}
	
	public static String unzipFromStream(InputStream in){
		GZIPInputStream gzis=null;
		ByteArrayInputStream bios=null;
		StringBuffer sb=new StringBuffer();
		try{
			if(in!=null){
				gzis=new GZIPInputStream(in);
				byte[] buffer = new byte[sChunk];
			    // Decompress the file.
			      int length;
			      while ((length = gzis.read(buffer, 0, sChunk)) != -1){
			      	System.out.println("length: "+length);
			      	byte []read=new byte[length];
			      	for(int i=0;i<length;i++)
			      		read[i]=buffer[i];
			      	sb.append(new String(read,"UTF-8"));
			      }
			        
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bios!=null){
				try{
					gzis.close();
					bios.close();
				}catch(Exception e1){
					System.out.println("Exception while closing input stream");
				}
			}
		}
		return sb.toString();
	}
}
