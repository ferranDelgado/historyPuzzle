<template>
    <div class="card">
        <div class="card-image">
            <figure class="image is-4by3">
                <img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">
            </figure>
        </div>
        <div class="card-content">
            <div class="media">
                <div class="media-left">
                    <figure class="image is-48x48">
                        <img src="https://bulma.io/images/placeholders/96x96.png" alt="Placeholder image">
                    </figure>
                </div>
                <div class="media-content">
                    <p class="title is-4">{{ title }}</p>
                    <p class="subtitle is-10">{{day}} {{ computedMonth }} {{ year }}</p>
                </div>
            </div>

            <div class="content">
                {{ info }}
                <p>
                    <a v-if="wikipedia.length > 0" href="#">Wikipedia</a>
                </p>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Card',
        props: {
            title: String,
            wikipedia: String,
            info: String,
            year: Number,
            month: Number,
            day: Number
        },
        components: {
        },
        computed: {
            computedMonth() {
                const monthNames = ["January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
                ];
                return monthNames[this.month - 1]
            }
        },
        data() {
            return {
            }
        },
        methods: {
        }
    }
</script>
