import React from "react";
import './Header.css';
import { Link } from "react-scroll";
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import SearchBar from "./SearchBar";

const Header = () => {
    const navigate = useNavigate();
    const handleHomeClick = () => {
        navigate('/');
    };

    return (
        <header className="header navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container">
                <nav className="navbar-nav">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link to="info" smooth={true} duration={500} className="nav-link" onClick={handleHomeClick}>
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
                </nav>
                <SearchBar />
                <nav className="navbar-nav">
                    <li className="nav-item dropdown">
                        <button className="nav-link dropdown-toggle btn btn-link" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Login
                        </button>
                        <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li>
                                <button className="dropdown-item" onClick={() => navigate('/admin-login')}>
                                    Admin
                                </button>
                            </li>
                            <li>
                                <button className="dropdown-item" onClick={() => navigate('/seller-login')}>
                                    Seller
                                </button>
                            </li>
                            <li>
                                <button className="dropdown-item" onClick={() => navigate('/customer-login')}>
                                    Customer
                                </button>
                            </li>
                        </ul>
                    </li>
                </nav>
            </div>
        </header>
    );
};

export default Header;
