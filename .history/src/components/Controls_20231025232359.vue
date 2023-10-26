<template>
    <div
      @click="click"
      ref="controls"
      class="controls">
    <div>
      <b-button id="calculate" variant="success">Calculate</b-button>
      <b-button id="undo" variant="danger" @click="undoMove">Undo</b-button>
    </div>
    </div>
</template>
<script>
export default {
  methods: {
    undoMove() {
      var comms_string = document.getElementById('comms').innerText;
      console.log(comms_string.split(",").slice(0,comms_string.length-2))
      document.getElementById('comms').innerText = comms_string.split(",").slice(0,comms_string.length-2);
    }
  }
}
</script>

<style scoped>
button {
  margin: 5px;
}
</style>