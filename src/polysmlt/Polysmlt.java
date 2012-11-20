/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package polysmlt;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import javax.swing.JOptionPane;

/**
 *
 * @author yasyf
 */
public class Polysmlt {

    public static int degree;
    public static String query = "";

    public static void main(String[] args) throws IOException {
        
        degree = Integer.parseInt(JOptionPane.showInputDialog(null,"Degree","2"));
        int numb;
        for(int i=degree;i>=0;i--)
        {
            numb = Integer.parseInt(JOptionPane.showInputDialog(null,"Coeficcient of X^"+i));
            query += "+"+numb+"x^"+i;
        }
        URI uri = URI.create("http://www.wolframalpha.com/input/?i="+URLEncoder.encode("roots of "+query));
        Desktop.getDesktop().browse(uri);

    }
}
