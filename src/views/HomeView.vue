<script setup>
import Board from '../components/Board.vue'
import Controls from '../components/Controls.vue'
</script>

<template>
  <main>
  <div class="home">
    <Board @set="set"></Board>
 <div class="status">
      <div class="status-inner"><div :class="'chessman '+(first === 1 ? 'black' : 'white')"></div> {{statusText}}</div>
    </div>
    <Controls />
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
  padding: .5rem 0;
  background-color: #aaa;
  border-radius: 5px;
}

.chessman {
  display: inline-block;
  height: 1.5rem;
  width: 1.5rem;
  background-color: black;
  border-radius: 50%;
  vertical-align: -.20rem;
  margin-right: .5rem;

  &.white {
    background-color: white;
  }
}
</style>