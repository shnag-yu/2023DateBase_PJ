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
    },
    {
      path : "/msglist",
      name : 'msglist',
      component : () => import('../views/MsgListView.vue')
    },
    {
      path : "/adminlogin",
      name : 'adminlogin',
      component : () => import('../views/AdminLoginView.vue')
    },
    {
      path : "/adminmain",
      name : 'adminmain',
      component : () => import('../views/AdminMainView.vue')
    },
    {
      path : "/adminuser",
      name : "adminuser",
      component : () => import('../views/AdminUserView.vue')
    },
    {
      path : "/adminmerchant",
      name : "adminmerchant",
      component : () => import('../views/AdminMerchantView.vue')
    },
    {
      path : "/adminproduct",
      name : "adminproduct",
      component : () => import('../views/AdminProductView.vue')
    },
    {
      path: "/advanced_query_user",
      name: 'advanced_query_user',
      component: () => import('../views/AdvancedQuery/User.vue')
    },
    {
      path: "/advanced_query_merchant",
      name: 'advanced_query_merchant',
      component: () => import('../views/AdvancedQuery/Merchant.vue')
    },
    {
      path: "/advanced_query_product",
      name: 'advanced_query_product',
      component: () => import('../views/AdvancedQuery/Product.vue')
    },
    {
      path: "/adminpricehistory/:productId",
      name: 'adminpricehistory',
      component: () => import('../views/AdminPriceHistoryView.vue')
    },
    { 
      path: "/adminplatform",
      name: 'adminplatform',
      component: () => import('../views/AdminPlatformView.vue')
    }
  ]
})

export default router
