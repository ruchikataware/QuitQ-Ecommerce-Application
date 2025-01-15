import React, { useState, useEffect, useContext } from "react";
import CategoryCard from "./CategoryCard";
import UpdateCategory from "./UpdateCategory";
import AdminService from "../services/AdminService";
import AuthContext from "./context/AuthProvider";
import ApiService from "../services/ApiService";

const ViewCategories = () => {
  console.log("ViewCategories() is called..");
  const [categories, setCategories] = useState([]);
  const [activeComponent, setActiveComponent] = useState("view");
  const [selectedCategory, setSelectedCategory] = useState(null);
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    console.log("ViewCategories useEffect() is called..");
    ApiService.showCategories().then((response) => {
      setCategories(response.data);
    });
  }, []);

  const handleDelete = (categoryId) => {
    console.log("ViewCategories handleDelete() is called..");
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this category?"
    );
    if (confirmDelete) {
      AdminService.deleteCategory(categoryId, auth.accessToken)
        .then((response) => {
          console.log('Response from delete-category api received...', response.data);
          setCategories(
            categories.filter((category) => category.categoryId !== categoryId)
          );
          alert("Category deleted successfully!");
        })
        .catch((error) => {
          console.error("Error deleting category:", error);
          alert("Failed to delete the category. Please try again.");
        });
    }
  };


  const handleUpdate = (category) => {
    setSelectedCategory(category);  
    setActiveComponent("update");  
  };

  return activeComponent === "view" ? (
    <div className="row">
      {categories.map((category) => (
        <CategoryCard
          key={category.categoryId}
          category={category}
          onDelete={handleDelete}
          onUpdate={handleUpdate}
        />
      ))}
    </div>
  ) : (
    <UpdateCategory
      category={selectedCategory}
      onBack={() => setActiveComponent("view")}
    />
  );
};

export default ViewCategories;
