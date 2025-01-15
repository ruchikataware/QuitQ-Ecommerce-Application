import React, { useState, useEffect, useContext } from "react";
import './ViewProducts.css';
import AdminService from "../services/AdminService";  
import AuthContext from "./context/AuthProvider";

const ViewProducts = () => {
  console.log("viewProducts() is called..");
  const [products, setProducts] = useState([]);
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    console.log("viewProducts useEffect() is called..");
    AdminService.getProducts(auth.accessToken).then((response) => {
      setProducts(response.data);
    }).catch((error) => {
      console.error("Error fetching products:", error);
    });
  }, []);

  const handleDelete = (productId) => {
    console.log("viewProducts handleDelete() is called..");
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this product?"
    );
    if (confirmDelete) {
      AdminService.deleteProduct(productId, auth.accessToken)
        .then(() => {
          setProducts(products.filter((product) => product.productId !== productId));
          alert("Product deleted successfully!");
        })
        .catch((error) => {
          console.error("Error deleting product:", error);
          alert("Failed to delete the product. Please try again.");
        });
    }
  };

  return (
    <div className="view-products-container" id="view-products-container">
      <h2 id="products-heading">Product List</h2>
      <table className="table table-striped" id="products-table">
        <thead>
          <tr id="products-table-header">
            <th id="column-name">Product Name</th>
            <th id="column-brand">Brand Name</th>
            <th id="column-description">Description</th>
            <th id="column-price">Price</th>
            <th id="column-stock">Stock Quantity</th>
            <th id="column-actions">Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => (
            <tr key={product.productId} id={`product-row-${product.productId}`}>
              <td id={`name-${product.productId}`}>{product.name}</td>
              <td id={`brand-${product.productId}`}>{product.brandName}</td>
              <td id={`description-${product.productId}`}>{product.description}</td>
              <td id={`price-${product.productId}`}>{product.price.toFixed(2)}</td>
              <td id={`stock-${product.productId}`}>{product.stockQuantity}</td>
              <td id={`actions-${product.productId}`}>
                <button
                  className="btn btn-danger"
                  id={`delete-button-${product.productId}`}
                  onClick={() => handleDelete(product.productId)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ViewProducts;
