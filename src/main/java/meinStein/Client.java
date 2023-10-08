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
        calcElem.addEventListener("click", (e)->{System.out.println(meinCtrl.cur.toStringArray());});
        div.appendChild(document.createTextNode("Connect 6 thing"));
        document.getBody().appendChild(div);
    }
}
