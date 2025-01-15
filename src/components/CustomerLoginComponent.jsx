import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import Header from './Header';
import AuthContext from './context/AuthProvider';
import AuthService from '../services/AuthService';
import { Link } from 'react-router-dom';

const CustomerLoginComponent = () => {
  console.log("CustomerLoginComponent() is called..");
  const { setAuth } = useContext(AuthContext);
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [errMsg, setErrMsg] = useState('');
  const { setIsCustomerLoggedIn } = useContext(AuthContext);

  const togglePasswordVisibility = () => {
    console.log("CustomerLoginComponent togglePasswordVisibility() is called..");
    setPasswordVisible((prevState) => !prevState);
  };

  const handleSubmit = (e) => {
    console.log("CustomerLoginComponent handleSubmit() is called..");
    e.preventDefault();
    const user = { username, password };

    AuthService.loginCustomer(user)
      .then((response) => {
        if (response.data.userDto.role !== 'CUSTOMER') throw new Error('Invalid Credentials!');
        console.log('Response received from login-customer API', response.data);
        const { accessToken } = response.data;
        setAuth({ username, accessToken, role: 'CUSTOMER', id: response.data.userDto.id });
        setIsCustomerLoggedIn(true);
        navigate('/'); 
      })
      .catch((error) => {
        console.error('Login failed. Please try again.', error);
        setErrMsg('Login failed. Please check your username and password and try again.');
      });
  };

  return (
    <div className="page-container">
      <Header />
      <div className="login-container">
        <div className="login-box">
          <h2>Customer Login</h2>
          {errMsg && <p className="error-message">{errMsg}</p>} {/* Display error message */}
          <form onSubmit={handleSubmit}>
            <div className="user-box">
              <input
                type="text"
                name="username"
                required
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
              <label>Username</label>
            </div>
            <div className="user-box">
              <input
                type={passwordVisible ? 'text' : 'password'}
                name="password"
                id="password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <label>Password</label>
              <span
                className="password-toggle-icon"
                onClick={togglePasswordVisibility}
              >
                <i className={`fas ${passwordVisible ? 'fa-eye-slash' : 'fa-eye'}`}></i>
              </span>
            </div>
            <button type="submit" className="submit-btn">Login</button>
          </form>
          <p className="register-link">
            Need an account?{' '}
            <Link to="/customer-register" className="register-link-text">
              Register
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default CustomerLoginComponent;
