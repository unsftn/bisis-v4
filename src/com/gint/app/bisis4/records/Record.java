package com.gint.app.bisis4.records;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gint.app.bisis4.records.serializers.PrimerakSerializer;
@JsonIgnoreProperties( ignoreUnknown = true )
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_records")
public class Record implements Serializable {

  /**
   * Default constructor.
   */
  public Record() {
    recordID = 0;
    pubType = 0;
    fields = new ArrayList<Field>();
    primerci = new ArrayList<Primerak>();
    godine = new ArrayList<Godina>();
  }

  /**
   * Initializes the new record with the given record id.
   * @param recordID The record identifier.
   */
  public Record(int recordID) {
    this.recordID = recordID;
    pubType = 0;
    fields = new ArrayList<Field>();
    primerci = new ArrayList<Primerak>();
    godine = new ArrayList<Godina>();
  }

  /**
   * Initializes the new record with the given record id and the list of fields.
   * @param recordID The given record identifier
   * @param fields The initial list of fields 
   * @param primerci Lista primeraka
   */
  public Record(int recordID, List<Field> fields, List<Primerak> primerci, 
      List<Godina> godine) {
    this.recordID = recordID;
    pubType = 0;
    this.fields = fields;
    this.primerci = primerci;
    this.godine = godine;
  }

  /**
   * Returns the number of fields in this record.
   * @return The number of fields in this record
   */
  public int getFieldCount() {
    return fields.size();
  }
  
  /**
   * Retrieves a field by its current index in the field list.
   * @param index The index of the field
   * @return The field with the given index; null if index is out of range
   */
  public Field getField(int index) {
    if (index >= fields.size() || index < 0)
      return null;
    return fields.get(index);
  }
  
  /**
   * Retrives the first field with given name from this record.
   * @param name The field name
   * @return The first field with the given name
   */
  public Field getField(String name) {
    for (int i = 0; i < fields.size(); i++) {
      Field field = fields.get(i);
      if (field.getName().equals(name)) {
        return field;
      }
    }
    return null;
  }
  
  /**
   * Retrieves all fields with the given name from this record.
   * @param name The field name
   * @return The list of all fields with the given name
   */
  public List<Field> getFields(String name) {
    ArrayList retVal = new ArrayList<Field>();
    for (int i = 0; i < fields.size(); i++) {
      Field field = fields.get(i);
      if (field.getName().equals(name))
        retVal.add(field);
    }
    return retVal;
  }
  
  /**
   * Retrieves the first subfield of the first field with the given name.
   * @param name The name of the subfield
   * @return The retrieved subfield
   */
  public Subfield getSubfield(String name) {
    if (name.length() != 4)
      return null;
    String fieldName = name.substring(0, 3);
    Field f = getField(fieldName);
    if (f == null)
      return null;
    Subfield sf = f.getSubfield(name.charAt(3));
    return sf;
  }
  
  /**
   * Retrieves all subfields with the given name from this record.
   * @param name The name of the subfield
   * @return The list of retrieved subfields
   */
  public List getSubfields(String name) {
    List retVal = new ArrayList();
    if (name.length() != 4)
      return retVal;
    String fieldName = name.substring(0, 3);
    Iterator it1 = getFields(fieldName).iterator();
    while (it1.hasNext()) {
      Field f = (Field)it1.next();
      Iterator it2 = f.getSubfields(name.charAt(3)).iterator();
      while (it2.hasNext()) {
        retVal.add(it2.next());
      }
    }
    return retVal;
  }
  
  /**
   * Retrieves the content of the first subfield with the given name.
   * @param name The name of the first subfield
   * @return The content of the subfield
   */
  public String getSubfieldContent(String name) {
    Subfield sf = getSubfield(name);
    if (sf == null)
      return null;
    else
      return sf.getContent();
  }
  
  /**
   * Retrieves the list of contents of all subfields with the given name.
   * @param name The name of the subfield
   * @return The list of subfield contents
   */
  public List getSubfieldsContent(String name) {
    List retVal = new ArrayList();
    Iterator subfields = getSubfields(name).iterator();
    while (subfields.hasNext()) {
      Subfield sf = (Subfield)subfields.next();
      retVal.add(sf.getContent());
    }
    return retVal;
  }
  
  /**
   * Appends the given field to the end of the list.
   * @param field The field to append
   */
  public void add(Field field) {
    fields.add(field);
  }
  
  /**
   * Removes the given field from the list.
   * @param field The field to remove
   */
  public void remove(Field field) {
    fields.remove(field);
  }

  /**
   * Sorts the fields, subfields, and subsubfields in this record.
   */
  public void sort() {
    for (int i = 1; i < fields.size(); i++) {
      for (int j = 0; j < fields.size() - i; j++) {
        Field f1 = (Field)fields.get(j);
        Field f2 = (Field)fields.get(j+1);
        if (f1.getName().compareTo(f2.getName()) > 0) {
          fields.set(j, f2);
          fields.set(j+1, f1);
        }
      }
    }
    for (int i = 0; i < fields.size(); i++) {
      Field f = (Field)fields.get(i);
      f.sort();
    }
  }
  
  
  public  void sortFields(){
   for (int i = 1; i < getFields().size(); i++) {
    for (int j = 0; j < getFields().size() - i; j++) {
      Field f1 = (Field)getFields().get(j);
      Field f2 = (Field)getFields().get(j+1);
      if (f1.getName().compareTo(f2.getName()) > 0) {
         getFields().set(j, f2);
         getFields().set(j+1, f1);
       }
     }
   }   
  }
  
  /**
   * Removes empty fields, subfields, and subsubfields from this record. 
   */
  public void pack() {
    Iterator it = fields.iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      f.pack();
      if (f.getSubfieldCount() == 0)
        it.remove();
    }
  }
  
  /**
   * Trims all subfield and subsubfield contents in this record. Returns this
   * record.
   * @return This record, with trimmed contents
   */
  public Record trim() {
    Iterator it = fields.iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
      f.trim();
    }
    return this;
  }

  /**
   * Returns a printable string representation of this record.
   */
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    for (Field f : fields)
      retVal.append(f.toString());
    for (Primerak p : primerci)
      retVal.append(p.toString());
    for (Godina g : godine)
      retVal.append(g.toString());
    return retVal.toString();
  }
  
  public Record primerciUPolja() {
    return PrimerakSerializer.primerciUPolja(this);
  }
  
  public Record poljaUPrimerke() {
    return PrimerakSerializer.poljaUPrimerke(this);
  }
  
  public Record poljaUPrimerke(String libDepartment) {
	  	PrimerakSerializer.setLibDep(libDepartment);
	    return PrimerakSerializer.poljaUPrimerke(this);
	  }
  
  public Record godineUPolja() {
    return PrimerakSerializer.godineUPolja(this);
  }

  public Record poljaUGodine() {
    return PrimerakSerializer.poljaUGodine(this);
  }
  
  public Record polje000UMetapodatke() {
    return PrimerakSerializer.polje000UMetapodatke(this);
  }
  
  public Record metapodaciUPolje000() {
    return PrimerakSerializer.metapodaciUPolje000(this);
  }
  
  public int getRN(){  	
  	try{
  		return Integer.parseInt(getSubfieldContent("001e"));
  	}catch(Exception e){
  		return 0;
  	}
  } 
  
  
  public void setRN(int rn){
  	Field f001;
  	Subfield sfRN;
  	if(getField("001")==null){
  		f001 = new Field("001");
  		add(f001);
  	}else
  		f001 = getField("001");
  	if(f001.getSubfield('e')==null){
  		sfRN = new Subfield('e');
  		f001.add(sfRN);
  	}else
  		sfRN = f001.getSubfield('e');
  	sfRN.setContent(String.valueOf(rn));	
  }
  
  // get master record number
  public int getMR(){
  	try{
  		return Integer.parseInt(getSubfieldContent("4741"));
  	}catch(Exception e){
  		return 0;
  	}
  	
  }
  
  // set master record number
  public void setMR(int mr){
  	Field f474 = getField("474");
  	if(f474==null){
  		f474 = new Field("474");
  		add(f474);
  	}
  	if(f474.getSubfield('1')==null)
  		f474.add(new Subfield('1',String.valueOf(mr)));
  	else
  		f474.getSubfield('1').setContent(String.valueOf(mr));
  }
  
  
  public Primerak getPrimerak(String invBroj){
	  for(Primerak p:primerci){
		  if(p.getInvBroj().equals(invBroj))
			  return p;
		  
	  }
	  return null;
  }
  
  
  public Godina getGodinaForInvBRSveske(String invBrojSveske){
	  for(Godina g:godine){
		  for(Sveska s:g.getSveske())
			  if(s.getInvBroj().equals(invBrojSveske))
			  return g;		  
	  }
	  return null;
  }
  
  
  public Godina getGodina(String invBroj){
	  for(Godina g:godine){		 
		  if(g.getInvBroj().equals(invBroj))
		  return g;		  
	  }
	  return null;
  }
  
  
  public Date getCreationDate() {
    return creationDate;
  }
  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }
  public Author getCreator() {
    return creator;
  }
  public void setCreator(Author creator) {
    this.creator = creator;
  }
  public List<Field> getFields() {
    return fields;
  }
  public void setFields(List<Field> fields) {
    this.fields = fields;
  }
  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }
  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }
  public Author getModifier() {
    return modifier;
  }
  public void setModifier(Author modifier) {
    this.modifier = modifier;
  }
  public int getPubType() {
    return pubType;
  }
  public void setPubType(int pubType) {
    this.pubType = pubType;
  }
  public int getRecordID() {
    return recordID;
  }
  public void setRecordID(int recordID) {
    this.recordID = recordID;
  }
  public List<Primerak> getPrimerci() {
    return primerci;
  }
  public void setPrimerci(List<Primerak> primerci) {
    this.primerci = primerci;
  }
  public List<Godina> getGodine() {
    return godine;
  }
  public void setGodine(List<Godina> godine) {
    this.godine = godine;
  }
  
  
  
  
  
  public Record copy(){
  	Record rec = new Record();
  	rec.setPubType(pubType);
  	rec.setCreator(creator);
  	rec.setModifier(modifier);
  	rec.setCreationDate(creationDate);
  	rec.setLastModifiedDate(lastModifiedDate);
  	for(Field f:fields)
  		rec.add(f.copy());
  	for(Primerak p:primerci)
  		rec.getPrimerci().add(p.copy());
  	for(Godina g: godine)
  		rec.getGodine().add(g.copy());
  	return rec;
  }
  
  /*
   * kopija zapisa bez polja
   */
  public Record shallowCopy(){
  	Record rec = new Record();
  	rec.setPubType(pubType);
  	rec.setCreator(creator);
  	rec.setModifier(modifier);
  	rec.setCreationDate(creationDate);
  	rec.setLastModifiedDate(lastModifiedDate);
  	return rec;
  }
  
  /*
   * kopira zapis bez inventarnih podataka
   */
  public Record copyWithoutHoldings(){
  	Record rec = new Record();
  	rec.setPubType(pubType);
  	rec.setCreator(creator);
  	rec.setModifier(modifier);
  	rec.setCreationDate(creationDate);
  	rec.setLastModifiedDate(lastModifiedDate);
  	for(Field f:fields)
  		rec.add(f.copy());
  	return rec;  	
  } 

  /** record identifier */
  private int recordID;
  /** publication type */
  private int pubType;
  /** the list of fields */
  private List<Field> fields;
  /** primerci */
  private List<Primerak> primerci;
  /** godine */
  private List<Godina> godine;
  /** record creator */
  private Author creator;
  /** record modifier */
  private Author modifier;
  /** record creation date */
  private Date creationDate;
  /** last modification date */
  private Date lastModifiedDate;
}
