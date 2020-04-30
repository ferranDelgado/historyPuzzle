const state = {
    isConnected: false,
    players: []
};

const mutations = {
    IS_CONNECTED(state, isConnected) {
        state.connected = isConnected
    },
    ADD_PLAYER(state, player) {
        state.players.splice(0,0, {id: player})
    }
};

const actions = {
    socketUnknownEvent({commit}, data) {
        console.log(commit);
        console.log(data)
    },
    userConnected({commit}, data) {
        console.log(data);
        commit('ADD_PLAYER', data.userName);
    }
};

export default {
    state,
    mutations,
    actions
}
