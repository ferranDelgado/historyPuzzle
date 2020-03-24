import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'landing-page',
            component: require('@/components/LandingPage').default
        },
        {
            path: '/create/',
            name: 'Create card',
            component: require('@/components/CreateCard/CreateCardPage').default
        },
        {
            path: '/update/:cardId',
            name: 'Update card',
            component: require('@/components/CreateCard/CreateCardPage').default
        },
        // {
        //     path: '/note/:noteId',
        //     name: 'Note',
        //     component: require('@/components/Note').default
        // },
        // {
        //     path: '/note/create/:clientId',
        //     name: 'NoteCreate',
        //     component: require('@/components/NoteCreate').default
        // },
        // {
        //     path: '/clients/list',
        //     name: 'ClientsList',
        //     component: require('@/components/ClientsList').default
        // },
        {
            path: '*',
            redirect: '/'
        }
    ]
})