import React, { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Login.css';
import Header from './Header';
import AuthContext from './context/AuthProvider';
import AuthService from '../services/AuthService';

const SellerLoginComponent = () => {
  console.log("SellerLoginComponent() is called..");
  const { setAuth } = useContext(AuthContext);
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [errMsg, setErrMsg] = useState('');
  const { setIsSellerLoggedIn } = useContext(AuthContext);

  const togglePasswordVisibility = () => {
    setPasswordVisible((prevState) => !prevState);
  };

  const handleSubmit = (e) => {
    console.log("SellerLoginComponent handleSubmit() is called..");
    e.preventDefault();
    const user = { username, password };

    AuthService.loginSeller(user)
      .then((response) => {
        if (response.data.userDto.role !== 'SELLER') throw new Error('Invalid Credentials!');
        console.log('Response received from login-seller API', response.data);
        const { accessToken } = response.data;
        setAuth({ username, accessToken, role: 'SELLER', id: response.data.userDto.id });
        setIsSellerLoggedIn(true);
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
          <h2>Seller Login</h2>
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
            <Link to="/seller-register" className="register-link-text">
              Register
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default SellerLoginComponent;
