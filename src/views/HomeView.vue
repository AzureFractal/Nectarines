<script setup>
import Board from '../components/Board.vue'
</script>

<template>
  <main>
    <div class="home">
      <Board @set="set"></Board>
      <div class="status">
        <div class="status-inner">
          <div :class="'chessman ' + (getCurPlayer(steps.length) ? 'white' : 'black')"></div>
          {{ statusText }}
        </div>
        <b-button id="outer-calculate" variant="success" @click="callInner('calculate')"
          >Calculate</b-button
        >
        <b-button id="calculate" variant="danger">Calculate (hidden)</b-button>
        <b-button id="undo" variant="danger" @click="undoMove">Undo</b-button>
        <b-button id="outer-engine" variant="info" @click="callInner('engine')">Engine</b-button>
        <b-button id="hint" variant="dark">Hint</b-button>
        <b-button id="engine" variant="danger">Engine (hidden)</b-button>
        <b-button id="diff1" variant="secondary">Easy</b-button>
        <b-button id="diff2" variant="secondary">Normal</b-button>
        <b-button id="diff3" variant="secondary">Hard</b-button>
        <b-button id="diff4" variant="secondary">Impossible</b-button>
      </div>
    </div>
  </main>
</template>

<script>
import Home from './home.js'
export default Home
</script>

<style lang="scss" scoped>
@import '../variables.scss';
h1 {
  color: $primary-color;
  font-size: 2.2rem;
  text-align: center;
  margin: 1.5rem 0;
}

.status {
  margin: 1.5rem 0;
  text-align: center;
}
.status-inner {
  max-width: 30rem;
  margin: 0 auto;
  padding: 0.5rem 0;
  background-color: #aaa;
  border-radius: 5px;
  color: black;
}

// hide the calculate and engine buttons
#calculate,
#engine, #outer-calculate, #outer-engine {
  display: None;
}

button {
  margin: 5px;
}

.chessman {
  display: inline-block;
  height: 1.5rem;
  width: 1.5rem;
  background-color: black;
  border-radius: 50%;
  vertical-align: -0.2rem;
  margin-right: 0.5rem;

  &.white {
    background-color: white;
  }
}
</style>
