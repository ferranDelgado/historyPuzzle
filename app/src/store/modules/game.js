import Vue from "vue";

const state = {
    discardedCards: [],
    cardsInHand: [],
    cardsInBoard: [],
    waitingCards: [],
    movingCard: null
};

const mutations = {
    SET_WAITING_CARDS(state, cards) {
        console.log(`Cards waiting in the deck ${cards.length}`);
        state.waitingCards = cards
    },
    SET_CARDS_IN_BOARD(state, cards) {
        state.cardsInBoard = cards
    },
    SET_CARDS_IN_HAND(state, cards) {
        console.log(`Cards in the hand ${cards.length}`);
        state.cardsInHand = cards
    },
    ADD_CARD_TO_DISCARDED_CARDS(state, newCard) {
        Vue.set(state.discardedCards, state.discardedCards.length, newCard.card)
    },
    CLEAN_DROP_ZONE(state) {
        state.cardsInBoard = state.cardsInBoard.filter(function (value) {
            return !value.isDropZone
        });
    },

    SET_CARD_UNDER_DECISION(state, {index, card}) {
        state.movingCard = {
            index: index,
            card: card
        }
    },
    UNSET_CARD_UNDER_DECISION(state) {
        state.movingCard = null
    },
    CORRECT_POSITION(state) {
        state.movingCard.card.cardType = null;
        state.movingCard = null
    },
    DISCARDED_ADD_CARD(state, newCard) {
        state.discardedCards.splice(0, 0, newCard);
    },
    BOARD_ADD_CARD(state, newCard) {
        console.log(`Adding card to board at index: ${newCard.index}`);
        state.cardsInBoard.splice(newCard.index, 0, newCard.card);
    },
    BOARD_DELETE_CARD(state, index) {
        console.log(`Delete card in board at index: ${index}`);
        Vue.delete(state.cardsInBoard, index)
    },
    HAND_ADD_CARD(state, card) {
        state.cardsInHand.splice(0, 0, card);
    },
    HAND_DELETE_CARD(state, index) {
        Vue.delete(state.cardsInHand, index)
    },
    WAITING_CARDS_GET_ONE(state) {
        if(state.waitingCards.length > 0) {
            let index = getRandomArbitrary(0, state.waitingCards.length);
            let newCard = state.waitingCards.splice(index, 1)[0];
            console.log(newCard);
            state.cardsInHand.splice(0,0, newCard);
        }
    }

};

let dropZoneSide = null;
let dropZoneIndex = null;
const dropZoneElement = {
    id: Math.random() * 1000 | 0,
    title: null,
    year: null,
    isDropZone: true,
    cardType: 'drop-zone-item'
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

const actions = {
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
    },
    sepDropZone({commit}, {index, side}) {
        if(state.movingCard != null) {
            return
        }
        const indexFix = side === "LEFT" ? index - 1 : index;
        const samePosition = side === dropZoneSide && dropZoneIndex === indexFix;
        if (!samePosition) {
            actions.cleanDropZone({commit});
            const newIndex = (side === "RIGHT" ? index + 1 : index);
            const addCard = {
                card: dropZoneElement,
                index: newIndex
            };
            commit('BOARD_ADD_CARD', addCard);
            dropZoneSide = side;
            dropZoneIndex = index;
        }
    },
    cleanDropZone({commit}) {
        dropZoneSide = null;
        dropZoneIndex = null;
        commit('CLEAN_DROP_ZONE');
    },
    releaseCard({commit}, {draggingCard, index}) {
        if (dropZoneSide === null || dropZoneIndex === null) {
            return;
        }

        console.log(`Releasing card in position ${index} to the ${dropZoneSide} of the index ${dropZoneIndex}`);
        const newIndex = dropZoneSide === "RIGHT" ? dropZoneIndex + 1 : dropZoneIndex;
        const card = {
            id: draggingCard.id,
            title: draggingCard.title,
            year: draggingCard.year,
            info: draggingCard.info,
            cardType: 'user-card',
            isDropZone: false
        };

        const newCard = {
            card: card,
            index: newIndex
        };
        commit('HAND_DELETE_CARD', index);
        actions.cleanDropZone({commit});
        commit('BOARD_ADD_CARD', newCard);
        commit('SET_CARD_UNDER_DECISION', newCard);

        dropZoneSide = null;
        dropZoneIndex = null;
    },
    returnToHand({commit}, index) {
        const card = state.cardsInBoard[index];
        commit('BOARD_DELETE_CARD', index);
        commit('HAND_ADD_CARD', card);
        commit('UNSET_CARD_UNDER_DECISION');
    },
    acceptCardPosition({commit}) {
        let movingCardIndex = state.movingCard.index;
        let card = state.movingCard.card;
        const nextCard = state.cardsInBoard[movingCardIndex + 1];
        const previousCard = state.cardsInBoard[movingCardIndex - 1];
        if(nextCard && previousCard) {
            if(card.year > nextCard.year || card.year < previousCard.year) {
                commit('BOARD_DELETE_CARD', movingCardIndex);
                commit('DISCARDED_ADD_CARD', card);
                commit('WAITING_CARDS_GET_ONE');
                commit('UNSET_CARD_UNDER_DECISION');
            } else {
                commit('CORRECT_POSITION');
            }
        } else if (nextCard) {
            if(card.year > nextCard.year) {
                commit('BOARD_DELETE_CARD', movingCardIndex);
                commit('DISCARDED_ADD_CARD', card);
                commit('WAITING_CARDS_GET_ONE');
                commit('UNSET_CARD_UNDER_DECISION');
            } else {
                commit('CORRECT_POSITION');
            }
        } else {
            if(card.year < previousCard.year) {
                commit('BOARD_DELETE_CARD', movingCardIndex);
                commit('DISCARDED_ADD_CARD', card);
                commit('WAITING_CARDS_GET_ONE');
                commit('UNSET_CARD_UNDER_DECISION');
            } else {
                commit('CORRECT_POSITION');
            }
        }
    }
};

function getRandomArbitrary(min, max) {
    return (Math.random() * (max - min) + min)|0;
}

export default {
    state,
    mutations,
    actions
}
