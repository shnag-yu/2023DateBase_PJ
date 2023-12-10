import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import UserMainView from '../views/UserMainView.vue'
import HomeView from '../views/HomeView.vue'
import ProductView from '../views/ProductView.vue'
import MyFavorite from '../views/MyFavorite.vue'
import MerchantRegisterView from '../views/MerchantRegisterView.vue'
import MerchantLoginView from '../views/MerchantLoginView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'hello',
      // component: HomeView
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/usermain',
      name: 'usermain',
      component: UserMainView
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView
    },
    {
      path: '/product/:productId',
      name: 'product',
      component: ProductView
    },
    {
      path : "/my-favorites",
      name :  'my-favorites',
      component : MyFavorite
    },
    {
      path : "/merchantregister",
      name :  'merchantregister',
      component : MerchantRegisterView
    },
    {
      path : "/merchantlogin",
      name : 'merchantlogin',
      component : MerchantLoginView
    },
    {
      path : "/merchantmain",
      name : 'merchantmain',
      component : () => import('../views/MerchantMainView.vue')
    }
  ]
})

export default router
