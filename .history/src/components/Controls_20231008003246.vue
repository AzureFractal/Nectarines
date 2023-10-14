import Vue from 'vue'
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

<template>
    <div
      @click="click"
      ref="controls"
      class="controls">
    <div>
      <b-button>Button</b-button>
      <b-button variant="danger">Button</b-button>
      <b-button variant="success">Button</b-button>
      <b-button variant="outline-primary">Button</b-button>
    </div>
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