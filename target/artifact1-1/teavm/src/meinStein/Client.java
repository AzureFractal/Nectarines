package meinStein;

import org.teavm.jso.dom.html.HTMLDocument;

public class Client {
    private static MeinCtrl meinCtrl;

    public static void main(String[] args) {
//        meinDisplay = new MeinDisplay();
        meinCtrl = new MeinCtrl();
        meinCtrl.resetBoard();

        var document = HTMLDocument.current();
        var div = document.createElement("div");
        var calcElem = document.getElementById("calculate");
        calcElem.addEventListener("click", (e)->{
            String[][] data = meinCtrl.cur.toStringArray();
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    System.out.print(data[i][j] + " ");
                }
                System.out.println(); // Move to the next line after each row
            }
            });
        div.appendChild(document.createTextNode("Connect 6 thing"));
        document.getBody().appendChild(div);
    }
}
