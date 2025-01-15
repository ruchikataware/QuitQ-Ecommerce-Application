import React, { useState, useEffect } from "react";
import './ViewProductsByCategory.css';
import ApiService from "../services/ApiService";
import { useSearch } from "./context/SearchContext";
import { useNavigate } from "react-router-dom"; // Import useNavigate

const ViewSearchedProducts = () => {
  console.log("ViewSearchedProducts() is called..");
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const { searchQuery, setSearchQuery } = useSearch();
  const navigate = useNavigate(); // Initialize useNavigate

  useEffect(() => {
    console.log("ViewSearchedProducts useEffect() is called..");
    ApiService.getAllProducts()
      .then((response) => {
        console.log("Response received from getAllProducts()..");
        setProducts(response.data);
        setFilteredProducts(response.data);
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
      });
  }, [setSearchQuery]);

  useEffect(() => {
    console.log("ViewSearchedProducts useEffect() is called..");
    if (searchQuery) {
      filterProducts(products, searchQuery);
    } else {
      setFilteredProducts(products);
    }
  }, [searchQuery, products]);

  const filterProducts = (allProducts, query) => {
    console.log("ViewSearchedProducts filterProducts() is called..");
    const filtered = allProducts.filter((product) =>
      product.name.toLowerCase().includes(query.toLowerCase())
    );
    setFilteredProducts(filtered);
  };

  const handleViewDetails = (product) => {
    navigate(`/view-product/${product.productId}`, { state: { product } }); 
  };

  return (
    <div className="products-by-category-container">
      <div className="products-grid">
        {filteredProducts.map((product) => (
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
              <button
                className="btn btn-primary"
                onClick={() => handleViewDetails(product)} 
                disabled={product.stockQuantity === 0}
              >
                View Details
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ViewSearchedProducts;
