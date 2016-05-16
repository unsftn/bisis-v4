package com.gint.app.bisis4.xmlmessaging;

import com.gint.app.bisis4.xmlmessaging.communication.LibraryServerDesc;
import com.gint.app.bisis4.xmlmessaging.util.RecordBriefs;

public class BriefInfoModel {
	private Boolean selected;
	private LibraryServerDesc libServ;
	private RecordBriefs briefInfo;
	public BriefInfoModel(Boolean selected, LibraryServerDesc libServ, RecordBriefs briefInfo) {
		super();
		this.selected = selected;
		this.libServ = libServ;
		this.briefInfo = briefInfo;
	}
	public RecordBriefs getBriefInfo() {
		return briefInfo;
	}
	public void setBriefInfo(RecordBriefs briefInfo) {
		this.briefInfo = briefInfo;
	}
	public LibraryServerDesc getLibServ() {
		return libServ;
	}
	public void setLibServ(LibraryServerDesc libServ) {
		this.libServ = libServ;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
