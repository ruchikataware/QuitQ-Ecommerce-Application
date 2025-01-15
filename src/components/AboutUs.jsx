import React, { useContext } from 'react';
import './AboutUs.css';
import Header from './Header';
import Footer from './Footer';
import AuthContext from './context/AuthProvider';
import AdminHeader from './AdminHeader';
import CustomerHeader from './CustomerHeader';
import SellerHeader from './SellerHeader';

const AboutUs = () => {
    const { isAdminLoggedIn, isCustomerLoggedIn, isSellerLoggedIn } = useContext(AuthContext);
    return (
        <>
            {isAdminLoggedIn ? <AdminHeader /> : (isCustomerLoggedIn ? <CustomerHeader /> : (isSellerLoggedIn ? <SellerHeader /> : <Header />))}
            <section id="about-us" className="about-us-section">
                <div className='about-us-content'>
                    <div className='text-section'>
                        <h1>Our Vision</h1>
                        <p>
                            QuickQ is committed to providing a
                            convenient and reliable online
                            shopping experience for customers in India.
                        </p>
                    </div>
                    <div className='image-section'>
                        <img src={require('../images/AU1.jpg')} alt="Vision 1" />
                    </div>
                </div>
            </section>
            <Footer />
        </>
    )
}
export default AboutUs;