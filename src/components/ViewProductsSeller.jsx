import React, { useState, useEffect, useContext } from "react";
import "./ViewProducts.css";
import AuthContext from "./context/AuthProvider";
import SellerService from "../services/SellerService";
import UpdateProductSeller from "./UpdateProductSeller";

const ViewProductsSeller = () => {
  console.log("viewProductseller() is called..");
  const [products, setProducts] = useState([]);
  const [activeComponent, setActiveComponent] = useState("view");
  const [selectedProduct, setSelectedProduct] = useState(null);
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    console.log("viewProductseller useEffect() is called..");
    SellerService.getProducts(auth.id, auth.accessToken)
      .then((response) => {
        setProducts(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
      });
  }, [auth.id, auth.accessToken]);

  const handleDelete = (productId) => {
    console.log("viewProductseller handleDelete() is called..");
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
    console.log("viewProductseller handleUpdate() is called..");
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
                  style={{ width: "100px", marginTop: "0px" }}
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
    </div>
  ) : (
    <UpdateProductSeller
      product={selectedProduct}
      onBack={() => setActiveComponent("view")}
    />
  );
};

export default ViewProductsSeller;
