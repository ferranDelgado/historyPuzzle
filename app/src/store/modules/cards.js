import Vue from "vue";

const state = {
    game: {
        discardedCards: [],
        cardsInHand: [],
        cardsInBoard: []
    },
    blocks: [
        {
          century: 1900,
            cards: [
                {
                    id: 2,
                    title: "World War I",
                    year: 1914,
                    month: 7,
                    day: 28,
                    picture: "",
                    wikipedia: "https://en.wikipedia.org/wiki/World_War_I"
                }
            ]
        },
        {
          century: 1900,
            cards: [
                {
                    id: 5,
                    title: "World War II",
                    year: 1945,
                    month: 9,
                    day: 1,
                    picture: "",
                    wikipedia: "https://en.wikipedia.org/wiki/World_War_II"
                }
            ]
        }
    ]
};

const mutations = {
    SAVE_CARDS(state, cards) {
        const reducer = (accumulator, currentValue) => {
            let century = (currentValue.year/100|0)*100;
            if(!(century in accumulator))
                accumulator[century] = { century: century, cards: []};
            accumulator[century].cards.push(currentValue);
            return accumulator
        };
        state.blocks = cards.reduce(reducer, {});
    },
    ADD_CARD(state, card) {
        let century = (card.year/100|0)*100;
        if(card.year in state.blocks) {
            Vue.set(state.blocks[century].cards, state.blocks[century].cards.length, card);
        } else {
            let newObj = {
              [century]: { century: century, cards: [card]}
            };
            
            state.blocks = Object.assign({}, state.blocks, newObj);
        }
    },
    SET_CARDS_IN_BOARD(state, cards) {
        state.game.cardsInBoard = cards
    },
    SET_CARDS_IN_HAND(state, cards) {
        state.game.cardsInHand = cards
    },
    ADD_CARD_TO_DISCARDED_CARDS(state, newCard) {
        Vue.set(state.game.discardedCards, state.game.discardedCards.length, newCard.card)
    },
    ADD_CARD_TO_CARDS_IN_BOARD(state, newCard) {
        state.game.cardsInBoard.splice(newCard.index, 0, newCard.card);
    },
    DELETE_CARD_TO_CARDS_IN_HAND(state, index) {
        Vue.delete(state.game.cardsInHand, index)
    }

};

const actions = {
    loadAllCards({commit}) {
        Vue.axios.get('card').then(result => {
            commit('SAVE_CARDS', result.data);
        }).catch(error => {
            throw new Error(`API ${error}`);
        });
    },
    createCard({commit}, object) {
        Vue.axios.post('card', object).then(result => {
            commit('ADD_CARD', result.data);
        }).catch(error => {
            throw new Error(`API ${error}`);
        });
    },
    loadGameOld({commit}, userId) {
        console.log(`Loading Game for ${userId}`);
        Vue.axios.get('card').then(result => {
            let handSize = 100;
            let init = getRandomArbitrary(0, (result.data.length - handSize));
            let sublist = result.data.slice(init, init+handSize);
            let index = getRandomArbitrary(0, handSize);
            let card = sublist.splice(index, 1);

            commit('SET_CARDS_IN_BOARD', card);
            commit('SET_CARDS_IN_HAND', sublist);
        }).catch(error => {
            throw new Error(`API ${error}`);
        });
    }
    // moveCard({commit}, movement) {
    //     function isPositionCorrect(card, index) {
    //         let cardInBoardYear;
    //         if(index >= state.game.cardsInBoard.length) {
    //             cardInBoardYear = state.game.cardsInBoard[state.game.cardsInBoard.length - 1].year;
    //             console.log(`Last Card: ${cardInBoardYear} <= ${card.year}; ${ cardInBoardYear <= card.year}`);
    //             return cardInBoardYear <= card.year;
    //         } else {
    //             cardInBoardYear = state.game.cardsInBoard[index].year;
    //             console.log(`Any card: ${cardInBoardYear} >= ${card.year}; ${ cardInBoardYear >= card.year}`);
    //             return cardInBoardYear >= card.year;
    //         }
    //
    //
    //     }
    //     const cardInHandIndex = state.game.cardsInHand.findIndex(function(value) {
    //         return value.id === movement.cardId
    //     });
    //
    //     const cardInHand = state.game.cardsInHand[cardInHandIndex];
    //
    //     const addCard = {
    //       card: {
    //           id: cardInHand.id,
    //           title: cardInHand.title,
    //           year: cardInHand.year
    //       },
    //       index: movement.index
    //     };
    //
    //     if(isPositionCorrect(cardInHand, movement.index)) {
    //         console.log(`Correct position ${JSON.stringify(movement)}`);
    //         commit('ADD_CARD_TO_CARDS_IN_BOARD', addCard);
    //     } else {
    //         console.log(`Incorrect position ${JSON.stringify(movement)}`);
    //         commit('ADD_CARD_TO_DISCARDED_CARDS', addCard);
    //     }
    //     commit('DELETE_CARD_TO_CARDS_IN_HAND', cardInHandIndex);
    // }
};

function getRandomArbitrary(min, max) {
    return (Math.random() * (max - min) + min)|0;
}

export default {
    state,
    mutations,
    actions
}
