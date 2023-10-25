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
            String commsStr = document.getElementById("comms").getInnerText();
            System.out.println(commsStr);
            var userMoves = commsStr.split(",");
            // Play user moves from buffer
            if (!commsStr.length > 1) {
                int stone1 =  Integer.valueOf(userMoves[userMoves.length-2].split(":")[0]);
                int stone2 =  Integer.valueOf(userMoves[userMoves.length-1].split(":")[0]);
                System.out.println("HELLO WORLD");
                System.out.println(stone1);
                System.out.println(stone2);
                meinCtrl.tryMove(stone1, stone2, 0);
            }
            // Get computer moves and update buffer 
            meinCtrl.cur.anaPlay(2, 2, MeinCtrl.OPT_DEFEND);
            document.getElementById("comms").setInnerHTML(meinCtrl.curGame.getMovesEncoded());
        });
    }
}
