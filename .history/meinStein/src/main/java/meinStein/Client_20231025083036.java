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
        calcElem.addEventListener("click", (e)->{
            // Play and update comms 
            meinCtrl.cur.anaPlay(2, 2, MeinCtrl.OPT_DEFEND);
            document.getElementById("comms").setInnerHTML(meinCtrl.curGame.getMovesEncoded());
        });
//        div.appendChild(document.createTextNode("Connect 6 thing"));
//        document.getBody().appendChild(div);
    }
}
