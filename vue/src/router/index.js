import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Layout from '../views/Layout.vue'
import Login from '../views/Login.vue'
import ManageBook from '../components/manage/ManageBook.vue'
import ManageUser from '../components/manage/ManageUser.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home,
  },
  {
    path: '/',
    name: 'Layout',
    component:Layout,
    redirect:'/manageBook',
    children:[
      {
        path: '/manageBook',
        name:'ManageBook',
        component:ManageBook,
      },
      {
        path: '/manageUser',
        name:'ManageUser',
        component:ManageUser,
      },
    ]
  },
  {
    path:'/login',
    name:'Login',
    component:Login,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
