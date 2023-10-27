package meinStein;

import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

public class Client {
    private static MeinCtrl meinCtrl;
    private static int depth = 2;
    private static int quietDepth = 2;

    public static int readCommsAndPlayMove(HTMLDocument document) {
        document.getElementById("comms1").setInnerHTML("Thinking");
        String commsStr = document.getElementById("comms").getInnerText();
        String[] userMoves = commsStr.split(",");
        // Play user moves from buffer
        if (userMoves.length > 1) {
            int stone1 =  Integer.parseInt(userMoves[userMoves.length-2].split(":")[0]);
            int stone2 =  Integer.parseInt(userMoves[userMoves.length-1].split(":")[0]);
            meinCtrl.tryMove(stone1, stone2, 0);
            meinCtrl.tryMove(stone2, stone1, 0);
        }
        System.out.println("Depth qDepth:" + depth + "," + quietDepth);
        // Get computer moves and update buffer
        int score = meinCtrl.cur.anaPlay(depth, quietDepth, MeinCtrl.OPT_DEFEND);
        document.getElementById("comms").setInnerHTML(meinCtrl.curGame.getMovesEncoded());
        document.getElementById("comms1").setInnerHTML("Done");
        // For now we need a negative sign here because the score is for the AI
        document.getElementById("bestMoves").setInnerHTML("Score:" + (-score) + " (+ve is good 4 u u r happy and healthy)");

        if (-MeinCtrl.posVal[MeinCtrl.DONE6] == score){
            document.getElementById("comms1").setInnerHTML("Black Won!");
        } else if (score == MeinCtrl.posVal[MeinCtrl.DONE6]) {
            document.getElementById("comms1").setInnerHTML("White Won!");
        }

        return score;
    }

    public static void updateDifficulty(HTMLDocument document, int depth, int quietDepth) {
        Client.depth = depth;
        Client.quietDepth = quietDepth;
        document.getElementById("difficultyText").setInnerHTML("Difficulty:" + Client.depth + "/" + Client.quietDepth);
    }

    public static void main(String[] args) {
        HTMLDocument document = HTMLDocument.current();
        meinCtrl = new MeinCtrl();
        // Render first piece
        document.getElementById("comms").setInnerHTML(meinCtrl.curGame.getMovesEncoded());

        HTMLElement calcElem = document.getElementById("calculate");
        calcElem.addEventListener("click", (e)->{
            readCommsAndPlayMove(document);
        });

        HTMLElement engineElem = document.getElementById("engine");
        engineElem.addEventListener("click", (e)->{
            for (int i = 0; i < 20; i++) {
                readCommsAndPlayMove(document);
            }
        });

        HTMLElement elem;
        elem = document.getElementById("diff1");
        elem.addEventListener("click", (e)->{
            updateDifficulty(document,1, 1);
        });

        elem = document.getElementById("diff2");
        elem.addEventListener("click", (e)->{
            updateDifficulty(document,2, 2);
        });

        elem = document.getElementById("diff3");
        elem.addEventListener("click", (e)->{
            updateDifficulty(document,3, 3);
        });

        elem = document.getElementById("diff4");
        elem.addEventListener("click", (e)->{
            updateDifficulty(document,4, 4);
        });
    }
}
