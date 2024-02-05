import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AddChannelFormView from '../views/AddChannelFormView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/channel/add',
      name: 'addChannelForm',
      component: AddChannelFormView
    },
    {
      path: '/channel/list',
      name: 'channelList',
      component: () => import('../views/ChannelListView.vue')
    },
    {
      path: '/channel/:id',
      name: 'channel',
      component: () => import('../views/ChannelView.vue'),
      props: true
    },
    {
      path: '/channel/:id/edit',
      name: 'editChannelForm',
      component: () => import('../views/EditChannelFormView.vue'),
      props: true
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    }
  ]
})

export default router
