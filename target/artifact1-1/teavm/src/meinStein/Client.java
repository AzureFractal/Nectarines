package meinStein;

import org.teavm.jso.dom.html.HTMLDocument;

public class Client {
    private static MeinCtrl meinCtrl;

    public static void main(String[] args) {
        meinCtrl = new MeinCtrl();
        meinCtrl.resetBoard();

        var document = HTMLDocument.current();
        var div = document.createElement("div");
        var calcElem = document.getElementById("calculate");
        var board = document.getElementById("board");
        calcElem.addEventListener("click", (e)->{
            meinCtrl.cur.anaPlay(3, 3, MeinCtrl.OPT_DEFEND);
            document.getElementById("comms").setInnerHTML(meinCtrl.cur.toStringEncoding());
        });
        div.appendChild(document.createTextNode("Connect 6 thing"));
        document.getBody().appendChild(div);
    }
}
