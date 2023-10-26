package meinStein;

import org.teavm.jso.dom.html.HTMLDocument;

public class Client {
    private static MeinCtrl meinCtrl;

    public static void main(String[] args) {
        var document = HTMLDocument.current();
        meinCtrl = new MeinCtrl();
        // Render first piece
        document.getElementById("comms").setInnerHTML(meinCtrl.curGame.getMovesEncoded());

        var calcElem = document.getElementById("calculate");
        calcElem.addEventListener("click", (e)->{
            document.getElementById("comms1").setInnerHTML("Thinking");
            String commsStr = document.getElementById("comms").getInnerText();
            var userMoves = commsStr.split(",");
            // Play user moves from buffer
            if (userMoves.length > 1) {
                int stone1 =  Integer.parseInt(userMoves[userMoves.length-2].split(":")[0]);
                int stone2 =  Integer.parseInt(userMoves[userMoves.length-1].split(":")[0]);
                meinCtrl.tryMove(stone1, stone2, 0);
                meinCtrl.tryMove(stone2, stone1, 0);
            }
            // Get computer moves and update buffer 
            meinCtrl.cur.anaPlay(2, 2, MeinCtrl.OPT_DEFEND);
            document.getElementById("comms").setInnerHTML(meinCtrl.curGame.getMovesEncoded());
            document.getElementById("comms1").setInnerHTML("Done");
        });
    }
}
