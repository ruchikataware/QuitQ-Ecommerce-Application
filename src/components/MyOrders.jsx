import React, { useState, useEffect, useContext } from 'react';
import CustomerService from '../services/CustomerService';
import AuthContext from './context/AuthProvider';
import './MyOrders.css';
import ApiService from '../services/ApiService';
import AdminService from '../services/AdminService';

const MyOrders = () => {
  console.log("MyOrders() is called..");
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedOrder, setSelectedOrder] = useState(null); 
  const { auth, isAdminLoggedIn } = useContext(AuthContext);

  useEffect(() => {
    console.log("MyOrders useEffect() is called..");
    CustomerService.getOrders(auth.id, auth.accessToken)
      .then(response => {
        console.log("Response received from getOrders():",response.data);
        setOrders(response.data || []);
        setLoading(false);
      })
      .catch(err => {
        console.error("Error from getOrders():",err);
        setError('Failed to load orders.');
        setLoading(false);
      });
  }, []);

  const handleViewDetails = (orderId) => {
    console.log("handleViewDetails() is called..");
    const order = orders.find(order => order.orderId === orderId);
    setSelectedOrder(order);
  };

  const handleCloseDetails = () => {
    console.log("handleCloseDetails() is called..");
    setSelectedOrder(null);
  };

  return (
    <div id="my-orders-container" className="my-orders-container">
      <h2 id="orders-heading">My Orders</h2>
      <table id="orders-table" className="orders-table">
        <thead id="orders-table-header">
          <tr>
            <th id="order-id-header">Order ID</th>
            <th id="order-date-header">Order Date</th>
            <th id="status-header">Status</th>
            <th id="total-amount-header">Total Amount</th>
            <th id="shipping-address-header">Shipping Address</th>
            <th id="actions-header">Actions</th>
          </tr>
        </thead>
        <tbody id="orders-table-body">
          {orders.map(order => (
            <tr key={`order-row-${order.orderId}`} id={`order-row-${order.orderId}`}>
              <td id={`order-id-${order.orderId}`}>{order.orderId}</td>
              <td id={`order-date-${order.orderId}`}>{new Date(order.orderDate).toLocaleString()}</td>
              <td id={`order-status-${order.orderId}`}>{order.status}</td>
              <td id={`order-total-${order.orderId}`}>{order.totalAmount.toFixed(2)}</td>
              <td id={`order-shipping-${order.orderId}`}>{order.shippingAddress.replace(/=+$/, '').replace(/\+/g, ' ').replace(/%2C/g, ',')}</td>
              <td id={`order-actions-${order.orderId}`}>
                <button
                  id={`view-details-btn-${order.orderId}`}
                  className="btn btn-primary"
                  onClick={() => handleViewDetails(order.orderId)}
                >
                  View Details
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {selectedOrder && (
        <div
          id={`order-details-modal-${selectedOrder.orderId}`}
          style={{
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            zIndex: 1000,
          }}
        >
          <div
            id={`order-details-content-${selectedOrder.orderId}`}
            style={{
              backgroundColor: 'white',
              padding: '20px',
              borderRadius: '5px',
              width: '600px',
              position: 'relative',
            }}
          >
            <button
              id={`close-modal-btn-${selectedOrder.orderId}`}
              style={{
                position: 'absolute',
                top: '10px',
                right: '10px',
                background: 'transparent',
                border: 'none',
                fontSize: '20px',
                cursor: 'pointer',
              }}
              onClick={handleCloseDetails}
            >
              &times;
            </button>
            <h3 id={`order-details-heading-${selectedOrder.orderId}`}>Order Details</h3>
            <table id={`order-items-table-${selectedOrder.orderId}`} className="order-items-table">
              <thead id={`order-items-header-${selectedOrder.orderId}`}>
                <tr>
                  <th id={`product-name-header-${selectedOrder.orderId}`}>Product Name</th>
                  <th id={`quantity-header-${selectedOrder.orderId}`}>Quantity</th>
                  <th id={`price-header-${selectedOrder.orderId}`}>Price</th>
                  <th id={`total-header-${selectedOrder.orderId}`}>Total</th>
                </tr>
              </thead>
              <tbody id={`order-items-body-${selectedOrder.orderId}`}>
                {selectedOrder.orderItems.map(item => (
                  <tr key={`order-item-row-${item.orderId}`} id={`order-item-row-${item.orderId}`}>
                    <td id={`product-name-${item.orderId}`}>{item.product.name}</td>
                    <td id={`product-quantity-${item.orderId}`}>{item.quantity}</td>
                    <td id={`product-price-${item.orderId}`}>{item.product.price.toFixed(2)}</td>
                    <td id={`product-total-${item.orderId}`}>{(item.quantity * item.product.price).toFixed(2)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
};

export default MyOrders;
