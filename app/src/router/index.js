import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/play/V1',
            name: 'Game',
            component: require('@/components/Board/BoardMyDraggable.vue').default
        },
        {
            path: '/play/V2',
            name: 'Game',
            component: require('@/components/Board/TimeLineBoard').default
        },
        {
            path: '/talk',
            name: 'Game',
            component: require('@/components/Talk').default
        },
        // {
        //     path: '/create/',
        //     name: 'Create card',
        //     component: require('@/components/CreateCard/CreateCardPage').default
        // },
        // {
        //     path: '/update/:cardId',
        //     name: 'Update card',
        //     component: require('@/components/CreateCard/CreateCardPage').default
        // },
        {
            path: '*',
            redirect: '/play/V2'
        }
    ]
})