<template>
  <div :class="'board first-' + first">
    <div @click="click" ref="board" class="board-inner">
      <div>
        <div v-for="(row, rowIndex) in board" :key="rowIndex">
          <div v-for="(c, cIndex) in row" :key="cIndex">
            <h3
                    v-if="!!c"
                    @click="clickChessman"
                    :class="'chessman ' + (c === 1 ? 'black' : 'white')"
                    :style="{
              marginTop: (0.94 + rowIndex*5.210) + '%',
              marginLeft: (0.94 + cIndex*5.210) + '%',
              }"
            ></h3>
          </div>
        </div>
      </div>
      <div v-if="showSteps">
        <div v-for="(s, index) in steps" :key="index">
          <div
                  @click="clickChessman"
                  :class="'step ' + (s.role === 1 ? 'black' : 'white')"
                  :style="{
          marginTop: (0.94-0.07 + s.position[0]*5.210) + '%',
          marginLeft: (0.94 + s.position[1]*5.210) + '%'
          }">
            {{ Math.floor((index - 1) / 2) + 2 }}
          </div>
        </div>
      </div>
      <div v-for="(s, index) in hints" :key="index">
        <div
                :class="'hint ' + (s.entryRole === 1 ? 'black' : 'white')"
                :style="{
        marginTop: (0.94 + s.x*5.210) + '%',
        marginLeft: (0.94 + s.y*5.210) + '%',
        opacity:0.3*(1-index/20.0)+0.1,
        }">
          {{}}
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  name: 'board',
  
  computed: {
    ...mapState({
      board: state => state.board.board,
      steps: state => state.board.steps,
      hints: state => state.board.hints,
      showSteps: state => state.home.showSteps,
      first: state => state.home.first,
      fives: state => state.board.fives
    })
  },
  methods: {
    click (e) {
      let y = e.offsetX,
          x = e.offsetY,
          width = this.$refs.board.clientWidth,
          offset = width*0.046, // Old 0.032
          step = width*0.0520; // Old 0.0515
      x = Math.floor((x+offset)/step) - 1
      y = Math.floor((y+offset)/step) - 1
      console.log(x, y, e.offsetX, e.offsetY);
      this.$emit('set', [x, y])
    },
    clickChessman (e) {
      e.preventDefault()
      e.stopPropagation()
    },
  }
}
</script>
<style lang="scss" scoped>
.board-inner {
  width: 42rem;
  height: 42rem;
  margin: 0 auto;
  position: relative;
  background-image: url("../assets/board19.png");
  background-size: 100%;
}
.chessman, .step, .hint {
  position: absolute;
  width: 1.85rem;
  height: 1.85rem;
  line-height: 1.85rem;
  text-align: center;
  border-radius: 50%;
  font-size: 1.1rem;
  user-select: none;
}
.chessman {
  top: 0;
  bottom: 0;
  background-color: black;

  &.white {
    background-color: white;
  }
}

.step {
  color: white;
  &.white {
    color: black;
  }
}

.hint {
  /*background-color: white;*/
  /*&.white {*/
  /*  background-color: black;*/
  /*}*/
  /*animation: pulse 2.0s infinite;*/
  background-color: #00e2ff;
  opacity: 0.2;
  pointer-events: none;
}

.first-2 {
  .chessman {
    background-color: white;
    &.white {
      background-color: black;
    }
  }
  .step {
    color: black;
    &.white {
      color: white;
    }
  }
}

.last-step {
  box-shadow: 0 0 0 .4rem rgba(255, 0, 0, 0.4);
  animation: pulse 1.2s infinite;
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(255, 0, 0, 0.8);
  }
  70% {
    box-shadow: 0 0 0 .6rem rgba(255, 0, 0, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(255, 0, 0, 0);
  }
}

.fives {
  animation: flash .8s infinite;
  box-shadow: none;
}

@keyframes flash {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}
</style>