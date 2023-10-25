package meinStein;

import org.teavm.jso.dom.html.HTMLDocument;

public class Client {
    private static MeinCtrl meinCtrl;

    public static void main(String[] args) {
        meinCtrl = new MeinCtrl();
        meinCtrl.resetBoard();

        var document = HTMLDocument.current();
        var calcElem = document.getElementById("calculate");
        calcElem.addEventListener("click", (e)->{
            // Play user moves from buffer
            String commsStr = document.getElementById("comms").getInnerText();
            if (commsStr != "CommunicationChannel") {

            }
            // var userMoves = commsStr.split(",");
            // int stone1 =  Integer.valueOf(userMoves[userMoves.length-2].split(":")[0]);
            // int stone2 =  Integer.valueOf(userMoves[userMoves.length-1].split(":")[0]);
            System.out.println("HELLO WORLD");
            // System.out.println(stone1);
            // System.out.println(stone2);
            System.out.println(commsStr);
            // meinCtrl.tryMove(stone1, stone2, 0);
            // Get computer moves and update buffer 
            meinCtrl.cur.anaPlay(2, 2, MeinCtrl.OPT_DEFEND);
            document.getElementById("comms").setInnerHTML(meinCtrl.curGame.getMovesEncoded());
        });
    }
}
