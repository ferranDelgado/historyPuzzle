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
                            <input id="create-update-card-wikipedia-input" class="input is-danger" type="email"
                                   placeholder="Email input" v-model="selectedCard.wikipedia">
                        </div>
                    </div>

                    <div class="field">
                        <label class="label" for="create-update-card-tags-input">Tags</label>
                        <span class="help is-danger">TODO: just an idea</span>
                        <div class="control">
                            <input id="create-update-card-tags-input" class="input" type="text"
                                   placeholder="Add new tag" value="">
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
                            <textarea id="create-update-card-info-input" class="textarea" placeholder="Textarea"
                                      v-model="selectedCard.info"></textarea>
                        </div>
                    </div>

                    <div class="field">
                        <label class="label" for="create-update-card-date-input">Date (YYYY/MM/DD)</label>
                        <span class="help is-danger">{{dateFormat}}</span>
                        <div class="control">
                            <input id="create-update-card-date-input" class="input" type="text"
                                   placeholder="Introduce date" v-model="dateString">
                        </div>
                    </div>

                    <div class="field is-grouped">
                        <div v-if="!id" class="control">
                            <button class="button is-link is-success" v-on:click="createCard">Create</button>
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
                        :year="selectedCard.year"
                        :month="selectedCard.month"
                        :day="selectedCard.day"
                ></Card>
            </div>
        </div>
    </div>
</template>

<script>
    import CardsDirectory from './CardsDirectory.vue'
    import Card from './Card.vue'

    function parseDateString(value) {
        let clean = value.trim();
        if (clean.length === 0) {
            throw "Empty String date"
        }
        let parts = clean.split('/');
        if (parts.length > 3) {
            throw "Too many parts [${parts}]"
        }

        if(isNaN(Number.parseInt(parts.join('')))) {
            throw "Illegal date format"
        }

        if (parts.length === 3 && parts[0].trim().length > 0 && parts[1].trim().length > 0 && parts[2].trim().length > 0) {
            let year = Number.parseInt(parts[0]);

            let month = Number.parseInt(parts[1]);
            if (month < 1 || month > 12) {
                throw "Month out of range [1-12]"
            }
            let day = Number.parseInt(parts[2]);
            let date = new Date(year, month - 1, day);
            if (day !== date.getDate()) {
                throw `Incorrect day ${day}`
            }
            return {day: day, month: month, year: year}
        } else if (parts.length === 2 && parts[1].trim().length > 0) {
            let year = Number.parseInt(parts[0]);
            let month = Number.parseInt(parts[1]);
            return {month: month, year: year}
        } else if (parts.length === 1) {
            let year = Number.parseInt(parts[0]);
            return {year: year}
        } else {
            throw `Incorrect format ${value}`
        }
    }

    export default {
        name: 'App',
        components: {
            CardsDirectory,
            Card
        },
        data() {
            return {
                dateString: "",
                id: this.$route.params.cardId,
                selectedCard: {
                    title: "",
                    wikipedia: "",
                    info: "",
                    year: null,
                    month: null,
                    day: null
                }
            }
        },
        computed: {
            dateFormat() {
                if (this.dateString.length === 0) {
                    return ""
                }
                try {
                    let date = parseDateString(this.dateString);
                    this.setCardDate(date.year, date.month, date.day);
                    return ""
                } catch (e) {
                    console.error(e);
                    return `Incorrect date format ${this.dateString}. It should be YYYY/MM/DD`
                }
            }
        },
        watch: {
            // call again the method if the route changes
            '$route': 'fetchData'
        },
        created() {
            this.$store.dispatch('loadUsers');
        },
        methods: {
            fetchData() {
                this.id = this.$route.params.cardId;
            },
            createCard() {
                this.$store.dispatch('createCard', this.selectedCard);
                console.log("Creating card " + this.selectedCard)
            },
            setCardDate(year, month, day) {
                console.log(`year: ${year}, month: ${month}, day: ${day}`);
                this.selectedCard.year = year;
                this.selectedCard.month = month;
                this.selectedCard.day = day;
            }
        }
    }
</script>

<style>
    #cart-creator {
        margin-top: 20px;
    }
</style>
