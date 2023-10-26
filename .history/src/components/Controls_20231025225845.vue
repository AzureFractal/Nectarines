<template>
    <div
      @click="click"
      ref="controls"
      class="controls">
    <div>
      <b-button id="calculate" variant="success">Calculate</b-button>
      <b-button id="undo" variant="danger">Undo</b-button>
    </div>
    </div>
</template>

<style scoped>
button {
  margin: 10px;
}
</style>