import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: '/inicio',
      name: 'inico',
      component: () => import('../views/Inicio.vue')  
    },
    {
      path: '/hombre-screen',
      name: 'hombre-screen',
      component: () => import('../views/Hombre/Hombre-screen.vue')  
    },
    {
      path: '/mujer-screen',
      name: 'mujer-screen',
      component: () => import('../views/Mujer/Mujer-screen.vue')  
    },
    {
      path: '/nenes-screen',
      name: 'nenes-screen',
      component: () => import('../views/Nenes/Nenes-screen.vue')  
    },
    {
      path: '/ofertas-screen',
      name: 'ofertas-screen',
      component: () => import('../views/Ofertas/Ofertas-screen.vue')  
    },
    {
      path: '/profile-screen',
      name: 'profile-screen',
      component: () => import('./../views/Acount/Acount-screen.vue')  
    },
    {
      path: '*',
      component: () => import('../components/NotFound.vue'),
    },
  ]
})

export default router
