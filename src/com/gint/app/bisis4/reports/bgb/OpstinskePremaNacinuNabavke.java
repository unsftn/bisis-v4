package com.gint.app.bisis4.reports.bgb;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Report;

public class OpstinskePremaNacinuNabavke extends Report {

  
  public void handleRecord(Record rec) {
    if (rec == null)
      return;
    String RN = rec.getSubfieldContent("001e");

    for (Primerak p : rec.getPrimerci()) {
      Date datInv = p.getDatumInventarisanja();
      boolean error = false;
      // inv. broj
      String invBr = p.getInvBroj();
      if (invBr == null ||invBr.trim().length() == 0) {
        log.error("RN: " + RN + " nedostaje inventarni broj\n");
        error = true;
      } 
      // nacin nabavke

      String nacinNabavke = p.getNacinNabavke();
      if (nacinNabavke == null || nacinNabavke.trim().length() == 0) {
        log.error("RN: " + RN + " nedostaje na\u010din nabavke za IN=" + invBr + "\n");
        error = true;
      } 
      // ogranak
      String ogranakID=p.getOdeljenje();
	  if (ogranakID == null || ogranakID.equals("")) {
			  ogranakID = "\u043d\u0435\u0440\u0430\u0437\u0432\u0440\u0441\u0442\u0430\u043d\u0438"; // nerazvrstani
	  }
		      
      if (error)
        continue;
      String key = settings.getParam("file") + getFilenameSuffix(datInv);
      
      Ogranak o=getItem(getList(key),ogranakID); 
      if (o == null ){
    	o = new Ogranak();
        o.ogr = ogranakID;
       	o.add(RN, nacinNabavke);
       	getList(key).add(o); 	
       }else{
    	   o.add(RN, nacinNabavke);
       }
    }
  }
  public Ogranak getItem(List<Ogranak> ol, String ogranakID) {
		
		for (Ogranak o : ol){
			
			if (o.ogr.compareToIgnoreCase(ogranakID)==0){	
				return o;
			}
		}
	    return null;
	    
	    
	  }
public List<Ogranak> getList(String key) {
	    List<Ogranak> list = itemMap.get(key);
	    if (list == null) {
	      list = new ArrayList<Ogranak>();
	      itemMap.put(key, list);
	    }
	    return list;
	  }
  public class Ogranak implements Comparable {
    public String ogr;
    public int na, pa;
    public int ni, pi;
    public int nk, pk;
    public int nl, pl;
    public int nm, pm;
    public int nn, pn;
    public int no, po;
    public int np, pp;
    public int nr, pr;
    public int nt, pt;
    public int nz, pz;
    public int nu, pu;
    public HashSet aset = new HashSet();
    public HashSet iset = new HashSet();
    public HashSet kset = new HashSet();
    public HashSet lset = new HashSet();
    public HashSet mset = new HashSet();
    public HashSet nset = new HashSet();
    public HashSet oset = new HashSet();
    public HashSet pset = new HashSet();
    public HashSet rset = new HashSet();
    public HashSet tset = new HashSet();
    public HashSet zset = new HashSet();
    public HashSet uset = new HashSet();
    
    public int compareTo(Object o) {
      if (o instanceof Ogranak) {
        Ogranak og1 = (Ogranak)o;
        return ogr.compareTo(og1.ogr);
      }
      return 0;
    }
    public void add(String rn, String nacin) {
      char n = nacin.charAt(0);
      switch (n) {
        case 'a':
          pa++;
          if (rn != null)
            if (!aset.contains(rn)) {
              na++;
              aset.add(rn);
            }
          break;
        case 'i':
          pi++;
          if (rn != null)
            if (!iset.contains(rn)) {
              ni++;
              iset.add(rn);
            }
          break;
        case 'k':
          pk++;
          if (rn != null)
            if (!kset.contains(rn)) {
              nk++;
              kset.add(rn);
            }
          break;
        case 'l':
          pl++;
          if (rn != null)
            if (!lset.contains(rn)) {
              nl++;
              lset.add(rn);
            }
          break;
        case 'm':
          pm++;
          if (rn != null)
            if (!mset.contains(rn)) {
              nm++;
              mset.add(rn);
            }
          break;
        case 'n':
          pn++;
          if (rn != null)
            if (!nset.contains(rn)) {
              nn++;
              nset.add(rn);
            }
          break;
        case 'o':
          po++;
          if (rn != null)
            if (!oset.contains(rn)) {
              no++;
              oset.add(rn);
            }
          break;
        case 'p':
          pp++;
          if (rn != null)
            if (!pset.contains(rn)) {
              np++;
              pset.add(rn);
            }
          break;
        case 'r':
          pr++;
          if (rn != null)
            if (!rset.contains(rn)) {
              nr++;
              rset.add(rn);
            }
          break;
        case 't':
          pt++;
          if (rn != null)
            if (!tset.contains(rn)) {
              nt++;
              tset.add(rn);
            }
          break;
        case 'z':
          pz++;
          if (rn != null)
            if (!zset.contains(rn)) {
              nz++;
              zset.add(rn);
            }
          break;
      }
      if (n != 't') {
        pu++;
        if (rn != null) {
          if (!uset.contains(rn)) {
            nu++;
            uset.add(rn);
          }
          if (!ukupnoNaslova.contains(rn))
            ukupnoNaslova.add(rn);
        }
      }
    }
    public String toString() {
    	String odeljenje;
    	System.out.println(ogr);
    	if (!ogr.equalsIgnoreCase("\u043d\u0435\u0440\u0430\u0437\u0432\u0440\u0441\u0442\u0430\u043d\u0438")){
    	  odeljenje=HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.ODELJENJE_CODER, ogr);
    	  int zarez=odeljenje.indexOf(",");
    	  if(zarez!=-1){
    		odeljenje=odeljenje.substring(0, zarez);
    	   }
    	}else{
    		odeljenje=ogr;
    	}
    	StringBuffer buf = new StringBuffer();
        buf.append("\n  <item id=\"");
        buf.append(ogr);
        buf.append("\">\n  <ogranak>");
        buf.append(odeljenje);
        buf.append(	"</ogranak>\n	<na>");
        buf.append(getNa());
        buf.append("</na>\n    <pa>");
        buf.append(getPa());
        buf.append("</pa>\n    <ni>");
        buf.append(getNi());
        buf.append("</ni>\n    <pi>");
        buf.append(getPi());
        buf.append("</pi>\n    <nk>");
        buf.append(getNk());
        buf.append("</nk>\n    <pk>");
        buf.append(getPk());
        buf.append("</pk>\n    <nl>");
        buf.append(getNl());
        buf.append("</nl>\n    <pl>");
        buf.append(getPl());
        buf.append("</pl>\n		<nm>");
        buf.append(getNm());
        buf.append("</nm>\n		<pm>");
        buf.append(getPm());
        buf.append("</pm>\n		<nn>");
        buf.append(getNn());
        buf.append("</nn>\n		<pn>");
        buf.append(getPn());
        buf.append("</pn>\n		<no>");
        buf.append(getNo());
        buf.append("</no>\n		<po>");
        buf.append(getPo());
        buf.append("</po>\n		<np>");
        buf.append(getNp());
        buf.append("</np>\n		<pp>");
        buf.append(getPp());
        buf.append("</pp>\n		<nr>");
        buf.append(getNr());
        buf.append("</nr>\n		<pr>");
        buf.append(getPr());
        buf.append("</pr>\n		<nt>");
        buf.append(getNt());
        buf.append("</nt>\n		<pt>");
        buf.append(getPt());
        buf.append("</pt>\n		<nz>");
        buf.append(getNz());
        buf.append("</nz>\n		<pz>");
        buf.append(getPz());
        buf.append("</pz>\n		<nu>");
        buf.append(getNu());
        buf.append("</nu>\n		<pu>");
        buf.append(getPu());
        buf.append("</pu>\n		</item>");
        
        return buf.toString();
    }
    public String getOgr() {
      return ogr;
    }
    public void setOgr(String ogr) {
      this.ogr = ogr;
    }
    public int getNa() {
      return na;
    }
    public void setNa(int na) {
      this.na = na;
    }
    public int getPa() {
      return pa;
    }
    public void setPa(int pa) {
      this.pa = pa;
    }
    public int getNi() {
      return ni;
    }
    public void setNi(int ni) {
      this.ni = ni;
    }
    public int getPi() {
      return pi;
    }
    public void setPi(int pi) {
      this.pi = pi;
    }
    public int getNk() {
      return nk;
    }
    public void setNk(int nk) {
      this.nk = nk;
    }
    public int getPk() {
      return pk;
    }
    public void setPk(int pk) {
      this.pk = pk;
    }
    public int getNl() {
      return nl;
    }
    public void setNl(int nl) {
      this.nl = nl;
    }
    public int getPl() {
      return pl;
    }
    public void setPl(int pl) {
      this.pl = pl;
    }
    public int getNm() {
      return nm;
    }
    public void setNm(int nm) {
      this.nm = nm;
    }
    public int getPm() {
      return pm;
    }
    public void setPm(int pm) {
      this.pm = pm;
    }
    public int getNn() {
      return nn;
    }
    public void setNn(int nn) {
      this.nn = nn;
    }
    public int getPn() {
      return pn;
    }
    public void setPn(int pn) {
      this.pn = pn;
    }
    public int getNo() {
      return no;
    }
    public void setNo(int no) {
      this.no = no;
    }
    public int getPo() {
      return po;
    }
    public void setPo(int po) {
      this.po = po;
    }
    public int getNp() {
      return np;
    }
    public void setNp(int np) {
      this.np = np;
    }
    public int getPp() {
      return pp;
    }
    public void setPp(int pp) {
      this.pp = pp;
    }
    public int getNr() {
      return nr;
    }
    public void setNr(int nr) {
      this.nr = nr;
    }
    public int getPr() {
      return pr;
    }
    public void setPr(int pr) {
      this.pr = pr;
    }
    public int getNt() {
      return nt;
    }
    public void setNt(int nt) {
      this.nt = nt;
    }
    public int getPt() {
      return pt;
    }
    public void setPt(int pt) {
      this.pt = pt;
    }
    public int getNz() {
      return nz;
    }
    public void setNz(int nz) {
      this.nz = nz;
    }
    public int getPz() {
      return pz;
    }
    public void setPz(int pz) {
      this.pz = pz;
    }
    public int getNu() {
      return nu;
    }
    public void setNu(int nu) {
      this.nu = nu;
    }
    public int getPu() {
      return pu;
    }
    public void setPu(int pu) {
      this.pu = pu;
    }
    public HashSet getOset() {
      return oset;
    }
    public void setOset(HashSet oset) {
      this.oset = oset;
    }
    
  }
  
  
  private Ogranak makeSum(List items) {
    Ogranak sum = new Ogranak();
    sum.ogr = "\u0423\u041a\u0423\u041f\u041d\u041e"; // UKUPNO
    for (int i = 0; i < items.size(); i++) {
      Ogranak o = (Ogranak)items.get(i);
      sum.na += o.na; sum.pa += o.pa;
      sum.ni += o.ni; sum.pi += o.pi;
      sum.nk += o.nk; sum.pk += o.pk;
      sum.nl += o.nl; sum.pl += o.pl;
      sum.nm += o.nm; sum.pm += o.pm;
      sum.nn += o.nn; sum.pn += o.pn;
      sum.no += o.no; sum.po += o.po;
      sum.np += o.np; sum.pp += o.pp;
      sum.nr += o.nr; sum.pr += o.pr;
      sum.nt += o.nt; sum.pt += o.pt;
      sum.nz += o.nz; sum.pz += o.pz;
      sum.nu += o.nu; sum.pu += o.pu;
    }
    sum.nu = ukupnoNaslova.size();
    return sum;
  }

 
@Override
public void init() {
    nf = NumberFormat.getInstance(Locale.GERMANY);
	nf.setMinimumFractionDigits(2);
	nf.setMaximumFractionDigits(2);
	itemMap.clear();
	log.info("Report initialized.");
}

@Override
public void finish() {
	log.info("Finishing report...");

	for (List<Ogranak> list : itemMap.values())
		Collections.sort(list);

	for (String key : itemMap.keySet()) {
		List<Ogranak> list = itemMap.get(key);
		PrintWriter out = getWriter(key);
		
		for (Ogranak o : list) {
			out.print(o.toString());
		}
		out.println("</report>");
		out.close();	
	}
	itemMap.clear();
	log.info("Report finished.");
	
}

@Override
public void finishOnline(StringBuffer buff) {
	// TODO Auto-generated method stub
	
}

@Override
public void finishInv() {
	// TODO Auto-generated method stub
	
}

private Set ukupnoNaslova = new HashSet();
private Map<String, List<Ogranak>> itemMap = new HashMap<String, List<Ogranak>>();
private static Log log = LogFactory.getLog(OpstinskePremaNacinuNabavke.class);
NumberFormat nf;
}
