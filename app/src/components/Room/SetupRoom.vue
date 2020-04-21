<template>
    <div id="setup-room-form">
        <div class="columns">
            <div class="column">
                <h2 class="title">Room: {{name}}</h2>
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
            </div>
        </div>

    </div>
</template>

<script>
  export default {
    name: 'room-form',
    data() {
      return {
        name: this.$route.params.roomName
      };
    },
    methods: {
      beginGame() {
        console.log(`Start play ${this.name}`);
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