import React, { useContext, useState } from 'react';
import './Login.css';
import Header from './Header';
import AuthContext from './context/AuthProvider';
import { useNavigate } from 'react-router-dom';
import AuthService from '../services/AuthService';

const AdminLoginComponent = () => {
  console.log("AdminLoginComponent() is called...");
  const { setAuth } = useContext(AuthContext);
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [errMsg, setErrMsg] = useState("");
  const { setIsAdminLoggedIn } = useContext(AuthContext);

  const togglePasswordVisibility = () => {
    console.log("togglePasswordVisibility() is called..");
    setPasswordVisible((prevState) => !prevState);
  };

  const handleSubmit = (e) => {
    console.log("AdminLoginComponent handleSubmit() is called..");
    e.preventDefault();
    const user = { username, password };


    AuthService.loginAdmin(user)
      .then((response) => {
        if (response.data.userDto.role !== 'ADMIN') throw new Error('Invalid Credentials!');
        const { accessToken } = response.data;
        console.log("Response received from login-admin api ", response.data);
        setAuth({ username, accessToken, role: 'ADMIN' });
        setIsAdminLoggedIn(true);
        navigate("/");
      }).catch((error) => {
        console.log("Login failed. Please try again. ", error);
        setErrMsg('Login failed. Please check your username and password and try again.');
      });
  };

  return (
    <div className="page-container">
      <Header />
      <div className="login-container">
        <div className="login-box">
          <h2>Admin Login</h2>
          {errMsg && <p className="error-message">{errMsg}</p>}
          <form action="" method="post">
            <div className="user-box">
              <input type="text" name="username" required="" onChange={(e) => setUsername(e.target.value)}
                value={username} />
              <label>Username</label>
            </div>
            <div className="user-box">
              <input
                type={passwordVisible ? 'text' : 'password'}
                name="password"
                id="password"
                required=""
                onChange={(e) => setPassword(e.target.value)}
                value={password}
              />
              <label>Password</label>
              <span
                className="password-toggle-icon"
                onClick={togglePasswordVisibility}
              >
                <i className={`fas ${passwordVisible ? 'fa-eye-slash' : 'fa-eye'}`}></i>
              </span>
            </div>
            <button type="submit" className="submit-btn" onClick={handleSubmit}>Login</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AdminLoginComponent;
