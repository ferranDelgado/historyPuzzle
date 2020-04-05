<template>
    <div v-drag-and-drop:options="options" class="drag-wrapper">
        <div>
            <ul id="discarded-cards" data-id="discarded-cards" @added="added">
                <li v-for="card in discardedCards" :key="card.id" :data-id="card.id">{{card.title}} [{{card.year}}]</li>
            </ul>
        </div>
        <div>
            <ul data-id="cards-in-board" @added="added">
                <li v-for="card in cardsInBoard" :key="card.id" :data-id="card.id">
                    {{card.title}} - [{{card.year}}]
                </li>
            </ul>
        </div>

        <div>
            <h2>Your hand</h2>
            <ul id="cards-in-hand-list" data-id="cards-in-hand" @added="added">
                <li v-for="card in cardsInHand" :key="card.id" :data-id="card.id">{{card.title}} [{{card.year}}]</li>
            </ul>
        </div>

    </div>
</template>

<script>

  export default {
    data() {
      const componentInstance = this;

      return {
        game: {
          cardsInBoard: [
            {id: 1, name: "World War I", year: 1945}
          ],
          cardsInHand: [
            {id: 2, name: "World War II", year: 1945},
            {id: 3, name: "Coronavirus", year: 2020}
          ]
        },
        options: {
          // dropzoneSelector: 'ul',
          // draggableSelector: 'li',
          // showDropzoneAreas: true,
          // multipleDropzonesItemsDraggingEnabled: true,
          // onDrop(event) {
          //   console.log(event);
          // },
          // onDragstart(event) {
          //   event.stop();
          // },
          onDragend(event) {
            // if you need to stop d&d
            // event.stop();

            // you can call component methods, just don't forget
            // that here `this` will not reference component scope,
            // so out from this returned data object make reference
            // to component instance
            componentInstance.someDummyMethod();

            // to detect if draggable element is dropped out
            if (!event.droptarget) {
              // console.log('event is dropped out');
            } else {
              // console.log(event)
            }
          }
        }
      }
    },
    created() {
      this.$store.dispatch('loadGame');
    },
    computed: {
      cardsInBoard: function () {
        return this.$store.state.cards.game.cardsInBoard
      },
      cardsInHand: function () {
        return this.$store.state.cards.game.cardsInHand
      },
      discardedCards: function () {
        return this.$store.state.cards.game.discardedCards;
      }
    },
    methods: {
      someDummyMethod() {
        // console.log('Hello from someDummyMethod');
      },
      added(event) {
        const id = event.detail.ids[0] | 0;
        const targetId = event.detail.droptarget.dataset.id;
        if (targetId === "cards-in-board") {
          const movement = {
            cardId: id,
            index: event.detail.index
          };
          this.$store.dispatch('moveCard', movement);
        } else {
          console.log("Wrong target")
        }
      }
    }
  };
</script>

<style scoped>
    /*#board-list {*/
    /*    !*height: 15px;*!*/
    /*}*/

    /*.drag-wrapper {*/
    /*    !*display: flex;*!*/
    /*    justify-content: center;*/
    /*}*/

    /*#discarded-cards {*/
    /*    background: #dc9b8f;*/
    /*}*/

    /*ul {*/
    /*    !*overflow-x: auto;*!*/
    /*    !*overflow-y: hidden;*!*/
    /*    !*display: flex;*!*/
    /*    !*flex-direction: row;*!*/
    /*    !*padding: 3px !important;*!*/
    /*    !*!*min-height: 75px;*!*!*/
    /*    !*!*min-height: 70vh;*!*!*/
    /*    !*!*width: 100px;*!*!*/
    /*    !*!*float: left;*!*!*/
    /*    !*list-style-type: none;*!*/
    /*    !*!*overflow-y: auto;*!*!*/
    /*    !*border: 2px solid #888;*!*/
    /*    !*border-radius: 0.2em;*!*/
    /*    !*background: #8adccc;*!*/
    /*    !*color: #555;*!*/
    /*    !*margin-right: 5px;*!*/
    /*}*/

    /*!* drop target state *!*/
    /*ul[aria-dropeffect="move"] {*/
    /*    border-color: #68b;*/
    /*    background: #fff;*/
    /*}*/

    /*!* drop target focus and dragover state *!*/
    /*ul[aria-dropeffect="move"]:focus,*/
    /*ul[aria-dropeffect="move"].dragover {*/
    /*    outline: none;*/
    /*    box-shadow: 0 0 0 1px #fff, 0 0 0 3px #68b;*/
    /*}*/

    /*!* draggable items *!*/
    /*!*li {*!*/
    /*!*    min-width: 190px;*!*/
    /*!*    height: 75px;*!*/
    /*!*    border-style: solid;*!*/
    /*!*    !*display: block;*!*!*/
    /*!*    list-style-type: none;*!*/
    /*!*    margin: 2px !important;*!*/
    /*!*    padding: 2px !important;*!*/
    /*!*    border-radius: 0.2em;*!*/
    /*!*    line-height: 1.3;*!*/
    /*!*}*!*/

    /*li:hover {*/
    /*    box-shadow: 0 0 0 2px #68b, inset 0 0 0 1px #ddd;*/
    /*}*/

    /*!* items focus state *!*/
    /*li:focus {*/
    /*    outline: none;*/
    /*    box-shadow: 0 0 0 2px #68b, inset 0 0 0 1px #ddd;*/
    /*}*/

    /*!* items grabbed state *!*/
    /*li[aria-grabbed="true"] {*/
    /*    background: #5cc1a6;*/
    /*    color: #fff;*/
    /*}*/

    /*@keyframes nodeInserted {*/
    /*    from {*/
    /*        opacity: 0.2;*/
    /*    }*/
    /*    to {*/
    /*        opacity: 0.8;*/
    /*    }*/
    /*}*/

    /*.item-dropzone-area {*/
    /*    min-width: 150px;*/
    /*    width: 20rem;*/

    /*    height: 6rem;*/
    /*    background: #888;*/
    /*    opacity: 0.8;*/
    /*    animation-duration: 0.5s;*/
    /*    animation-name: nodeInserted;*/
    /*}*/
</style>
