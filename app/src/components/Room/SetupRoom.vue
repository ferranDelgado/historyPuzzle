<template>
    <div id="setup-room-form">
        <div class="columns">
            <div class="column">
                <h2 class="title">Room: {{name}} Player: {{userName}}</h2>
                <div class="field">
                    <label for="setup-room-deck-input" class="label">Deck</label>
                    <div class="select">
                        <select id="setup-room-deck-input">
                            <option>Historical milestones</option>
                            <option>Religion</option>
                            <option>Science</option>
                        </select>
                    </div>
                </div>

                <button class="button is-link is-fullwidth" @click="beginGame">Play</button>
            </div>
            <div class="column">
                <h2 class="title">Players</h2>
                <h2 class="subtitle" v-if="isConnected">You are connected</h2>
                <h2 class="subtitle" v-else>You are not connected</h2>
                <ul>
                    <li v-for="player in connectedPlayers" :key="player.id">
                        {{ player.id }}
                    </li>
                </ul>
            </div>
        </div>

    </div>
</template>

<script>

  export default {
    name: 'room-form',
    data() {
      return {
        userName: '',
        name: this.$route.params.roomName,
        isConnected: false,
        socketMessage: ''
      };
    },
    mounted() {
      if(!localStorage.userName) {
        localStorage.userName = 'New User ' + (Math.random() * 1000 | 0)
      }

      this.userName = localStorage.userName;
      this.$webSocketsConnect(this.userName);
      console.log("Mounted");
    },
    methods: {
      beginGame() {
        console.log(`Start play ${this.name}`);
        this.$webSocketsSend({message: "hola"})
      }
    },
    computed: {
      connectedPlayers: function () {
        return this.$store.state.socket.players
      }
    }
  }
</script>

<style scoped>
    #setup-room-form {
        width: 50%;
        margin: 0 auto;
        min-width: 200px;
    }
</style>