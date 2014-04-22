package system;

import java.util.Calendar;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
		System.out.println("Dummy results");
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Display disp = new Display();
                disp.setVisible(true);
            }
        });
    }

}

