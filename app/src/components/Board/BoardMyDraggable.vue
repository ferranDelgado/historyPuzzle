<template>
    <div class="drag-wrapper columns">
        <div id="left-part" class="column is-four-fifths">
            <ul id="board" class="horizontal-list">
                <li v-for="(card, index) in cardsInBoard" :key="card.id"
                    @dragover="dragOver(card, index, $event)"
                    @dragleave="dragLeave(card.id)"
                    v-bind:class="card.cardType"
                >
                    <Card
                            v-if="card.cardType !== 'drop-zone-item'"
                            :title="card.title"
                            :info="card.info"
                            :wikipedia="card.wikipedia"
                            :year="card.cardType === 'user-card' ? NaN : card.year"
                            :show-mini-image="true"
                    >
                        <div class="field has-addons">
                            <p class="control">
                                <button
                                        class="button is-small is-success is-light"
                                        v-if="card.cardType === 'user-card'"
                                        @click="acceptCardPosition(index)">

                                    <span>Accept</span>
                                    <span class="icon is-small">
                                    <font-awesome-icon icon="check"></font-awesome-icon>
                                </span>
                                </button>
                            </p>
                            <p class="control">
                                <button
                                        class="button is-small is-danger is-light"
                                        v-if="card.cardType === 'user-card'"
                                        @click="returnToHand(index)">
                                    <span>Discard</span>
                                    <span class="icon is-small">
                                    <font-awesome-icon icon="times-circle"></font-awesome-icon>
                                </span>
                                </button>
                            </p>
                        </div>
                    </Card>
                </li>
            </ul>

            <ul id="hand" class="horizontal-list">
                <li v-for="(card, index) in cardsInHand" :key="card.id"
                    draggable="true"
                    @dragstart="dragStart(card)"
                    @dragend="dragEnd(index)"
                    @dragleave="dragEnd(index)"
                >
                    <Card
                            :title="card.title"
                            :info="card.info"
                            :wikipedia="card.wikipedia"
                            :show-mini-image="true"
                    />
                </li>
            </ul>
        </div>
        <div id="right-part" class="column">
            <ul id="discarded">
                <li v-for="card in discardedCards" :key="card.id">
                    <Card
                            :title="card.title"
                            :info="card.info"
                            :wikipedia="card.wikipedia"
                            :year="card.year"
                            :show-mini-image="true"
                    />
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
  import Card from '../Card.vue'

  export default {
    name: "Board-game",
    components: {
      Card
    },
    data() {
      return {
        draggingCard: null,
        dropZoneSide: null,
        dropZoneIndex: -1,

        timeoutTask: null,
        cardsHand: [
          {
            id: 1,
            title: `Event ${Math.random() * 99000 | 0}`,
            year: 123,
          },
          {
            id: 2,
            title: `Event ${Math.random() * 99000 | 0}`,
            year: 123,
          },
          {
            id: 3,
            title: `Event ${Math.random() * 99000 | 0}`,
            year: 123,
          },
          {
            id: 5,
            title: `Event ${Math.random() * 99000 | 0}`,
            year: 123,
          },
          {
            id: 4,
            title: `Event ${Math.random() * 99000 | 0}`,
            year: 123,
          }
        ],
        cardsBoard: [
          {
            id: Math.random() * 1000 | 0,
            title: "title",
            year: 123,
            isDropZone: false
          }
        ]
      }
    },
    created() {
      this.$store.dispatch('loadGame');
    },
    computed: {
      cardsInBoard: function () {
        return this.$store.state.game.cardsInBoard
      },
      cardsInHand: function () {
        return this.$store.state.game.cardsInHand
      },
      discardedCards: function () {
        return this.$store.state.game.discardedCards;
      }
    },
    methods: {
      // Cars in hand events
      dragStart(card) {
        this.draggingCard = card;
      },
      dragEnd(index) {
        const newObject = {
          id: this.draggingCard.id,
          title: this.draggingCard.title,
          year: this.draggingCard.year,
          info: this.draggingCard.info,
          isDropZone: false
        };
        this.$store.dispatch('releaseCard', {draggingCard: newObject, index: index});
      },
      // ------------------- Cards in board events --------------------------------- //
      dragOver(card, index, event) {
        clearTimeout(this.timeoutTask);
        this.timeoutTask = null;
        if (card.isDropZone) {
          return
        }
        const targetRect = event.target.getClientRects()[0];
        const half = targetRect.x + targetRect.width / 2;
        const movement = {
          index: index,
          side: (event.clientX > half) ? "RIGHT" : "LEFT"
        };
        this.$store.dispatch('sepDropZone', movement);
      },
      dragLeave() {
        const that = this;
        this.timeoutTask = setTimeout(function () {
          that.$store.dispatch('cleanDropZone');
        }, 50)
      },
      // ---------------------
      returnToHand(index) {
        this.$store.dispatch('returnToHand', index);
      },
      acceptCardPosition() {
        this.$store.dispatch('acceptCardPosition');
      }
    }
  };
</script>

<style scoped>

    #discarded {
        height: 500px;
    }

    #hand {
        flex-flow: wrap;
    }

    #hand li, #board li {
        width: 350px;
    }

    #board {
        background-color: aquamarine;
    }

    #right-part {
        border-radius: 5px;
        background: #dc998a;
    }

    #left-part {
        border-radius: 5px;
    }

    #drop-zone-element {
        background: #888;
    }

    .drop-zone-item {
        background: #888;
    }

    /*********************/
    .drag-wrapper {
        /*display: flex;*/
        justify-content: center;
    }

    .horizontal-list {
        overflow-x: auto;
        overflow-y: hidden;
        display: flex;
        flex-direction: row;
        padding: 3px !important;
        list-style-type: none;
        border-radius: 0.2em;
        background: #8adccc;
        color: #555;
        margin-right: 5px;
    }

    /* drop target state */
    .horizontal-list[aria-dropeffect="move"] {
        border-color: #68b;
        background: #fff;
    }

    /* drop target focus and dragover state */
    .horizontal-list[aria-dropeffect="move"]:focus,
    .horizontal-list[aria-dropeffect="move"].dragover {
        outline: none;
        box-shadow: 0 0 0 1px #fff, 0 0 0 3px #68b;
    }

    ul {
        margin: 10px;
    }

    /* draggable items */
    li {
        min-width: 350px;
        min-height: 75px;
        list-style-type: none;
        margin: 2px;
        padding: 2px;
        border-radius: 0.2em;
        line-height: 1.3;
    }

    li:hover {
        box-shadow: 0 0 0 2px #68b, inset 0 0 0 1px #ddd;
    }

    /* items focus state */
    li:focus {
        outline: none;
        box-shadow: 0 0 0 2px #68b, inset 0 0 0 1px #ddd;
    }

    /* items grabbed state */
    li[aria-grabbed="true"] {
        background: #5cc1a6;
        color: #fff;
    }

    @keyframes nodeInserted {
        from {
            opacity: 0.2;
        }
        to {
            opacity: 0.8;
        }
    }

    .item-dropzone-area {
        min-width: 150px;
        width: 20rem;

        height: 6rem;
        background: #888;
        opacity: 0.8;
        animation-duration: 0.5s;
        animation-name: nodeInserted;
    }
</style>
