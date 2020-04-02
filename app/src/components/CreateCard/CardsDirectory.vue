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
            <b-menu-item v-for="block in blocks" :key="block.century">
                <template v-slot:label="props">
                    {{ block.century }}
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
            }
        },
        computed: {
            blocks: function () {
                return this.$store.state.cards.blocks
            }
        }
    }
</script>