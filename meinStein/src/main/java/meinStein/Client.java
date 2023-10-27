package meinStein;

import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

import java.util.Arrays;

import static meinStein.MeinCtrl.NUM_FEAT;

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

            if (meinCtrl.cur.num[stone1] == 0 && meinCtrl.cur.num[stone2] == 0) {
                int col = ((userMoves.length - 1) / 2) % 2 == 0 ? MeinCtrl.black : MeinCtrl.white;
                // This chunk must be run before actually playing the move, otherwise evalTwoSq will erase the move
                MeinCtrl.Move combinedMove = meinCtrl.cur.evalTwoSq(col, stone1, stone2);
                for (int i = 0; i < NUM_FEAT; i++) {
                    if (col == MeinCtrl.black) {
                        MeinCtrl.runningFeatCountBlack[i] += combinedMove.featCountPlayer[i];
                        MeinCtrl.runningFeatCountWhite[i] += combinedMove.featCountOpp[i];
                    } else {
                        MeinCtrl.runningFeatCountWhite[i] += combinedMove.featCountPlayer[i];
                        MeinCtrl.runningFeatCountBlack[i] += combinedMove.featCountOpp[i];
                    }

                    System.out.println(MeinCtrl.chainType[i] + ":" + MeinCtrl.runningFeatCountBlack[i] + "," + MeinCtrl.runningFeatCountWhite[i]);
                }

                meinCtrl.tryMove(stone1, stone2);
                meinCtrl.tryMove(stone2, stone1);
            }
        }
        System.out.println("Depth qDepth:" + depth + "," + quietDepth);
        // Get computer moves and update buffer
        int score = meinCtrl.cur.anaPlay(depth, quietDepth, MeinCtrl.OPT_DEFEND);
        document.getElementById("comms").setInnerHTML(meinCtrl.curGame.getMovesEncoded());
        document.getElementById("comms1").setInnerHTML("Done");
        // For now we need a negative sign here because the score is for the AI
        document.getElementById("scoreText").setInnerHTML("Score:" + (-score) + " (+ve is good 4 u u r happy and healthy)");

        String chainCountText = "";
        for (int i = 0; i < NUM_FEAT; i++) {
            chainCountText += MeinCtrl.chainType[i] + ":";
            chainCountText += MeinCtrl.runningFeatCountBlack[i] + ",";
            chainCountText += MeinCtrl.runningFeatCountWhite[i] + "\n";
        }
        document.getElementById("chainCount").setInnerHTML(chainCountText);


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
