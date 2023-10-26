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
      var commsArr = document.getElementById('comms').innerText.split(",");
      console.log(commsArr.slice(0,commsArr.length-3))
      document.getElementById('comms').innerText = commsArr.split(",").slice(0,commsArr.length-3);
    }
  }
}
</script>

<style scoped>
button {
  margin: 5px;
}
</style>