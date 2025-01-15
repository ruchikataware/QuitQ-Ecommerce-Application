import React, { useState, useEffect } from "react";
import ApiService from "../services/ApiService";
import ViewCategoryCard from "./ViewCategoryCard";
import ViewProductsByCategorySeller from "./ViewProductsByCategorySeller";

const ViewCategoriesSeller = () => {
  console.log("ViewCategoriesSeller() is called..");
  const [categories, setCategories] = useState([]);
  const [activeComponent, setActiveComponent] = useState("view");
  const [selectedCategoryId, setSelectedCategoryId] = useState(null);
  useEffect(() => {
    ApiService.showCategories().then((response) => {
      setCategories(response.data);
    });
  }, []);

  const handleViewProducts = (categoryId) => {
    console.log("ViewCategoriesSeller handleViewProducts() is called..");
    setSelectedCategoryId(categoryId);
    setActiveComponent("");
  };

  return activeComponent === "view" ? (
    <div className="row">
      {categories.map((category) => (
        <ViewCategoryCard
          key={category.categoryId}
          category={category}
          onViewProducts={handleViewProducts}
        />
      ))}
    </div>
  ) : (
    <ViewProductsByCategorySeller
      categoryId={selectedCategoryId}
      onBack={() => setActiveComponent("view")}
    />
  );
};

export default ViewCategoriesSeller;
