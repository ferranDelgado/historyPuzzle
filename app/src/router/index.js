import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/play/',
            name: 'Game',
            component: require('@/components/Board/BoardMyDraggable').default
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
            redirect: '/play'
        }
    ]
})