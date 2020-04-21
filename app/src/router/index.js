import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/play/V1',
            name: 'Game V1',
            component: require('@/components/Board/BoardMyDraggable.vue').default
        },
        {
            path: '/play/V2',
            name: 'Game V2',
            component: require('@/components/Board/TimeLineBoard').default
        },
        {
            path: '/talk',
            name: 'Talking ',
            component: require('@/components/Talk').default
        },
        {
            path: '/room/',
            name: 'Create Room',
            component: require('@/components/Room/CreateRoom').default
        },
        {
            path: '/room/:roomName',
            name: 'Setup Room',
            component: require('@/components/Room/SetupRoom').default
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

        // {
        //     path: '*',
        //     redirect: '/play/V2'
        // }
    ]
})