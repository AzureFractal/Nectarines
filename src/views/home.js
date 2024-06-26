// @ is an alias to /src
import { mapState } from 'vuex'
import Board from '@/components/Board.vue'

import {
  ADD_CHESSMAN,
  SET_STATUS,
  RESET_BOARD,
  FORWARD,
  BACKWARD,
  SET_FIRST
} from '@/store/mutations'

import * as STATUS from '@/status.js'

export default {
  name: 'home',
  data() {
    return {
      bigText: '',
      score: 0,
      step: -1,
      lastScore: 0,
      startTime: +new Date()
    }
  },
  created () {
    const boardLengthToBeRefactored = 19;

    console.log("Initializing for the first time by adding the MutationObserver");
    const outerThis = this;
    this.$store.dispatch(SET_STATUS, STATUS.PLAYING)

    // Select the element by its ID ("comms")
    const targetElement = document.getElementById('comms')

    // Options for the observer (which mutations to observe)
    const config = { attributes: true, childList: true, subtree: true }

    // Callback function to execute when changes are detected
    const callback = function(mutationsList, observer) {
      const commsLengthPerEntry = 7;
      const element = document.getElementById("comms").innerText;
      console.log("Hi I read the comms of length", element.length);

      // Have to do this because steps is read-only
      while (outerThis.steps.length>0) {
        outerThis.steps.pop();
      }
      outerThis.$store.dispatch("RESET_BOARD");

      for (let i = 0; i < element.length / commsLengthPerEntry; i++) {
        const entryPosition = parseInt(element.substr(i * commsLengthPerEntry, 4), 10)
        const entryRole = parseInt(element.substr(i * commsLengthPerEntry + 5, 1), 10)
        const x = entryPosition % boardLengthToBeRefactored
        const y = Math.floor(entryPosition / boardLengthToBeRefactored)

        outerThis.$store.dispatch('ADD_CHESSMAN', {
          position: [y, x],
          role: entryRole
        })
      }
    }

    // Create Mutation Observer, and observe
    const observer = new MutationObserver(callback);
    observer.observe(targetElement, config);



    // Callback function to execute when changes are detected
    const callbackHint = function(mutationsList, observer) {
      const element = document.getElementById("commsHint").innerText;
      const hintMoveArray = element.split(",");

      // Have to do this because hints is read-only
      while (outerThis.hints.length>0) {
        outerThis.hints.pop();
      }

      if (element !== "") {
        for (let i = 0; i < hintMoveArray.length; i++) {
          const entryPosition = parseInt(hintMoveArray[i], 10);
          const entryRole = parseInt("1", 10);
          const x = Math.floor(entryPosition / boardLengthToBeRefactored);
          const y = entryPosition % boardLengthToBeRefactored;
          outerThis.hints.push({x, y, entryRole});
        }
      }
    };

    // Create Mutation Observer, and observe
    const observerHint = new MutationObserver(callbackHint);
    observerHint.observe(document.getElementById('commsHint'), config);
  },
  components: {
    Board
  },
  computed: {
    statusText() {
      if (this.status === STATUS.LOADING) {
        return 'Loading...'
      } else if (this.status === STATUS.READY) {
        return 'Ready'
      } else if (this.status === STATUS.THINKING) {
        return 'AI is thinking...'
      } else if (this.status === STATUS.PLAYING) {
        return 'is playing (your turn)'
      } else return 'Loading...'
    },
    ...mapState({
      board: (state) => state.board.board,
      steps: (state) => state.board.steps,
      stepsTail: (state) => state.board.stepsTail,
      hints: state => state.board.hints,
      status: (state) => state.home.status,
      current_player: (state) => state.home.current_player,
      first: (state) => state.home.first,
      randomOpening: (state) => state.home.randomOpening,
      version: 'version'
    })
  },
  methods: {
    biggerText: false,
    showStartDialog() {
      if (this.status !== status.READY) return false
      this.$refs.offensive.open()
    },
    showGiveDialog() {
      if (this.status !== status.PLAYING) return false
      this.$refs.give.open()
    },
    start(first) {
      this.$refs.offensive.close()
      this.$store.dispatch(SET_FIRST, first)
      this.$store.dispatch(RESET_BOARD)
      this.showBigText('START!', () => {
        this.worker.postMessage({
          type: 'START',
          first: first === 1,
          randomOpening: this.randomOpening
        })
        //if (first === 1 && !this.randomOpening) {
        //  this.worker.postMessage({
        //    type: "BEGIN"
        //  });
        //}
        this.$store.dispatch(SET_STATUS, status.PLAYING)
      })
    },
    end() {
      this.$store.dispatch(SET_STATUS, status.READY)
    },

    forward() {
      if (!this.canForward()) return false
      this.$store.dispatch(FORWARD)
      this.worker.postMessage({
        type: 'FORWARD'
      })
    },

    backward() {
      if (!this.canBackward()) return false
      this.$store.dispatch(BACKWARD)
      this.worker.postMessage({
        type: 'BACKWARD'
      })
    },

    showBigText(title, callback) {
      this.bigText = title
      this.$refs.big.open()
      setTimeout(() => {
        this.$refs.big.close()
      }, 1000)
      setTimeout(() => {
        callback && callback.call(this)
      }, 1500)
    },
    _set(position, role) {
      this.$store.dispatch(ADD_CHESSMAN, {
        position: position,
        role: role
      })
    },

    // 0 if black 1 if white's turn
    getCurPlayer(numSteps) {
      return Math.floor((numSteps + 1) / 2) % 2
    },

    // Button functions
    undoMove() {
      var commsArr = document.getElementById('comms').innerText.replace(/,$/, "").split(',')
      var trimLength = (commsArr.length % 2 === 0) ? 1 : 2;
      document.getElementById('comms').innerText = commsArr.slice(0, commsArr.length - trimLength).join(',')
    },
    _callInner(innerBtnId) {
      var clickEvent = new Event('click')
      document.getElementById(innerBtnId).dispatchEvent(clickEvent)
      this.$store.commit(SET_STATUS, STATUS.PLAYING)
    },
    _sleep(ms) {
      return new Promise((resolve) => setTimeout(resolve, ms))
    },
    callInner(innerBtnId) {
      this.$store.commit(SET_STATUS, STATUS.THINKING)
      this._sleep(100).then(() => this._callInner(innerBtnId))
    },

    set(position) {
      console.log(position)
      const x = position[0]
      const y = position[1]
      if (this.board[x][y] !== 0) {
        throw new Error('BOARD SQUARE NOT EMPTY')
      }

      this._set(position, this.current_player)
      this.startTime = +new Date()

      const boardSize = 19
      let tempString = ''
      for (let i = 0; i < this.steps.length; i++) {
        tempString += (this.steps[i].position[0] * boardSize + this.steps[i].position[1])
          .toString()
          .padStart(4, '0')
        tempString += ':'
        tempString += (this.getCurPlayer(i) + 1).toString()
        tempString += ','
      }
      document.getElementById('comms').innerText = tempString

      // if user placed both stones, call calculate as callback
      if (this.steps.length > 2) {
        let last_2_moves = tempString.split(',').slice(0,-1).slice(-2)
        if (last_2_moves[0].slice(-1) == last_2_moves[1].slice(-1)) {
          this.callInner('calculate')
        }
      }
    },

    canBackward() {
      return this.status === status.PLAYING && this.steps.length >= 2
    },
    canForward() {
      return this.status === status.PLAYING && this.stepsTail.length >= 2
    },
  }
}
