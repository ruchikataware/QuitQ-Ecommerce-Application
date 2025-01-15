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
import ViewProduct from '../components/ViewProduct';

const ViewProductPage = () => {
  const { isAdminLoggedIn, isCustomerLoggedIn, isSellerLoggedIn } = useContext(AuthContext);
  return (
    <div className="main-container">
      {isAdminLoggedIn ? <AdminHeader /> : (isCustomerLoggedIn ? <CustomerHeader /> : (isSellerLoggedIn ? <SellerHeader /> : <Header />))}
      <ViewProduct />
    </div>
  );
};

export default ViewProductPage;
