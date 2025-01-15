import React, { useContext, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './ViewProduct.css';
import AuthContext from './context/AuthProvider';
import CustomerService from '../services/CustomerService';

const ViewProduct = () => {
  console.log("ViewProduct() is called..");
  const { auth, isCustomerLoggedIn } = useContext(AuthContext);
  const navigate = useNavigate();
  const location = useLocation(); 
  const product = location.state?.product; 
  const [quantity, setQuantity] = useState(1); 

  if (!product) return <div id="product-not-found">Product not found</div>; 

  const handleBack = () => {
    console.log("ViewProduct handleBack() is called..");
    navigate(-1); 
  };

  const handleAddToCart = () => {
    console.log("ViewProduct handleAddToCart() is called..");
    if (!isCustomerLoggedIn) {
      const isConfirm = window.confirm('You need to log in as a customer!!!');
      if(isConfirm) {
        navigate('/logout')
        navigate('/customer-login')        
      };
    } else {
      CustomerService.addToCart(auth.id, product.productId, quantity, auth.accessToken).then((response) => {
        console.log("Added to cart...", response.data);
        alert('Product added to cart successfully...');
        navigate('/my-cart');
      }).catch((error) => {
        console.log(error);
      });
    }
  };

  const handleQuantityChange = (e) => {
    console.log("ViewProduct handleQuantityChange() is called..");
    const value = Math.max(1, Math.min(e.target.value, product.stockQuantity)); 
    setQuantity(value);
  };

  return (
    <div id="view-product-container">
      <div id="product-card-large">
        <div id="product-image-container">
          <img
            id="product-image"
            src={product.image || 'placeholder-image.jpg'}
            alt={product.name}
          />
        </div>
        <div id="product-details-container">
          <h1 id="product-name">{product.name}</h1>
          <p id="product-description">{product.description}</p>
          <p id="product-brandName">{product.brandName}</p>
          <p id="product-price" className="product-price">{product.price.toFixed(2)}</p>
          <div id="product-quantity-container">
            <label htmlFor="quantity">Quantity:</label>
            <input
              type="number"
              id="quantity"
              value={quantity}
              min="1"
              max={product.stockQuantity}
              onChange={handleQuantityChange}
            />
          </div>
          <div id="product-actions">
            <button id="add-to-cart-button" className="btn btn-primary" onClick={handleAddToCart}>
              Add to Cart
            </button>
            <button id="back-button" className="btn btn-secondary" onClick={handleBack}>
              Back
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewProduct;
