import React from "react";
import './Header.css';
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import { Link } from "react-scroll";
import SearchBar from "./SearchBar";

const SellerHeader = () => {
  console.log("SellerHeader() is called..");
  const navigate = useNavigate();

  return (
    <header className="header navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <nav className="navbar-nav d-flex w-100 align-items-center">
          <ul className="navbar-nav me-auto">
            <li className="nav-item">
              <Link to="info" smooth={true} duration={500} className="nav-link" onClick={() => navigate('/')}>
                Home
              </Link>
            </li>
            <li className="nav-item">
              <button className="nav-link btn-link" onClick={() => navigate('/about')}>
                About Us
              </button>
            </li>
            <li className="nav-item">
              <button className="nav-link btn-link" onClick={() => navigate('/services')}>
                Services
              </button>
            </li>
            <li className="nav-item">
              <button className="nav-link btn-link" onClick={() => navigate('/products')}>
                Products
              </button>
            </li>
          </ul>

          <div className="search-container mx-auto">
            <SearchBar />
          </div>

          <ul className="navbar-nav ms-auto">
            <li className="nav-item dropdown">
              <button className="nav-link dropdown-toggle btn btn-link" id="profileDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <img
                  src="https://via.placeholder.com/40"
                  alt="Profile"
                  className="rounded-circle"
                  style={{ width: "40px", height: "40px" }}
                />
              </button>
              <ul className="dropdown-menu dropdown-menu-end" aria-labelledby="profileDropdown">
                <li>
                  <button className="dropdown-item" onClick={() => navigate('/seller-controls')}>
                    Dashboard
                  </button>
                </li>
                <li>
                  <button className="dropdown-item" onClick={() => navigate('/logout')}>
                    Logout
                  </button>
                </li>
              </ul>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  );
};

export default SellerHeader;
