import React, { useContext } from 'react';
import HeroSection from '../components/HeroSection';
import InfoSection from '../components/InfoSection';
import Header from '../components/Header';
import Footer from '../components/Footer';
import AdminHeader from '../components/AdminHeader';
import AuthContext, { AuthProvider } from '../components/context/AuthProvider';
import CustomerHeader from '../components/CustomerHeader';
import SellerHeader from '../components/SellerHeader';
import ViewAllProducts from '../components/ViewAllProducts';
import MyCart from '../components/MyCart';
import MyOrders from '../components/MyOrders';

const AdminMyOrdersPage = () => {
  return (
    <div className="main-container">
      <AdminHeader />
      <MyOrders />
    </div>
  );
};

export default AdminMyOrdersPage;
