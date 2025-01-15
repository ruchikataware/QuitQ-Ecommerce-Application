import React, { useContext, useState } from "react";
import "./AddCategory.css";
import AuthContext from "./context/AuthProvider";
import AdminService from "../services/AdminService";
import { useNavigate } from "react-router-dom";

const AddCategory = () => {
  const { auth } = useContext(AuthContext);
  const navigate = useNavigate();

  const [category, setCategory] = useState({
    name: "",
    description: "",
    image: "",
  });

  const [error, setError] = useState("");

  const handleChange = (e) => {
    console.log("HandleChange is called");
    const { name, value } = e.target;
    setCategory({ ...category, [name]: value });
  };

  const handleSubmit = (e) => {
    console.log("handleSubmit() is called..")
    e.preventDefault(); 
    AdminService.addCategory(category, auth.accessToken).then(response => {
      alert("Category added successfully!", response.data);
      console.log(auth);
      setCategory({ name: "", description: "", image: "" });
      window.location.reload();
    }).catch(error => {
      console.log(error);
      setError("Failed to add category. Ensure fields are valid.");
      console.log(auth);
    })
  };

  return (
    <div className="add-category-container">
      <h2>Add New Category</h2>
      {error && <p className="error-message">{error}</p>}

      <form className="add-category-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Category Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={category.name}
            onChange={handleChange}
            placeholder="Enter category name"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="description">Description:</label>
          <textarea
            id="description"
            name="description"
            value={category.description}
            onChange={handleChange}
            placeholder="Enter category description"
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
            value={category.image}
            onChange={handleChange}
            placeholder="Enter image URL"
            required
          />
        </div>

        <button type="submit" className="submit-btn">Add Category</button>
      </form>
    </div>
  );
};

export default AddCategory;
