import Vue from "vue";

const state = {
    blocks: [
        {
            year: 1910,
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
            year: 1940,
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
            if(!(currentValue.year in accumulator))
                accumulator[currentValue.year] = {year: currentValue.year, cards: []};
            accumulator[currentValue.year].cards.push(currentValue);
            return accumulator
        };
        state.blocks = cards.reduce(reducer, {});
    },
    ADD_CARD(state, card) {
        if(card.year in state.blocks) {
            Vue.set(state.blocks[card.year].cards, state.blocks[card.year].cards.length, card);
        } else {
            let newObj = {
              [card.year]: { year: card.year, cards: [card]}
            };
            
            state.blocks = Object.assign({}, state.blocks, newObj);
            // state.blocks[card.year] = {year: card.year, cards: []};
            // Vue.set(state.blocks, card.year, {year: card.year, cards: [card]})
        }
        // state.blocks[card.year].cards.push(card);
        console.log("state.blocks");
        console.log(state.blocks)
    }
};

const actions = {
    loadUsers({commit}) {
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
    }
};

export default {
    state,
    mutations,
    actions
}
