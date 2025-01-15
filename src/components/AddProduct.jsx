import React, { useContext, useState, useEffect } from "react";
import "./AddProduct.css";
import AuthContext from "./context/AuthProvider";
import ApiService from "../services/ApiService";
import SellerService from "../services/SellerService";
import { useNavigate } from "react-router-dom";

const AddProduct = () => {
  console.log("AddProduct() is called..")
  const { auth } = useContext(AuthContext);

  const [product, setProduct] = useState({
    name: "",
    brandName: "",
    description: "",
    price: "",
    stockQuantity: "",
    image: "",
    category: { categoryId: "" },
    seller: { sellerId: "" }
  });

  const [categories, setCategories] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  useEffect(() => {
    console.log("AddProduct useEffect() is called..")
    ApiService.showCategories(auth.accessToken)
      .then((response) => {
        console.log(response.data);
        setCategories(response.data);
        navigate('/seller-controls');
      })
      .catch((error) => {
        console.error("Error fetching categories:", error);
        setError("Failed to load categories.");
      });
  }, [auth.accessToken]);

  const handleChange = (e) => {
    console.log("AddProduct handleChange() is called...");
    const { name, value } = e.target;
    setProduct({ ...product, [name]: value });
  };

  const handleSubmit = (e) => {
    console.log("AddProduct handleSubmit() is called..")
    e.preventDefault();
    product.seller.sellerId = auth.id;
    SellerService.addProduct(product, auth.accessToken)
      .then((response) => {
        alert("Product added successfully!", response.data);
        setProduct({
          name: "",
          brandName: "",
          description: "",
          price: "",
          stockQuantity: "",
          image: "",
          category: { categoryId: "" },
          seller: { sellerId: "" }
        });
      })
      .catch((error) => {
        console.error("Error adding product:", error);
        setError("Failed to add product. Ensure all fields are valid.");
      });
  };

  return (
    <div className="add-product-container">
      <h2>Add New Product</h2>
      {error && <p className="error-message">{error}</p>}

      <form className="add-product-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Product Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={product.name}
            onChange={handleChange}
            placeholder="Enter product name"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="brandName">Brand Name:</label>
          <input
            type="text"
            id="brandName"
            name="brandName"
            value={product.brandName}
            onChange={handleChange}
            placeholder="Enter brand name"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="description">Description:</label>
          <textarea
            id="description"
            name="description"
            value={product.description}
            onChange={handleChange}
            placeholder="Enter product description"
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
            value={product.price}
            onChange={handleChange}
            placeholder="Enter product price"
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
            value={product.stockQuantity}
            onChange={handleChange}
            placeholder="Enter stock quantity"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="image">Image URL:</label>
          <input
            type="url"
            id="image"
            name="image"
            pattern="^(http|https)://.*\.(jpg|jpeg|png|gif|bmp)$"
            title="Enter a valid image URL ending with jpg, jpeg, png, gif, or bmp"
            value={product.image}
            onChange={handleChange}
            placeholder="Enter image URL"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="categoryId">Category:</label>
          <select
            id="categoryId"
            name="categoryId"
            value={product.category.categoryId}
            onChange={(e) =>
              setProduct({
                ...product,
                category: { ...product.category, categoryId: e.target.value },
              })
            }
            required
          >
            <option value="" disabled>
              Select a category
            </option>
            {categories.map((category) => (
              <option key={category.categoryId} value={category.categoryId}>
                {category.name}
              </option>
            ))}
          </select>
        </div>


        <button type="submit" className="submit-btn">Add Product</button>
      </form>
    </div>
  );
};

export default AddProduct;
