import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: () => import('../views/Inicio.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../modules/Auth/Views/Login.vue')
    },
    {
      path: '/enviarSolicitdVendedor',
      name: 'enviarSolicitdVendedor',
      component: () => import('../modules/Vendedores/Solicitudes/Views/EnviarSolicitud.vue')
    },
    {
      path: '/crearCuenta',
      name: 'crearCuenta',
      component: () => import('../modules/Auth/Views/CrearCuenta.vue')
    },
    {
      path: '/carritoCompras',
      name: 'carritoCompras',
      component: () => import('../modules/Carrito/Views/CarritoCompras.vue')
    },
    {
      path: '/checkoutDireccion',
      name: 'checkoutDireccion',
      component: () => import('../modules/Ventas/Direcciones/Views/Direccion.vue')
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
    //Sub-Menu-Kid
    {
      path: '/kid-ropa',
      name: 'kid-ropa',
      component: () => import('./../views/Nenes/Ropa/kid-ropa.vue')
    },
    {
      path: '/kid-calzado',
      name: 'kid-calzado',
      component: () => import('./../views/Nenes/Calzado/kid-calzado.vue')
    },
    {
      path: '/kid-accesorios',
      name: 'kid-accesorios',
      component: () => import('./../views/Nenes/Accesorios/kid-accesorios.vue')
    },
    {
      path: '/kid-producto',
      name: 'kid-producto',
      component: () => import ('./../views/Nenes/Productos/kid-producto.vue')
    },
    {
      path: '/ofertas-screen',
      name: 'ofertas-screen',
      component: () => import('../views/Ofertas/Ofertas-screen.vue')
    },
    {
      path: '/profile-screen',
      name: 'profile-screen',
      component: () => import('./../views/Acount/Acount-screen.vue'),
      beforeEnter: (to, from, next) => {
        // Verificar si el token está presente en el localStorage
        const token = localStorage.getItem('token');

        if (token) {
          // Aquí puedes realizar la lógica para verificar la validez del token si es necesario
          // Por ejemplo, podrías decodificar el token para verificar su validez o tiempo de expiración

          // Si el token es válido, permite que el usuario acceda a la ruta
          next();
        } else {
          // Si el token no está presente en el localStorage, redirigir al usuario a la página de inicio de sesión
          next('/login');
        }
      }
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
      path: '/usuarios',
      name: 'usuarios',
      component: () => import('./../modules/Usuarios/views/Usuarios.vue')
    },
    {
      path: '*',
      component: () => import('../components/NotFound.vue'),
    },
  ]
})

export default router
