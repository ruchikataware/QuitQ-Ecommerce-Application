import React, { useState, useEffect } from "react";
import './ViewProductsByCategory.css';
import ApiService from "../services/ApiService";
import { useNavigate } from "react-router-dom";

const ViewAllProducts = () => {
  console.log("ViewAllProducts() is called..");
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    console.log("ViewAllProducts useEffect() is called..");
    ApiService.getAllProducts()
      .then((response) => {
        console.log("getAllProducts() is called..");
        setProducts(response.data);
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
      });
  }, []);

  const handleViewDetails = (product) => {
    console.log("handleViewDetails() is called..");
    navigate(`/view-product/${product.productId}`, { state: { product } });
  };

  return (
    <div className="products-by-category-container">
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
              <button className="btn btn-primary" disabled={product.stockQuantity === 0}
                onClick={() => handleViewDetails(product)}>
                View Details
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ViewAllProducts;
