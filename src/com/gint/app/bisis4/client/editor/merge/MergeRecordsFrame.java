
package com.gint.app.bisis4.client.editor.merge;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.html.HTMLEditorKit;

import net.miginfocom.swing.MigLayout;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.editor.editorutils.CenteredDialog;
import com.gint.app.bisis4.client.editor.recordtree.RecordUtils;
import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.records.serializers.PrimerakSerializer;

public class MergeRecordsFrame extends JInternalFrame {
	
	public MergeRecordsFrame(){
		super("Spajanje zapisa", false, true, false, true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setSize(new Dimension(180,400));
		initialize();
	}
	
	public void initialize(){
		setLayout(new MigLayout("insets 10 10 10 10","[]","[][]20[][]20[]"));
		add(new JLabel("Id osnovnog zapisa:"),"wrap");		
		add(idOsnovniZapisTxtFld,"wrap");
		idsOstaliZapisiTxtArea = new JTextArea(12,8);		
		scrollPane = new JScrollPane(idsOstaliZapisiTxtArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(new JLabel("Id-ovi zapisa koji se dodaju:"),"wrap");
		add(scrollPane,"wrap");		
		mergeButton = new JButton(new ImageIcon(getClass().getResource(
  	"/com/gint/app/bisis4/client/images/Check16.png")));
		mergeButton.setText("Spoji");
		

  okBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {					
					executeMerge();
			}   	
  });
  cancelBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {					
				checkMergedDialog.dispose();
			}   	
  });
		
		add(mergeButton,"");
		
		mergeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleMergeRecords();			
			}			
		});		
	}	
	
	
	private void handleMergeRecords() {
		try{
					String idOsnovni = idOsnovniZapisTxtFld.getText();
					boolean numberFormatOk = true;
					int idOsnovniInt = 0;
					
					try{
						idOsnovniInt = Integer.parseInt(idOsnovni);
					}catch(NumberFormatException e){	
						numberFormatOk = false;
					}		
					if(numberFormatOk){			
						String text = idsOstaliZapisiTxtArea.getText();
						String[] idsToAddStringArray = text.split("\n");
						int[] idsToAdd = new int[idsToAddStringArray.length];
						for(int i=0; i<idsToAddStringArray.length;i++){
							try{
								idsToAdd[i] = Integer.parseInt(idsToAddStringArray[i]);					
							}catch(NumberFormatException e){
								numberFormatOk = false;
							}
						}			
						if(!numberFormatOk){
							JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
									"Gre\u0161ka u id broju", 
									"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
							return;
						}
						recBasic = BisisApp.getRecordManager().getRecord(idOsnovniInt);			
						if(recBasic==null){
							JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
									"Ne postoji zapis sa id = "+idOsnovniInt+"!", 
									"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
							return;
						}
						recsToAdd = new ArrayList<Record>();
						for(int id:idsToAdd){
							Record rec = BisisApp.getRecordManager().getRecord(id);
							if(rec==null){
								JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
										"Ne postoji zapis sa id = "+id+"!", 
										"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
								return;					
							}
							recsToAdd.add(rec);			
						}
						
						// merge - prepisuju se primerci odnosno godine
						// UDK - na osnovni zapis se dodaje 675 iz prvog zapisa koji ga
						// sadrzi (od zapisa koji se dodaju)
						
						for(Record rec:recsToAdd){
							for(Primerak p:rec.getPrimerci())
								recBasic.getPrimerci().add(p.copy());				
							for(Godina g:rec.getGodine())
								recBasic.getGodine().add(g.copy());
						}
						
					 String udk = "";
			   for (int i = 0; i < recsToAdd.size(); i++) {
			     String test = recsToAdd.get(i).getSubfieldContent("675a");
			     if (test != null) {
			       udk = test;
			       break;
			     }
			   }
			   Field f675 = recBasic.getField("675");
			   if (f675 == null) {
			     f675 = new Field("675");
			     recBasic.add(f675);
			   }
			   Subfield sf675a = f675.getSubfield('a');
			   if (sf675a == null) {
			     sf675a = new Subfield('a');
			     f675.add(sf675a);
			   }
			   sf675a.setContent(udk);
			
			   checkMergedDialog = 
			   		new CenteredDialog(BisisApp.getMainFrame());
			   checkMergedDialog.setTitle("Spajanje zapisa");
			   JEditorPane editorPane = new JEditorPane();
			   editorPane.setEditable(false);
			   editorPane.setEditorKit(new HTMLEditorKit());
			   String checkMergedStr = "";
			   checkMergedStr +=("<html><b>Osnovni zapis: </b><br>");
			   checkMergedStr +=("<b>ID = "+recBasic.getRecordID()+"</b><br>");   
			   checkMergedStr +=(RecordFactory.toFullFormat(0, RecordUtils.sortFields(PrimerakSerializer.primerciUPolja(recBasic)), true));
			   PrimerakSerializer.poljaUPrimerke(recBasic);
			   checkMergedStr +=("\n");
			   checkMergedStr +=("-----------------------------------------------------<br>");
			   checkMergedStr +=("<b>Zapisi koji \u0107e biti obrisani: </b><br>");
			   for(Record rec:recsToAdd){
			   	checkMergedStr +=("<b>ID = "+rec.getRecordID()+"</b><br>");   	
			   	checkMergedStr +=(RecordFactory.toFullFormat(0, PrimerakSerializer.primerciUPolja(rec), true));
			   	checkMergedStr +=("<br>-----------------------<br>");
			   }
			   checkMergedStr +=("</html>");  
			   
			   JScrollPane scrollPane = new JScrollPane(editorPane);
			   editorPane.setSize(300,200);
			   editorPane.setText(checkMergedStr);
			   checkMergedDialog.setLayout(new MigLayout("","[][]","[]"));
			   checkMergedDialog.add(scrollPane,"span 2, grow, wrap");   
			   okBtn.setText("Spoji");   
			   cancelBtn.setText("Odustani");
			   checkMergedDialog.add(okBtn, "split 2");
			   checkMergedDialog.add(cancelBtn,"");
			   checkMergedDialog.setSize(600,400);
			   checkMergedDialog.setVisible(true);
			   
						}
		}catch(Exception e){
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
					"Gre\u0161ka u spajanju zapisa!\n "+e.getClass(), 
					"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
		}
			
		}
		
	private void executeMerge(){
		try{
			BisisApp.getRecordManager().update(recBasic);
			for(Record rec:recsToAdd){
				BisisApp.getRecordManager().delete(rec.getRecordID());			
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
					"Gre\u0161ka prilikom spajanja zapisa!\nSpajanje nije izvr\u0161eno!", 
					"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
			return;					
			
		}
		JOptionPane.showMessageDialog(BisisApp.getMainFrame(), 
				"Spajanje zapisa uspe\u0161no izvr\u0161eno!","Spajanje zapisa",
				JOptionPane.INFORMATION_MESSAGE);
		checkMergedDialog.dispose();
	}
		

	private JTextField idOsnovniZapisTxtFld = new JTextField(10);
	private JTextArea idsOstaliZapisiTxtArea;
	private JScrollPane scrollPane;
	private JButton mergeButton;	
	private Record recBasic;
	private List<Record> recsToAdd;
	private CenteredDialog checkMergedDialog;
	private JButton okBtn = new JButton(new ImageIcon(getClass().getResource(
 "/com/gint/app/bisis4/client/images/ok.gif")));
	private JButton cancelBtn  = new JButton(new ImageIcon(getClass().getResource(
 "/com/gint/app/bisis4/client/images/remove.gif")));   
	

}

	