<template>
  <div :class="'board first-' + first ">
    <div
      @click="click"
      ref="board"
      class="board-inner">
      <div>
        <div v-for="(row, rowIndex) in board" :key="rowIndex">
          <div
            v-for="(c, cIndex) in row">
            <h3
            v-if="!!c"
            :key="cIndex"
            @click="clickChessman"
            :class="'chessman ' + (c === 1 ? 'black ' : 'white ')"
            :style="{
              marginTop: (0.94 + rowIndex*5.210) + '%',
              marginLeft: (0.94 + cIndex*5.210) + '%',
              }">
              </h3>
          </div>
        </div>
      </div>
      <div
        v-if="showSteps"
        v-for="(s, index) in steps"
        :key="index"
        @click="clickChessman"
        :class="'step ' + (s.role === 1 ? 'black' : 'white')"
        :style="{
          marginTop: (0.94-0.07 + s.position[0]*5.210) + '%',
          marginLeft: (0.94 + s.position[1]*5.210) + '%'
          }">
          {{Math.floor((index-1)/2)+2}}
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
          offset = width*0.032, // Old 0.044
          step = width*0.0515; // Old 0.065
      x = Math.floor((x+offset)/step) - 1
      y = Math.floor((y+offset)/step) - 1
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
.chessman, .step {
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