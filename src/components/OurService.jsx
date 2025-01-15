import React, { useContext } from 'react';
import './OurService.css';
import Footer from './Footer';
import Header from './Header';
import AdminHeader from './AdminHeader';
import AuthContext from './context/AuthProvider';
import CustomerHeader from './CustomerHeader';
import SellerHeader from './SellerHeader';

const OurService = () => {
  const { isAdminLoggedIn, isCustomerLoggedIn, isSellerLoggedIn } = useContext(AuthContext);
  return (
    <div className='outer-div'>
      {console.log("OurService() is called..")}
      {isAdminLoggedIn ? <AdminHeader /> : (isCustomerLoggedIn ? <CustomerHeader /> : (isSellerLoggedIn ? <SellerHeader /> : <Header />))}
      <div className="services-page">
        <h1>Our Services</h1>
        <p>
          At QuitQ, we are dedicated to offering the following services to enhance your online shopping experience:
        </p>
        <div className="services-cards">
          <div className="service-card">
            <i className="fas fa-truck"></i>
            <h3>Express Delivery</h3>
            <p>Enjoy fast and reliable delivery to your doorstep, ensuring that you receive your orders promptly.</p>
          </div>
          <div className="service-card">
            <i className="fas fa-lock"></i>
            <h3>Secure Payment</h3>
            <p>We prioritize the security of your transactions, ensuring that your purchases are safe and secure.</p>
          </div>
          <div className="service-card">
            <i className="fas fa-headset"></i>
            <h3>24/7 Customer Support</h3>
            <p>Our customer support team is available round the clock to assist you with any inquiries or concerns.</p>
          </div>
          <div className="service-card">
            <i className="fas fa-gift"></i>
            <h3>Exclusive Deals</h3>
            <p>Unlock special discounts and exclusive offers as a valued customer of QuitQ.</p>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );

}
export default OurService;