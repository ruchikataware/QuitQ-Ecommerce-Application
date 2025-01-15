import React, { useState, useEffect, useContext } from "react";
import './ViewCustomers.css';
import AdminService from "../services/AdminService"; 
import AuthContext from "./context/AuthProvider";

const ViewCustomers = () => {
  console.log("ViewCustomers() is called..");
  const [customers, setCustomers] = useState([]);
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    console.log("ViewCustomers useEffect() is called..");
    AdminService.getCustomers(auth.accessToken).then((response) => {
      console.log("Response received from getCustomers()..");
      setCustomers(response.data);
    }).catch((error) => {
      console.error("Error fetching customers:", error);
    });
  }, []);

  const handleDelete = (customerId) => {
    console.log("ViewCustomers handleDelete() is called..");
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this customer?"
    );
    if (confirmDelete) {
      AdminService.deleteCustomer(customerId, auth.accessToken)
        .then(() => {
          console.log("Customer deleted..");
          setCustomers(customers.filter((customer) => customer.customerId !== customerId));
          alert("Customer deleted successfully!");
        })
        .catch((error) => {
          console.error("Error deleting customer:", error);
          alert("Failed to delete the customer. Please try again.");
        });
    }
  };

  return (
    <div className="view-customers-container" id="view-customers-container">
      <h2 id="customers-heading">Customer List</h2>
      <table className="table table-striped" id="customers-table">
        <thead>
          <tr id="customers-table-header">
            <th id="column-username">Username</th>
            <th id="column-firstname">First Name</th>
            <th id="column-lastname">Last Name</th>
            <th id="column-address">Address</th>
            <th id="column-phone">Phone Number</th>
            <th id="column-actions">Actions</th>
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.customerId} id={`customer-row-${customer.customerId}`}>
              <td id={`username-${customer.customerId}`}>{customer.username}</td>
              <td id={`firstname-${customer.customerId}`}>{customer.firstName}</td>
              <td id={`lastname-${customer.customerId}`}>{customer.lastName}</td>
              <td id={`address-${customer.customerId}`}>{customer.address}</td>
              <td id={`phone-${customer.customerId}`}>{customer.phoneNumber}</td>
              <td id={`actions-${customer.customerId}`}>
                <button
                  className="btn btn-danger"
                  id={`delete-button-${customer.customerId}`}
                  onClick={() => handleDelete(customer.customerId)}
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

export default ViewCustomers;
