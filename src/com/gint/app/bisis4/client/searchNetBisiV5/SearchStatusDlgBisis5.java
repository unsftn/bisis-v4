package com.gint.app.bisis4.client.searchNetBisiV5;

import com.gint.app.bisis4.utils.Messages;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.gint.app.bisis4.client.BisisApp;

import com.gint.app.bisis4.utils.WindowUtils;
import net.miginfocom.swing.MigLayout;

public class SearchStatusDlgBisis5 extends JDialog {

	  public SearchStatusDlgBisis5(){
		  super(BisisApp.getMainFrame(), Messages.getString("SEARCH_INFO"), true);
	        setSize(250,150);
		    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		    progressBar.setMinimum(0);
		    progressBar.setIndeterminate(true);
		    progressBar.setMinimumSize(new Dimension(206,6));
		    cancelBtn.setIcon(new ImageIcon(getClass().getResource(
		        "/com/gint/app/bisis4/client/images/remove.gif")));
		   
		    MigLayout mig = new MigLayout(
		        "",
		        "[]",
		        "[]para[]para[]");
		    setLayout(mig);
		    add(new JLabel(Messages.getString("SEARCH_SEARCHING_IN_PROGRESS_HTML")), "center, wrap");
		    add(progressBar, "span 2, center, growx, wrap");
		    add(cancelBtn, "span 2, split 2, tag cancel");
			  pack();
		    WindowUtils.centerOnScreen(this);
	  }

	  public void addActionListener(final SearchTaskBisis5 task) {
		  cancelBtn.addActionListener(e -> {
        setVisible(false);
        task.setIsCancelled(true);
      });
	  }

	  private JProgressBar progressBar = new JProgressBar();
	  private JButton cancelBtn=new JButton(Messages.getString("SEARCH_CANCEL"));

	}
