import React, { useState, useEffect } from "react";
import './ViewProductsByCategory.css';
import ApiService from "../services/ApiService";
import { useNavigate } from "react-router-dom";

const ViewProductsByCategory = ({ categoryId, onBack }) => {
  console.log("ViewProductsByCategory() is called..");
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    console.log("ViewProductsByCategory useEffect() is called..");
    ApiService.getProductsByCategory(categoryId).then((response) => {
      console.log("Response received from getProductsByCategory()..");
      setProducts(response.data);
      console.log(response.data);
    }).catch((error) => {
      console.error("Error fetching products:", error);
    });
  }, [categoryId]);

  const handleViewDetails = (product) => {
    navigate(`/view-product/${product.productId}`, { state: { product } }); 
  };

  return (
    <div className="products-by-category-container">
      <button className="btn btn-secondary" onClick={onBack}>
        Back to Categories
      </button>
      <h2>Products</h2>
      <div className="products-grid">
        {products.map((product) => (
          <div className="product-card" key={product.productId}>
            <div className="product-image">
              <img src={product.image || "placeholder-image.jpg"} alt={product.name} />
              {product.stockQuantity === 0 && (
                <span className="out-of-stock-tag">Out of Stock</span>
              )}
            </div>
            <div className="product-details">
              <h3>{product.name}</h3>
              <p>{product.description}</p>
              <p className="product-price">{product.price.toFixed(2)}</p>
              <button onClick={() => handleViewDetails(product)} className="btn btn-primary" disabled={product.stockQuantity === 0}>
                View Details
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ViewProductsByCategory;
