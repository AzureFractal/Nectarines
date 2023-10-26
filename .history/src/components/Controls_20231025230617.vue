<template>
    <div
      @click="click"
      ref="controls"
      class="controls">
    <div>
      <b-button id="calculate" variant="success">Calculate</b-button>
      <b-button id="undo" variant="danger" @click="buttonClick">Undo</b-button>
    </div>
    </div>
</template>
<script>
</script>

<style scoped>
button {
  margin: 5px;
}
</style>