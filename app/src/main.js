import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import {library} from '@fortawesome/fontawesome-svg-core'
import {
    faAngleDown,
    faAngleLeft,
    faAngleRight,
    faArchive,
    faArrowUp,
    faCaretDown,
    faCaretUp,
    faCheck,
    faCheckCircle,
    faChild,
    faCircle,
    faCoffee,
    faComment,
    faExclamationCircle,
    faExclamationTriangle,
    faEye,
    faEyeSlash,
    faInfoCircle,
    faPlus,
    faUpload,
    faUserSecret,
    faTimesCircle,
    faPuzzlePiece
} from '@fortawesome/free-solid-svg-icons'

import {faTwitter, faWikipediaW} from '@fortawesome/free-brands-svg-icons'
import {FontAwesomeIcon, FontAwesomeLayers, FontAwesomeLayersText} from '@fortawesome/vue-fontawesome'
import axios from 'axios'
import VueAxios from 'vue-axios'

library.add(faWikipediaW, faPlus,
    faUserSecret, faCheck, faCheckCircle, faInfoCircle, faExclamationTriangle, faExclamationCircle,
    faArrowUp, faAngleRight, faAngleLeft, faAngleDown,
    faEye, faEyeSlash, faCaretDown, faCaretUp, faUpload, faCoffee, faChild, faCircle, faArchive, faComment, faTwitter,
    faTimesCircle,
    faPuzzlePiece
);

Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('font-awesome-layers', FontAwesomeLayers);
Vue.component('font-awesome-layers-text', FontAwesomeLayersText);

Vue.config.productionTip = false;
Vue.use(Buefy, {
    defaultIconComponent: 'font-awesome-icon',
    defaultIconPack: 'fas',
});

Vue.use(VueAxios, axios);

Vue.axios.defaults.baseURL = process.env.NODE_ENV === "production" ? "/" : "http://localhost:5050/";

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
