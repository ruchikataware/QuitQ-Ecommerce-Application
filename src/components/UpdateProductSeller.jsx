import React, { useState, useEffect, useContext } from "react";
import "./UpdateCategory.css";
import SellerService from "../services/SellerService";
import AuthContext from "./context/AuthProvider";
import ApiService from "../services/ApiService";
import { useNavigate } from "react-router-dom";

const UpdateProductSeller = ({ product, onBack }) => {
  console.log("UpdateProductSeller() is called..");
  const [updatedProduct, setUpdatedProduct] = useState(product);
  const [categories, setCategories] = useState([]);
  const { auth } = useContext(AuthContext);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    console.log("UpdateProductSeller useEffect() is called..");
    ApiService.showCategories().then((response) => {
      setCategories(response.data);
    });
  }, []);

  const handleChange = (e) => {
    console.log("UpdateProductSeller handleChange() is called..");
    const { name, value } = e.target;
    setUpdatedProduct({ ...updatedProduct, [name]: value });
  };

  const handleSubmit = (e) => {
    console.log("UpdateProductSeller handleSubmit() is called..");
    e.preventDefault();
    SellerService.updateProduct(
      updatedProduct.productId,
      updatedProduct,
      auth.accessToken
    )
      .then(() => {
        console.log("Product updated.");
        alert("Product updated successfully!");
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error updating product:", error);
        setError("Failed to update product. Ensure all fields are valid.");
      });
  };

  return (
    <div className="add-product-container">
      <h2>Update Product</h2>
      {error && <p className="error-message">{error}</p>}

      <form className="add-product-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Product Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={updatedProduct.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="brandName">Brand Name:</label>
          <input
            type="text"
            id="brandName"
            name="brandName"
            value={updatedProduct.brandName}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="description">Description:</label>
          <textarea
            id="description"
            name="description"
            value={updatedProduct.description}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="price">Price:</label>
          <input
            type="number"
            id="price"
            name="price"
            step="0.01"
            min="0"
            value={updatedProduct.price}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="stockQuantity">Stock Quantity:</label>
          <input
            type="number"
            id="stockQuantity"
            name="stockQuantity"
            min="0"
            value={updatedProduct.stockQuantity}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="category">Category:</label>
          <select
            id="category"
            name="category"
            value={updatedProduct.category || ""}
            onChange={handleChange}
            required
          >
            <option value="" disabled>
              Select a category
            </option>
            {categories.map((category) => (
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="image">Image URL:</label>
          <input
            type="url"
            id="image"
            name="image"
            value={updatedProduct.image}
            onChange={handleChange}
            required
          />
        </div>

        <div className="button-group">
          <button type="submit" className="submit-btn">
            Update Product
          </button>
          <button
            style={{ marginTop: "15px", width: "100%" }}
            type="button"
            className="back-btn"
            onClick={onBack}
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default UpdateProductSeller;
