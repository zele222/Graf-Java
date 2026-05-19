package app;
import com.formdev.flatlaf.FlatDarkLaf;
import view.MainWindow;

import javax.swing.*;

public class  Main {
    public static void main(String[] args)
    {
        try {
        FlatDarkLaf.setup();
        } catch( Exception ex ) {
        System.err.println( "Failed to initialize LaF" );
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow();
            }
        });
    }
}
