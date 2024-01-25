import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'inicio',
      component: () => import('../views/Inicio.vue')
    },
    {
      path: '/inicio',
      name: 'inicio',
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
      path: '/men-ropa',
      name: 'men-ropa',
      component: () => import('./../views/Hombre/Ropa/men-ropa.vue')
    },
    {
      path: '/men-calzado',
      name: 'men-calzado',
      component: () => import('./../views/Hombre/Calzado/men-calzado.vue')
    },
    {
      path: '/men-accesorios',
      name: 'men-accesorios',
      component: () => import('./../views/Hombre/Accesorios/men-accesorios.vue')
    },
    {
      path: '*',
      component: () => import('../components/NotFound.vue'),
    },
  ]
})

export default router
