// @ is an alias to /src
import { mapState } from 'vuex'
import Board from '@/components/Board.vue'

import {
  ADD_CHESSMAN,
  SET_STATUS,
  SET_BOARD,
  SET_STEPS,
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
      deep: state => state.home.deep,
      spread: state => state.home.spread,
      first: state => state.home.first,
      randomOpening: state => state.home.randomOpening,
      version: 'version'
    })
  },
  watch: {
    deep () {
      this.updateConfig()
    },
    spread () {
      this.updateConfig()
    }
  },
  methods: {
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
    },
   
    set (position) {
      console.log(position);
      const x = position[0]
      const y = position[1]
      if(this.board[x][y] !== 0) {
        throw new Error("NOT_EMPTY")
      }
      
      this._set(position, 2)

      this.worker.postMessage({
        type: "GO",
        x: x,
        y: y
      })
      this.$store.dispatch(SET_status, status.THINKING)
      this.startTime = + new Date()
    },

    canBackward () {
      return this.status === status.PLAYING && this.steps.length >= 2
    },
    canForward () {
      return this.status === status.PLAYING && this.stepsTail.length >= 2
    },
    updateConfig () {
      this.worker.postMessage({
        type: 'CONFIG',
        config: {
          searchDeep: this.deep,
          spread: this.spread
        }
      })
    }
  }
}