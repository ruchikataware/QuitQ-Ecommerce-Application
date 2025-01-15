import React, { useState, useEffect, useContext } from "react";
import "./ViewProducts.css";
import AuthContext from "./context/AuthProvider";
import SellerService from "../services/SellerService";
import UpdateProductSeller from "./UpdateProductSeller";

const ViewProductsByCategorySeller = ({ categoryId, onBack }) => {
  console.log("ViewProductsByCategorySeller() is called..");
  const [products, setProducts] = useState([]);
  const { auth } = useContext(AuthContext);
  const [activeComponent, setActiveComponent] = useState("view");
  const [selectedProduct, setSelectedProduct] = useState(null);

  useEffect(() => {
    console.log("ViewProductsByCategorySeller useEffect() is called..");
    SellerService.getProductsByCategory(auth.id, categoryId, auth.accessToken)
      .then((response) => {
        setProducts(response.data);
        console.log("Response received from getProductsByCategory():",response.data);
      })
      .catch((error) => {
        console.error("Error fetching products by category:", error);
      });
  }, [auth.id, categoryId, auth.accessToken]);

  const handleDelete = (productId) => {
    console.log("ViewProductsByCategorySeller handleDelete() is called..");
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this product?"
    );
    if (confirmDelete) {
      SellerService.deleteProduct(productId, auth.accessToken)
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

  const handleUpdate = (product) => {
    console.log("ViewProductsByCategorySeller handleUpdate() is called..");
    product.seller = {};
    product.seller.sellerId = auth.id;
    setSelectedProduct(product); 
    setActiveComponent("update"); 
  };

  return activeComponent === "view" ? (
    <div className="view-products-container">
      <h2>Product List</h2>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Product Name</th>
            <th>Brand Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Stock Quantity</th>
            <th>Update</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => (
            <tr key={product.productId}>
              <td>{product.name}</td>
              <td>{product.brandName}</td>
              <td>{product.description}</td>
              <td>{product.price.toFixed(2)}</td>
              <td>{product.stockQuantity}</td>
              <td>
                <button
                  style={{width: "100px", marginTop: "0px"}}
                  className="btn btn-primary"
                  onClick={() => handleUpdate(product)}
                >
                  Update
                </button>
              </td>
              <td>
                <button
                  className="btn btn-danger"
                  onClick={() => handleDelete(product.productId)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button style={{width: "50%"}} type="button" className="back-btn" onClick={onBack}>
        Back to Categories
      </button>

    </div>
  ) : (
    <UpdateProductSeller
      product={selectedProduct}
      onBack={() => setActiveComponent("view")}
    />
  );
};


export default ViewProductsByCategorySeller;
