package com.gint.app.bisis4.client.circ.report;

public class Item {
	public int uclanio=0;
	public int produzio=0;
	public int razduzio=0;
	public int zaduzio=0;
	public String ime;
	
	public Item(String ime){
		this.ime=ime;
	}
    public void addUclanio(Long u){
    	this.uclanio=this.uclanio +u.intValue();
    }
    public void addZaduzio(Long z){
    	this.zaduzio=this.zaduzio +z.intValue();
    }
    public void addProduzio(Long p){
    	this.produzio=this.produzio +p.intValue();
    }
    public void addRazduzio(Long r){
    	this.razduzio=this.razduzio +r.intValue();
    }
}