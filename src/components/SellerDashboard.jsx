import React, { useState } from "react";
import './SellerDashboard.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import SellerHeader from "./SellerHeader";
import AddProduct from "./AddProduct";
import ViewCategoriesSeller from "./ViewCategoriesSeller";
import ViewProductsSeller from "./ViewProductsSeller";
import SellerMyOrders from "./AdminMyOrders";
import MyOrderItems from "./MyOrderItems";

const SellerDashboard = () => {
  console.log("SellerDashboard() is called..");
  const [activeComponent, setActiveComponent] = useState("view-categories");

  const renderContent = () => {
    console.log("renderContent() is called..");
    switch (activeComponent) {
      case "view-categories":
        return <ViewCategoriesSeller />;
      case "add-products":
        return <AddProduct />;
      case "view-products":
        return <ViewProductsSeller />;
      case "view-orders":
        return <MyOrderItems />;
      default:
        return <ViewCategoriesSeller />;
    }
  };

  return (
    <div className="seller-dashboard">
      <div className="seller-dashboard-header">
        <SellerHeader />
      </div>
      <div className="row g-0">
        <div className="col-md-3 bg-dark sidebar">
          <nav id="seller-dashboard-nav" className="nav flex-column text-light">
            <h3 id="sidebar-title" className="text-center py-3">Seller Dashboard</h3>

            <button
              id="view-categories"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("view-categories")}
            >
              View Categories
            </button>

            <button
              id="add-products"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("add-products")}
            >
              Add Products
            </button>

            <button
              id="view-products"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("view-products")}
            >
              View Products
            </button>

            <button
              id="view-orders"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("view-orders")}
            >
              View Orders
            </button>
          </nav>
        </div>

        <div className="col-md-9 main-content-area">
          {renderContent()}
        </div>
      </div>
    </div>
  );
};

export default SellerDashboard;
