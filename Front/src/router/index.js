import Vue from 'vue'
import VueRouter from 'vue-router'
import { jwtDecode } from "jwt-decode";
import Inicio from './../views/Inicio.vue'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  routes: [

    { path: '/', component: Inicio },
    {
      path: '/login',
      name: 'login',
      component: () => import('../modules/Auth/Views/Login.vue')
    },
    {
      path: '/recuperarContrasena',
      name: 'recuperarContrasena',
      component: () => import('../modules/Auth/Views/RecuperarPassword.vue')
    },
    {
      path: '/enviarSolicitdVendedor',
      name: 'enviarSolicitdVendedor',
      component: () => import('../modules/Vendedores/Solicitudes/Views/EnviarSolicitud.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');
        const tokenDecoded = jwtDecode(token);

        if (tokenDecoded.rol === '[COMPRADOR]') {
          next();
        } else {
          next('/inicio');
        }
      }
    },
    {
      path: '/crearCuenta',
      name: 'crearCuenta',
      component: () => import('../modules/Auth/Views/CrearCuenta.vue')
    },
    {
      path: '/carritoCompras',
      name: 'carritoCompras',
      component: () => import('../modules/Carrito/Views/CarritoCompras.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');
        if (token) {
          next();
        } else {
          next('/login');
        }
      }
    },
    {
      path: '/checkoutDireccion',
      name: 'checkoutDireccion',
      component: () => import('../modules/Venta/Views/Direcciones/CheckoutDireccion.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');
        if (token) {
          next();
        } else {
          next('/login');
        }
      }
    },
    {
      path: '/checkoutMetodoPago',
      name: 'checkoutMetodoPago',
      component: () => import('../modules/Venta/Views/MetodoPago/CheckoutMetodoPago.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');
        if (token) {
          next();
        } else {
          next('/login');
        }
      }
    },
    {
      path: '/profile-screen',
      name: 'profile-screen',
      component: () => import('./../modules/Account/Views/Acount-screen.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');

        if (token) {
          next();
        } else {
          next('/login');
        }
      }
    },
    {
      path: '/vendedores',
      name: 'vendedores',
      component: () => import('./../modules/Vendedores/ListaVendedores/Vendedores.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');
        const tokenDecoded = jwtDecode(token);

        if (tokenDecoded.rol === '[ADMIN]') {
          next();
        } else {
          next('/inicio');
        }
      }
    },
    {
      path: '/Productos',
      name: 'productos',
      component: () => import('./../modules/Productos/Views/Productos.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');
        const tokenDecoded = jwtDecode(token);

        if (tokenDecoded.rol === '[ADMIN]') {
          next();
        } else {
          next('/inicio');
        }
      }
    },
    {
      path: '/MisProductos',
      name: 'MisProductos',
      component: () => import('./../modules/Productos/Views/ProductosVendedor/MisProductos.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');
        const tokenDecoded = jwtDecode(token);

        if (tokenDecoded.rol === '[VENDEDOR]') {
          next();
        } else {
          next('/inicio');
        }
      }
    },
    {
      path: '/categorias-screen',
      name: 'categorias-screen',
      component: () => import('./../modules/Categorias/Views/Categorias/Categorias.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');
        const tokenDecoded = jwtDecode(token);

        if (tokenDecoded.rol === '[ADMIN]') {
          next();
        } else {
          next('/inicio');
        }
      }
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
      component: () => import('./../views/Nenes/Productos/kid-producto.vue')
    },
    {
      path: '/ofertas-screen',
      name: 'ofertas-screen',
      component: () => import('../views/Ofertas/Ofertas-screen.vue')
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
      component: () => import('./../modules/Usuarios/views/Usuarios.vue'),
      beforeEnter: (to, from, next) => {
        const token = localStorage.getItem('token');
        const tokenDecoded = jwtDecode(token);

        if (tokenDecoded.rol === '[ADMIN]') {
          next();
        } else {
          next('/inicio');
        }
      }
    },
    {
      path: '*',
      component: () => import('../components/NotFound.vue'),
    },
  ]
})


export default router
