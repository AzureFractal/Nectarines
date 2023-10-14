<template>
    <div
      @click="click"
      ref="board"
      class="board-inner">
    </div>
</template>

<style scoped>
.board-inner {
  width: 37rem;
  height: 37rem;
  margin: 0 auto;
  position: relative;
  background-image: url("../assets/board.jpg");
  background-size: 100%;
}
</style>