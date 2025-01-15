import React, { useState, useEffect, useContext } from "react";
import './ViewSellers.css';
import AdminService from "../services/AdminService";  
import AuthContext from "./context/AuthProvider";

const ViewSellers = () => {
  console.log("ViewSellers() is called..");
  const [sellers, setSellers] = useState([]);
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    console.log("ViewSellers useEffect() is called..");
    AdminService.getSellers(auth.accessToken).then((response) => {
      setSellers(response.data);
    }).catch((error) => {
      console.error("Error fetching sellers:", error);
    });
  }, []);

  const handleDelete = (sellerId) => {
    console.log("ViewSellers handleDelete() is called..");
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this seller?"
    );
    if (confirmDelete) {
      AdminService.deleteSeller(sellerId, auth.accessToken)
        .then(() => {
          setSellers(sellers.filter((seller) => seller.sellerId !== sellerId));
          alert("Seller deleted successfully!");
        })
        .catch((error) => {
          console.error("Error deleting seller:", error);
          alert("Failed to delete the seller. Please try again.");
        });
    }
  };

  return (
    <div className="view-sellers-container" id="view-sellers-container">
      <h2 id="sellers-heading">Seller List</h2>
      <table className="table table-striped" id="sellers-table">
        <thead>
          <tr id="sellers-table-header">
            <th id="column-username">Username</th>
            <th id="column-storeName">Store Name</th>
            <th id="column-businessAddress">Address</th>
            <th id="column-registrationNumber">BRN</th>
            <th id="column-contactPerson">Contact Person</th>
            <th id="column-contactPhone">Phone</th>
            <th id="column-actions">Actions</th>
          </tr>
        </thead>
        <tbody>
          {sellers.map((seller) => (
            <tr key={seller.sellerId} id={`seller-row-${seller.sellerId}`}>
              <td id={`username-${seller.sellerId}`}>{seller.username}</td>
              <td id={`storeName-${seller.sellerId}`}>{seller.storeName}</td>
              <td id={`businessAddress-${seller.sellerId}`}>{seller.businessAddress}</td>
              <td id={`registrationNumber-${seller.sellerId}`}>{seller.businessRegistrationNumber}</td>
              <td id={`contactPerson-${seller.sellerId}`}>{seller.contactPersonName}</td>
              <td id={`contactPhone-${seller.sellerId}`}>{seller.contactPersonPhone}</td>
              <td id={`actions-${seller.sellerId}`}>
                <button
                  className="btn btn-danger"
                  id={`delete-button-${seller.sellerId}`}
                  onClick={() => handleDelete(seller.sellerId)}
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

export default ViewSellers;
