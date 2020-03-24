<template>
    <div id="cart-creator">
        <div class="columns">
            <div class="column is-one-third">
                <CardsDirectory></CardsDirectory>
            </div>
            <div class="column">
                <div id="cart-creator-form">
                    <div class="field">
                        <label class="label" for="create-update-card-title-input">Title</label>
                        <div class="control">
                            <input id="create-update-card-title-input" class="input" type="text"
                                   v-model="selectedCard.title" placeholder="Text input">
                        </div>
                    </div>

                    <div class="field">
                        <label class="label" for="create-update-card-wikipedia-input">Wikipedia link</label>
                        <div class="control">
                            <input id="create-update-card-wikipedia-input" class="input is-danger" type="email" placeholder="Email input" v-model="selectedCard.wikipedia">
                        </div>
                    </div>

                    <div class="field">
                        <label class="label" for="create-update-card-tags-input">Tags</label>
                        <span class="help is-danger">TODO: just an idea</span>
                        <div class="control">
                            <input id="create-update-card-tags-input" class="input" type="text" placeholder="Add new tag" value="">
                        </div>
                        <b-tag type="is-primary"
                               closable
                               aria-close-label="Close tag"
                               @close="isTag1Active = false">
                            Spain
                        </b-tag>
                        <b-tag type="is-primary"
                               closable
                               aria-close-label="Close tag"
                               @close="isTag1Active = false">
                            France
                        </b-tag>
                        <b-tag type="is-primary"
                               closable
                               aria-close-label="Close tag"
                               @close="isTag1Active = false">
                            War
                        </b-tag>
                    </div>

                    <div class="field">
                        <label class="label" for="create-update-card-info-input">Message</label>
                        <div class="control">
                            <textarea id="create-update-card-info-input" class="textarea" placeholder="Textarea" v-model="selectedCard.info"></textarea>
                        </div>
                    </div>

                    <b-field label="Select a date">
                        <b-datepicker
                                placeholder="Type or select a date..."
                                editable>
                        </b-datepicker>
                    </b-field>

                    <div class="field is-grouped">
                        <div v-if="!id" class="control">
                            <button class="button is-link is-success">Create</button>
                        </div>
                        <div v-if="id" class="control">
                            <button class="button is-link is-light">Update</button>
                        </div>
                        <div class="control">
                            <button class="button is-link is-danger">Delete</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="column  is-one-third">
                <Card
                        :title="selectedCard.title"
                        :info="selectedCard.info"
                        :wikipedia="selectedCard.wikipedia"
                ></Card>
            </div>
        </div>
    </div>
</template>

<script>
    import CardsDirectory from './CardsDirectory.vue'
    import Card from './Card.vue'

    export default {
        name: 'App',
        components: {
            CardsDirectory,
            Card
        },
        data() {
            return {
                id: this.$route.params.cardId,
                selectedCard: {
                    title: "",
                    wikipedia: "",
                    info: ""
                }
            }
        },
        computed: {
            hello() {
                console.log(`Hello ${this.$store.state.cards.hello}`);
                return this.$store.state.cards.hello
            }
        },
        watch: {
            // call again the method if the route changes
            '$route': 'fetchData'
        },
        created() {
            console.log("Created");
            this.$store.dispatch('loadUsers');
        },
        methods: {
            fetchData() {
                this.id = this.$route.params.cardId;
            }
        }
    }
</script>

<style>
    #cart-creator {
        margin-top: 20px;
    }
</style>
