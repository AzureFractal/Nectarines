import {
  SET_LANG,
  SET_SHOW_STEPS,
  SET_STATUS,
  SET_FIRST,
  SET_RANDOM,
  CHANGE_PLAYER
} from '../mutations.js'

import * as status from '@/status.js'

const state = {
  lang: 'zh',
  deep: 8,
  showSteps: true,
  spread: true,
  status: status.LOADING,
  current_player: 1,
  first: 1,
  randomOpening: false,
  deepList: [{
    value: 4,
    title: 'idiot'
  }, {
    value: 6,
    title: 'easy'
  }, {
    value: 8,
    title: 'normal'
  }]
}

const getters = {
  lang: state => state.lang,
  status: state => state.status,
  current_player: state => state.current_player,
  showSteps: state => state.showSteps,
  first: state => state.first,
  randomOpening: state => state.randomOpening
}

const mutations = {
  [SET_LANG] (state, lang) {
    state.lang = lang
  },
  [SET_SHOW_STEPS] (state, show) {
    state.showSteps = show
  },
  [SET_STATUS] (state, status) {
    state.status = status
  },
  [SET_FIRST] (state, first) {
    state.first = first
  },
  [SET_RANDOM] (state, random) {
    state.randomOpening = random
  },
  [CHANGE_PLAYER] (state) {
    state.current_player = (state.current_player === 1) ? 2 : 1
  }
}

const actions = {
  [SET_LANG] ({commit}, lang) {
    commit(SET_LANG, lang)
  },
  [SET_SHOW_STEPS] ({commit}, show) {
    commit(SET_SHOW_STEPS, show)
  },
  [SET_STATUS] ({commit}, status) {
    commit(SET_STATUS, status)
  },
  [SET_FIRST] ({commit}, first) {
    commit(SET_FIRST, first)
  },
  [SET_RANDOM] ({commit}, s) {
    commit(SET_RANDOM, s)
  },
  [CHANGE_PLAYER] ({commit}) {
    commit(CHANGE_PLAYER)
  }
}

export default {
  namespace: true,
  state,
  getters,
  actions,
  mutations
}