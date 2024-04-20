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

        // Backtrack to latest common ancestor
        while (!meinCtrl.curGame.getMovesEncoded().equals(commsStr) && meinCtrl.curGame.ply > 1) {
            meinCtrl.takeBack(0);
        }

        // Extract new moves
        String[] newMoves = commsStr.substring(meinCtrl.curGame.getMovesEncoded().length()).split(",");

        // Do length - 1 here because there is a trailing ","
        // Play new moves
        for (int i = 0; i < newMoves.length - 1; i+=2) {
            int stone1 =  Integer.parseInt(newMoves[i].split(":")[0]);
            int stone2 =  Integer.parseInt(newMoves[i+1].split(":")[0]);

            if (meinCtrl.cur.num[stone1] == 0 && meinCtrl.cur.num[stone2] == 0) {
//                int col = ((newMoves.length - 1) / 2) % 2 == 0 ? MeinCtrl.black : MeinCtrl.white;
//                // This chunk must be run before actually playing the move, otherwise evalTwoSq will erase the move
//                MeinCtrl.Move combinedMove = meinCtrl.cur.evalTwoSq(col, stone1, stone2);
//                for (int j = 0; j < NUM_FEAT; j++) {
//                    if (col == MeinCtrl.black) {
//                        MeinCtrl.runningFeatCountBlack[j] += combinedMove.featCountPlayer[j];
//                        MeinCtrl.runningFeatCountWhite[j] += combinedMove.featCountOpp[j];
//                    } else {
//                        MeinCtrl.runningFeatCountWhite[j] += combinedMove.featCountPlayer[j];
//                        MeinCtrl.runningFeatCountBlack[j] += combinedMove.featCountOpp[j];
//                    }
//
//                    System.out.println(MeinCtrl.chainType[j] + ":" + MeinCtrl.runningFeatCountBlack[j] + "," + MeinCtrl.runningFeatCountWhite[j]);
//                }

                meinCtrl.tryMove(stone1, stone2);
            }
        }

        System.out.println("Depth qDepth:" + depth + "," + quietDepth);
        // Get computer moves and update buffer
        int score = meinCtrl.cur.anaPlay(depth, quietDepth);
        document.getElementById("comms").setInnerHTML(meinCtrl.curGame.getMovesEncoded());
        document.getElementById("comms1").setInnerHTML("Done");
        // For now we need a negative sign here because the score is for the AI
        document.getElementById("scoreText").setInnerHTML("Your Score: " + (-score) +
                " <br> Your win prob: " +
                Math.round((1.0 / (1.0+Math.exp(-0.001*-score)))*100)  + "%");

//        String chainCountText = "";
//        for (int i = 1; i < NUM_FEAT; i++) {
//            chainCountText += MeinCtrl.chainType[i] + ":";
//            chainCountText += MeinCtrl.runningFeatCountBlack[i] + ",";
//            chainCountText += MeinCtrl.runningFeatCountWhite[i] + "\n";
//        }
//        document.getElementById("chainCount").setInnerHTML(chainCountText);


        if (score == -MeinCtrl.posVal[MeinCtrl.DONE6]){
            document.getElementById("comms1").setInnerHTML("Black Won!");
        } else if (score == MeinCtrl.posVal[MeinCtrl.DONE6]) {
            document.getElementById("comms1").setInnerHTML("White Won!");
        }
        document.getElementById("commsHint").setInnerHTML("");

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
        // Display default depth/quietDepth
        updateDifficulty(document, Client.depth, Client.quietDepth);

        HTMLElement calcElem = document.getElementById("calculate");
        calcElem.addEventListener("click", (e)->{
            readCommsAndPlayMove(document);
        });

        HTMLElement engineElem = document.getElementById("engine");
        engineElem.addEventListener("click", (e)->{
            for (int i = 0; i < 20; i++) {
                int score = readCommsAndPlayMove(document);
                if (score == -MeinCtrl.posVal[MeinCtrl.DONE6] || score == MeinCtrl.posVal[MeinCtrl.DONE6]) {
                    break;
                }
            }
        });

        HTMLElement hintElem = document.getElementById("hint");
        hintElem.addEventListener("click", (e)->{
            meinCtrl.cur.listEval(meinCtrl.cur.getColToMove());
            String result = "";
            for (int i = 0; i < meinCtrl.cur.listLen[1]; i++) {
                if (i != 0) {
                    result += ",";
                }
                result += meinCtrl.cur.listMoves[i].i1;
                result += ":";
                result += meinCtrl.cur.listMoves[i].score;
            }
            document.getElementById("commsHint").setInnerHTML(result);
        });

        HTMLElement elem;
        elem = document.getElementById("diff1");
        elem.addEventListener("click", (e)->{
            updateDifficulty(document, 1, 1);
        });

        elem = document.getElementById("diff2");
        elem.addEventListener("click", (e)->{
            updateDifficulty(document, 2, 2);
        });

        elem = document.getElementById("diff3");
        elem.addEventListener("click", (e)->{
            updateDifficulty(document, 3, 3);
        });

        elem = document.getElementById("diff4");
        elem.addEventListener("click", (e)->{
            updateDifficulty(document, 4, 4);
        });
    }
}
