import React, { useState, useEffect, useContext } from "react";
import ApiService from "../services/ApiService";
import AuthContext from "./context/AuthProvider";
import "./MyOrderItems.css";
import SellerService from "../services/SellerService";

const MyOrderItems = () => {
  console.log("MyOrderItems() is called..");
  const [orderItems, setOrderItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    console.log("MyOrderItems useEffect() is called..");
    SellerService.getOrderItems(auth.id, auth.accessToken)
      .then((response) => {
        console.log("Response received from getOrderItems():",response.data);
        setOrderItems(response.data || []);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error from getOrderItems():",err);
        setError("Failed to load order items.");
        setLoading(false);
      });
  }, []);

  if (loading) return <div id="loading-message">Loading...</div>;
  if (error) return <div id="error-message">{error}</div>;

  return (
    <div id="order-items-container" className="order-items-container">
      <h2 id="order-items-heading">My Order Items</h2>
      <table id="order-items-table" className="order-items-table">
        <thead id="order-items-table-header">
          <tr>
            <th id="item-id-header">Order Item ID</th>
            <th id="order-id-header">Order ID</th>
            <th id="product-id-header">Product ID</th>
            <th id="product-name-header">Product Name</th>
            <th id="quantity-header">Quantity</th>
            <th id="price-header">Price</th>
            <th id="total-header">Total</th>
          </tr>
        </thead>
        <tbody id="order-items-table-body">
          {orderItems.map((item) => (
            <tr key={`order-item-row-${item.orderItemId}`} id={`order-item-row-${item.orderItemId}`}>
              <td id={`item-id-${item.orderItemId}`}>{item.orderItemId}</td>
              <td id={`order-id-${item.orderItemId}`}>{item.orderId}</td>
              <td id={`product-id-${item.orderItemId}`}>{item.productId}</td>
              <td id={`product-name-${item.orderItemId}`}>{item.productName}</td>
              <td id={`quantity-${item.orderItemId}`}>{item.quantity}</td>
              <td id={`price-${item.orderItemId}`}>{item.price.toFixed(2)}</td>
              <td id={`total-${item.orderItemId}`}>{(item.quantity * item.price).toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default MyOrderItems;
