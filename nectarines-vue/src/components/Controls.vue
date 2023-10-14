<template>
    <div
      @click="click"
      ref="controls"
      class="controls">
    <div>
      <b-button id="calculate">Calculate</b-button>
    </div>
    </div>
</template>

<!--This seems to be the same in Controls.vue and Board.vue-->
<style scoped>
.board-inner {
  width: 42rem;
  height: 42rem;
  margin: 0 auto;
  position: relative;
  background-image: url("../assets/board19.png");
  background-size: 100%;
}
</style>