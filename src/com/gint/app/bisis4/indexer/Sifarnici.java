package com.gint.app.bisis4.indexer;

/**
 * 
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;

/**
 * @author dimicb
 *
 */
public class Sifarnici {
  
  public static void main(String[] args){
	  
	if (args.length != 1) {
		System.out.println("Enter path to records.dat file as argument.");
		System.exit(0);
	}
    
    String line = "";
    List<String> sif_SigFormat = new ArrayList<String>();
    List<String> sif_NacinNabavke = new ArrayList<String>();
    List<String> sif_Status = new ArrayList<String>();
    List<String> sif_Povez= new ArrayList<String>();
    List<String> sif_Odeljenja = new ArrayList<String>();
    List<String> sif_Dostupnost = new ArrayList<String>();
    List<String> sif_IntOznaka = new ArrayList<String>();
    List<String> sif_Podlokacija = new ArrayList<String>();
    List<String> sif_InvKnjiga = new ArrayList<String>();    
    
    List<String> invBrojevi = new ArrayList<String>();
    
    try {
      
      //output
      File out = new File("bisis_coders.txt");
      FileOutputStream outstream = new FileOutputStream(out,true);;
      OutputStreamWriter buff = new OutputStreamWriter(outstream,"UTF-8");
      
      //output
      File outsql = new File("bisis_coders.sql");
      FileOutputStream outstreamsql = new FileOutputStream(outsql,true);;
      OutputStreamWriter buffsql = new OutputStreamWriter(outstreamsql,"UTF-8");
      //input
     RandomAccessFile in = new RandomAccessFile(args[0], "r");
      int brojac = 0;
      while ((line = in.readLine()) != null) {
       try{
        brojac++;
        if(brojac % 1000 == 0)
          System.out.println(brojac);
        line = line.trim();
        if ("".equals(line))
          continue;
        Record rec = RecordFactory.fromUNIMARC(0, line);
        for(Primerak p:rec.getPrimerci()){      
         if(!invBrojevi.contains(p.getInvBroj()))
        	 invBrojevi.add(p.getInvBroj());
         else
        	 System.out.println("Dupli inventarni broj "+p.getInvBroj());
        	
        	
          if(p.getSigFormat()!=null && !sif_SigFormat.contains(p.getSigFormat())){
            sif_SigFormat.add(p.getSigFormat());            
          }
          if(p.getNacinNabavke()!=null && !sif_NacinNabavke.contains(p.getNacinNabavke())){
            sif_NacinNabavke.add(p.getNacinNabavke());            
          }
          if(p.getSigFormat()!=null && !sif_SigFormat.contains(p.getSigFormat())){
            sif_SigFormat.add(p.getSigFormat());            
          }
          if(p.getStatus()!=null && !sif_Status.contains(p.getStatus())){
            sif_Status.add(p.getStatus());            
          }
          if(p.getPovez()!=null && !sif_Povez.contains(p.getPovez())){
            sif_Povez.add(p.getPovez());            
          }
          if(p.getOdeljenje()!=null && !sif_Odeljenja.contains(p.getOdeljenje())){
            sif_Odeljenja.add(p.getOdeljenje());            
          }
          if(p.getDostupnost()!=null && !sif_Dostupnost.contains(p.getDostupnost())){
            sif_Dostupnost.add(p.getDostupnost());            
          }
          if(p.getSigIntOznaka()!=null && !sif_IntOznaka.contains(p.getSigIntOznaka())){
            sif_IntOznaka.add(p.getSigIntOznaka());            
          }
          if(p.getSigPodlokacija()!=null && !sif_Podlokacija.contains(p.getSigPodlokacija())){
            sif_Podlokacija.add(p.getSigPodlokacija());            
          }
          //inventarna knjiga
          if(returnInvKnj(p)!=null && !sif_InvKnjiga.contains((returnInvKnj(p)))){
        	  sif_InvKnjiga.add(returnInvKnj(p));
        	  //System.out.println(returnInvKnj(p));
          }          
        }
        
        for(Godina g:rec.getGodine()){          
          if(g.getSigFormat()!=null && !sif_SigFormat.contains(g.getSigFormat())){
            sif_SigFormat.add(g.getSigFormat());            
          }
          if(g.getNacinNabavke()!=null && !sif_NacinNabavke.contains(g.getNacinNabavke())){
            sif_NacinNabavke.add(g.getSigFormat());            
          }
          if(g.getPovez()!=null && !sif_Povez.contains(g.getPovez())){
            sif_Povez.add(g.getPovez());            
          }
          if(g.getOdeljenje()!=null && !sif_Odeljenja.contains(g.getOdeljenje())){
            sif_Odeljenja.add(g.getOdeljenje());            
          }
          if(g.getDostupnost()!=null && !sif_Dostupnost.contains(g.getDostupnost())){
            sif_Dostupnost.add(g.getDostupnost());            
          }
          if(g.getSigIntOznaka()!=null && !sif_IntOznaka.contains(g.getSigIntOznaka())){
            sif_IntOznaka.add(g.getSigIntOznaka());            
          }
          if(g.getSigPodlokacija()!=null && !sif_Podlokacija.contains(g.getSigPodlokacija())){
            sif_Podlokacija.add(g.getSigPodlokacija());            
          }
          if(returnInvKnj(g)!=null && !sif_InvKnjiga.contains((returnInvKnj(g)))){
        	  sif_InvKnjiga.add(returnInvKnj(g));
          }
        }  
       }catch(Exception e){
        	e.printStackTrace();
        }
      }
      
      buff.append("Sifarnici za sigFormat:\n");      
      for(String sifra:sif_SigFormat){
      	if(sifra!=null){
      		buff.append(sifra+"\n");
      		buffsql.append("INSERT INTO SigFormat (SigFormat_ID, Format_Opis) VALUES ('+"+sifra+"', '');"+"\n");
      	}
      }
      buff.append("Sifarnici za Nacin nabavke:\n");      
      for(String sifra:sif_NacinNabavke){
      	if(sifra!=null){
      		buff.append(sifra+"\n");
      		buffsql.append("INSERT INTO Nacin_nabavke (Nacin_ID, Nacin_Opis) VALUES ('"+sifra+"', '');"+"\n");
      	}
      }
      buff.append("Sifarnici za Status:\n");      
      for(String sifra:sif_Status){
      	if(sifra!=null){
        buff.append(sifra+"\n");
        buffsql.append("INSERT INTO Status_Primerka (Status_ID, Status_Opis, Zaduziv) VALUES ('"+sifra+"', '', 0);"+"\n");
      	}
      }
      buff.append("Sifarnici za Povez:\n");      
      for(String sifra:sif_Povez){
      	if(sifra!=null){
        buff.append(sifra+"\n");
        buffsql.append("INSERT INTO Povez (Povez_ID, Povez_Opis) VALUES ('"+sifra+"', '');"+"\n");
      	}
      }
      buff.append("Sifarnici za Odeljenja:\n");      
      for(String sifra:sif_Odeljenja){
      	if(sifra!=null){      		
        buff.append(sifra+"\n");
        buffsql.append("INSERT INTO Odeljenje (Odeljenje_ID, Odeljenje_Naziv) VALUES ('"+sifra+"', '');"+"\n");
      	}
      }
      buff.append("Sifarnici za Dostupnost:\n");      
      for(String sifra:sif_Dostupnost){
      	if(sifra!=null){
        buff.append(sifra+"\n");
        buffsql.append("INSERT INTO Dostupnost (Dostupnost_ID, Dostupnost_Opis) VALUES ('"+sifra+"', '');"+"\n");
      	}
      }
      buff.append("Sifarnici za Internu oznaku:\n");      
      for(String sifra:sif_IntOznaka){
      	if(sifra!=null){
      		buff.append(sifra+"\n");
      		buffsql.append("INSERT INTO Interna_oznaka (IntOzn_ID, IntOzn_Opis) VALUES ('"+sifra+"', '');"+"\n");
      	}
      }
      buff.append("Sifarnici za Podlokaciju:\n");      
      for(String sifra:sif_Podlokacija){
      	if(sifra!=null){
        buff.append(sifra+"\n");
        buffsql.append("INSERT INTO Podlokacija (Podlokacija_ID, Podlokacija_Opis) VALUES ('"+sifra+"', '');"+"\n");
      	}
      }
      buff.append("Sifarnici za Inventarnu knigu:\n");      
      for(String sifra:sif_InvKnjiga){
      	if(sifra!=null){
        buff.append(sifra+"\n");
        buffsql.append("INSERT INTO Invknj (invknj_id, invknj_opis) VALUES ('"+sifra+"','');"+"\n");
      	}
      }
      buff.close();    
      buffsql.close();
      
    } catch (Exception e) {     
      e.printStackTrace();
    }    
  }
  
  public static String returnInvKnj(Object o){
	  try{
		  if(o instanceof Primerak){
			  Primerak p = (Primerak)o;			 
			  return p.getInvBroj().substring(2,4);
		  }else if (o instanceof Godina){
			  Godina g = (Godina)o;
			  return g.getInvBroj().substring(2,4);
		  }
	  }catch(Exception e){
		  return null;
	  }
	  return null;
	  
  }
  
  
  

}
