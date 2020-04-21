<template>
    <div>
        <p>
            <b>Cient Id</b> {{ clientId }}
        </p>
        <button class="button" @click="connect">Connect</button>
        <button class="button" @click="disconnect">Disconnect</button>
        <button class="button" @click="talk">Say Hi</button>
        <p>
            {{ text }}
        </p>
    </div>
</template>

<script>
  let websocket;

  export default {
    data() {
      return {
        clientId: Math.random().toString(36).substr(2, 9),
        text: "Nothing here yet"
      }
    },
    methods: {
      connect() {
        const that = this;
        console.log("connect");
        this.text = "We are connected";
        websocket = new WebSocket( "ws://localhost:5050/data?clientId=" + this.clientId);
        websocket.onopen = function (evt) {
          console.log("On open event");
          console.log(evt)
        };
        websocket.onclose = function (evt) {
          console.log("On close event");
          console.log(evt)
        };
        websocket.onmessage = function (evt) {
          console.log(`Message ${evt.data}`);
          that.text = evt.data;
          console.log(evt)
        };
        websocket.onerror = function (evt) {
          console.log("On error event");
          console.log(evt)
        };
      },
      disconnect() {
        console.log("disconnect");
        websocket.close()
      },
      talk() {
        console.log("talk");
        websocket.send(`Hi ${new Date()}`)
      }
    }
  }
</script>