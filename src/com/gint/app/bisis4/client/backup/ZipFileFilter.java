package com.gint.app.bisis4.client.backup;

import java.io.File;

import javax.swing.filechooser.FileFilter;

class ZipFileFilter extends FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".zip");
    }
    
    public String getDescription() {
        return ".zip files";
    }
}
