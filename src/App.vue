<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="wrapper">
      <div class="greetings">
        <h1 class="green">{{ 'Nectarines' }}</h1>
        <h3>Play Connect Six!</h3>
      </div>
    </div>
  </header>

  <RouterView />
  <!--  Actual text-->
  <div id="statusText" >Status Text</div>
  <div id="difficultyText" >Difficulty Text</div>
  <div id="scoreText" >Score Text</div>
  <div id="chainCount" class="invis">Chain Count</div>

  <!--  Communication channels-->
  <div id="comms" class="invis">CommunicationChannel</div>
  <div id="comms1">Communication Channel For Status</div>
  <div id="comms2" class="invis">Communication Channel For Best Moves</div>
  <div id="commsHint" class="invis"></div>
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.invis {
  display: none;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}

@media (min-width: 1024px) {
  header {
    display: flex;
    margin: auto;
    text-align: center;
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
