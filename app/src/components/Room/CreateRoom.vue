<template>
    <div id="create-room-form">
        <form v-on:submit.prevent="">
            <div class="field">
                <label for="create-room-name-input" class="label">Name</label>
                <div class="control">
                    <input id="create-room-name-input" class="input" type="text" placeholder="Text input" v-model="name">
                </div>
            </div>
            <div class="field is-grouped">
                <div class="control">
                    <button class="button is-link" @click="create" v-bind:disabled="name === ''">Create</button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
  export default {
    name: 'room-form',
    data() {
      return {
        name: ''
      };
    },
    methods: {
      create() {
        console.log(`Go to ${this.name}`);
        this.$router.push(`room/${this.name}`)
      }
    }
  }
</script>

<style scoped>
    #create-room-form {
        width: 50%;
        margin: 0 auto;
        min-width: 200px;
    }
</style>