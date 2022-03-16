package gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

final class FramePrincipal2 extends FileFilter {
    private final FramePrincipal this$0;

    FramePrincipal2(FramePrincipal var1) {
        this.this$0 = var1;
    }

    public boolean accept(File f) {
        return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
    }

    public String getDescription() {
        return "Text file";
    }
}
