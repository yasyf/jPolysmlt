package polysmlt;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author yasyf
 */
public class Polysmlt {

    public static int degree;
    public static String queryString = "";
    private static String appid = "RUEHUK-7LYXQWXVWK";

    public static void main(String[] args) throws IOException, WAException {

        degree = Integer.parseInt(JOptionPane.showInputDialog(null, "Degree", "2"));
        int numb;
        for (int i = degree; i >= 0; i--) {
            numb = Integer.parseInt(JOptionPane.showInputDialog(null, "Coeficcient of X^" + i));
            queryString += "+" + numb + "x^" + i;
        }
        queryString = "roots of " + queryString;
        System.out.println(queryString);
        WAEngine engine = new WAEngine();
        engine.setAppID(appid);
        engine.addFormat("plaintext");

        WAQuery query = engine.createQuery();
        query.setInput(queryString);
        WAQueryResult queryResult = engine.performQuery(query);
        String result = "";
        Integer resultNumb = 0;
        for (WAPod pod : queryResult.getPods()) {
            if ("RootsInTheComplexPlane".equals(pod.getID())) {
                break;
            }
            for (WASubpod subpod : pod.getSubpods()) {
                for (Object element : subpod.getContents()) {
                    if (element instanceof WAPlainText) {
                        resultNumb++;
                        if (resultNumb > 1 && !"".equals(((WAPlainText) element).getText())) {
                            result += ((WAPlainText) element).getText() + "\n\n";
                        }
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, result);
    }
}
