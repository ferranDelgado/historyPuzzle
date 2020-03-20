<template>
    <b-menu>
        <b-menu-list>
            <b-menu-item
                    tag="router-link"
                    to='/create/'
            >
                <template v-slot:label>
                    <font-awesome-icon icon="plus"></font-awesome-icon>
                    Create Card
                </template>
            </b-menu-item>
        </b-menu-list>

        <b-menu-list label="Cards">
            <b-menu-item v-for="block in blocks" :key="block.year">
                <template v-slot:label="props">
                    {{ block.year }}
                    <font-awesome-icon :icon="props.expanded ? 'caret-up' : 'caret-down' "></font-awesome-icon>
                </template>
                <b-menu-item
                        v-for="card in block.cards"
                        :key="card.id"
                        :label="card.title"
                        tag="router-link"
                        :to="'/update/' + card.id"
                />
            </b-menu-item>
        </b-menu-list>
    </b-menu>
</template>

<script>
    export default {
        data() {
            return {
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
            }
        }
    }
</script>