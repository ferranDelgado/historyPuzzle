import Vue from "vue";

const state = {
  discardedCards: [],
  cardsInHand: [],
  cardsInBoard: [],
  waitingCards: []
};

const mutations = {
  CORRECT_MOVEMENT(state, {cardInHandIndex, movingPositionIndex}) {
    const card = state.cardsInHand.splice(cardInHandIndex, 1);
    state.cardsInBoard.splice(movingPositionIndex, 0, card[0]);
  },

  INCORRECT_MOVEMENT(state, cardInHandIndex) {
    const card = state.cardsInHand.splice(cardInHandIndex, 1);
    state.discardedCards.splice(0, 0, card[0]);

    const newCard = state.waitingCards.splice(0, 1);
    if (newCard.length > 0) {
      state.cardsInHand.splice(0, 0, newCard[0])
    }
  },
  SET_WAITING_CARDS(state, cards) {
    state.waitingCards = cards
  },
  SET_CARDS_IN_BOARD(state, cards) {
    state.cardsInBoard = cards
  },
  SET_CARDS_IN_HAND(state, cards) {
    state.cardsInHand = cards
  }
};

/**
 * Shuffles array in place.
 * @param {Array} a items An array containing the items.
 */
function shuffle(a) {
  let j, x, i;
  for (i = a.length - 1; i > 0; i--) {
    j = Math.floor(Math.random() * (i + 1));
    x = a[i];
    a[i] = a[j];
    a[j] = x;
  }
  return a;
}

function yearOr(index, defaultYear) {
  let card = state.cardsInBoard[index];
  if (typeof card === "undefined") {
    console.log(`Year at position ${index} undefined. Default: ${defaultYear}`);
    return defaultYear
  } else {
    console.log(`Year at position ${index}: ${card.year}`);
    return card.year
  }
}

const actions = {
  moveCard({commit}, {cardInHandIndex, movingPositionIndex}) {
    console.log(`Moving position: ${movingPositionIndex}`);
    const movement = {
      cardInHandIndex: cardInHandIndex,
      movingPositionIndex: movingPositionIndex
    };
    const cardInHand = state.cardsInHand[cardInHandIndex];
    if (typeof cardInHand === "undefined") {
      throw `Wrong Index ${cardInHand} [${cardInHandIndex}]`
    }
    const previousCardYear = yearOr(movingPositionIndex - 1, cardInHand.year - 1);
    const nextCardYear = yearOr(movingPositionIndex, cardInHand.year + 1);

    if (cardInHand.year > previousCardYear && cardInHand.year < nextCardYear) {
      console.log(`Correct position: ${cardInHand.year} > ${previousCardYear} && ${cardInHand.year} < ${nextCardYear}`);
      commit('CORRECT_MOVEMENT', movement);
    } else {
      console.log(`Incorrect position: ${cardInHand.year} > ${previousCardYear} && ${cardInHand.year} < ${nextCardYear}`);
      commit('INCORRECT_MOVEMENT', cardInHandIndex);
    }

  },
  loadGame({commit}, userId) {
    console.log(`Loading Game for ${userId}`);
    Vue.axios.get('card').then(result => {
      console.log(`Cards received: ${result.data.length}`);
      shuffle(result.data);
      let handSize = 10;
      let cardsInHand = result.data.slice(0, handSize);
      let waitingCards = result.data.slice(handSize, result.data.length);

      let index = getRandomArbitrary(0, waitingCards.length);
      let card = waitingCards.splice(index, 1);

      commit('SET_WAITING_CARDS', waitingCards);
      commit('SET_CARDS_IN_BOARD', card);
      commit('SET_CARDS_IN_HAND', cardsInHand);
    }).catch(error => {
      throw new Error(`API ${error}`);
    });
  }
};

function getRandomArbitrary(min, max) {
  return (Math.random() * (max - min) + min) | 0;
}

export default {
  state,
  mutations,
  actions
}
