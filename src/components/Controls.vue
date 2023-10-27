<template>
    <div
      @click="click"
      ref="controls"
      class="controls">
    <div>
      <b-button id="calculate" variant="success">Calculate</b-button>
      <b-button id="undo" variant="danger" @click="undoMove">Undo</b-button>
      <b-button id="engine" variant="info">Engine</b-button>
        <b-button id="diff1" variant="secondary">Diff 1</b-button>
        <b-button id="diff2" variant="secondary">Diff 2</b-button>
        <b-button id="diff3" variant="secondary">Diff 3</b-button>
        <b-button id="diff4" variant="secondary">Diff 4</b-button>
    </div>
    </div>
</template>
<script>
export default {
  methods: {
    undoMove() {
      var commsArr = document.getElementById('comms').innerText.split(",");
      console.log(commsArr.slice(0,commsArr.length-2))
      document.getElementById('comms').innerText = commsArr.slice(0,commsArr.length-2).join(",");
    }
  }
}
</script>

<style scoped>
button {
  margin: 5px;
}
</style>