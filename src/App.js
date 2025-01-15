import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import HomePage from './pages/HomePage';
import AboutUsPage from './pages/AboutUsPage';
import { Routes, Route } from 'react-router-dom';
import 'font-awesome/css/font-awesome.min.css';
import OurServicePage from './pages/OurServicePage';
import { RegisterCustomerComponent } from './components/RegisterCustomer';
import CustomerLoginComponent from './components/CustomerLoginComponent';
import SellerLoginComponent from './components/SellerLoginComponent';
import AdminLoginComponent from './components/AdminLoginComponent';
import { RegisterSellerComponent } from './components/RegisterSeller';
import RequireAuth from './components/context/RequireAuth';
import { AuthProvider } from './components/context/AuthProvider';
import LogoutComponent from './components/LogoutComponent';
import AdminDashboard from './components/AdminDashboard';
import SellerDashboard from './components/SellerDashboard';
import Products from './pages/Products';
import { SearchProvider } from './components/context/SearchContext';
import SearchedProducts from './pages/SearchedProducts';
import ViewProductPage from './pages/ViewProductPage';
import MyCartPage from './pages/MyCartPage';
import MyOrdersPage from './pages/MyOrdersPage';
import AdminMyOrdersPage from './pages/AdminMyOrdersPage';

function App() {
  return (
    <AuthProvider>
      <SearchProvider>
        <div className="App">
          <Routes>
            {/* Public Routes */}
            <Route path='/' element={<HomePage />} />
            <Route path='/products' element={<Products />} />
            <Route path='/view-products/:search' element={<SearchedProducts />} />
            <Route path='/view-product/:productId' element={<ViewProductPage />} />
            <Route path="/customer-login" element={<CustomerLoginComponent />} />
            <Route path="/seller-login" element={<SellerLoginComponent />} />
            <Route path="/admin-login" element={<AdminLoginComponent />} />
            <Route path="/customer-register" element={<RegisterCustomerComponent />} />
            <Route path="/seller-register" element={<RegisterSellerComponent />} />
            <Route path='/about' element={<AboutUsPage />} />
            <Route path='/services' element={<OurServicePage />} />

            {/* Protected Routes */}
            <Route element={<RequireAuth allowedRoles={["ADMIN", "CUSTOMER", "SELLER"]} />}>
              <Route path='/logout' element={<LogoutComponent />} />
            </Route>

            {/* Protected Routes for admin only */}
            <Route element={<RequireAuth allowedRoles={["ADMIN"]} />}>
              <Route path='/admin-controls' element={<AdminDashboard />} />
            </Route>

            {/* Protected Routes for seller only */}
            <Route element={<RequireAuth allowedRoles={["SELLER"]} />}>
              <Route path='/seller-controls' element={<SellerDashboard />} />
            </Route>

            {/* Protected Routes for customer only */}
            <Route element={<RequireAuth allowedRoles={["CUSTOMER"]} />}>
              <Route path='/my-cart' element={<MyCartPage />} />
              <Route path='/my-orders' element={<MyOrdersPage />} />
            </Route>
          </Routes>
        </div>
      </SearchProvider>
    </AuthProvider>
  );
}

export default App;
