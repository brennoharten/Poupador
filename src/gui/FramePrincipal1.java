package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class FramePrincipal1 extends WindowAdapter {
    private final FramePrincipal this$0;

    FramePrincipal1(FramePrincipal var1) {
        this.this$0 = var1;
    }

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}