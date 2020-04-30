let webSocketService = {};


webSocketService.install = function (Vue, options) {
  console.log("Installing web socket.");

  let ws;
  let reconnectInterval = options.reconnectInterval || 1000;

  Vue.prototype.$webSocketsConnect = (username) => {
    options.url = options.url.replace("http", "ws");
    ws = new WebSocket(`${options.url}?userName=${username}`);

    ws.onopen = () => {
      // Restart reconnect interval
      reconnectInterval = options.reconnectInterval || 1000
    };

    ws.onmessage = (event) => {
      // New message from the backend - use JSON.parse(event.data)
      handleNotification(event)
    };

    ws.onclose = (event) => {
      if (event) {
        // Event.code 1000 is our normal close event
        if (event.code !== 1000) {
          let maxReconnectInterval = options.maxReconnectInterval || 3000;
          setTimeout(() => {
            if (reconnectInterval < maxReconnectInterval) {
              // Reconnect interval can't be > x seconds
              reconnectInterval += 1000
            }
            Vue.prototype.$webSocketsConnect()
          }, reconnectInterval)
        }
      }
    };

    ws.onerror = (error) => {
      console.log(error);
      ws.close()
    }
  };

  Vue.prototype.$webSocketsDisconnect = () => {
    // Our custom disconnect event
    ws.close()
  };

  Vue.prototype.$webSocketsSend = (data) => {
    // Send data to the backend - use JSON.stringify(data)
    ws.send(JSON.stringify(data))
  };

  /*
    Here we write our custom functions to not make a mess in one function
  */
  function handleNotification (params) {
    console.log(params);
    let event = JSON.parse(params.data);
    const eventType = event.type;
    console.log(`Event received ${eventType}`);
    if(eventType === "OPEN_EVENT") {
      options.store.dispatch('userConnected', event)
    } else {
      options.store.dispatch('socketUnknownEvent', params.data)
    }
  }
};

export default webSocketService