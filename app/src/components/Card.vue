<template>
    <div class="card">
        <div v-if="showImage" class="card-image">
            <figure class="image is-4by3">
                <img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">
            </figure>
        </div>
        <div class="card-content">
            <div class="media">
                <div v-if="showMiniImage" class="media-left">
                    <figure class="image is-80x80">
                        <img src="https://bulma.io/images/placeholders/96x96.png" alt="Placeholder image">
                    </figure>
                </div>
                <div class="media-content">
                    <p class="title is-4">{{ title }}</p><p class="subtitle is-6">
                        <span v-if="year" class="tag is-dark">{{computedDay}} {{ computedMonth }} {{ computedYear }}</span>
                    </p>
                </div>
            </div>

            <div class="content">
                <b-collapse  v-if="info.length > 0" :open="false" position="is-bottom" aria-id="contentIdForA11y1">
                    <template v-slot:trigger="props">
                        <a aria-controls="contentIdForA11y1">
                            <!--                        <b-icon :icon="!props.open ? 'menu-down' : 'menu-up'"></b-icon>-->
                            <font-awesome-icon :icon="props.open ? 'caret-up' : 'caret-down' "></font-awesome-icon>
                            {{ !props.open ? 'More info' : 'Less info' }}
                        </a>
                    </template>
                    <p>
                        {{ info }}
                    </p>
                </b-collapse>
                <p>
                    <a v-if="wikipedia.length > 0" href="#">Wikipedia</a>
                </p>
            </div>
            <slot></slot>
        </div>
    </div>
</template>

<script>
  export default {
    name: 'Card',
    props: {
      explanationOpen: {
        type: Boolean,
        default: false
      },
      title: String,
      wikipedia: {
        type: String,
        default: ''
      },
      info: {
        type: String,
        default: ''
      },
      year: Number,
      month: {
        type: Number,
        default: 0
      },
      day: {
        type: Number,
        default: 0
      },
      showMiniImage: {
        type: Boolean,
        default: false
      },
      showImage: {
        type: Boolean,
        default: false
      }
    },
    components: {},
    computed: {
      computedYear() {
        if(this.year < 0) {
          return `${Math.abs(this.year)}BC`
        } else {
          return `${this.year}`
        }
      },
      computedDay() {
        /**
         * TODO: this should be stracted and maybe use momentjs
         */
        if(typeof this.day === "undefined") {
          return ""
        } else if(this.day < 1 || this.day > 31) {
          return ""
        }

        switch (this.day|0) {
          case 0:
            return "";
          case 1:
            return "1st";
          case 2:
            return "2nd";
          case 3:
            return "3rd";
          case 21:
            return "21st";
          case 22:
            return "22nd";
          case 23:
            return "23rd";
          case 31:
            return "31st";
          default:
            return `${this.day|0}th`
        }
      },
      computedMonth() {
        const monthNames = ["January", "February", "March", "April", "May", "June",
          "July", "August", "September", "October", "November", "December"
        ];
        if (this.month >= 1 && this.month <= 12) {
          return monthNames[this.month - 1];
        } else {
          return "";
        }
      }
    },
    data() {
      return {}
    },
    methods: {}
  }
</script>

<style scoped>
    .media-left {
        margin-right: 0.5rem;
    }

    .card-content {
        padding: 0.5rem;
    }

    .card .media:not(:last-child) {
         margin-bottom: 0;
    }
</style>