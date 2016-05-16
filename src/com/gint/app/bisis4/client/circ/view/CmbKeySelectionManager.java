package com.gint.app.bisis4.client.circ.view;

import java.io.Serializable;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox.KeySelectionManager;

import com.gint.app.bisis4.client.circ.model.EduLvl;
import com.gint.app.bisis4.client.circ.model.Groups;
import com.gint.app.bisis4.client.circ.model.Languages;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.MmbrTypes;
import com.gint.app.bisis4.client.circ.model.Organization;
import com.gint.app.bisis4.client.circ.model.UserCategs;
import com.gint.app.bisis4.client.circ.model.WarningTypes;

public class CmbKeySelectionManager implements KeySelectionManager, Serializable {
    public int selectionForKey(char aKey,ComboBoxModel aModel) {
        int i,c;
        int currentSelection = -1;
        Object selectedItem = aModel.getSelectedItem();
        String v;
        String pattern;

        if ( selectedItem != null ) {
            for ( i=0,c=aModel.getSize();i<c;i++ ) {
                if ( selectedItem == aModel.getElementAt(i) ) {
                    currentSelection  =  i;
                    break;
                }
            }
        }

        pattern = ("" + aKey).toLowerCase();
        aKey = pattern.charAt(0);

        for ( i = ++currentSelection, c = aModel.getSize() ; i < c ; i++ ) {
            Object elem = aModel.getElementAt(i);
            String name;
            if (elem instanceof Groups) {
              name = (((Groups)elem).getInstName());
            } else if (elem instanceof EduLvl){
              name = (((EduLvl)elem).getName());
            } else if (elem instanceof Languages){
              name = (((Languages)elem).getName());
            } else if (elem instanceof Location){
              name = (((Location)elem).getName());
            } else if (elem instanceof MmbrTypes){
              name = (((MmbrTypes)elem).getName());
            } else if (elem instanceof Organization){
              name = (((Organization)elem).getName());
            } else if (elem instanceof UserCategs){
              name = (((UserCategs)elem).getName());
            } else if (elem instanceof WarningTypes){
              name = (((WarningTypes)elem).getName());
            } else {
              name = ((elem == null) ? "" : elem.toString());
            }
            name = name.toLowerCase();
            if ( name.length() > 0 && name.charAt(0) == aKey )
              return i; 
        }

        for ( i = 0 ; i < currentSelection ; i ++ ) {
            Object elem = aModel.getElementAt(i);
            String name;
            if (elem instanceof Groups) {
              name = (((Groups)elem).getInstName());
            } else if (elem instanceof EduLvl){
              name = (((EduLvl)elem).getName());
            } else if (elem instanceof Languages){
              name = (((Languages)elem).getName());
            } else if (elem instanceof Location){
              name = (((Location)elem).getName());
            } else if (elem instanceof MmbrTypes){
              name = (((MmbrTypes)elem).getName());
            } else if (elem instanceof Organization){
              name = (((Organization)elem).getName());
            } else if (elem instanceof UserCategs){
              name = (((UserCategs)elem).getName());
            } else if (elem instanceof WarningTypes){
              name = (((WarningTypes)elem).getName());
            } else {
              name = ((elem == null) ? "" : elem.toString());
            }
            
            name = name.toLowerCase();
            if ( name.length() > 0 && name.charAt(0) == aKey )
              return i; 
        }
        return -1;
    }
}
