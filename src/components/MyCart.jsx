import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiService from "../services/ApiService";
import './MyCart.css';
import AuthContext from './context/AuthProvider';
import CustomerService from '../services/CustomerService';

const MyCart = () => {
  console.log("MyCart() is called..");
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [shippingAddress, setShippingAddress] = useState("");
  const navigate = useNavigate();
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    console.log("MyCart useEffect() is called..");
    CustomerService.getCart(auth.id, auth.accessToken)
      .then(response => {
        console.log(response.data);
        if (Array.isArray(response.data.cartItems)) {
          setCartItems(response.data.cartItems);
        } else {
          setCartItems([]);
        }
        setLoading(false);
      })
      .catch(err => {
        setError('Failed to load cart items.');
        setLoading(false);
      });
  }, [auth.id, auth.accessToken]);

  const handleQuantityChange = (cartItemId, newQuantity) => {
    console.log("MyCart handleQuantityChange() is called..");
    const updatedCartItems = cartItems.map(item =>
      item.cartItemId === cartItemId
        ? { ...item, quantity: newQuantity }
        : item
    );
    setCartItems(updatedCartItems);
  };

  const handleDeleteItem = (cartItemId) => {
    console.log("MyCart handleDeleteItem() is called..");
    const item = cartItems.find(item => item.cartItemId === cartItemId);
    const productId = item.product.productId;
    CustomerService.deleteProductFromCart(auth.id, productId, auth.accessToken)
      .then((response) => {
        alert('Product Removed Successfully...');
        setCartItems(response.data.cartItems);
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleUpdateItem = (cartItemId) => {
    console.log("MyCart handleUpdateItem() is called..");
    const updatedItem = cartItems.find(item => item.cartItemId === cartItemId);
    if (updatedItem) {
      const { quantity } = updatedItem;
      const productId = updatedItem.product.productId;
      CustomerService.updateCart(auth.id, productId, quantity, auth.accessToken)
        .then((response) => {
          alert('Cart Updated Successfully...');
          setCartItems(response.data.cartItems);
          console.log("Updated cart:", response.data);
        })
        .catch((error) => {
          console.error("Update Cart Error: ",error);
        });
    }
  };

  const handleProceedToCheckout = () => {
    console.log("MyCart handleProceedToCheckout() is called..");
    if (!shippingAddress.trim()) {
      alert("Please enter your shipping address.");
      return;
    }

    CustomerService.placeOrder(auth.id, shippingAddress, auth.accessToken)
      .then((response) => {
        console.log("Response received from placeOrder().");
        alert("Your order is placed successfully...");
        console.log(response.data);
        setCartItems([]);
        setShowModal(false);
        navigate('/my-orders');
      })
      .catch((err) => {
        console.error("Error received from placeOrder()..: ",err);
        alert("Failed to place the order. Please try again.");
      });
  };

  const handleModalClose = () => {
    console.log("handleModalClose() is called..");
    setShowModal(false);
  };

  const handleModalOpen = () => {
    console.log("handleModelOpen() is called..");
    setShowModal(true);
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="my-cart-container">
      <h2>My Cart</h2>
      <table className="cart-table">
        <thead>
          <tr>
            <th>Product Name</th>
            <th>Brand Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {cartItems.map(item => (
            <tr key={item.cartItemId}>
              <td>{item.product.name}</td>
              <td>{item.product.brandName}</td>
              <td>
                <input
                  type="number"
                  value={item.quantity}
                  min="1"
                  max={item.product.stockQuantity}
                  onChange={(e) => handleQuantityChange(item.cartItemId, parseInt(e.target.value))}
                />
              </td>
              <td>{item.product.price.toFixed(2)}</td>
              <td>{(item.product.price * item.quantity).toFixed(2)}</td>
              <td>
                <button className="btn btn-danger" onClick={() => handleDeleteItem(item.cartItemId)}>
                  Delete
                </button>
                <button
                  style={{ width: "100px", marginLeft: "10px", marginTop: "0" }}
                  className="btn btn-primary"
                  onClick={() => handleUpdateItem(item.cartItemId)}
                >
                  Update
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="cart-total">
        <span>Total: {cartItems.reduce((total, item) => total + (item.product.price * item.quantity), 0).toFixed(2)}</span>
      </div>
      <div className="cart-actions">
        <button onClick={handleModalOpen} className="btn btn-primary">
          Proceed to Checkout
        </button>
      </div>

      {showModal && (
        <div style={{
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: 'rgba(0, 0, 0, 0.5)',
          zIndex: 1000
        }}>
          <div style={{
            backgroundColor: 'white',
            padding: '20px',
            borderRadius: '5px',
            width: '400px',
            textAlign: 'center'
          }}>
            <h3>Enter Your Shipping Address</h3>
            <input
              type='text'
              value={shippingAddress}
              onChange={(e) => setShippingAddress(e.target.value)}
              placeholder="Enter your shipping address here"
              style={{
                width: '100%',
                padding: '10px',
                marginTop: '10px',
              }}
            />
            <div style={{
              display: 'flex',
              justifyContent: 'space-between',
              marginTop: '20px'
            }}>
              <button
                className="btn btn-secondary"
                onClick={handleModalClose}
                style={{
                  padding: '10px 20px',
                  backgroundColor: '#f39c12',
                  color: 'white',
                  width: "40%",
                  height: '50px',
                  marginBottom: "0",
                  borderRadius: '5px',
                }}
              >
                Cancel
              </button>
              <button
                className="btn btn-primary"
                onClick={handleProceedToCheckout}
                style={{
                  padding: '10px 20px',
                  backgroundColor: '#3498db',
                  width: "40%",
                  height: '50px',
                  marginBottom: "10px",
                  marginTop: "0",
                  color: 'white',
                  borderRadius: '5px',
                }}
              >
                OK
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default MyCart;
