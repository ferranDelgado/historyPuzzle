<template>
    <div class="columns">
        <div class="column  is-four-fifths">
            <div class="timeline">
                <div class="timeline__new_card"
                     v-if="selectedCard.id > 0"
                     @click="newCardPositionClicked(0)"
                     v-bind:class="newCardPositionIndex === 0 ? 'new-card-selector-selected': ''"
                >
                <span v-if="newCardPositionIndex === 0" class="timeline__new-card-selector">
                    Are you sure?
                    <button class="button is-small is-success is-light"
                            @click="moveCard(0)">
                        <span>Yes</span>
                        <span class="icon is-small">
                            <font-awesome-icon icon="check"></font-awesome-icon>
                        </span>
                    </button>
                </span>
                </div>
                <div class="timeline__box" v-for="(card, index) in waitingCards" :key="card.id">
                    <div class="timeline__date">
                        <!--                <span class="timeline__day">{{index}}</span>-->
                        <span class="timeline__month">{{card.year}}</span>
                    </div>
                    <div class="timeline__post">
                        <div class="timeline__content">
                            <p>{{card.title}}</p>
                        </div>
                    </div>
                    <div class="timeline__new_card"
                         v-if="selectedCard.id > 0"
                         @click="newCardPositionClicked(index + 1)"
                         v-bind:class="newCardPositionIndex === (index + 1) ? 'new-card-selector-selected': ''"
                    >
                <span v-if="newCardPositionIndex === (index + 1)" class="timeline__new-card-selector">
                    Are you sure?
                    <button class="button is-small is-success is-light"
                            @click="moveCard(index + 1)">
                        <span>Yes</span>
                        <span class="icon is-small">
                            <font-awesome-icon icon="check"></font-awesome-icon>
                        </span>
                    </button>
                </span>
                    </div>
                </div>
                <div id="hand">
                    <ul id="board" class="horizontal-list">
                        <li v-for="(card, index) in cardsInHand" :key="card.id"
                            @click="cardInHandClicked(card, index)"
                        >
                            <div class="card hand-card"
                                 v-bind:class="selectedCard.id === card.id ? 'selected-card' : ''">
                                <div class="card-image">
                                    <figure class="image is-4by3">
                                        <img src="https://bulma.io/images/placeholders/1280x960.png"
                                             alt="Placeholder image">
                                    </figure>
                                </div>
                                <div class="card-content">
                                    <div class="content">
                                        {{card.title}}
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="column">
            <ul>
                <li v-for="card in discaredCards" :key="card.id">
                    <span>{{card.title}}</span>
                    <span class="tag is-dark">{{card.year}}</span>
                </li>
            </ul>
        </div>
    </div>

</template>

<script>
    function prettyYear(year) {
      const withCommas = Math.abs(year);//.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      if(year < 0) {
        return `${withCommas}BC`
      } else {
        return withCommas
      }
    }
  let mapper = card => ({
    title: card.title,
    year: prettyYear(card.year)
  });

  export default {
    name: "time-line-game",
    data() {
      return {
        selectedCard: {id: -1},
        selectedCardPositionIndex: null,
        newCardPositionIndex: null
      }
    },
    created() {
      this.$store.dispatch('loadGame');
    },
    computed: {
      discaredCards: function () {
        return this.$store.state.gameV2.discardedCards
      },
      waitingCards: function () {

        return this.$store.state.gameV2.cardsInBoard.map(mapper)
      },
      cardsInHand: function () {
        return this.$store.state.gameV2.cardsInHand
      }
    },
    methods: {
      cardInHandClicked(card, index) {
        this.newCardPositionIndex = null;
        if (this.selectedCard.id === card.id) {
          this.selectedCard = {id: -1};
          this.selectedCardPositionIndex = null;
        } else {
          this.selectedCard = card;
          this.selectedCardPositionIndex = index
        }
      },
      newCardPositionClicked(index) {
        if (this.newCardPositionIndex === index) {
          this.newCardPositionIndex = null
        } else {
          this.newCardPositionIndex = index
        }
      },
      moveCard(index) {
        const movement = {
          cardInHandIndex: this.selectedCardPositionIndex,
          movingPositionIndex: index
        };
        this.$store.dispatch('moveCard', movement);
      }
    }
  };
</script>

<style scoped>

    .card-content {
        padding: 1rem;
    }

    #hand {
        width: 102%;
        position: fixed;
        bottom: 0;
        left: 50%;
        margin-left: -50%;
    }

    #hand li {
        padding: 5px;
    }

    .hand-card {
        width: 160px;
    }

    .hand-card:hover, .selected-card {
        border-style: solid;
        border-color: red;
    }

    .horizontal-list {
        overflow-x: auto;
        overflow-y: hidden;
        display: flex;
        flex-direction: row;
        padding: 3px !important;
        list-style-type: none;
        border-radius: 0.2em;
        background: #363636;
        /*background: #8adccc;*/
        color: #555;
        margin-right: 5px;
    }

    .timeline {
        --uiTimelineMainColor: #222;
        --uiTimelineSecondaryColor: #fff;

        position: relative;
        padding-top: 3rem;
        padding-bottom: 3rem;
    }

    .timeline:before {
        content: "";
        width: 4px;
        height: 100%;
        background-color: #222;

        position: absolute;
        top: 0;
    }


    .timeline__box {
        position: relative;
    }

    .timeline__box:not(:last-of-type) {
        margin-bottom: 30px;
    }

    .timeline__box:before {
        content: "";
        width: 100%;
        height: 2px;
        background-color: var(--uiTimelineMainColor);

        position: absolute;
        left: 0;
        z-index: -1;
    }

    .timeline__new_card {
        top: 5px;
        min-width: 40px;
        min-height: 40px;
        position: relative;
        /* padding: .5rem 1.5rem; */
        background-color: orange;
        width: 40px;
        height: 40px;
        border-radius: 33px;
        border-style: solid;
        border-color: grey;
    }

    .timeline__new_card:hover {
        border-color: black;
    }

    .new-card-selector-selected {
        border-color: black;
        width: 240px;
        border-radius: 5px;
        padding: 4px;
    }

    .timeline__date {
        min-width: 65px;
        position: absolute;
        left: 0;

        box-sizing: border-box;
        padding: .5rem 1.0rem;
        text-align: center;

        background-color: var(--uiTimelineMainColor);
        color: var(--uiTimelineSecondaryColor);
    }

    .timeline__day {
        font-size: 2rem;
        font-weight: 700;
        display: block;
    }

    .timeline__month {
        display: block;
        font-size: .8em;
        text-transform: uppercase;
    }


    .timeline__post {
        padding: 1.5rem 2rem;
        border-radius: 2px;
        border-left: 3px solid var(--uiTimelineMainColor);
        box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 1px 2px 0 rgba(0, 0, 0, .24);
        background-color: var(--uiTimelineSecondaryColor);
    }

    p {
        margin-top: 0;
        margin-bottom: 1.5rem;
        line-height: 1.5;
    }

    p:last-child {
        margin-bottom: 0;
    }


    @media screen and (min-width: 641px) {

        .timeline__new_card:first-child {
            left: 12px;
            top: -5px;
        }

        .timeline__new_card {
            left: -69px;
        }

        .timeline:before {
            left: 30px;
        }

        .timeline__group {
            padding-top: 55px;
        }

        .timeline__box {
            padding-left: 80px;
        }

        .timeline__box:before {
            top: 5px;
            transform: translateY(-50%);
        }

        .timeline__date {
            /*top: 50%;*/
            /*margin-top: -27px;*/
        }
    }

    @media screen and (max-width: 640px) {

        .timeline__new_card:first-child {
            left: -17px;
            top: -5px;
        }

        .timeline:before {
            left: 0;
        }

        .timeline__group {
            /*padding-top: 0px;*/
        }

        .timeline__box {
            padding-left: 90px;
            /*padding-top: 70px;*/
        }

        .timeline__box:before {
            top: 5px;
        }

        .timeline__date {
            top: 0;
        }

        .timeline__new_card {
            left: -106px;
        }
    }

</style>
