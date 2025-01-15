import React, { useContext, useState } from "react";
import "./UpdateCategory.css";
import AdminService from "../services/AdminService";
import AuthContext from "./context/AuthProvider";

const UpdateCategory = ({ category, onBack }) => {
  console.log("UpdateCategory() is called..");
  const [updatedCategory, setUpdatedCategory] = useState(category);
  const { auth } = useContext(AuthContext);
  const [error, setError] = useState("");

  const handleChange = (e) => {
    console.log("handleChange() is called..");
    const { name, value } = e.target;
    setUpdatedCategory({ ...updatedCategory, [name]: value });
  };

  const handleSubmit = (e) => {
    console.log("handleSubmit() is called..");
    e.preventDefault();
    AdminService.updateCategory(updatedCategory.categoryId, updatedCategory, auth.accessToken)
      .then(() => {
        console.log("Response received from updateCategory:",Response.data);
        alert("Category updated successfully!");
        window.location.reload();
      })
      .catch((error) => {
        console.error(error);
        setError("Failed to update category. Ensure fields are valid.");
      });
  };

  return (
    <div className="update-category-container">
      <h2>Update Category</h2>
      {error && <p className="error-message">{error}</p>}

      <form className="update-category-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Category Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={updatedCategory.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="description">Description:</label>
          <textarea
            id="description"
            name="description"
            value={updatedCategory.description}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="image">Image URL:</label>
          <input
            type="url"
            id="image"
            name="image"
            value={updatedCategory.image}
            onChange={handleChange}
            required
          />
        </div>

        <div className="update-category-btns">
          <button type="submit" className="submit-btn">Update Category</button>
          <button type="button" className="back-btn" onClick={onBack}>Back</button>
        </div>
      </form>
    </div>
  );
};

export default UpdateCategory;
