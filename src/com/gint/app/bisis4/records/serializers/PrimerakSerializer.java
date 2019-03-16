package com.gint.app.bisis4.records.serializers;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.format.PubTypes;
import com.gint.app.bisis4.format.UField;
import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.records.Subsubfield;

public class PrimerakSerializer {

  public static Field getField(Primerak p) {
    Field f = new Field("996");
    UField uf = PubTypes.getFormat().getField("996");
    String ind1DefVal = uf.getInd1().getDefaultValue();
    if (isEmpty(ind1DefVal))
      ind1DefVal = " ";
    String ind2DefVal = uf.getInd2().getDefaultValue();
    if (isEmpty(ind2DefVal))
      ind2DefVal = " ";
    f.setInd1(ind1DefVal.charAt(0));
    f.setInd2(ind2DefVal.charAt(0));
    
    if (!isEmpty(p.getDatumRacuna()) || !isEmpty(p.getBrojRacuna())) {
      Subfield sf1 = new Subfield('1');
      if (!isEmpty(p.getBrojRacuna()))
        addSubsubfield(sf1, 'm', p.getBrojRacuna());
      if (!isEmpty(p.getDatumRacuna()))
        addSubsubfield(sf1, 'q', dateFormat.format(p.getDatumRacuna()));
      f.add(sf1);
    }
    
    if (!isEmpty(p.getDobavljac()))
      addSubfield(f, '2', p.getDobavljac());
    
    if (!isEmpty(p.getCena()))
      addSubfield(f, '3', p.getCena().toPlainString());
    
    if (!isEmpty(p.getFinansijer()))
      addSubfield(f, '4', p.getFinansijer());
    
    if (!isEmpty(p.getUsmeravanje()))
      addSubfield(f, '5', p.getUsmeravanje());
    
    Subfield sfd = new Subfield('d');
    f.add(sfd);
    if (!isEmpty(p.getSigDublet()))
      addSubsubfield(sfd, 'd', p.getSigDublet());
    if (!isEmpty(p.getSigFormat()))
      addSubsubfield(sfd, 'f', p.getSigFormat());
    if (!isEmpty(p.getSigIntOznaka()))
      addSubsubfield(sfd, 'i', p.getSigIntOznaka());
    if (!isEmpty(p.getSigPodlokacija()))
      addSubsubfield(sfd, 'l', p.getSigPodlokacija());
    if (!isEmpty(p.getSigNumerusCurens()))
      addSubsubfield(sfd, 'n', p.getSigNumerusCurens());
    if (!isEmpty(p.getSigUDK()))
      addSubsubfield(sfd, 'u', p.getSigUDK());
    
    if (!isEmpty(p.getInvBroj()))
      addSubfield(f, 'f', p.getInvBroj());
    
    if (!isEmpty(p.getDatumInventarisanja()))
      addSubfield(f, 'o', dateFormat.format(p.getDatumInventarisanja()));
    
    if (!isEmpty(p.getStatus()))
      addSubfield(f, 'q', p.getStatus());
    
    if (!isEmpty(p.getNapomene()))
      addSubfield(f, 'r', p.getNapomene());

    if (!isEmpty(p.getPovez()))
      addSubfield(f, 's', p.getPovez());
    
    if (!isEmpty(p.getNacinNabavke()))
      addSubfield(f, 'v', p.getNacinNabavke());

    if (!isEmpty(p.getOdeljenje()))
      addSubfield(f, 'w', p.getOdeljenje());
    
    if(!isEmpty(p.getDostupnost()))
      addSubfield(f, 'p', p.getDostupnost());
    
    if(!isEmpty(p.getDatumStatusa()))
    	addSubfield(f, 't', dateFormat.format(p.getDatumStatusa()));
    
    //dodato zbog mobilne aplikacije	
    addSubfield(f, '9', String.valueOf(p.getStanje()));

    return f;
  }
  
  public static Primerak getPrimerak(Field f) {  	
    if (!f.getName().equals("996"))
      return null;
    Primerak p = new Primerak();
    boolean toLog = false;
    p.setBrojRacuna(getSubsubfieldContent(f, '1', 'm'));
    try {
      String s = getSubsubfieldContent(f, '1', 'q');
      if (s != null)
        p.setDatumRacuna(dateFormat.parse(s));
    } catch (ParseException ex) {
      log.warn("Neispravan datum racuna: " + 
          getSubsubfieldContent(f, '1', 'q'));
      toLog = true;
    }
    p.setDobavljac(getSubfieldContent(f, '2'));
    String cena = getSubfieldContent(f, '3');
    try {
      if (cena != null) {
        cena = cena.replace(',', '.');
        BigDecimal num = new BigDecimal(cena);
        if (num.precision() <= 10 && 0 <= num.scale() && num.scale() <= 5){
          p.setCena(num);
        }else{
          p.setCena(null);
          log.warn("Neispravna cena: " + cena);
          toLog = true;
        }
//        if(cena.contains("E") || cena.contains("e")) throw new NumberFormatException();
      }
    } catch (NumberFormatException ex) {
      p.setCena(null);
      log.warn("Neispravna cena(exception) : " + cena);
      toLog = true;
    }
    
    String fdi = getSubsubfieldContent(f, 'd', 'i');
    String fdl = getSubsubfieldContent(f, 'd', 'l');
//    
    if (fdi != null && fdi != null)
    	System.out.println("ima oba za ");
//    
    if (fdi != null ) {
    	p.setSigIntOznaka(fdi);
    }
    if (fdl != null )
    	p.setSigIntOznaka(fdl);
    
    p.setFinansijer(getSubfieldContent(f, '4'));
    p.setUsmeravanje(getSubfieldContent(f, '5'));
    p.setSigDublet(getSubsubfieldContent(f, 'd', 'd'));
    p.setSigFormat(getSubsubfieldContent(f, 'd', 'f'));   
    // p.setSigIntOznaka(getSubsubfieldContent(f, 'd', 'i'));
    p.setSigPodlokacija(mapSublocation(getSubfieldContent(f, 'w')));
    p.setSigNumerusCurens(getSubsubfieldContent(f, 'd', 'n'));
    p.setSigUDK(getSubsubfieldContent(f, 'd', 'u'));
    p.setInvBroj(getSubfieldContent(f, 'f'));
    String s = getSubfieldContent(f, 'o');
    if (s != null)
      try {
        p.setDatumInventarisanja(dateFormat.parse(s));
      } catch (ParseException ex) {
        try {
          p.setDatumInventarisanja(dateFormat2.parse(s));
        } catch (ParseException ex2) {
          log.warn("Neispravan datum inventarisanja: " + 
              getSubfieldContent(f, 'o'));
          toLog = true;
        }
      }
    p.setStatus(getSubfieldContent(f, 'q'));
    p.setNapomene(getSubfieldContent(f, 'r'));
    p.setPovez(getSubfieldContent(f, 's'));
    p.setNacinNabavke(getSubfieldContent(f, 'v'));
    p.setOdeljenje(mapSublocation(getSubfieldContent(f, 'w')).substring(0, 2));
    p.setDostupnost(getSubfieldContent(f, 'p'));
    try{
    if (getSubfieldContent(f, '9') != null){
    	p.setStanje(Integer.parseInt(getSubfieldContent(f, '9')));
    }else{
    	p.setStanje(0);
    }
    }catch(NumberFormatException ex){
     log.warn("Neispravno stanje: " + 
     		getSubfieldContent(f, '9'));
     toLog = true;
     p.setStanje(0);
    }
    try {
      String ds = getSubfieldContent(f, 't');
      if (ds != null){
        p.setDatumStatusa(dateFormat.parse(ds));
      }else {
    	  p.setDatumStatusa(null);
      }
    } catch (ParseException ex) {
      log.warn("Neispravan datum statusa: " + 
      		getSubfieldContent(f, 't'));
      toLog = true;
      p.setDatumStatusa(null);
    }
    p.setVersion(0);
    if (toLog){
      log.warn("Primerak: "+ p.toString());
    }
    return p;
  }
  
  public static Field getField(Godina g) {
    Field f = new Field("997");
    UField uf = PubTypes.getFormat().getField("997");
    String ind1DefVal = uf.getInd1().getDefaultValue();
    if (isEmpty(ind1DefVal))
      ind1DefVal = " ";
    String ind2DefVal = uf.getInd2().getDefaultValue();
    if (isEmpty(ind2DefVal))
      ind2DefVal = " ";
    f.setInd1(ind1DefVal.charAt(0));
    f.setInd2(ind2DefVal.charAt(0));
    
    if (!isEmpty(g.getDatumRacuna()) || !isEmpty(g.getBrojRacuna())) {
      Subfield sf1 = new Subfield('1');
      if (!isEmpty(g.getBrojRacuna()))
        addSubsubfield(sf1, 'm', g.getBrojRacuna());
      if (!isEmpty(g.getDatumRacuna()))
        addSubsubfield(sf1, 'q', dateFormat.format(g.getDatumRacuna()));
      f.add(sf1);
    }
    
    if (!isEmpty(g.getDobavljac()))
      addSubfield(f, '2', g.getDobavljac());
    
    if (!isEmpty(g.getCena()))
      addSubfield(f, '3', g.getCena().toPlainString());
    
    if (!isEmpty(g.getFinansijer()))
      addSubfield(f, '4', g.getFinansijer());
    
    Subfield sfd = new Subfield('d');
    f.add(sfd);
    if (!isEmpty(g.getSigDublet()))
      addSubsubfield(sfd, 'd', g.getSigDublet());
    if (!isEmpty(g.getSigFormat()))
      addSubsubfield(sfd, 'f', g.getSigFormat());
    if (!isEmpty(g.getSigIntOznaka()))
      addSubsubfield(sfd, 'i', g.getSigIntOznaka());
    if (!isEmpty(g.getSigPodlokacija()))
      addSubsubfield(sfd, 'l', g.getSigPodlokacija());
    if (!isEmpty(g.getSigNumerusCurens()))
      addSubsubfield(sfd, 'n', g.getSigNumerusCurens());
    if (!isEmpty(g.getSigNumeracija()))
      addSubsubfield(sfd, 's', g.getSigNumeracija());
    if (!isEmpty(g.getSigUDK()))
      addSubsubfield(sfd, 'u', g.getSigUDK());
    
    if (!isEmpty(g.getInvBroj()))
      addSubfield(f, 'f', g.getInvBroj());
    
    if (!isEmpty(g.getGodiste()))
      addSubfield(f, 'j', g.getGodiste());
    
    if (!isEmpty(g.getGodina()))
      addSubfield(f, 'k', g.getGodina());
    
    if (!isEmpty(g.getBroj()))
      addSubfield(f, 'm', g.getBroj());
    
    if (!isEmpty(g.getDatumInventarisanja()))
      addSubfield(f, 'o', dateFormat.format(g.getDatumInventarisanja()));
    
    if (!isEmpty(g.getNapomene()))
      addSubfield(f, 'r', g.getNapomene());

    if (!isEmpty(g.getPovez()))
      addSubfield(f, 's', g.getPovez());
    
    if (!isEmpty(g.getNacinNabavke()))
      addSubfield(f, 'v', g.getNacinNabavke());

    if (!isEmpty(g.getOdeljenje()))
      addSubfield(f, 'w', g.getOdeljenje());
    
    if (!isEmpty(g.getDostupnost()))
      addSubfield(f, 'p', g.getDostupnost());

    return f;
  }
  
  //pri svakom prepisu dopuniti mapu
  public static String mapSublocation(String oldW) {
	  
	  if (oldW == null)
		  return libDep;
	  
	  Map<String, String> sublocationMap = new HashMap<>();
	  
	  if (libDep.startsWith("01")) {
	  // Periodika - 01 jedini slucaj da prepisujemo iz BISIS3!
	  sublocationMap.put("01", "0105");
	  }
	  
	  if (libDep.startsWith("02")) {
	  // Vozdovac - 02
	  sublocationMap.put("01", "0201");
	  sublocationMap.put("02", "0202");
	  sublocationMap.put("03", "0203");
	  sublocationMap.put("04", "0204");
	  sublocationMap.put("05", "0205");
	  sublocationMap.put("06", "0206");
	  }
	  
	  if (libDep.startsWith("03")) {
	  // Vracar - 03
	  sublocationMap.put("020", "0320");
	  sublocationMap.put("021", "0321");
	  sublocationMap.put("022", "0322");
	  sublocationMap.put("023", "0323");
	  sublocationMap.put("024", "0324");
	  }

	  if (libDep.startsWith("04")) {
	  // Zvezdara - 04 
	  sublocationMap.put("025", "0425");
	  sublocationMap.put("026", "0426");
	  sublocationMap.put("027", "0427");
	  sublocationMap.put("028", "0428");
	  sublocationMap.put("029", "0429");
	  sublocationMap.put("030", "0430");
	  sublocationMap.put("031", "0431");
	  sublocationMap.put("032", "0432");
	  }
	  
	  if (libDep.startsWith("05")) {
	  // Cukarica - 05
	  sublocationMap.put("01", "0501");
	  sublocationMap.put("02", "0502");
	  sublocationMap.put("03", "0503");
	  sublocationMap.put("04", "0504");
	  sublocationMap.put("05", "0505");
	  sublocationMap.put("06", "0506");
	  sublocationMap.put("07", "0507");
	  sublocationMap.put("08", "0508");
	  sublocationMap.put("09", "0509");
	  sublocationMap.put("10", "0510");
	  }
	  
	  if (libDep.startsWith("06")) {
	  // Rakovica - 06
	  sublocationMap.put("01", "0601");
	  sublocationMap.put("02", "0602");
	  sublocationMap.put("03", "0603");
	  sublocationMap.put("04", "0604");
	  sublocationMap.put("05", "0605");
	  sublocationMap.put("06", "0606");
	  }
	  
	  if (libDep.startsWith("07")) {
	  // Barajevo - 07
	  sublocationMap.put("01", "0701");
	  sublocationMap.put("02", "0702");
	  }
	  
	  if (libDep.startsWith("08")) {
	  // Mladenovac - 08
	  sublocationMap.put("21", "0821");
	  sublocationMap.put("20", "0820");
	  sublocationMap.put("55", "0855");
	  sublocationMap.put("51", "0851");
	  sublocationMap.put("30", "0830");
	  }
	  
	  if (libDep.startsWith("09")) {
	  // Savski venac - 09
	  sublocationMap.put("090", "0990");
	  sublocationMap.put("091", "0991");
	  sublocationMap.put("092", "0992");
	  sublocationMap.put("093", "0993");
	  sublocationMap.put("094", "0994");
	  sublocationMap.put("095", "0995");
	  sublocationMap.put("096", "0996");
	  sublocationMap.put("097", "0997");
	  sublocationMap.put("098", "0998");
	  sublocationMap.put("099", "0999");
	  }
	  
	  if (libDep.startsWith("10")) {
	  // Stari grad - 10
	  sublocationMap.put("10", "1010");
	  sublocationMap.put("11", "1011");
	  sublocationMap.put("12", "1012");
	  }
	  
	  if (libDep.startsWith("11")) {
	  // Zemun - 11
	  sublocationMap.put("01", "1101");
	  sublocationMap.put("02", "1102");
	  sublocationMap.put("03", "1103");
	  sublocationMap.put("05", "1105");
	  sublocationMap.put("06", "1106");
	  sublocationMap.put("07", "1107");
	  sublocationMap.put("10", "1110");
	  sublocationMap.put("12", "1112");
	  sublocationMap.put("13", "1113");
	  sublocationMap.put("15", "1115");
	  sublocationMap.put("19", "1119");
	  sublocationMap.put("20", "1120");
	  }
	  
	  if (libDep.startsWith("12")) {
	  // Novi Beograd - 12
	  sublocationMap.put("01", "1201");
	  sublocationMap.put("02", "1202");
	  sublocationMap.put("03", "1203");
	  sublocationMap.put("04", "1204");
	  sublocationMap.put("05", "1205");
	  sublocationMap.put("06", "1206");
	  sublocationMap.put("07", "1207");
	  sublocationMap.put("08", "1208");
	  sublocationMap.put("09", "1209");
	  sublocationMap.put("10", "1210");
	  }
	  
	  if (libDep.startsWith("13")) {
	  // Grocka - 13
	  sublocationMap.put("01", "1301");
	  sublocationMap.put("02", "1302");
	  sublocationMap.put("03", "1303");
	  sublocationMap.put("04", "1304");
	  }
	  
	  if (libDep.startsWith("15")) {
	  // Sopot - 15
	  sublocationMap.put("01", "1501");
	  sublocationMap.put("02", "1502");
	  sublocationMap.put("03", "1503");
	  sublocationMap.put("04", "1504");
	  }
	  
	  
	  return sublocationMap.get(oldW) != null ? sublocationMap.get(oldW) : libDep;
  }
  
  public static Godina getGodina(Field f) {  	
    if (!f.getName().equals("997"))
      return null;
    Godina g = new Godina();
    boolean toLog = false;
    g.setBrojRacuna(getSubsubfieldContent(f, '1', 'm'));
    try {
      String s = getSubsubfieldContent(f, '1', 'q');
      if (s != null)
        g.setDatumRacuna(dateFormat.parse(s));
    } catch (ParseException ex) {
      log.warn("Neispravan datum racuna: " + 
          getSubsubfieldContent(f, '1', 'q'));
      toLog = true;
    }
    g.setDobavljac(getSubfieldContent(f, '2'));
    String cena = getSubfieldContent(f, '3');
    try {
      if (cena != null) {
        cena = cena.replace(',', '.');
        BigDecimal num = new BigDecimal(cena);
        if (num.precision() <= 12 && num.scale() <= 2){
          g.setCena(num);
        }else{
          g.setCena(null);
          log.warn("Neispravna cena: " + cena);
          toLog = true;
        }
      }
    } catch (NumberFormatException ex) {
      g.setCena(null);
      log.warn("Neispravna cena: " + cena);
      toLog = true;
    }
    g.setFinansijer(getSubfieldContent(f, '4'));
    g.setSigDublet(getSubsubfieldContent(f, 'd', 'd'));
    g.setSigFormat(getSubsubfieldContent(f, 'd', 'f'));
    g.setSigIntOznaka(getSubsubfieldContent(f, 'd', 'i'));
//    g.setSigPodlokacija(getSubsubfieldContent(f, 'd', 'l'));
    g.setSigPodlokacija(mapSublocation(getSubfieldContent(f, 'w')));
    g.setSigNumerusCurens(getSubsubfieldContent(f, 'd', 'n'));
    g.setSigNumeracija(getSubsubfieldContent(f, 'd', 's'));
    g.setSigUDK(getSubsubfieldContent(f, 'd', 'u'));
    g.setInvBroj(getSubfieldContent(f, 'f'));
    g.setGodiste(getSubfieldContent(f, 'j'));
    g.setGodina(getSubfieldContent(f, 'k'));
    g.setBroj(getSubfieldContent(f, 'm'));
    String s = getSubfieldContent(f, 'o');
    if (s != null)
      try {
        g.setDatumInventarisanja(dateFormat.parse(s));
      } catch (ParseException ex) {
        try {
          g.setDatumInventarisanja(dateFormat2.parse(s));
        } catch (ParseException ex2) {
          log.warn("Neispravan datum inventarisanja: " + 
              getSubfieldContent(f, 'o'));
          toLog = true;
        }
      }
    g.setNapomene(getSubfieldContent(f, 'r'));
    g.setPovez(getSubfieldContent(f, 's'));
    g.setNacinNabavke(getSubfieldContent(f, 'v'));
    g.setOdeljenje(mapSublocation(getSubfieldContent(f, 'w')).substring(0, 2));
    g.setDostupnost(getSubfieldContent(f, 'p'));
    if (toLog){
      log.warn("Godina: "+ g.toString());
    }
    return g;
  }
  
  public static Record primerciUPolja(Record rec) {
    for (Primerak p : rec.getPrimerci())
      rec.add(getField(p));
    rec.getPrimerci().clear();
    return rec;
  }
  
  public static Record poljaUPrimerke(Record rec) {  	
    String lastSigFormat = null;
    String lastSigPodlokacija = null;
    String lastSigIntOznaka = null;
    String lastSigDublet = null;
    String lastSigNumerusCurens = null;
    String lastSigUDK = null;
    Iterator<Field> it = rec.getFields("996").iterator();    
    while (it.hasNext()) {
      Field f = it.next();
      Primerak p = getPrimerak(f);
//      if (p.isSigDefined()) {
//        lastSigFormat = p.getSigFormat();
//        lastSigPodlokacija = p.getSigPodlokacija();
//        lastSigIntOznaka = p.getSigIntOznaka();
//        lastSigDublet = p.getSigDublet();
//        lastSigNumerusCurens = p.getSigNumerusCurens();
//        lastSigUDK = p.getSigUDK();
//      } else {
//        p.setSigFormat(lastSigFormat);
//        p.setSigPodlokacija(lastSigPodlokacija);
//        p.setSigIntOznaka(lastSigIntOznaka);
//        p.setSigDublet(lastSigDublet);
//        p.setSigNumerusCurens(lastSigNumerusCurens);
//        p.setSigUDK(lastSigUDK);
//      }
      rec.getPrimerci().add(p);
      rec.getFields().remove(f);
    }    
    return rec;
  }
  
  public static Record godineUPolja(Record rec) {
    for (Godina g : rec.getGodine())
      rec.add(getField(g));
    rec.getGodine().clear();
    return rec;
  }
  
  public static Record poljaUGodine(Record rec) {
    String lastSigFormat = null;
    String lastSigPodlokacija = null;
    String lastSigIntOznaka = null;
    String lastSigDublet = null;
    String lastSigNumerusCurens = null;
    String lastSigUDK = null;
    String lastSigNumeracija = null;
    Iterator<Field> it = rec.getFields("997").iterator();
    while (it.hasNext()) {
      Field f = it.next();
      Godina g = getGodina(f);
//      if (g.isSigDefined()) {
//        lastSigFormat = g.getSigFormat();
//        lastSigPodlokacija = g.getSigPodlokacija();
//        lastSigIntOznaka = g.getSigIntOznaka();
//        lastSigDublet = g.getSigDublet();
//        lastSigNumerusCurens = g.getSigNumerusCurens();
//        lastSigUDK = g.getSigUDK();
//        lastSigNumeracija = g.getSigNumeracija();
//      } else {
//        g.setSigFormat(lastSigFormat);
//        g.setSigPodlokacija(lastSigPodlokacija);
//        g.setSigIntOznaka(lastSigIntOznaka);
//        g.setSigDublet(lastSigDublet);
//        g.setSigNumerusCurens(lastSigNumerusCurens);
//        g.setSigUDK(lastSigUDK);
//        g.setSigNumeracija(lastSigNumeracija);
//      }
      rec.getGodine().add(g);
      //removeInventarFrom997(f);
      rec.getFields().remove(f);
    }
    
    return rec;
  }
  
  /*
   * iz polja 997 izbacuje inventarne podatke 
   * (inv broj, status, nacin nabavke,...)
   * a ostavlja podatke koji su vezani za obradu
   * (godiste, brojevi sveska, knjige,...) 
   * 
   */
  public static void removeInventarFrom997(Field f){  	
  		f.removeSubfield('1');
  		f.removeSubfield('2');
  		f.removeSubfield('3');
  		f.removeSubfield('4');
  		f.removeSubfield('d');  		
  		f.removeSubfield('f');
  		f.removeSubfield('r');
  		f.removeSubfield('s');
  		f.removeSubfield('v');
  		f.removeSubfield('w');
  		f.removeSubfield('p');
  		f.removeSubfield('o');
  }  
    
  public static Record polje000UMetapodatke(Record rec) {
    Field f = rec.getField("000");
    if (f == null)
      return rec;
    String sfa = rec.getSubfieldContent("000a");
    if (sfa != null) {
      // TODO: treba podrzati sve tipove publikacija
    	
    	// default tip publikacije
    	rec.setPubType(1);
    /*if (sfa.startsWith("001") || sfa.startsWith("002") || sfa.startsWith("003"))
        rec.setPubType(1);*/
      if (sfa.startsWith("004") || sfa.startsWith("005"))
        rec.setPubType(2);
      if (sfa.startsWith("006"))
        rec.setPubType(3);
    }
    String sfb = rec.getSubfieldContent("000b");
    if (sfb != null && sfb.length() == 16) {
      String crDate = sfb.substring(0, 8);
      String modDate = sfb.substring(8);
      try {
        rec.setCreationDate(dateFormat.parse(crDate));
        if (!"00000000".equals(modDate))
          rec.setLastModifiedDate(dateFormat.parse(modDate));
      } catch (ParseException ex) {
        log.warn("Unable to parse dates: " + sfb);
        log.warn(ex);
      }
    }else{
    	rec.setCreationDate(new Date());
    	rec.setLastModifiedDate(new Date());
    }
    String sfc = rec.getSubfieldContent("000c");
    if (sfc != null) {
      if (sfc.indexOf('@') != -1)
        rec.setCreator(new Author(sfc));
      else
        rec.setCreator(new Author(sfc, "unknown"));
    }
    String sfd = rec.getSubfieldContent("000d");
    if (sfd != null) {
      if (sfd.indexOf('@') != -1)
        rec.setModifier(new Author(sfd));
      else
        rec.setModifier(new Author(sfd, "unknown"));
    }
    rec.getFields().remove(f);
    return rec;
  }
  
  public static Record metapodaciUPolje000(Record rec) {
    if (rec.getField("000") != null)
      return rec;
    Field f000 = new Field("000");
    Subfield sfa = new Subfield('a');
    switch (rec.getPubType()) {
      // TODO: podrzati sve tipove publikacija
      case 1:
        sfa.setContent("00101");
        break;
      case 2:
        sfa.setContent("00401");
        break;
      case 3:
        sfa.setContent("00601");
        break;
    }
    f000.add(sfa);
    if (rec.getCreationDate() != null) {
      Subfield sfb = new Subfield('b');
      f000.add(sfb);
      String s1 = dateFormat.format(rec.getCreationDate());
      String s2 = "00000000";
      if (rec.getLastModifiedDate() != null)
        s2 = dateFormat.format(rec.getLastModifiedDate());
      sfb.setContent(s1 + s2);
    }
    if (rec.getCreator() != null) {
      Subfield sfc = new Subfield('c');
      f000.add(sfc);
      sfc.setContent(rec.getCreator().getCompact());
    }
    if (rec.getModifier() != null) {
      Subfield sfd = new Subfield('d');
      f000.add(sfd);
      sfd.setContent(rec.getModifier().getCompact());
    }
    rec.getFields().add(0, f000);
    return rec;
  }
  
  private static boolean isEmpty(String s) {
    return s == null || "".equals(s);
  }
  
  private static boolean isEmpty(Date d) {
    return d == null;
  }
  
  private static boolean isEmpty(BigDecimal bd) {
    return bd == null;
  }
  
  private static void addSubsubfield(Subfield sf, char name, String content) {
    Subsubfield ssf = new Subsubfield(name);
    ssf.setContent(content);
    sf.add(ssf);
  }
  
  private static void addSubfield(Field f, char name, String content) {
    Subfield sf = new Subfield(name);
    sf.setContent(content);
    f.add(sf);
  }
  
  private static String getSubfieldContent(Field f, char name) {
    Subfield sf = f.getSubfield(name);
    if (sf == null)
      return null;
    String cnt = sf.getContent();
    if ("".equals(cnt))
      return null;
    return cnt;
  }
  
  private static String getSubsubfieldContent(Field f, char sfName, char ssfName) {  	
    Subfield sf = f.getSubfield(sfName);
    if (sf == null)
      return null;
    Subsubfield ssf = sf.getSubsubfield(ssfName);    
    if (ssf == null)
      return null;
    String cnt = ssf.getContent();
    if ("".equals(cnt))
      return null;
    return cnt;
  }
  
  public static void setLibDep(String ld) {
	  libDep = ld;
  }
  
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
  private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("ddMMyy");
  private static Log log = LogFactory.getLog(PrimerakSerializer.class);
  private static String libDep = "";
  
  
}
