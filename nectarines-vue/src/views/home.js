// @ is an alias to /src
import { mapState } from 'vuex'
import Board from '@/components/Board.vue'

import {
  ADD_CHESSMAN,
  SET_STATUS,
  SET_BOARD,
  SET_STEPS,
  CHANGE_PLAYER,
  RESET_BOARD,
  FORWARD,
  BACKWARD,
  SET_FIRST,
  SET_FIVES
} from '@/store/mutations'

export default {
  name: 'home',
  data () {
    return {
      bigText: '',
      score: 0,
      step: -1,
      lastScore: 0,
      startTime: + new Date()
    }
  },
  created () {
  },
  components: {
    Board,
  },
  computed: {
    ...mapState({
      board: state => state.board.board,
      steps: state => state.board.steps,
      stepsTail: state => state.board.stepsTail,
      status: state => state.home.status,
      current_player: state => state.home.current_player,
      first: state => state.home.first,
      randomOpening: state => state.home.randomOpening,
      version: 'version'
    })
  },
  methods: {
    biggerText: false,
    showStartDialog () {
      if (this.status !== status.READY) return false
      this.$refs.offensive.open()
    },
    showGiveDialog () {
      if (this.status !== status.PLAYING) return false
      this.$refs.give.open()
    },
    start (first) {
      this.$refs.offensive.close()
      this.$store.dispatch(SET_status, status.LOCKED)
      this.$store.dispatch(SET_FIRST, first)
      this.$store.dispatch(RESET_BOARD)
      this.showBigText('START!', () => {
        this.worker.postMessage({
          type: "START",
          first: first === 1,
          randomOpening: this.randomOpening
        });
      //if (first === 1 && !this.randomOpening) {
      //  this.worker.postMessage({
      //    type: "BEGIN"
      //  });
      //}
        this.$store.dispatch(SET_status, status.PLAYING)
      })
    },
    end () {
      this.$store.dispatch(SET_status, status.READY)
    },

    forward () {
      if (!this.canForward()) return false
      this.$store.dispatch(FORWARD)
      this.worker.postMessage({
        type: "FORWARD"
      });
    },

    backward() {
      if (!this.canBackward()) return false
      this.$store.dispatch(BACKWARD)
      this.worker.postMessage({
        type: "BACKWARD"
      });
    },
    give () {
      this.$store.dispatch(SET_status, status.LOCKED)
      this.$refs.give.close()
      this.showBigText(this.$t('you lose'), () => {
        this.end()
      })
    },

    showBigText (title, callback) {
      this.bigText = title
      this.$refs.big.open()
      setTimeout(() => {
        this.$refs.big.close()
      }, 1000)
      setTimeout(() => {
        callback && callback.call(this)
      }, 1500)
    },
    _set (position, role) {
      this.$store.dispatch(ADD_CHESSMAN, {
        position: position,
        role: role
      })
      this.$store.dispatch(CHANGE_PLAYER)
    },
   
    set (position) {
      if (this.biggerText !== true) {
        console.log("Initializing for the first time by adding the MutationObserver");
        const outerThis = this;

        // Select the element by its ID ("comms")
        const targetElement = document.getElementById('comms');

        // Options for the observer (which mutations to observe)
        const config = { attributes: true, childList: true, subtree: true };

        // Callback function to execute when changes are detected
        const callback = function(mutationsList, observer) {
          const commsLengthPerEntry = 4;
          const boardLengthToBeRefactored = 19;
          const element = document.getElementById("comms").innerText;
          console.log("Hi I read the comms of length", element.length);

          for (let i = 0; i < element.length / commsLengthPerEntry; i++) {
            const x = i % boardLengthToBeRefactored;
            const y = Math.floor(i / boardLengthToBeRefactored);
            const entry = parseInt(element.substr(i*commsLengthPerEntry, commsLengthPerEntry), 10);
            outerThis.board[y][x] = entry === 0 ? 0 : ((entry + 1) % 2) + 1;
          }
        };

        // Create a new Mutation Observer
        const observer = new MutationObserver(callback);

        // Start observing the target element
        observer.observe(targetElement, config);

        this.biggerText = true;
      }

      console.log(position);
      const x = position[0]
      const y = position[1]
      if(this.board[x][y] !== 0) {
        throw new Error("NOT_EMPTY")
      }
      
      this._set(position, this.current_player)
      this.startTime = + new Date()
    },

    canBackward () {
      return this.status === status.PLAYING && this.steps.length >= 2
    },
    canForward () {
      return this.status === status.PLAYING && this.stepsTail.length >= 2
    },
  }
}