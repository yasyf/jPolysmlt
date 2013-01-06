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
        String[] options = {"POLYSMLT", "Other"};
        int option = JOptionPane.showOptionDialog(null, "Which Function?", "jMath", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (option == 0) {

            degree = Integer.parseInt(JOptionPane.showInputDialog(null, "Degree", "2"));
            int numb;
            for (int i = degree; i >= 0; i--) {
                numb = Integer.parseInt(JOptionPane.showInputDialog(null, "Coeficcient of X^" + i));
                queryString += "+" + numb + "x^" + i;
            }
            queryString = "solve " + queryString + " over the reals";

        } else {
            queryString = JOptionPane.showInputDialog(null, "Enter Query");
        }
        WAEngine engine = new WAEngine();
        engine.setAppID(appid);
        engine.addFormat("plaintext");

        WAQuery query = engine.createQuery();
        query.setInput(queryString);
        WAQueryResult queryResult = engine.performQuery(query);
        String result = "";
        Integer resultNumb = 0;
        String temp;
        if (queryResult.isError()) {
            result = queryResult.getErrorMessage();
        } else {
            fetch:
            {
                for (WAPod pod : queryResult.getPods()) {
                    if ("RootsInTheComplexPlane".equals(pod.getID())) {
                        break fetch;
                    } else {
                        for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {
                                    resultNumb++;
                                    temp = ((WAPlainText) element).getText();
                                    if (resultNumb > 1 && !"".equals(temp)) {
                                        if (resultNumb > 2) {
                                            result += "\n\n";
                                        }
                                        result += temp;
                                        if ("(no real solutions)".equals(temp)) {
                                            break fetch;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, result);
    }
}
