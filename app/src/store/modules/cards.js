import Vue from "vue";

const state = {
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
            console.log(`year: ${currentValue.year} century: ${century}`);
            if(!(century in accumulator))
                accumulator[century] = { century: century, cards: []};
            accumulator[century].cards.push(currentValue);
            return accumulator
        };
        state.blocks = cards.reduce(reducer, {});
    },
    ADD_CARD(state, card) {
        let century = (card.year/100|0)*100
        if(card.year in state.blocks) {
            Vue.set(state.blocks[century].cards, state.blocks[century].cards.length, card);
        } else {
            let newObj = {
              [century]: { century: century, cards: [card]}
            };
            
            state.blocks = Object.assign({}, state.blocks, newObj);
        }
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
