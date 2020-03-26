import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import {library} from '@fortawesome/fontawesome-svg-core'
import {
    faPlus, faUserSecret, faCheck, faCheckCircle, faInfoCircle, faExclamationTriangle, faExclamationCircle,
    faArrowUp, faAngleRight, faAngleLeft, faAngleDown,
    faEye, faEyeSlash, faCaretDown, faCaretUp, faUpload, faCoffee, faChild, faCircle, faArchive, faComment
} from '@fortawesome/free-solid-svg-icons'

import {faTwitter, faWikipediaW} from '@fortawesome/free-brands-svg-icons'
import {FontAwesomeIcon, FontAwesomeLayers, FontAwesomeLayersText} from '@fortawesome/vue-fontawesome'

library.add(faWikipediaW, faPlus,
    faUserSecret, faCheck, faCheckCircle, faInfoCircle, faExclamationTriangle, faExclamationCircle,
    faArrowUp, faAngleRight, faAngleLeft, faAngleDown,
    faEye, faEyeSlash, faCaretDown, faCaretUp, faUpload, faCoffee, faChild, faCircle, faArchive, faComment, faTwitter
);

Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('font-awesome-layers', FontAwesomeLayers);
Vue.component('font-awesome-layers-text', FontAwesomeLayersText);

Vue.config.productionTip = false;
Vue.use(Buefy, {
    defaultIconComponent: 'font-awesome-icon',
    defaultIconPack: 'fas',
});

import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios);

Vue.axios.defaults.baseURL = "http://localhost:5050/";

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
