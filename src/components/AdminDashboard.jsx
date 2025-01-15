import React, { useState } from "react";
import './AdminDashboard.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import AdminHeader from "./AdminHeader";
import AddCategory from "./AddCategory";
import ViewCategories from "./ViewCategories";
import { useNavigate } from "react-router-dom";
import ViewCustomers from "./ViewCustomers";
import ViewSellers from "./ViewSellers";
import ViewProducts from "./ViewProducts";
import AdminMyOrdersPage from "../pages/AdminMyOrdersPage";
import MyOrdersPage from "../pages/MyOrdersPage";
import MyOrders from "./MyOrders";
import AdminMyOrders from "./AdminMyOrders";

const AdminDashboard = () => {
  console.log("AdminDashboard() is called..")
  const [activeComponent, setActiveComponent] = useState("home");

  const renderContent = () => {
    switch (activeComponent) {
      case "add-category":
        return <AddCategory />;
      case "view-customers":
        return <ViewCustomers />;
      case "view-sellers":
        return <ViewSellers />;
      case "view-products":
        return <ViewProducts />;
      case "order-details":
        return <AdminMyOrders />
      default:
        return <ViewCategories />;
    }
  };

  return (
    <div className="admin-dashboard">
      <div className="admin-dashboard-header">
        <AdminHeader />
      </div>
      <div className="row g-0">
        <div className="col-md-3 bg-dark sidebar">
          <nav id="admin-dashboard-nav" className="nav flex-column text-light">
            <h3 id="sidebar-title" className="text-center py-3">Admin Dashboard</h3>

            <button
              id="view-categories"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("view-categories")}
            >
              View Categories
            </button>

            <button
              id="view-sellers"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("view-sellers")}
            >
              View Sellers
            </button>

            <button
              id="view-customers"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("view-customers")}
            >
              View Customers
            </button>

            <button
              id="view-products"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("view-products")}
            >
              View Products
            </button>

            <button
              id="add-category"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("add-category")}
            >
              Add Category
            </button>
            <button
              id="order-details"
              className="nav-link text-light btn btn-link"
              onClick={() => setActiveComponent("order-details")}
            >
              Order Details
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

export default AdminDashboard;
